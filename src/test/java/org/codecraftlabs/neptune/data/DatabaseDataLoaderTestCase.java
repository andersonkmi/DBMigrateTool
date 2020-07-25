package org.codecraftlabs.neptune.data;

import org.codecraftlabs.neptune.config.DatabaseConfig;
import org.codecraftlabs.neptune.config.PostgreSQLConfiguration;
import org.codecraftlabs.neptune.utility.DatabaseJsonExportTool;
import org.codecraftlabs.neptune.utility.DatabaseSchemaExportException;
import org.junit.jupiter.api.Test;

class DatabaseJsonExportToolTestCase {
	@Test
	public void testCase001() throws DatabaseSchemaExportException {
		DatabaseConfig config = new PostgreSQLConfiguration("localhost", 5432, "postgres", "postgres", "maui");

		DatabaseJsonExportTool tool = new DatabaseJsonExportTool();
		String json = tool.export(config);
		System.out.println(json);
	}
}
