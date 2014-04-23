package org.sharpsw.dbmigrate.data;

import org.junit.Before;
import org.junit.Test;
import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.config.MySQLConfiguration;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;

public class DatabaseDataLoaderTestCase {
	private DatabaseSchemaParser service;
	
	@Before
	public void setUp() throws Exception {
		service = new DatabaseSchemaParser(new DatabaseConnectionFactory());
	}

	@Test
	public void testMysqlLoaderOK() {
		try {
			DatabaseConfig configuration = new MySQLConfiguration("localhost", 3306, "pagamentodigital", "root", "anderson");

			@SuppressWarnings("unused")
			Database database = service.load(configuration);			
		} catch (DatabaseSchemaParseException exception) {
			exception.printStackTrace();
		}
	}

}
