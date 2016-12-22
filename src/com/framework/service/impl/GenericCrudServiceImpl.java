package com.framework.service.impl;

import com.framework.bean.BaseEntity;
import com.framework.dao.GenericDAO;
import com.framework.dao.Pager;
import com.framework.service.GenericCrudService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
@Transactional
@Service("crudService")
public class GenericCrudServiceImpl implements GenericCrudService {

    @Autowired
    private GenericDAO genericDAO;

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> List<E> list(Class<E> clazz) {
        return genericDAO.list(clazz);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> E get(Class<E> clazz, ID id) {
        return genericDAO.get(clazz, id);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void delete(E entity) {
        genericDAO.delete(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> ID save(E entity) {
        return genericDAO.save(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void update(E entity) {
        genericDAO.update(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void saveOrUpdate(E entity) {
        genericDAO.saveOrUpdate(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> long count(Class<E> clazz) {
        return genericDAO.count(clazz);
    }

    @Override
    public <T extends Serializable> Pager<T> page(Pager<T> pager) {
        return genericDAO.page(pager);
    }

    @Override
    public List<?> hql(String hql, Object... args) {
        return genericDAO.hql(hql, args);
    }

    @Override
    public <T> List<T> hql(Class<T> expectType, String hql, Object... args) {
        return genericDAO.hql(expectType, hql, args);
    }

    @Override
    public List<?> sql(String sql, Object... args) {
        return genericDAO.sql(sql, args);
    }

    @Override
    public <T> List<T> sql(Class<T> expectType, String sql, Object... args) {
        return genericDAO.sql(expectType, sql, args);
    }

    @Override
    public Object uniqueResultHql(String hql, Object... args) {
        return genericDAO.uniqueResultHql(hql, args);
    }

    @Override
    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args) {
        return genericDAO.uniqueResultHql(expectType, hql, args);
    }

    @Override
    public Object uniqueResultSql(String sql, Object... args) {
        return genericDAO.uniqueResultSql(sql, args);
    }

    @Override
    public <T> T uniqueResultSql(Class<T> expectType, String sql, Object... args) {
        return genericDAO.uniqueResultSql(expectType, sql, args);
    }

    @Override
    public List<?> criteria(DetachedCriteria criteria) {
        return genericDAO.criteria(criteria);
    }

    @Override
    public <T> T callback(HibernateCallback<T> callback) {
        return genericDAO.callback(callback);
    }

    @Override
    public <T> T topResultHql(Class<T> clazz, String hql, Object... args) {
        return genericDAO.topResultHql(clazz, hql, args);
    }

    @Override
    public Object topResultHql(String hql, Object... args) {
        return genericDAO.topResultHql(hql, args);
    }

    @Override
    public Object topResultSql(String sql, Object... args) {
        return genericDAO.topResultSql(sql, args);
    }

    @Override
    public <T> T topResultSql(Class<T> clazz, String sql, Object... args) {
        return genericDAO.topResultSql(clazz, sql, args);
    }

    @Override
    public <T> Iterator<T> iterate(Class<T> clazz, final String hql, final Object... args) {
        return genericDAO.iterate(clazz, hql, args);
    }

    @Override
    public void closeIterator(Iterator<?> iterator) {
        genericDAO.closeIterator(iterator);
    }

    @Override
    public int bulkUpdate(String hql, Object... args) {
        return genericDAO.bulkUpdate(hql, args);
    }

    @Override
    public int bulkUpdateSql(final String sql, final Object... args) {
        return genericDAO.bulkUpdateSql(sql, args);
    }

    @Override
    public <T> List<T> topResultSql(Class<T> clazz, final int top, final String sql, final Object... args) {
        return genericDAO.topResultSql(clazz, top, sql, args);
    }

    @Override
    public <T> List<T> topResultHql(Class<T> clazz, final int top, final String hql, final Object... args) {
        return genericDAO.topResultHql(clazz, top, hql, args);
    }
}
