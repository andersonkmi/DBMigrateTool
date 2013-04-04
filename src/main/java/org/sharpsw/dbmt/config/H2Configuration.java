package org.sharpsw.dbmt.config;

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
        return DatabaseVendor.XML;
    }

    public final void setServer(final String server) {
    }
}
