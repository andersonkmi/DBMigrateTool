package org.sharpsw.dbmigrate.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.config.MySQLConfiguration;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;

public class DatabaseDataLoaderTestCase {
	private DatabaseSchemaParser service;
	
	@BeforeEach
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

	@Test
	public void testParsingWithNullDBConfigFail() throws DatabaseSchemaParseException {
		service.load(null);
	}
	
	@Test
	public void testParsingWithNullDBConnFactoryFail() throws DatabaseSchemaParseException {
		DatabaseSchemaParser parser = new DatabaseSchemaParser(null);
		DatabaseConfig configuration = new MySQLConfiguration("localhost", 3306, "pagamentodigital", "root", "anderson");		
		parser.load(configuration);
	}
}
