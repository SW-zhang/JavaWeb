package com.framework.dao.hibernate;

import com.framework.bean.BaseEntity;
import com.framework.dao.AddScalar;
import com.framework.dao.GenericDAO;
import com.framework.dao.Pager;
import com.framework.dao.query.CriteriaQueryBuilder;
import com.framework.dao.query.HqlQueryBuilder;
import com.framework.dao.query.HqlQueryBuilder.HqlQuery;
import com.framework.dao.query.QueryBuilder;
import com.framework.dao.query.SqlQueryBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
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
    public List<?> hql(String hql, Object... args) {
        logger.debug("HQL query, hql:[{}], args count:{}.", hql, args.length);
        return getHibernateTemplate().find(hql, args);
    }

    @Override
    public <T> List<T> hql(Class<T> expectType, String hql, Object... args) {
        logger.debug("HQL query, expectType:{}, hql:[{}], args count:{}.", expectType.getName(), hql, args.length);
        return (List<T>) getHibernateTemplate().find(hql, args);
    }

    @Override
    public List<?> sql(final String sql, final Object... args) {
        logger.debug("SQL query, sql:[{}], args count:{}.", sql, args.length);
        return getHibernateTemplate().execute((HibernateCallback<List<?>>) session -> {
            NativeQuery query = session.createNativeQuery(sql);
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }
            return query.list();
        });
    }

    @Override
    public <T> List<T> sql(Class<T> expectType, final String sql, final Object... args) {
        logger.debug("SQL query, expectType:{}, sql:[{}], args count:{}.", expectType.getName(), sql, args.length);
        return getHibernateTemplate().execute(session -> {
            NativeQuery<T> query = session.createNativeQuery(sql);
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }
            AddScalar.addScalar(query, expectType);
            return query.list();
        });
    }

    @Override
    public Object uniqueResultSql(final String sql, final Object... args) {
        logger.debug("SQL unique query, sql:[{}], args count:{}.", sql, args.length);
        return getHibernateTemplate().execute(session -> {
            NativeQuery query = session.createNativeQuery(sql);
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }
            return query.uniqueResult();
        });
    }

    @Override
    public <T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args) {
        logger.debug("SQL unique query, expectType:{}, sql:[{}], args count:{}.", expectType.getName(), sql, args.length);
        return getHibernateTemplate().execute(session -> {
            NativeQuery<T> query = session.createNativeQuery(sql);
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }
            AddScalar.addScalar(query, expectType);
            return query.uniqueResult();
        });
    }

    @Override
    public Object topResultSql(final String sql, final Object... args) {
        return callback(session -> {
            NativeQuery sqlQuery = session.createNativeQuery(sql);
            sqlQuery.setMaxResults(1);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    sqlQuery.setParameter(i + 1, args[i]);
                }
            }
            return sqlQuery.uniqueResult();
        });
    }

    @Override
    public <T> T topResultSql(Class<T> expectType, final String sql, final Object... args) {
        return callback(session -> {
            NativeQuery<T> query = session.createNativeQuery(sql);
            query.setMaxResults(1);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i + 1, args[i]);
                }
            }
            AddScalar.addScalar(query, expectType);
            return query.uniqueResult();
        });
    }

    @Override
    public <T> List<T> topResultSql(Class<T> expectType, final int top, final String sql, final Object... args) {
        return callback((HibernateCallback<List<T>>) session -> {
            NativeQuery query = session.createNativeQuery(sql);
            query.setMaxResults(top);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i + 1, args[i]);
                }
            }
            AddScalar.addScalar(query, expectType);
            return query.list();
        });
    }

    @Override
    public int updateSql(final String sql, final Object... args) {
        return callback(session -> {
            NativeQuery query = session.createNativeQuery(sql);
            for (int i = 0; args != null && i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }
            return query.executeUpdate();
        });
    }


    @Override
    public Object uniqueResultHql(String hql, Object... args) {
        logger.debug("HQL unique query, hql:[{}], args count:{}.", hql, args.length);
        List<?> results = getHibernateTemplate().find(hql, args);
        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
    }

    @Override
    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args) {
        logger.debug("HQL unique query, expectType:{}, hql:[{}], args count:{}.", expectType.getName(), hql, args.length);
        List<?> results = getHibernateTemplate().find(hql, args);
        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return (T) results.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
    }

    @Override
    public Object topResultHql(final String hql, final Object... args) {
        return callback(session -> {
            Query query = session.createQuery(hql);
            query.setMaxResults(1);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
            }
            return query.uniqueResult();
        });
    }


    @Override
    public <T> T topResultHql(Class<T> clazz, final String hql, final Object... args) {
        return callback(session -> {
            Query query = session.createQuery(hql);
            query.setMaxResults(1);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
            }
            return (T) query.uniqueResult();
        });
    }

    @Override
    public <T> List<T> topResultHql(Class<T> clazz, final int top, final String hql, final Object... args) {
        return callback((HibernateCallback<List<T>>) session -> {
            Query query = session.createQuery(hql);
            query.setMaxResults(top);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
            }
            return query.list();
        });
    }


    @Override
    public int updateHql(String hql, Object... args) {
        return getHibernateTemplate().bulkUpdate(hql, args);
    }

    @Override
    public <T> Iterator<T> iterate(Class<T> clazz, final String hql, final Object... args) {
        return (Iterator<T>) getHibernateTemplate().iterate(hql, args);
    }

    @Override
    public void closeIterator(Iterator<?> iterator) {
        getHibernateTemplate().closeIterator(iterator);
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
    public <T extends Serializable> Pager<T> page(final Pager<T> pager, Class<T> clazz) {
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
            List<T> results = hibernateQuery.list();

            pager.setResults(results);
            pager.setCount((int) count);
            return pager;
        } else if (CriteriaQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            return getHibernateTemplate().execute(session -> {
                Criteria criteria = ((CriteriaQueryBuilder) queryBuilder).buildCriteria(session);
                Criteria criteria2 = ((CriteriaQueryBuilder) queryBuilder).buildCriteria(session);
                criteria.setFirstResult(pager.getPageSize() * pager.getCurrentPage());
                criteria.setMaxResults(pager.getPageSize());
                List<T> results = criteria.list();

                criteria2.setProjection(Projections.rowCount());
                long count = (Long) criteria2.uniqueResult();

                pager.setResults(results);
                pager.setCount((int) count);
                return pager;
            });
        } else if (SqlQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            final SqlQueryBuilder.SqlQuery query = ((SqlQueryBuilder) queryBuilder).buildSqlQuery();
            return getHibernateTemplate().execute(session -> {
                NativeQuery sql = session.createNativeQuery(query.getSql());
                NativeQuery sqlCount = session.createNativeQuery(query.getSqlCount());
                Object[] parameters = query.getQueryParameter();
                for (int i = 0; i < parameters.length; i++) {
                    sql.setParameter(i + 1, parameters[i]);
                    sqlCount.setParameter(i + 1, parameters[i]);
                }
                long count = ((Number) sqlCount.uniqueResult()).longValue();
                sql.setMaxResults(pager.getPageSize());
                sql.setFirstResult(pager.getPageSize() * pager.getCurrentPage());

                AddScalar.addScalar(sql, clazz);
                List<T> results = sql.list();
                pager.setCount((int) count);
                pager.setResults(results);
                return pager;
            });


        } else {
            throw new IllegalArgumentException("Query Class not supported, Pager.getQuery.getClass():" + queryBuilder.getClass());
        }
    }

    @Override
    public <T extends Serializable> List<T> page(Class<T> clazz, QueryBuilder queryBuilder, Integer page_index, Integer page_size) {
        if (HqlQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            HqlQuery query = ((HqlQueryBuilder) queryBuilder).buildHqlQuery();
            String hql = query.getHql();
            Object[] parameters = query.getQueryParameter();

            Query hibernateQuery = currentSession().createQuery(hql);
            hibernateQuery.setFirstResult(page_index * page_size);
            hibernateQuery.setMaxResults(page_size);
            for (int i = 0; i < parameters.length; i++) {
                hibernateQuery.setParameter(i, parameters[i]);
            }
            return hibernateQuery.list();

        } else if (CriteriaQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            Criteria criteria = ((CriteriaQueryBuilder) queryBuilder).buildCriteria(currentSession());
            criteria.setFirstResult(page_index * page_size);
            criteria.setMaxResults(page_size);

            return criteria.list();

        } else if (SqlQueryBuilder.class.isAssignableFrom(queryBuilder.getClass())) {
            final SqlQueryBuilder.SqlQuery query = ((SqlQueryBuilder) queryBuilder).buildSqlQuery();
            NativeQuery sql = currentSession().createNativeQuery(query.getSql());
            Object[] parameters = query.getQueryParameter();
            for (int i = 0; i < parameters.length; i++) {
                sql.setParameter(i + 1, parameters[i]);
            }
            sql.setMaxResults(page_size);
            sql.setFirstResult(page_size * page_index);

            AddScalar.addScalar(sql, clazz);

            return sql.list();
        } else {
            throw new IllegalArgumentException("Query Class not supported, Query.getClass():" + queryBuilder.getClass());
        }
    }
}
