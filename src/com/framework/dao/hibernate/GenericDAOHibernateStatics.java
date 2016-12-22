package com.framework.dao.hibernate;

import com.framework.bean.BaseEntity;
import com.framework.dao.GenericDaoStatistics;
import com.framework.dao.Pager;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateCallback;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class GenericDAOHibernateStatics extends GenericDAOHibernate {

    private GenericDaoStatistics statistics = new GenericDaoStatistics();

    public GenericDaoStatistics getStatistics() {
        return statistics;
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> List<E> list(Class<E> clazz) {
        long start = System.currentTimeMillis();
        List<E> result = super.list(clazz);
        statistics.listCost(clazz, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> E get(Class<E> clazz, ID id) {
        long start = System.currentTimeMillis();
        E result = super.get(clazz, id);
        statistics.getCost(clazz, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void delete(E entity) {
        long start = System.currentTimeMillis();
        statistics.deleteCost(entity.getClass(), System.currentTimeMillis() - start);
        super.delete(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> ID save(E entity) {
        long start = System.currentTimeMillis();
        ID result = super.save(entity);
        statistics.saveCost(entity.getClass(), System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void update(E entity) {
        long start = System.currentTimeMillis();
        super.update(entity);
        statistics.updateCost(entity.getClass(), System.currentTimeMillis() - start);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void saveOrUpdate(E entity) {
        long start = System.currentTimeMillis();
        statistics.saveOrUpdateCost(entity.getClass(), System.currentTimeMillis() - start);
        super.saveOrUpdate(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> long count(Class<E> clazz) {
        long start = System.currentTimeMillis();
        long result = super.count(clazz);
        statistics.countCost(clazz, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T extends Serializable> Pager<T> page(Pager<T> pager) {
        long start = System.currentTimeMillis();
        Pager<T> result = super.page(pager);
        statistics.pageCost(pager.getQuery() == null ? "" : pager.getQuery().getClass().getName(), System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public List<?> hql(String hql, Object... args) {
        long start = System.currentTimeMillis();
        List<?> result = super.hql(hql, args);
        statistics.hqlCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> List<T> hql(Class<T> expectType, String hql, Object... args) {
        long start = System.currentTimeMillis();
        List<T> result = super.hql(expectType, hql, args);
        statistics.hqlClassCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public List<?> sql(String sql, Object... args) {
        long start = System.currentTimeMillis();
        List<?> result = super.sql(sql, args);
        statistics.sqlCost(sql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> List<T> sql(Class<T> expectType, String sql, Object... args) {
        long start = System.currentTimeMillis();
        List<T> result = super.sql(expectType, sql, args);
        statistics.sqlClassCost(sql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public Object uniqueResultHql(String hql, Object... args) {
        long start = System.currentTimeMillis();
        Object result = super.uniqueResultHql(hql, args);
        statistics.uniqueResultHqlCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args) {
        long start = System.currentTimeMillis();
        T result = super.uniqueResultHql(expectType, hql, args);
        statistics.uniqueResultHqlClassCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public Object uniqueResultSql(String sql, Object... args) {
        long start = System.currentTimeMillis();
        Object result = super.uniqueResultSql(sql, args);
        statistics.uniqueResultSqlCost(sql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> T uniqueResultSql(Class<T> expectType, String sql, Object... args) {
        long start = System.currentTimeMillis();
        T result = super.uniqueResultSql(expectType, sql, args);
        statistics.uniqueResultSqlClassCost(sql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public List<?> criteria(DetachedCriteria criteria) {
        long start = System.currentTimeMillis();
        List<?> result = super.criteria(criteria);
        statistics.criteriaCost(criteria.getClass(), System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> T callback(HibernateCallback<T> callback) {
        long start = System.currentTimeMillis();
        T result = super.callback(callback);
        statistics.callbackCost(callback.getClass(), System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> List<T> topResultHql(Class<T> clazz, int top, String hql, Object... args) {
        long start = System.currentTimeMillis();
        List<T> result = super.topResultHql(clazz, top, hql, args);
        statistics.topResultHqlClassCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> List<T> topResultSql(Class<T> clazz, int top, String sql, Object... args) {
        long start = System.currentTimeMillis();
        List<T> result = super.topResultSql(clazz, top, sql, args);
        statistics.topResultSqlClassCost(sql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> T topResultHql(Class<T> clazz, String hql, Object... args) {
        long start = System.currentTimeMillis();
        T result = super.topResultHql(clazz, hql, args);
        statistics.topResultHqlClassCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public Object topResultHql(String hql, Object... args) {
        long start = System.currentTimeMillis();
        Object result = super.topResultHql(hql, args);
        statistics.topResultHqlCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public Object topResultSql(String sql, Object... args) {
        long start = System.currentTimeMillis();
        Object result = super.topResultSql(sql, args);
        statistics.topResultSqlCost(sql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> T topResultSql(Class<T> clazz, String sql, Object... args) {
        long start = System.currentTimeMillis();
        T result = super.topResultSql(clazz, sql, args);
        statistics.topResultSqlClassCost(sql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public <T> Iterator<T> iterate(Class<T> clazz, String hql, Object... args) {
        long start = System.currentTimeMillis();
        Iterator<T> result = super.iterate(clazz, hql, args);
        statistics.iterateCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public void closeIterator(Iterator<?> iterator) {
        long start = System.currentTimeMillis();
        statistics.closeIterator(System.currentTimeMillis() - start);
        super.closeIterator(iterator);
    }

    @Override
    public int bulkUpdate(String hql, Object... args) {
        long start = System.currentTimeMillis();
        int result = super.bulkUpdate(hql, args);
        statistics.bulkUpdateCost(hql, System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public int bulkUpdateSql(String sql, Object... args) {
        long start = System.currentTimeMillis();
        int result = super.bulkUpdateSql(sql, args);
        statistics.bulkUpdateSqlCost(sql, System.currentTimeMillis() - start);
        return result;
    }


}
