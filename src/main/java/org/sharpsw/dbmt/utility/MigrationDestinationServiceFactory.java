package org.sharpsw.dbmt.utility;

import org.sharpsw.dbmt.config.DatabaseVendor;
import org.sharpsw.dbmt.config.DatabaseConfig;

public class MigrationDestinationServiceFactory {
    public static MigrationDestinationBaseService getService(DatabaseConfig config) {
        MigrationDestinationBaseService service = null;
        if (config.getDatabaseVendor() == DatabaseVendor.ORACLE_11G) {
            service = new MigrationDestinationOracleService(config);
        }
        return service;
    }
}
