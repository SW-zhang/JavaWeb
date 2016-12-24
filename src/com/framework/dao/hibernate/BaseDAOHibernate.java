package com.framework.dao.hibernate;

import com.framework.bean.BaseEntity;
import com.framework.dao.BaseDAO;
import com.framework.dao.Pager;
import com.framework.dao.query.CriteriaQueryBuilder;
import com.framework.dao.query.HqlQueryBuilder;
import com.framework.dao.query.HqlQueryBuilder.HqlQuery;
import com.framework.dao.query.QueryBuilder;
import com.framework.generic.Generics;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

public abstract class BaseDAOHibernate<E extends BaseEntity<? extends E, K>, K extends Serializable> extends HibernateDaoSupport implements BaseDAO<E, K> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<K> idClass;

    protected Class<E> entityClass;

    protected BaseDAOHibernate() {
        detectPersistenceClass();
        detectPersistenceKey();
    }

    @SuppressWarnings("unchecked")
    protected void detectPersistenceClass() {
        entityClass = (Class<E>) Generics.getSuperClassGenericType(getClass(), BaseDAOHibernate.class, 0);
        logger.info("Detect Persistence Class:" + entityClass + " detected.");
    }

    @SuppressWarnings("unchecked")
    protected void detectPersistenceKey() {
        entityClass = (Class<E>) Generics.getSuperClassGenericType(getClass(), BaseDAOHibernate.class, 1);
        logger.info("Detect Id Class:" + idClass + " detected.");
    }

    protected BaseDAOHibernate(Class<E> entityClass, Class<K> idClass) {
        this.entityClass = entityClass;
        this.idClass = idClass;
    }

    @Override
    public E get(K id) {
        return getHibernateTemplate().get(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public K save(E entity) {
        return (K) getHibernateTemplate().save(entity);
    }

    @Override
    public void update(E entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void saveOrUpdate(E entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void delete(E entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public List<E> list() {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Override
    public int count() {
        return (Integer) currentSession().createCriteria(entityClass).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Pager<E> page(final Pager<E> pager) {
        final QueryBuilder queryBuilder = pager.getQuery();
        if (HqlQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            HqlQuery query = ((HqlQueryBuilder) queryBuilder).buildHqlQuery();
            String hql = query.getHql();
            String hqlCount = query.getHqlCount();
            Object[] parameters = query.getQueryParameter();

            long count = (Long) getHibernateTemplate().find(hqlCount, parameters).get(0);

            Query hibernateQuery = currentSession().createQuery(hql);
            hibernateQuery.setFirstResult(pager.getPageSize() * pager.getCurrentPage());
            hibernateQuery.setMaxResults(pager.getPageSize());
            for (int i = 0; i < parameters.length; i++) {
                hibernateQuery.setParameter(i, parameters[i]);
            }
            @SuppressWarnings("unchecked")
            List<E> results = hibernateQuery.list();

            pager.setResults(results);
            pager.setCount((int) count);
            return pager;
        } else if (CriteriaQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            return getHibernateTemplate().execute(new HibernateCallback<Pager<E>>() {
                @Override
                public Pager<E> doInHibernate(Session session) throws HibernateException {
                    Criteria criteria = ((CriteriaQueryBuilder) queryBuilder).buildCriteria(session);
                    Criteria criteria2 = ((CriteriaQueryBuilder) queryBuilder).buildCriteria(session);
                    criteria.setFirstResult(pager.getPageSize() * pager.getCurrentPage());
                    criteria.setMaxResults(pager.getPageSize());
                    @SuppressWarnings("unchecked")
                    List<E> results = criteria.list();

                    criteria2.setProjection(Projections.rowCount());
                    long count = (Long) criteria2.uniqueResult();

                    pager.setResults(results);
                    pager.setCount((int) count);
                    return pager;
                }
            });
        } else {
            throw new IllegalArgumentException("Query Class not supported, Pager.getQuery.getClass():" + queryBuilder.getClass());
        }
    }

    @Override
    public List<?> hql(String hql, Object... args) {
        return getHibernateTemplate().find(hql, args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> hql(Class<T> expectType, String hql, Object... args) {
        return (List<T>) getHibernateTemplate().find(hql, args);
    }

    @Override
    public List<?> sql(final String sql, final Object... args) {
        return getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
            @Override
            public List<?> doInHibernate(Session session) throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return query.list();
            }
        });
    }

    @Override
    public <T> List<T> sql(Class<T> expectType, final String sql, final Object... args) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return query.list();
            }
        });
    }

    @Override
    public Object uniqueResultHql(String hql, Object... args) {
        List<?> results = getHibernateTemplate().find(hql, args);
        if (results.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args) {
        List<?> results = getHibernateTemplate().find(hql, args);
        // CHECK: Use org.hibernate.NonUniqueResultException insead?
        if (results.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else if (results.size() == 1) {
            return (T) results.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
    }

    @Override
    public Object uniqueResultSql(final String sql, final Object... args) {
        return getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return query.uniqueResult();
            }
        });
    }

    @Override
    public <T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args) {
        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T doInHibernate(Session session) throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return (T) query.uniqueResult();
            }
        });
    }

}
