package org.sharpsw.dbmigrate.config;

public enum DatabaseVendor {
    ORACLE_11G("Oracle 11G"),
    ORACLE_12G("Oracle 12C"),
    MSSQLSERVER_2012("Microsoft SQL Server 2012"),
    POSTGRESQL("PostgreSQL 9"),
    HYPERSQL("Hypersonic Database"),
    MYSQL("MySQL Server"),
    XML("Flat XML");

    private String description;

    DatabaseVendor(final String descr) {
        this.description = descr;
    }

    public String getDescription() {
        return this.description;
    }
}
