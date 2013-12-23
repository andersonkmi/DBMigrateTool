package org.sharpsw.dbmt.utility;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.config.DatabaseVendor;

public class MigrationDestinationServiceFactory {
    public static MigrationDestinationBaseService getService(DatabaseConfig config) {
        MigrationDestinationBaseService service = null;
        if (config.getDatabaseVendor() == DatabaseVendor.ORACLE_11G) {
            service = new MigrationDestinationOracleService(config);
        }
        return service;
    }
}
