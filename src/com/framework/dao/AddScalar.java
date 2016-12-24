package com.framework.dao;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * 将sql查询结果封装到对象中
 */
public class AddScalar {

    /**
     * 如果不是基本类型 则添加字段映射
     *
     * @param query
     * @param clazz
     */
        public static void addScalar(NativeQuery query, Class clazz) {
        if (clazz != null && !AddScalar.isBaseType(clazz)) {
            if (isEntity(clazz)) {
                query.addEntity(clazz);
                return;
            }
            AddScalar.addSclar2(query, clazz);
            query.setResultTransformer(Transformers.aliasToBean(clazz));
        }
    }


    public static boolean isBaseType(Class clazz) {
        if (String.class == clazz ||
                Long.class == clazz ||
                Integer.class == clazz ||
                Character.class == clazz ||
                Short.class == clazz ||
                Double.class == clazz ||
                Float.class == clazz ||
                Boolean.class == clazz ||
                Date.class == clazz) {
            return true;
        }
        return false;
    }

    public static boolean isEntity(Class clazz) {
        return clazz.getDeclaredAnnotation(Entity.class) != null;
    }

    /**
     * 将field type 和 Hibernate的类型进行了对应。这里其实不是多余的，如果不进行一定的对应可能会有问题。
     * 问题有两个：
     * 1. 在oracle中我们可能把一些字段设为NUMBER(%)，而在Bean中的字段定的是long。那么查询时可能会报：
     * java.math.BeigDecimal不能转换成long等错误
     * 2. 如果不这样写的话，可能Bean中的field就得是大写的，如：name就得写成NAME,userCount就得写成USERCOUNT
     * 这样是不是很扯(V_V)
     *
     * @param sqlQuery SQLQuery
     * @param clazz    T.class
     */
    private static void addSclar2(NativeQuery sqlQuery, Class clazz) {
        if (clazz == null) {
            throw new NullPointerException("[clazz] could not be null!");
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) continue;
            if ((field.getType() == long.class) || (field.getType() == Long.class)) {
                sqlQuery.addScalar(field.getName(), LongType.INSTANCE);
            } else if ((field.getType() == int.class) || (field.getType() == Integer.class)) {
                sqlQuery.addScalar(field.getName(), IntegerType.INSTANCE);
            } else if ((field.getType() == char.class) || (field.getType() == Character.class)) {
                sqlQuery.addScalar(field.getName(), CharArrayType.INSTANCE);
            } else if ((field.getType() == short.class) || (field.getType() == Short.class)) {
                sqlQuery.addScalar(field.getName(), ShortType.INSTANCE);
            } else if ((field.getType() == double.class) || (field.getType() == Double.class)) {
                sqlQuery.addScalar(field.getName(), DoubleType.INSTANCE);
            } else if ((field.getType() == float.class) || (field.getType() == Float.class)) {
                sqlQuery.addScalar(field.getName(), FloatType.INSTANCE);
            } else if ((field.getType() == boolean.class) || (field.getType() == Boolean.class)) {
                sqlQuery.addScalar(field.getName(), BooleanType.INSTANCE);
            } else if (field.getType() == String.class) {
                sqlQuery.addScalar(field.getName(), StringType.INSTANCE);
            } else if (field.getType() == Date.class) {
                sqlQuery.addScalar(field.getName(), TimestampType.INSTANCE);
            }
        }
    }
}