package com.dujay;

import java.lang.annotation.Annotation;
import java.util.Optional;

import org.immutables.value.Value;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.CommaParameterSplitter;
import com.beust.jcommander.converters.IParameterSplitter;
import com.beust.jcommander.converters.NoConverter;
import com.beust.jcommander.validators.NoValidator;
import com.beust.jcommander.validators.NoValueValidator;

@Value.Immutable
public interface ParameterImpl extends Parameter {

    public String name();

    public Optional<String> shortName();

    @Override
    @Value.Derived
    public default Class<? extends Annotation> annotationType() {
        return Parameter.class;
    }

    @Override
    @Value.Default
    public default int arity() {
        return -1;
    }

    @Override
    @Value.Default
    public default Class<? extends IStringConverter<?>> converter() {
        return NoConverter.class;
    }

    @Override
    @Value.Default
    public default String description() {
        return "";
    }

    @Override
    @Value.Default
    public default String descriptionKey() {
        return "";
    }

    @Override
    @Value.Default
    public default boolean echoInput() {
        return false;
    }

    @Override
    @Value.Default
    public default boolean forceNonOverwritable() {
        return false;
    }

    @Override
    @Value.Default
    public default boolean help() {
        return false;
    }

    @Override
    @Value.Default
    public default boolean hidden() {
        return false;
    }

    @Override
    @Value.Default
    public default Class<? extends IStringConverter<?>> listConverter() {
        return NoConverter.class;
    }

    @Override
    @Value.Derived
    public default String[] names() {
        return new String[] { "--" + name() };
    }

    @Override
    @Value.Default
    public default boolean password() {
        return false;
    }

    @Override
    @Value.Default
    public default boolean required() {
        return false;
    }

    @Override
    @Value.Default
    public default Class<? extends IParameterSplitter> splitter() {
        return CommaParameterSplitter.class;
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Value.Default
    public default Class<? extends IValueValidator> validateValueWith() {
        return NoValueValidator.class;
    }

    @Override
    @Value.Default
    public default Class<? extends IParameterValidator> validateWith() {
        return NoValidator.class;
    }

    @Override
    @Value.Default
    public default boolean variableArity() {
        return false;
    }

}
