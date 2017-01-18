package com.dujay;

import java.lang.annotation.Annotation;

import org.immutables.value.Value;

import com.google.inject.name.Named;

@Value.Immutable
@Value.Style(allParameters = true)
public interface NamedImpl extends Named {

    @Override
    @Value.Derived
    public default Class<? extends Annotation> annotationType() {
        return Named.class;
    }

    @Override
    public String value();

}
