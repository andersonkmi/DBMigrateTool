package org.sharpsw.dbmigrate.config;

public enum DatabaseVendor {
    ORACLE_11G("Oracle 11G"),
    ORACLE_12G("Oracle 12C"),
    MSSQLSERVER_2012("Microsoft SQL Server 2012"),
    POSTGRESQL("PostgreSQL 9"),
    H2("H2 Database"),
    MYSQL("MySQL Server");

    private String description;

    DatabaseVendor(final String descr) {
        this.description = descr;
    }

    public String getDescription() {
        return this.description;
    }
}
