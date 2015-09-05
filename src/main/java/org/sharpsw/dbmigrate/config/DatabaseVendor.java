package org.sharpsw.dbmigrate.config;

public enum DatabaseVendor {
    ORACLE("Oracle", "org.jdbc.driver.OracleDriver"),
    MSSQLSERVER("Microsoft SQL Server", "net.sourceforge.jtds.jdbc.Driver"),
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver"),
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
