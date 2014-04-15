package org.sharpsw.dbmigrate.utility;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.config.DatabaseVendor;

public class MigrationDestinationServiceFactory {
    public static MigrationDestinationBaseService getService(DatabaseConfig config) {
        MigrationDestinationBaseService service = null;
        if (config.getDatabaseVendor() == DatabaseVendor.ORACLE) {
            service = new MigrationDestinationOracleService(config);
        }
        return service;
    }
}
