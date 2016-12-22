package com.framework.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页器
 * 提供获取集合数据，记录总数，分页数等方法。
 *
 * @Class Name Page<T>
 */
public class Page<T> implements Serializable {

    private Integer pageSize;//每页大小
    private Integer currentPage;//当前页码
    private List<T> data = new ArrayList<>();//结果数据
    private Long count;//总记录数
    private List<Sort> sort;

    /**
     * 默认每页20条记录
     */
    public Page() {
        this(20, 0);
    }

    public Page(Integer pageSize, Integer currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public Page(Integer pageSize, Integer currentPage, List<Sort> sort) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.sort = sort;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    /**
     * 获取总页数
     */
    public Long getPages() {
        if (count % pageSize == 0)
            return count / pageSize;
        else
            return count / pageSize + 1;
    }

    /**
     * 判断是否有下一页
     *
     * @return boolean
     */
    public boolean hasNextPage() {
        return this.getCurrentPage() < this.getPages() - 1;
    }

    /**
     * 判断是否有前一页
     *
     * @return boolean
     */
    public boolean hasPreviousPage() {
        return this.getCurrentPage() > 1;
    }

}
