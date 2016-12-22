package com.framework.dao.query;

import org.hibernate.Criteria;
import org.hibernate.Session;

public interface CriteriaQueryBuilder extends QueryBuilder {

    public Criteria buildCriteria(Session session);
}
