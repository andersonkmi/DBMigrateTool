package org.sharpsw.dbmigrate.config;

public enum DatabaseVendor {
    ORACLE_11G("Oracle 11G", "org.jdbc.driver.OracleDriver"),
    ORACLE_12G("Oracle 12C", "org.jdbc.driver.OracleDriver"),
    MSSQLSERVER_2012("Microsoft SQL Server 2012", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    POSTGRESQL("PostgreSQL 9", "org.postgresql.Driver"),
    H2("H2 Database", "org.hsqldb.jdbcDriver"),
    MYSQL("MySQL Server", "com.mysql.jdbc.Driver");

    private String description;
    
    private String driverClassName;

    DatabaseVendor(final String descr, final String driverClassName) {
        this.description = descr;
        this.driverClassName = driverClassName;
    }

    public String getDescription() {
        return this.description;
    }
    
    public String getDriverClassName() {
    	return this.driverClassName;
    }
}
