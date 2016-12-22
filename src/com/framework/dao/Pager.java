package com.framework.dao;

import com.framework.dao.query.QueryBuilder;

import java.io.Serializable;
import java.util.List;

public class Pager<T extends Serializable> implements Serializable {

    private QueryBuilder query;

    private List<T> results;

    private int firstResult;

    private int maxResults;

    private long count;

    private String order;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Pager() {
    }

    public Pager(QueryBuilder query, int firstResult, int maxResult) {
        this.query = query;
        this.firstResult = (firstResult - 1) * maxResult;
        this.maxResults = maxResult;
    }

    /**
     * @return the query
     */
    public QueryBuilder getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(QueryBuilder query) {
        this.query = query;
    }

    /**
     * @return the results
     */
    public List<T> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<T> results) {
        this.results = results;
    }

    /**
     * @return the firstResult
     */
    public int getFirstResult() {
        return firstResult;
    }

    /**
     * @param firstResult the firstResult to set
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(long count) {
        this.count = count;
    }

    public String getStatisticsKey() {
        return query.toString();
    }
}
