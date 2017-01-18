package com.dujay;

import java.lang.annotation.Annotation;

import com.google.inject.Provides;

public class ProvidesImpl implements Provides {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Provides.class;
    }

}
