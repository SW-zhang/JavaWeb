package com.framework.dao;

import java.io.Serializable;

public class Sort implements Serializable {
    private String orderField;
    private OrderType orderType;

    public Sort(String orderField, OrderType orderType) {
        this.orderField = orderField;
        this.orderType = orderType;
    }

    public Sort(String orderField) {
        this.orderField = orderField;
        this.orderType = OrderType.ASC;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public static enum OrderType {
        ASC, DESC;
    }
}
