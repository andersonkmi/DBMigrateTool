package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.MSSQLSERVER;

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
        return MSSQLSERVER.getDriverClassName();
    }


    public String getInstance() {
        return this.instance;
    }
    
    public void setInstance(final String instance) {
    	this.instance = instance;
    }

    @Override
    public String getConnectionString() {
    	if(this.instance.isEmpty()) {
    		return String.format("jdbc:jtds:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s", this.getServer(), this.getPort(), this.getDatabase(), this.getUser(), this.getPassword());
    	} else {
    		return String.format("jdbc:jtds:sqlserver://%s\\%s:%d;databaseName=%s;user=%s;password=%s", this.getServer(), this.getInstance(), this.getPort(), this.getDatabase(), this.getUser(), this.getPassword());
    	}
    }

    @Override
    public DatabaseVendor getDatabaseVendor() {
        return MSSQLSERVER;
    }
    
    private String instance;
}
