package org.codecraftlabs.neptune.config;

import java.util.Optional;

public class DatabaseConfigBuilder {
    public static Optional<DatabaseConfig> build(DatabaseVendor vendor, String address, Integer port, String user, String password, String databaseName) {
        if (vendor == null) {
            return Optional.empty();
        }

        if (vendor == DatabaseVendor.POSTGRESQL) {
            var config = new PostgreSQLConfiguration();
            config.setServer(address);
            config.setPort(port);
            config.setUser(user);
            config.setPassword(password);
            config.setDatabase(databaseName);
            return Optional.of(config);
        }

        if (vendor == DatabaseVendor.MYSQL) {
            var config = new MySQLConfiguration();
            config.setServer(address);
            config.setPort(port);
            config.setUser(user);
            config.setPassword(password);
            config.setDatabase(databaseName);
            return Optional.of(config);
        }

        return Optional.empty();
    }
}
