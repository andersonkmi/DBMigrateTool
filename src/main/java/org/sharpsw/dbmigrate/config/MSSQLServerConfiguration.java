package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.MSSQLSERVER_2012;

public class MSSQLServerConfiguration extends BaseDatabaseConfiguration{
    public MSSQLServerConfiguration() {
    	super("", new Integer(1433), "", "", "");
        this.instance = "";
    }

    public MSSQLServerConfiguration(String server, String instance, Integer port, String user, String password, String database) {
    	super(server, port, database, user, password);
        this.instance = instance;
    }

    @Override
    public String getDriverClassName() {
        return MSSQLSERVER_2012.getDriverClassName();
    }


    public String getInstance() {
        return this.instance;
    }
    
    public void setInstance(final String instance) {
    	this.instance = instance;
    }

    @Override
    public String getConnectionString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("jdbc:sqlserver://").append(this.getServer());
        if (!this.instance.isEmpty()) {
            buffer.append("\\").append(this.instance);
        }
        buffer.append(":").append(this.getPort().toString());
        buffer.append(";databaseName=").append(this.getDatabase());
        buffer.append(";user=").append(this.getUser());
        buffer.append(";password=").append(this.getPassword());
        return buffer.toString();
    }

    @Override
    public DatabaseVendor getDatabaseVendor() {
        return MSSQLSERVER_2012;
    }

    private String instance;
}
