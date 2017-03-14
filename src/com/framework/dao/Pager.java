package com.framework.dao;

import com.framework.dao.query.QueryBuilder;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @param <T>
 */
public class Pager<T extends Serializable> implements Serializable {

    private QueryBuilder query;

    private List<T> results;

    private Integer currentPage;

    private Integer pageSize;

    private Integer count;

    public Pager() {
        this.currentPage = 0;
        this.pageSize = 10;
    }

    public Pager(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    /**
     * @return the query
     */
    @Transient
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
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return totalPages
     */
    public Integer getTotalPages() {
        return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
    }
}
