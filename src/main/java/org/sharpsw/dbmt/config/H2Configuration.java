package org.sharpsw.dbmt.config;

public class H2Configuration extends BaseDatabaseConfiguration {
    public H2Configuration(final String connectionString, final DatabaseVendor vendor, final String driverClass) {
        super(connectionString, vendor, driverClass);
    }
}
