package org.sharpsw.dbmigrate.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.config.MySQLConfiguration;

public class DatabaseXmlExportToolTestCase {
	private DatabaseXmlExportTool tool;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.tool = new DatabaseXmlExportTool();
	}

	@Test
	public void testExportOK() throws DatabaseSchemaExportException {
		DatabaseConfig configuration = new MySQLConfiguration("localhost", 3306, "pagamentodigital", "root", "anderson");
		String xml = this.tool.export(configuration);
		System.out.println(xml);
	}

}
