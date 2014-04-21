package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.ORACLE;

public class OracleConfiguration extends BaseDatabaseConfiguration {

    public OracleConfiguration() {
    	super("", new Integer(1521), "", "", "");
    }

    public OracleConfiguration(String server, Integer port, String service, String user, String password) {
    	super(server, port, service, user, password);
    }

    @Override
    public String getDriverClassName() {
        return ORACLE.getDriverClassName();
    }

    @Override
    public String getConnectionString() {
    	return String.format("jdbc:oracle:thin:%s/%s@//%s:%d/%s", this.getUser(), this.getPassword(), this.getServer(), this.getPort(), this.getDatabase());
    }

    public DatabaseVendor getDatabaseVendor() {
        return ORACLE;
    }
}
