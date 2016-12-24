package com.framework.dao.query;

public interface SqlQueryBuilder extends QueryBuilder {

    public SqlQuery buildSqlQuery();

    public static class SqlQuery {
        private String sql;
        private String sqlCount;
        private Object[] parameters;

        public String getSql() {
            return sql;
        }

        public String getSqlCount() {
            return sqlCount;
        }

        public Object[] getParameters() {
            return parameters;
        }

        public Object[] getQueryParameter() {
            return parameters == null ? new Object[]{} : parameters;
        }

        public SqlQuery(String sql, String sqlCount, Object[] parameters) {
            this.sql = sql;
            this.sqlCount = sqlCount;
            this.parameters = parameters;
        }
    }
}
