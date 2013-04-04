package org.sharpsw.dbmt.config;

import static org.sharpsw.dbmt.config.DatabaseVendor.H2;

public class H2Configuration implements IDatabaseConfiguration {
    private String server;
    private Integer port;
    private String userName;
    private String password;
    private String databaseName;

    public H2Configuration(final String server, final Integer port, final String userName, final String password, final String databaseName) {

    }

    @Override
    public String getConnectionString() {
        return "";
    }

    @Override
    public String getDriverClassName() {
        return "";
    }

    @Override
    public DatabaseVendor getDatabaseVendor() {
        return H2;
    }

    public final void setServer(final String server) {
    	this.server = server;
    }
    
    public final void setPort(final Integer port) {
    	this.port = port;
    }
    
    public final void setUserName(final String userName) {
    	this.userName = userName;
    }
    
    public final void setPassword(final String password) {
    	this.password = password;
    }
    
    public final void setDatabaseName(final String database) {
    	this.databaseName = database;
    }
}
