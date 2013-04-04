package org.sharpsw.dbmt.tests;

import java.sql.Connection;
import java.util.List;

import org.sharpsw.dbmt.base.Database;
import org.sharpsw.dbmt.base.MetadataGenerator;
import org.sharpsw.dbmt.config.MSAccessConfiguration;
import org.sharpsw.dbmt.connectivity.DBConnectionCreator;
import org.sharpsw.dbmt.support.OracleDDLVisitor;
import org.sharpsw.dbmt.support.TableVisitorProtocol;

public class CreateTableTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MSAccessConfiguration configuration = new MSAccessConfiguration();
		configuration.setUser("admin");
		configuration.setPassword("");
		configuration.setDriverName("Driver do Microsoft Access (*.mdb)");
		configuration.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
		configuration.setDatabaseFile("C:\\anderson\\test_fk.mdb");

		DBConnectionCreator creator = new DBConnectionCreator(configuration);
		Connection connection = creator.getConnection();
		System.out.println(connection.hashCode());
		
		MetadataGenerator generator = new MetadataGenerator(connection);
		Database database = generator.generate();
		
		TableVisitorProtocol.Visitor visitor = new OracleDDLVisitor();
		database.accept(visitor);
		
		List<String> statements = visitor.getTableCreateStatements();
		for(String element: statements) {
			System.out.println(element);
		}
	}

}
