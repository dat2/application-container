package com.dujay;

import com.google.inject.AbstractModule;

public abstract class ConfigModule extends AbstractModule {

    private RootConfig rootConfig;

    public ConfigModule(RootConfig rootConfig) {
        this.rootConfig = rootConfig;
    }

    public RootConfig getRootConfig() {
        return rootConfig;
    }

    @Override
    public void configure() {
    }

    public <T> T get(String name, Class<T> clazz) {
        return rootConfig.getConfigValue(name, clazz).get();
    }
}
