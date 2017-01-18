package com.dujay;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.dujay.test.DatabaseModule;
import com.dujay.test.TestApplication;
import com.google.common.base.CaseFormat;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodCall;

public class ApplicationContext {

    private DynamicType.Builder<RootConfig> rootConfigBuilder;
    private DynamicType.Builder<? extends ConfigModule> configModuleBuilder;
    private List<AbstractModule> guiceModules;

    public ApplicationContext() throws Exception {
        this.rootConfigBuilder = new ByteBuddy().subclass(RootConfig.class);
        this.configModuleBuilder = new ByteBuddy().subclass(ConfigModule.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
                .defineConstructor(Visibility.PUBLIC).withParameters(RootConfig.class)
                .intercept(MethodCall.invoke(ConfigModule.class.getConstructor(RootConfig.class)).withArgument(0));
        this.guiceModules = new ArrayList<>();
    }

    // TODO handle collisions
    private void addConfigField(String annotationName, Field field) throws Exception {
        this.rootConfigBuilder = this.rootConfigBuilder.defineField(field.getName(), field.getType(), Visibility.PRIVATE)
                .annotateField(ImmutableParameterImpl.builder().name(annotationName).build());

        String name = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, annotationName.replace(".", "_"));

        // getValue(field.getName(), field.getType())
        this.configModuleBuilder = this.configModuleBuilder.defineMethod("provide" + name, field.getType(), Visibility.PUBLIC)
                .intercept(MethodCall.invoke(ConfigModule.class.getMethod("get", String.class, Class.class))
                        .with(annotationName, field.getType()))
                .annotateMethod(new ProvidesImpl(), ImmutableNamedImpl.of(annotationName));
    }

    public <T extends AbstractModule> void registerModule(Class<T> moduleClass) throws Exception {
        this.guiceModules.add(moduleClass.newInstance());

        // loop through annotations
        for (Field field : moduleClass.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType() == Config.class) {
                    Config config = (Config) annotation;
                    this.addConfigField(config.name(), field);
                }
            }
        }
    }

    public <T extends Application> void run(Class<T> mainClass, String[] args) throws Exception {
        RootConfig rootConfig = this.rootConfigBuilder.make().load(getClass().getClassLoader()).getLoaded().newInstance();
        ConfigModule configModule = this.configModuleBuilder.make().load(getClass().getClassLoader()).getLoaded()
                .getDeclaredConstructor(RootConfig.class).newInstance(rootConfig);

        this.guiceModules.add(0, configModule);

        // pull out main class
        new JCommander(rootConfig, args);
        Injector injector = Guice.createInjector(this.guiceModules);
        T instance = injector.getInstance(mainClass);

        System.out.println(rootConfig);
        System.out.println(instance);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ApplicationContext();
        context.registerModule(DatabaseModule.class);
        context.run(TestApplication.class, args);
    }
}
