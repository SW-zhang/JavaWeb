package com.framework.service.impl;

import com.framework.bean.BaseEntity;
import com.framework.dao.GenericDAO;
import com.framework.dao.Pager;
import com.framework.dao.Sort;
import com.framework.dao.query.HqlQueryBuilder;
import com.framework.dao.query.SqlQueryBuilder;
import com.framework.service.GenericCrudService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Transactional
@Service("crudService")
public class GenericCrudServiceImpl implements GenericCrudService {

    @Autowired
    private GenericDAO genericDAO;

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
    public int updateHql(String hql, Object... args) {
        return genericDAO.updateHql(hql, args);
    }

    @Override
    public int updateSql(final String sql, final Object... args) {
        return genericDAO.updateSql(sql, args);
    }

    @Override
    public <T> List<T> topResultSql(Class<T> clazz, final int top, final String sql, final Object... args) {
        return genericDAO.topResultSql(clazz, top, sql, args);
    }

    @Override
    public <T> List<T> topResultHql(Class<T> clazz, final int top, final String hql, final Object... args) {
        return genericDAO.topResultHql(clazz, top, hql, args);
    }

    /**
     * 分页查询
     *
     * @param pager  分页对象
     * @param clazz  类
     * @param params 条件
     */
    @Override
    public <T extends Serializable> void pager(Class clazz, Pager<T> pager, Map<String, Object> params) {
        pager(clazz, pager, params, null);
    }

    /**
     * 分页查询
     *
     * @param pager  分页对象
     * @param clazz  类
     * @param orders 排序字段
     */
    @Override
    public <T extends Serializable> void pager(Class clazz, Pager<T> pager, List<Sort> orders) {
        pager(clazz, pager, null, orders);
    }

    /**
     * 分页查询
     *
     * @param pager  分页对象
     * @param clazz  类
     * @param params 条件（key是字段名 value是字段值）
     * @param orders 排序字段
     */
    @Override
    public <T extends Serializable> void pager(Class clazz, Pager<T> pager, Map<String, Object> params, List<Sort> orders) {
        Assert.notNull(pager);
        pager.setQuery(new HqlQueryBuilder() {
            @Override
            public HqlQuery buildHqlQuery() {
                Object[] param = null;
                StringBuilder hql = new StringBuilder(" from " + clazz.getName());
                param = assembleSql(hql, params, null, orders);
                String hql2 = hql.toString();
                return new HqlQuery(hql2, "select count(*) " + hql2, param);
            }
        });
        genericDAO.page(pager);
    }

    /**
     * 分页查询 hql
     *
     * @param pager  分页对象
     * @param hql    hql语句
     * @param params 参数
     */
    @Override
    public <T extends Serializable> void hqlPager(Pager<T> pager, String hql, Object[] params) {
        Assert.notNull(pager);
        Assert.hasLength(hql);
        pager.setQuery(new HqlQueryBuilder() {
            @Override
            public HqlQuery buildHqlQuery() {
                return new HqlQuery(hql, "select count(*) " + hql.substring(hql.indexOf("from"), hql.length()), params);
            }
        });
        genericDAO.page(pager);
    }

    /**
     * 分页查询  sql
     *
     * @param pager  分页对象
     * @param sql    sql语句
     * @param params 参数
     */
    @Override
    public <T extends Serializable> void sqlPager(Pager<T> pager, String sql, Object[] params) {
        Assert.notNull(pager);
        Assert.hasLength(sql);
        pager.setQuery(new SqlQueryBuilder() {

            @Override
            public SqlQuery buildSqlQuery() {
                return new SqlQuery(sql, "select count(*) " + sql.substring(sql.indexOf("from"), sql.length()), params);
            }
        });
        genericDAO.page(pager);
    }

    /**
     * 拼装sql
     *
     * @param sql    拼装对象
     * @param params where条件集合
     * @param groups 分组集合
     * @param orders 排序集合
     * @return resultParam
     */
    private Object[] assembleSql(StringBuilder sql, Map<String, Object> params, List<String> groups, List<Sort> orders) {
        Object[] resultParam = null;
        boolean first = true;
        int size = 0;
        //**********参数拼装*****************************************
        if (!CollectionUtils.isEmpty(params)) {
            resultParam = new Object[params.size()];
            sql.append(" where ");
            for (String key : params.keySet()) {
                if (first) {
                    first = false;
                } else {
                    sql.append(" and ");
                }
                sql.append(key).append(" = ? ");
                resultParam[size++] = params.get(key);
            }
        }
        //**********分组拼装*****************************************
        if (!CollectionUtils.isEmpty(groups)) {
            sql.append(" group by ");
            first = true;
            for (String group : groups) {
                if (first) {
                    first = false;
                } else {
                    sql.append(",");
                }
                sql.append(group).append(" ");
            }
        }
        //**********排序拼装*****************************************
        if (!CollectionUtils.isEmpty(orders)) {
            sql.append(" order by ");
            first = true;
            for (Sort sort : orders) {
                if (first) {
                    first = false;
                } else {
                    sql.append(",");
                }
                sql.append(sort.getOrderField()).append(" ").append(sort.getOrderType().toString().toLowerCase());
            }
        }
        return resultParam;
    }
}
