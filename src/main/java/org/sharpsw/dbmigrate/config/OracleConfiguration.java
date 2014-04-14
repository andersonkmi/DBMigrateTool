package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.ORACLE_11G;

public class OracleConfiguration implements DatabaseConfig {

    public OracleConfiguration() {
        this.server = "";
        this.port = new Integer(1521);
        this.service = "";
        this.user = "";
        this.password = "";
    }

    public OracleConfiguration(String server, Integer port, String service, String user, String password) {
        this.server = server;
        this.port = port;
        this.service = service;
        this.user = user;
        this.password = password;
    }

    @Override
    public String getDriverClassName() {
        return ORACLE_11G.getDriverClassName();
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServer() {
        return this.server;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getService() {
        return this.service;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getConnectionString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("jdbc:oracle:thin:");
        buffer.append(this.user).append("/").append(this.password);
        buffer.append("@//").append(this.server).append(":").append(this.port.toString());
        buffer.append("/").append(this.service);
        return buffer.toString();
    }

    public DatabaseVendor getDatabaseVendor() {
        return ORACLE_11G;
    }

    private String server;
    private Integer port;
    private String service;
    private String user;
    private String password;
}
