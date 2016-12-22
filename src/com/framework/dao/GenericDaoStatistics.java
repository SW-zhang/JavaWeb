package com.framework.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class GenericDaoStatistics {

    private long start = System.currentTimeMillis();

    public static class Cost implements Serializable {
        private long count;
        private long cost;

        public synchronized void cost(long cost) {
            count++;
            this.cost += cost;
        }

        public long avg() {
            return count == 0 ? 0 : cost / count;
        }

        public long getCount() {
            return count;
        }

        public long getCost() {
            return cost;
        }

        public synchronized void reset() {
            this.cost = 0;
            this.count = 0;
        }

        @Override
        public String toString() {
            return "Cost [count=" + count + ", cost=" + cost + ", avg=" + avg() + "]";
        }
    }

    // List<E> list(Class<E> clazz)
    private GenericDaoStatistics.Cost listCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> list = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void listCost(Class clazz, long cost) {
        listCost.cost(cost);
        Cost classCost = list.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            list.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // E get(Class<E> clazz, SimpleLongID id)
    private GenericDaoStatistics.Cost getCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> get = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void getCost(Class clazz, long cost) {
        getCost.cost(cost);
        Cost classCost = get.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            get.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // void delete(E entity)
    private GenericDaoStatistics.Cost deleteCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> delete = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void deleteCost(Class clazz, long cost) {
        deleteCost.cost(cost);
        Cost classCost = delete.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            delete.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // SimpleLongID save(E entity)
    private GenericDaoStatistics.Cost saveCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> save = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void saveCost(Class clazz, long cost) {
        saveCost.cost(cost);
        Cost classCost = save.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            save.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // void update(E entity)
    private GenericDaoStatistics.Cost updateCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> update = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void updateCost(Class clazz, long cost) {
        updateCost.cost(cost);
        Cost classCost = update.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            update.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // void saveOrUpdate(E entity)
    private GenericDaoStatistics.Cost saveOrUpdateCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> saveOrUpdate = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void saveOrUpdateCost(Class clazz, long cost) {
        saveOrUpdateCost.cost(cost);
        Cost classCost = saveOrUpdate.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            saveOrUpdate.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // long count(Class<E> clazz)
    private GenericDaoStatistics.Cost countCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> count = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void countCost(Class clazz, long cost) {
        countCost.cost(cost);
        Cost classCost = count.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            count.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // Pager<T> page(Pager<T> pager)
    private GenericDaoStatistics.Cost pageCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> page = new HashMap<String, GenericDaoStatistics.Cost>();

    public void pageCost(String key, long cost) {
        pageCost.cost(cost);
        Cost classCost = page.get(key);
        if (classCost == null) {
            classCost = new Cost();
            page.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // List<?> hql(String hql, Object... args)
    private GenericDaoStatistics.Cost hqlCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> hql = new HashMap<String, GenericDaoStatistics.Cost>();

    public void hqlCost(String key, long cost) {
        hqlCost.cost(cost);
        Cost classCost = hql.get(key);
        if (classCost == null) {
            classCost = new Cost();
            hql.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // <T> List<T> hql(Class<T> expectType, String hql, Object... args)
    private GenericDaoStatistics.Cost hqlClassCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> hqlClass = new HashMap<String, GenericDaoStatistics.Cost>();

    public void hqlClassCost(String key, long cost) {
        hqlClassCost.cost(cost);
        Cost classCost = hqlClass.get(key);
        if (classCost == null) {
            classCost = new Cost();
            hqlClass.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // List<?> sql(final String sql, final Object... args)
    private GenericDaoStatistics.Cost sqlCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> sql = new HashMap<String, GenericDaoStatistics.Cost>();

    public void sqlCost(String key, long cost) {
        sqlCost.cost(cost);
        Cost classCost = sql.get(key);
        if (classCost == null) {
            classCost = new Cost();
            sql.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // <T> List<T> sql(Class<T> expectType, final String sql, final Object... args)
    private GenericDaoStatistics.Cost sqlClassCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> sqlClass = new HashMap<String, GenericDaoStatistics.Cost>();

    public void sqlClassCost(String key, long cost) {
        sqlClassCost.cost(cost);
        Cost classCost = sqlClass.get(key);
        if (classCost == null) {
            classCost = new Cost();
            sqlClass.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // Object uniqueResultHql(String hql, Object... args)
    private GenericDaoStatistics.Cost uniqueResultHqlCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> uniqueResultHql = new HashMap<String, GenericDaoStatistics.Cost>();

    public void uniqueResultHqlCost(String key, long cost) {
        uniqueResultHqlCost.cost(cost);
        Cost classCost = uniqueResultHql.get(key);
        if (classCost == null) {
            classCost = new Cost();
            uniqueResultHql.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args)
    private GenericDaoStatistics.Cost uniqueResultHqlClassCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> uniqueResultHqlClass = new HashMap<String, GenericDaoStatistics.Cost>();

    public void uniqueResultHqlClassCost(String key, long cost) {
        uniqueResultHqlClassCost.cost(cost);
        Cost classCost = uniqueResultHqlClass.get(key);
        if (classCost == null) {
            classCost = new Cost();
            uniqueResultHqlClass.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // Object uniqueResultSql(final String sql, final Object... args);
    private GenericDaoStatistics.Cost uniqueResultSqlCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> uniqueResultSql = new HashMap<String, GenericDaoStatistics.Cost>();

    public void uniqueResultSqlCost(String key, long cost) {
        uniqueResultSqlCost.cost(cost);
        Cost classCost = uniqueResultSql.get(key);
        if (classCost == null) {
            classCost = new Cost();
            uniqueResultSql.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // <T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args);
    private GenericDaoStatistics.Cost uniqueResultSqlClassCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> uniqueResultSqlClass = new HashMap<String, GenericDaoStatistics.Cost>();

    public void uniqueResultSqlClassCost(String key, long cost) {
        uniqueResultSqlClassCost.cost(cost);
        Cost classCost = uniqueResultSqlClass.get(key);
        if (classCost == null) {
            classCost = new Cost();
            uniqueResultSqlClass.put(key, classCost);
        }
        classCost.cost(cost);
    }


    // List<?> criteria(DetachedCriteria criteria);
    private GenericDaoStatistics.Cost criteriaCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> criteria = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void criteriaCost(Class clazz, long cost) {
        criteriaCost.cost(cost);
        Cost classCost = criteria.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            criteria.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    // <T> T callback(HibernateCallback<T> callback);
    private GenericDaoStatistics.Cost callbackCost = new Cost();
    private Map<Class, GenericDaoStatistics.Cost> callback = new HashMap<Class, GenericDaoStatistics.Cost>();

    public void callbackCost(Class clazz, long cost) {
        callbackCost.cost(cost);
        Cost classCost = callback.get(clazz);
        if (classCost == null) {
            classCost = new Cost();
            callback.put(clazz, classCost);
        }
        classCost.cost(cost);
    }

    //	Object topResultHql(final String hql, final Object... args);
    private GenericDaoStatistics.Cost topResultHqlCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> topResultHql = new HashMap<String, GenericDaoStatistics.Cost>();

    public void topResultHqlCost(String key, long cost) {
        topResultHqlCost.cost(cost);
        Cost classCost = topResultHql.get(key);
        if (classCost == null) {
            classCost = new Cost();
            topResultHql.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // <T> T topResultHql(Class<T> clazz, final String hql, final Object... args);
    private GenericDaoStatistics.Cost topResultHqlClassCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> topResultHqlClass = new HashMap<String, GenericDaoStatistics.Cost>();

    public void topResultHqlClassCost(String key, long cost) {
        topResultHqlClassCost.cost(cost);
        Cost classCost = topResultHqlClass.get(key);
        if (classCost == null) {
            classCost = new Cost();
            topResultHqlClass.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // Object topResultSql(final String sql, final Object... args);
    private GenericDaoStatistics.Cost topResultSqlCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> topResultSql = new HashMap<String, GenericDaoStatistics.Cost>();

    public void topResultSqlCost(String key, long cost) {
        topResultSqlCost.cost(cost);
        Cost classCost = topResultSql.get(key);
        if (classCost == null) {
            classCost = new Cost();
            topResultSql.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // <T> T topResultSql(Class<T> clazz, final String sql, final Object... args);
    private GenericDaoStatistics.Cost topResultSqlClassCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> topResultSqlClass = new HashMap<String, GenericDaoStatistics.Cost>();

    public void topResultSqlClassCost(String key, long cost) {
        topResultSqlClassCost.cost(cost);
        Cost classCost = topResultSqlClass.get(key);
        if (classCost == null) {
            classCost = new Cost();
            topResultSqlClass.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // <T> Iterator<T> iterate(Class<T> clazz, final String hql, final Object... args);
    private GenericDaoStatistics.Cost iterateCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> iterate = new HashMap<String, GenericDaoStatistics.Cost>();

    public void iterateCost(String key, long cost) {
        iterateCost.cost(cost);
        Cost classCost = iterate.get(key);
        if (classCost == null) {
            classCost = new Cost();
            iterate.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // void closeIterator(Iterator<?> iterator);
    private GenericDaoStatistics.Cost closeIteratorCost = new Cost();

    public void closeIterator(long cost) {
        closeIteratorCost.cost(cost);
    }

    // int bulkUpdate(String hql, Object... args);
    private GenericDaoStatistics.Cost bulkUpdateCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> bulkUpdate = new HashMap<String, GenericDaoStatistics.Cost>();

    public void bulkUpdateCost(String key, long cost) {
        bulkUpdateCost.cost(cost);
        Cost classCost = bulkUpdate.get(key);
        if (classCost == null) {
            classCost = new Cost();
            bulkUpdate.put(key, classCost);
        }
        classCost.cost(cost);
    }

    // int bulkUpdateSql(final String sql, final Object... args);
    private GenericDaoStatistics.Cost bulkUpdateSqlCost = new Cost();
    private Map<String, GenericDaoStatistics.Cost> bulkUpdateSql = new HashMap<String, GenericDaoStatistics.Cost>();

    public void bulkUpdateSqlCost(String key, long cost) {
        bulkUpdateSqlCost.cost(cost);
        Cost classCost = bulkUpdateSql.get(key);
        if (classCost == null) {
            classCost = new Cost();
            bulkUpdateSql.put(key, classCost);
        }
        classCost.cost(cost);
    }

    /**
     * @return the start
     */
    public long getStart() {
        return start;
    }

    /**
     * @return the listCost
     */
    public GenericDaoStatistics.Cost getListCost() {
        return listCost;
    }

    /**
     * @return the list
     */
    public Map<Class, GenericDaoStatistics.Cost> getList() {
        return list;
    }

    /**
     * @return the getCost
     */
    public GenericDaoStatistics.Cost getGetCost() {
        return getCost;
    }

    /**
     * @return the get
     */
    public Map<Class, GenericDaoStatistics.Cost> getGet() {
        return get;
    }

    /**
     * @return the deleteCost
     */
    public GenericDaoStatistics.Cost getDeleteCost() {
        return deleteCost;
    }

    /**
     * @return the delete
     */
    public Map<Class, GenericDaoStatistics.Cost> getDelete() {
        return delete;
    }

    /**
     * @return the saveCost
     */
    public GenericDaoStatistics.Cost getSaveCost() {
        return saveCost;
    }

    /**
     * @return the save
     */
    public Map<Class, GenericDaoStatistics.Cost> getSave() {
        return save;
    }

    /**
     * @return the updateCost
     */
    public GenericDaoStatistics.Cost getUpdateCost() {
        return updateCost;
    }

    /**
     * @return the update
     */
    public Map<Class, GenericDaoStatistics.Cost> getUpdate() {
        return update;
    }

    /**
     * @return the saveOrUpdateCost
     */
    public GenericDaoStatistics.Cost getSaveOrUpdateCost() {
        return saveOrUpdateCost;
    }

    /**
     * @return the saveOrUpdate
     */
    public Map<Class, GenericDaoStatistics.Cost> getSaveOrUpdate() {
        return saveOrUpdate;
    }

    /**
     * @return the countCost
     */
    public GenericDaoStatistics.Cost getCountCost() {
        return countCost;
    }

    /**
     * @return the count
     */
    public Map<Class, GenericDaoStatistics.Cost> getCount() {
        return count;
    }

    /**
     * @return the pageCost
     */
    public GenericDaoStatistics.Cost getPageCost() {
        return pageCost;
    }

    /**
     * @return the page
     */
    public Map<String, GenericDaoStatistics.Cost> getPage() {
        return page;
    }

    /**
     * @return the hqlCost
     */
    public GenericDaoStatistics.Cost getHqlCost() {
        return hqlCost;
    }

    /**
     * @return the hql
     */
    public Map<String, GenericDaoStatistics.Cost> getHql() {
        return hql;
    }

    /**
     * @return the hqlClassCost
     */
    public GenericDaoStatistics.Cost getHqlClassCost() {
        return hqlClassCost;
    }

    /**
     * @return the hqlClass
     */
    public Map<String, GenericDaoStatistics.Cost> getHqlClass() {
        return hqlClass;
    }

    /**
     * @return the sqlCost
     */
    public GenericDaoStatistics.Cost getSqlCost() {
        return sqlCost;
    }

    /**
     * @return the sql
     */
    public Map<String, GenericDaoStatistics.Cost> getSql() {
        return sql;
    }

    /**
     * @return the sqlClassCost
     */
    public GenericDaoStatistics.Cost getSqlClassCost() {
        return sqlClassCost;
    }

    /**
     * @return the sqlClass
     */
    public Map<String, GenericDaoStatistics.Cost> getSqlClass() {
        return sqlClass;
    }

    /**
     * @return the uniqueResultHqlCost
     */
    public GenericDaoStatistics.Cost getUniqueResultHqlCost() {
        return uniqueResultHqlCost;
    }

    /**
     * @return the uniqueResultHql
     */
    public Map<String, GenericDaoStatistics.Cost> getUniqueResultHql() {
        return uniqueResultHql;
    }

    /**
     * @return the uniqueResultHqlClassCost
     */
    public GenericDaoStatistics.Cost getUniqueResultHqlClassCost() {
        return uniqueResultHqlClassCost;
    }

    /**
     * @return the uniqueResultHqlClass
     */
    public Map<String, GenericDaoStatistics.Cost> getUniqueResultHqlClass() {
        return uniqueResultHqlClass;
    }

    /**
     * @return the uniqueResultSqlCost
     */
    public GenericDaoStatistics.Cost getUniqueResultSqlCost() {
        return uniqueResultSqlCost;
    }

    /**
     * @return the uniqueResultSql
     */
    public Map<String, GenericDaoStatistics.Cost> getUniqueResultSql() {
        return uniqueResultSql;
    }

    /**
     * @return the uniqueResultSqlClassCost
     */
    public GenericDaoStatistics.Cost getUniqueResultSqlClassCost() {
        return uniqueResultSqlClassCost;
    }

    /**
     * @return the uniqueResultSqlClass
     */
    public Map<String, GenericDaoStatistics.Cost> getUniqueResultSqlClass() {
        return uniqueResultSqlClass;
    }

    /**
     * @return the criteriaCost
     */
    public GenericDaoStatistics.Cost getCriteriaCost() {
        return criteriaCost;
    }

    /**
     * @return the criteria
     */
    public Map<Class, GenericDaoStatistics.Cost> getCriteria() {
        return criteria;
    }

    /**
     * @return the callbackCost
     */
    public GenericDaoStatistics.Cost getCallbackCost() {
        return callbackCost;
    }

    /**
     * @return the callback
     */
    public Map<Class, GenericDaoStatistics.Cost> getCallback() {
        return callback;
    }

    /**
     * @return the topResultHqlCost
     */
    public GenericDaoStatistics.Cost getTopResultHqlCost() {
        return topResultHqlCost;
    }

    /**
     * @return the topResultHql
     */
    public Map<String, GenericDaoStatistics.Cost> getTopResultHql() {
        return topResultHql;
    }

    /**
     * @return the topResultHqlClassCost
     */
    public GenericDaoStatistics.Cost getTopResultHqlClassCost() {
        return topResultHqlClassCost;
    }

    /**
     * @return the topResultHqlClass
     */
    public Map<String, GenericDaoStatistics.Cost> getTopResultHqlClass() {
        return topResultHqlClass;
    }

    /**
     * @return the topResultSqlCost
     */
    public GenericDaoStatistics.Cost getTopResultSqlCost() {
        return topResultSqlCost;
    }

    /**
     * @return the topResultSql
     */
    public Map<String, GenericDaoStatistics.Cost> getTopResultSql() {
        return topResultSql;
    }

    /**
     * @return the topResultSqlClassCost
     */
    public GenericDaoStatistics.Cost getTopResultSqlClassCost() {
        return topResultSqlClassCost;
    }

    /**
     * @return the topResultSqlClass
     */
    public Map<String, GenericDaoStatistics.Cost> getTopResultSqlClass() {
        return topResultSqlClass;
    }

    /**
     * @return the iterateCost
     */
    public GenericDaoStatistics.Cost getIterateCost() {
        return iterateCost;
    }

    /**
     * @return the iterate
     */
    public Map<String, GenericDaoStatistics.Cost> getIterate() {
        return iterate;
    }

    /**
     * @return the closeIteratorCost
     */
    public GenericDaoStatistics.Cost getCloseIteratorCost() {
        return closeIteratorCost;
    }

    /**
     * @return the bulkUpdateCost
     */
    public GenericDaoStatistics.Cost getBulkUpdateCost() {
        return bulkUpdateCost;
    }

    /**
     * @return the bulkUpdate
     */
    public Map<String, GenericDaoStatistics.Cost> getBulkUpdate() {
        return bulkUpdate;
    }

    /**
     * @return the bulkUpdateSqlCost
     */
    public GenericDaoStatistics.Cost getBulkUpdateSqlCost() {
        return bulkUpdateSqlCost;
    }

    /**
     * @return the bulkUpdateSql
     */
    public Map<String, GenericDaoStatistics.Cost> getBulkUpdateSql() {
        return bulkUpdateSql;
    }

}
