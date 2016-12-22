package com.framework.dao.query;

import java.util.List;

public interface HqlQueryBuilder extends QueryBuilder {

    public HqlQuery buildHqlQuery();

    public static class HqlQuery {

        protected String hql;
        protected List<Object> parameters;
        protected String hqlCount;

        protected HqlQuery() {
        }

        public HqlQuery(String hql, String hqlCount, List<Object> parameters) {
            this.hql = hql;
            this.hqlCount = hqlCount;
            this.parameters = parameters;
        }

        public String getHql() {
            return hql;
        }

        public String getHqlCount() {
            return hqlCount;
        }

        public List<Object> getParameters() {
            return parameters;
        }

        public Object[] getQueryParameter() {
            return parameters == null ? new Object[]{} : parameters.toArray();
        }

        @Override
        public String toString() {
            return "HqlQuery [hql=" + hql + ", parameters=" + parameters + ", hqlCount=" + hqlCount + "]";
        }
    }

}
