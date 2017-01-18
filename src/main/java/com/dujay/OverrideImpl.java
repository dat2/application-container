package com.dujay;

import java.lang.annotation.Annotation;

public class OverrideImpl implements Override {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Override.class;
    }

}
