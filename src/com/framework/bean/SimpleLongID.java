package com.framework.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public class SimpleLongID implements Serializable {

    private Long id;

    public SimpleLongID() {
    }

    public SimpleLongID(Long id) {
        this.id = id;
    }

    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleLongID id1 = (SimpleLongID) o;

        return !(id != null ? !id.equals(id1.id) : id1.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SimpleLongID{" +
                "id=" + id +
                '}';
    }
}