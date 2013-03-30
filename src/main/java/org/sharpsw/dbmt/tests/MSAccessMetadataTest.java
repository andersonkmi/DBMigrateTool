package org.sharpsw.dbmt.tests;

import org.sharpsw.dbmt.config.MSAccessConfigurationImpl;
import org.sharpsw.dbmt.config.OracleConfigurationImpl;
import org.sharpsw.dbmt.utility.DBMigrationTool;

public class MSAccessMetadataTest {
	public static void main(String[] args) {
		try {
			MSAccessConfigurationImpl configuration = new MSAccessConfigurationImpl();
			configuration.setUser("admin");
			configuration.setPassword("");
			configuration.setDriverName("Driver do Microsoft Access (*.mdb)");
			configuration.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
			configuration.setDatabaseFile("D:\\BDs\\maps.mdb");
			
			OracleConfigurationImpl destinationConfig = new OracleConfigurationImpl();
			destinationConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			destinationConfig.setServer("winxp");
			destinationConfig.setPort(new Integer(1521));
			destinationConfig.setService("SHARPSW");
			destinationConfig.setUser("sharpsw");
			destinationConfig.setPassword("sharpsw");
						
			DBMigrationTool tool = new DBMigrationTool(configuration, destinationConfig);
			tool.migrate();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}