package com.framework.bean;

import java.io.Serializable;

public interface BaseEntity<T extends BaseEntity<? extends T, K>, K extends Serializable> extends Serializable {

    public K getId();

    public void setId(K id);

    @Override
    public String toString();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object object);
}
