package com.dujay;

import java.lang.reflect.Field;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.base.CaseFormat;

public class RootConfig {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public <T> Optional<T> getConfigValue(String name, Class<T> clazz) {
        String fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name.replace(".", "_"));

        try {
            Field field = getClass().getDeclaredField(fieldName);
            return Optional.of(clazz.cast(field.get(this)));
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            return Optional.empty();
        }
    }
}
