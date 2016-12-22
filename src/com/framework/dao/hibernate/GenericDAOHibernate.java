package com.framework.dao.hibernate;

import com.framework.bean.BaseEntity;
import com.framework.dao.GenericDAO;
import com.framework.dao.Page;
import com.framework.dao.Pager;
import com.framework.dao.query.QueryBuilder;
import com.framework.dao.query.SqlQueryBuilder;
import com.framework.dao.query.CriteriaQueryBuilder;
import com.framework.dao.query.HqlQueryBuilder;
import com.framework.dao.query.HqlQueryBuilder.HqlQuery;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class GenericDAOHibernate extends HibernateDaoSupport implements GenericDAO {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> List<E> list(Class<E> clazz) {
        logger.debug("Listing All Entries for Class:{}.", clazz.getName());
        return getHibernateTemplate().loadAll(clazz);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> E get(Class<E> clazz, ID id) {
        logger.debug("Getting Entry for Class:{}, using Id:{}.", clazz.getName(), id);
        return getHibernateTemplate().get(clazz, id);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void delete(E entity) {
        logger.debug("Deleting Entry for Class:{}, using Id:{}.", entity.getClass().getName(), entity.getId());
        getHibernateTemplate().delete(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> ID save(E entity) {
        logger.debug("Saving Entry for Class:{}, Id:{}.", entity.getClass().getName(), entity.getId());
        return (ID) getHibernateTemplate().save(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void update(E entity) {
        logger.debug("Updating Entry for Class:{}, Id:{}.", entity.getClass().getName(), entity.getId());
        getHibernateTemplate().update(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void saveOrUpdate(E entity) {
        logger.debug("Saving Or Updating for Class:{}, Id:{}.", entity.getClass().getName(), entity.getId());
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public <E extends BaseEntity<E, ID>, ID extends Serializable> long count(Class<E> clazz) {
        logger.debug("Counting Entries for Class:{}.", clazz.getName());
        return (Long) getSessionFactory().getCurrentSession().createCriteria(clazz).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public <T extends Serializable> Pager<T> page(final Pager<T> pager) {
        final QueryBuilder queryBuilder = pager.getQuery();
        if (HqlQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            HqlQuery query = ((HqlQueryBuilder) queryBuilder).buildHqlQuery();
            String hql = query.getHql();
            String hqlCount = query.getHqlCount();
            Object[] parameters = query.getQueryParameter();

            long count = (Long) getHibernateTemplate().find(hqlCount, parameters).get(0);

            Query hibernateQuery = currentSession().createQuery(hql);
            hibernateQuery.setFirstResult(pager.getFirstResult());
            hibernateQuery.setMaxResults(pager.getMaxResults());
            for (int i = 0; i < parameters.length; i++) {
                hibernateQuery.setParameter(i, parameters[i]);
            }
            @SuppressWarnings("unchecked")
            List<T> results = hibernateQuery.list();

            pager.setResults(results);
            pager.setCount(count);
            return pager;
        } else if (CriteriaQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            return getHibernateTemplate().execute(new HibernateCallback<Pager<T>>() {
                @Override
                public Pager<T> doInHibernate(Session session) throws HibernateException {
                    Criteria criteria = ((CriteriaQueryBuilder) queryBuilder).buildCriteria(session);
                    Criteria criteria2 = ((CriteriaQueryBuilder) queryBuilder).buildCriteria(session);
                    criteria.setFirstResult(pager.getFirstResult());
                    criteria.setMaxResults(pager.getMaxResults());
                    @SuppressWarnings("unchecked")
                    List<T> results = criteria.list();

                    criteria2.setProjection(Projections.rowCount());
                    long count = (Long) criteria2.uniqueResult();

                    pager.setResults(results);
                    pager.setCount(count);
                    return pager;
                }
            });
        } else if (SqlQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            final SqlQueryBuilder.SqlQuery query = ((SqlQueryBuilder) queryBuilder).buildSqlQuery();
            return getHibernateTemplate().execute(new HibernateCallback<Pager<T>>() {
                @Override
                public Pager<T> doInHibernate(Session session) throws HibernateException {
                    Query sql = session.createQuery(query.getSql());
                    Query sqlCount = session.createQuery(query.getSqlCount());
                    Object[] parameters = query.getQueryParameter();
                    for (int i = 0; i < parameters.length; i++) {
                        sql.setParameter(i, parameters[i]);
                        sqlCount.setParameter(i, parameters[i]);
                    }
                    long count = ((Number) sqlCount.uniqueResult()).longValue();
                    sql.setMaxResults(pager.getMaxResults());
                    sql.setFirstResult(pager.getFirstResult());
                    @SuppressWarnings("unchecked")
                    List<T> results = sql.list();
                    pager.setCount(count);
                    pager.setResults(results);
                    return pager;
                }
            });


        } else {
            throw new IllegalArgumentException("Query Class not supported, Pager.getQuery.getClass():" + queryBuilder.getClass());
        }
    }

    @Override
    public List<?> hql(String hql, Object... args) {
        logger.debug("HQL query, hql:[{}], args count:{}.", hql, args.length);
        return getHibernateTemplate().find(hql, args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> hql(Class<T> expectType, String hql, Object... args) {
        logger.debug("HQL query, expectType:{}, hql:[{}], args count:{}.", new Object[]{expectType.getName(), hql, args.length});
        return (List<T>) getHibernateTemplate().find(hql, args);
    }

    @Override
    public List<?> sql(final String sql, final Object... args) {
        logger.debug("SQL query, sql:[{}], args count:{}.", sql, args.length);
        return getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
            @Override
            public List<?> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return query.list();
            }
        });
    }

    @Override
    public <T> List<T> sql(Class<T> expectType, final String sql, final Object... args) {
        logger.debug("SQL query, expectType:{}, sql:[{}], args count:{}.", new Object[]{expectType.getName(), sql, args.length});
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return query.list();
            }
        });
    }

    @Override
    public Object uniqueResultHql(String hql, Object... args) {
        logger.debug("HQL unique query, hql:[{}], args count:{}.", hql, args.length);
        List<?> results = getHibernateTemplate().find(hql, args);
        // CHECK: Use org.hibernate.NonUniqueResultException insead?
        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args) {
        logger.debug("HQL unique query, expectType:{}, hql:[{}], args count:{}.", new Object[]{expectType.getName(), hql, args.length});
        List<?> results = getHibernateTemplate().find(hql, args);
        // CHECK: Use org.hibernate.NonUniqueResultException insead?
        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return (T) results.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
    }

    @Override
    public Object uniqueResultSql(final String sql, final Object... args) {
        logger.debug("SQL unique query, sql:[{}], args count:{}.", sql, args.length);
        return getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return query.uniqueResult();
            }
        });
    }

    @Override
    public <T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args) {
        logger.debug("SQL unique query, expectType:{}, sql:[{}], args count:{}.", new Object[]{expectType.getName(), sql, args.length});
        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return (T) query.uniqueResult();
            }
        });
    }

    @Override
    public List<?> criteria(DetachedCriteria criteria) {
        logger.debug("DetachedCriteria query:{}.", criteria);
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public <T> T callback(HibernateCallback<T> callback) {
        logger.debug("Callback Execute:{}.", callback);
        return getHibernateTemplate().execute(callback);
    }

    @Override
    public <T> List<T> topResultHql(Class<T> clazz, final int top, final String hql, final Object... args) {
        return callback(new HibernateCallback<List<T>>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setMaxResults(top);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        query.setParameter(i, args[i]);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public <T> List<T> topResultSql(Class<T> clazz, final int top, final String sql, final Object... args) {
        return callback(new HibernateCallback<List<T>>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query sqlQuery = session.createQuery(sql);
                sqlQuery.setMaxResults(top);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        sqlQuery.setParameter(0, args[i]);
                    }
                }
                return sqlQuery.list();
            }
        });
    }

    @Override
    public <T> T topResultHql(Class<T> clazz, final String hql, final Object... args) {
        return callback(new HibernateCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setMaxResults(1);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        query.setParameter(i, args[i]);
                    }
                }
                return (T) query.uniqueResult();
            }
        });
    }

    @Override
    public Object topResultHql(final String hql, final Object... args) {
        return callback(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setMaxResults(1);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        query.setParameter(i, args[i]);
                    }
                }
                return query.uniqueResult();
            }
        });
    }

    @Override
    public Object topResultSql(final String sql, final Object... args) {
        return callback(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query sqlQuery = session.createQuery(sql);
                sqlQuery.setMaxResults(1);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        sqlQuery.setParameter(0, args[i]);
                    }
                }
                return sqlQuery.uniqueResult();
            }
        });
    }

    @Override
    public <T> T topResultSql(Class<T> clazz, final String sql, final Object... args) {
        return callback(new HibernateCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T doInHibernate(Session session) throws HibernateException {
                Query sqlQuery = session.createQuery(sql);
                sqlQuery.setMaxResults(1);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        sqlQuery.setParameter(0, args[i]);
                    }
                }
                return (T) sqlQuery.uniqueResult();
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Iterator<T> iterate(Class<T> clazz, final String hql, final Object... args) {
        return (Iterator<T>) getHibernateTemplate().iterate(hql, args);
    }

    @Override
    public void closeIterator(Iterator<?> iterator) {
        getHibernateTemplate().closeIterator(iterator);
    }

    @Override
    public int bulkUpdate(String hql, Object... args) {
        return getHibernateTemplate().bulkUpdate(hql, args);
    }

    @Override
    public int bulkUpdateSql(final String sql, final Object... args) {
        return callback(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(sql);
                for (int i = 0; args != null && i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return query.executeUpdate();
            }
        });
    }
}
