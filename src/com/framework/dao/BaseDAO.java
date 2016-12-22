package com.framework.dao;

import com.framework.bean.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<E extends BaseEntity<? extends E, K>, K extends Serializable> {

    public E get(K id);

    public K save(E entity);

    public void update(E entity);

    public void saveOrUpdate(E entity);

    public void delete(E entity);

    public List<E> list();

    public int count();

    public Pager<E> page(Pager<E> pager);


    public List<?> hql(String hql, Object... args);

    public <T> List<T> hql(Class<T> expectType, String hql, Object... args);

    public List<?> sql(final String sql, final Object... args);

    public <T> List<T> sql(Class<T> expectType, final String sql, final Object... args);

    public Object uniqueResultHql(String hql, Object... args);

    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args);

    public Object uniqueResultSql(final String sql, final Object... args);

    public <T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args);
}
