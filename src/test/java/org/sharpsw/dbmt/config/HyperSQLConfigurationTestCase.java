package org.sharpsw.dbmt.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        assertEquals("jdbc:hsqldb:hsql://localhost:9001/teste;username=sa;password=password", config.getConnectionString());
    }
}
