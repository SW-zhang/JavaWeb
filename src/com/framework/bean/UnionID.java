package com.framework.bean;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 联合主键  需要再引用的地方  getId方法上加 @EmbeddedId 注解
 */
@Embeddable
public class UnionID implements Serializable {

    private String cstNo;
    private String cstName;

    public UnionID() {
    }

    public UnionID(String cstNo, String cstName) {
        this.cstNo = cstNo;
        this.cstName = cstName;
    }

    public String getCstNo() {
        return cstNo;
    }

    public void setCstNo(String cstNo) {
        this.cstNo = cstNo;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnionID unionID = (UnionID) o;

        if (cstNo != null ? !cstNo.equals(unionID.cstNo) : unionID.cstNo != null) return false;
        return !(cstName != null ? !cstName.equals(unionID.cstName) : unionID.cstName != null);

    }

    @Override
    public int hashCode() {
        int result = cstNo != null ? cstNo.hashCode() : 0;
        result = 31 * result + (cstName != null ? cstName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UnionID{" +
                "cstNo='" + cstNo + '\'' +
                ", cstName='" + cstName + '\'' +
                '}';
    }
}