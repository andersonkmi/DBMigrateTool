package org.sharpsw.dbmt.utility;

import org.sharpsw.dbmt.config.DatabaseVendor;
import org.sharpsw.dbmt.config.IDatabaseConfiguration;

public class MigrationDestinationServiceFactory {
	public static MigrationDestinationBaseService getService(IDatabaseConfiguration config) {
		MigrationDestinationBaseService service = null;
		if(config.getDatabaseVendor() == DatabaseVendor.ORACLE) {
			service = new MigrationDestinationOracleService(config);
		}
		return service;
	}
}
