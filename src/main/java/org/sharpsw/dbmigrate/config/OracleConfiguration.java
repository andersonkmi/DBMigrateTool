package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.ORACLE_11G;

public class OracleConfiguration extends BaseDatabaseConfiguration {

    public OracleConfiguration() {
    	super("", new Integer(1521), "", "", "");
    }

    public OracleConfiguration(String server, Integer port, String service, String user, String password) {
    	super(server, port, service, user, password);
    }

    @Override
    public String getDriverClassName() {
        return ORACLE_11G.getDriverClassName();
    }

    @Override
    public String getConnectionString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("jdbc:oracle:thin:");
        buffer.append(this.getUser()).append("/").append(this.getPassword());
        buffer.append("@//").append(this.getServer()).append(":").append(this.getPort().toString());
        buffer.append("/").append(this.getDatabase());
        return buffer.toString();
    }

    public DatabaseVendor getDatabaseVendor() {
        return ORACLE_11G;
    }
}
