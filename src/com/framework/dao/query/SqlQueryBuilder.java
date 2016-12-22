package com.framework.dao.query;

import java.util.List;

public interface SqlQueryBuilder extends QueryBuilder {

    public SqlQuery buildSqlQuery();

    public static class SqlQuery {
        private String sql;
        private String sqlCount;
        private List<Object> parameters;

        public String getSql() {
            return sql;
        }

        public String getSqlCount() {
            return sqlCount;
        }

        public List<Object> getParameters() {
            return parameters;
        }

        public Object[] getQueryParameter() {
            return parameters == null ? new Object[]{} : parameters.toArray();
        }

        public SqlQuery(String sql, String sqlCount, List<Object> parameters) {
            this.sql = sql;
            this.sqlCount = sqlCount;
            this.parameters = parameters;
        }
    }
}
