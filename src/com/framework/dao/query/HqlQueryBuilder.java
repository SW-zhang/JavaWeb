package com.framework.dao.query;

public interface HqlQueryBuilder extends QueryBuilder {

    public HqlQuery buildHqlQuery();

    public static class HqlQuery {

        protected String hql;
        protected Object[] parameters;
        protected String hqlCount;

        protected HqlQuery() {
        }

        public HqlQuery(String hql, String hqlCount, Object[] parameters) {
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

        public Object[] getParameters() {
            return parameters;
        }

        public Object[] getQueryParameter() {
            return parameters == null ? new Object[]{} : parameters;
        }

        @Override
        public String toString() {
            return "HqlQuery [hql=" + hql + ", parameters=" + parameters + ", hqlCount=" + hqlCount + "]";
        }
    }

}
