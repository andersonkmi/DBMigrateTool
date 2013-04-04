package org.sharpsw.dbmt.config;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HyperSQLConfigurationTestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConfigurationOK() {
		HyperSQLConfiguration config = new HyperSQLConfiguration("localhost", "teste", new Integer(9001), "sa", "password", "org.hsqldb.jdbcDriver");
		Assert.assertEquals("jdbc:hsqldb:hsql://localhost:9001/teste;username=sa;password=password", config.getConnectionString());
	}
	
	@Test(expected = InvalidConfigurationException.class)
	public void testNullServerNameFail() {
		@SuppressWarnings("unused")
        HyperSQLConfiguration config = new HyperSQLConfiguration(null, "teste", new Integer(9001), "sa", "password", "org.hsqldb.jdbcDriver");
	}
}
