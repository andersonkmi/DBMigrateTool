package org.codecraftlabs.neptune.data;

import org.codecraftlabs.neptune.config.DatabaseConfig;
import org.codecraftlabs.neptune.config.PostgreSQLConfiguration;
import org.codecraftlabs.neptune.utility.DatabaseJsonExportTool;
import org.codecraftlabs.neptune.utility.DatabaseSchemaExportException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class DatabaseJsonExportToolTestCase {
	private DatabaseConfig config;

	@Container
	public GenericContainer<?> postgres = new GenericContainer<>(new DockerImageName("neptune-psql:latest").toString()).withExposedPorts(5432).waitingFor(Wait.forListeningPort());

	@BeforeEach
	public void setUp() {
		String address = postgres.getHost();
		Integer port = postgres.getFirstMappedPort();
		config = new PostgreSQLConfiguration(address, port, "postgres", "postgres", "sample");
	}

	@Test
	public void testCase001() throws DatabaseSchemaExportException {
		DatabaseJsonExportTool tool = new DatabaseJsonExportTool();
		String json = tool.export(config);
		System.out.println(json);
	}
}
