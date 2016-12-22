package com.framework.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Generics {

    public static Class<?> getSuperClassGenericType(Class<?> clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

    public static Class<?> getSuperClassGenericType(Class<?> clazz, int index) {
        while (!clazz.equals(Object.class)) {
            Type type = clazz.getGenericSuperclass();
            clazz = clazz.getSuperclass();
            if (!(type instanceof ParameterizedType))
                continue;

            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types.length < index || !(types[index] instanceof Class))
                continue;
            return (Class<?>) types[index];
        }
        return Object.class;
    }

    public static <T> Class<?> getSuperClassGenericType(Class<? extends T> source, Class<T> target) {
        return getSuperClassGenericType(source, target, 0);
    }

    public static <T> Class<?> getSuperClassGenericType(Class<? extends T> source, Class<T> target, int index) {
        if (!target.isAssignableFrom(source) || source.equals(target)) {
            throw new IllegalArgumentException("target class must a super class to source.");
        }
        Class<?> clazz = source;
        while (!(clazz.getSuperclass().equals(target))) {
            clazz = clazz.getSuperclass();
        }
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("Does " + target.getName() + " not parameterized?");
        }
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        if (types.length < index) {
            throw new IndexOutOfBoundsException("Does " + target.getName() + " have no many parameterized type arguments fix index:" + index + " actual "
                    + types.length);
        }
        return (Class<?>) types[index];
    }
}
