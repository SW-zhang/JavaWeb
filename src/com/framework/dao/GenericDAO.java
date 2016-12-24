package com.framework.dao;

import com.framework.bean.BaseEntity;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateCallback;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface GenericDAO {

    public <E extends BaseEntity<E, ID>, ID extends Serializable> List<E> list(Class<E> clazz);

    public <E extends BaseEntity<E, ID>, ID extends Serializable> E get(Class<E> clazz, ID id);

    public <E extends BaseEntity<E, ID>, ID extends Serializable> void delete(E entity);

    public <E extends BaseEntity<E, ID>, ID extends Serializable> ID save(E entity);

    public <E extends BaseEntity<E, ID>, ID extends Serializable> void update(E entity);

    public <E extends BaseEntity<E, ID>, ID extends Serializable> void saveOrUpdate(E entity);

    public <E extends BaseEntity<E, ID>, ID extends Serializable> long count(Class<E> clazz);

    public <T extends Serializable> Pager<T> page(Pager<T> pager, Class<T> clazz);

    public List<?> hql(String hql, Object... args);

    public <T> List<T> hql(Class<T> expectType, String hql, Object... args);

    public List<?> sql(final String sql, final Object... args);

    public <T> List<T> sql(Class<T> expectType, final String sql, final Object... args);

    public Object uniqueResultHql(String hql, Object... args);

    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args);

    public Object uniqueResultSql(final String sql, final Object... args);

    public <T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args);

    public List<?> criteria(DetachedCriteria criteria);

    public <T> T callback(HibernateCallback<T> callback);


    public <T> T topResultHql(Class<T> clazz, final String hql, final Object... args);

    public Object topResultHql(final String hql, final Object... args);

    public Object topResultSql(final String sql, final Object... args);

    public <T> T topResultSql(Class<T> clazz, final String sql, final Object... args);

    public <T> List<T> topResultHql(Class<T> clazz, final int top, final String hql, final Object... args);

    public <T> List<T> topResultSql(Class<T> clazz, final int top, final String sql, final Object... args);

    public <T> Iterator<T> iterate(Class<T> clazz, final String hql, final Object... args);

    public void closeIterator(Iterator<?> iterator);

    public int updateHql(String hql, Object... args);

    public int updateSql(final String sql, final Object... args);

}
