package org.codecraftlabs.neptune.data;

import org.codecraftlabs.neptune.config.DatabaseConfig;
import org.codecraftlabs.neptune.config.DatabaseConfigBuilder;
import org.codecraftlabs.neptune.config.DatabaseVendor;
import org.codecraftlabs.neptune.util.DatabaseJsonExportTool;
import org.codecraftlabs.neptune.util.DatabaseSchemaExportException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

@Testcontainers
public class DatabaseJsonExportToolTestCase {
	private DatabaseConfig config;

	@Container
	public GenericContainer<?> postgres = new GenericContainer<>(new DockerImageName("neptune-psql:latest").toString()).withExposedPorts(5432).waitingFor(Wait.forListeningPort());

	@BeforeEach
	public void setUp() {
		String address = postgres.getHost();
		Integer port = postgres.getFirstMappedPort();
		Optional<DatabaseConfig> result = DatabaseConfigBuilder.build(DatabaseVendor.POSTGRESQL, address, port, "postgres", "postgres", "sample");
		result.ifPresent(databaseConfig -> config = databaseConfig);
	}

	@Test
	public void testCase001() throws DatabaseSchemaExportException {
		DatabaseJsonExportTool tool = new DatabaseJsonExportTool();
		String json = tool.export(config);
		System.out.println(json);
	}
}
