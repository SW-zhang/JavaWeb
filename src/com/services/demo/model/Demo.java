package com.services.demo.model;

import com.framework.bean.BaseEntity;
import com.framework.bean.SimpleLongID;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "function")
public class Demo implements BaseEntity<Demo, SimpleLongID> {
    private static final long serialVersionUID = 1L;

    private SimpleLongID id;

    private String name; // 功能模块名称
    private Long parent_id; // 父功能模块id
    private Integer level; // 级别
    private String path; // 功能路径
    private Integer status; // 状态 0：不可用 1：可用
    private Date create_time; // 创建时间

    public Demo() {
        this.id = new SimpleLongID();
    }

    public Demo(SimpleLongID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @EmbeddedId
    public SimpleLongID getId() {
        return id;
    }

    @Override
    public void setId(SimpleLongID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Demo function = (Demo) o;

        if (id != null ? !id.equals(function.id) : function.id != null) return false;
        if (name != null ? !name.equals(function.name) : function.name != null) return false;
        if (parent_id != null ? !parent_id.equals(function.parent_id) : function.parent_id != null) return false;
        if (level != null ? !level.equals(function.level) : function.level != null) return false;
        if (path != null ? !path.equals(function.path) : function.path != null) return false;
        if (status != null ? !status.equals(function.status) : function.status != null) return false;
        return !(create_time != null ? !create_time.equals(function.create_time) : function.create_time != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parent_id != null ? parent_id.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (create_time != null ? create_time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                ", level=" + level +
                ", path='" + path + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                '}';
    }
}
