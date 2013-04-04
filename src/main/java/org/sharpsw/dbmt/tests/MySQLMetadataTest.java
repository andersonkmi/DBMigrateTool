package org.sharpsw.dbmt.tests;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.sharpsw.dbmt.base.Database;
import org.sharpsw.dbmt.base.MetadataGenerator;
import org.sharpsw.dbmt.base.Table;
import org.sharpsw.dbmt.config.MySQLConfiguration;
import org.sharpsw.dbmt.connectivity.DBConnectionCreator;

public class MySQLMetadataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MySQLConfiguration configuration = new MySQLConfiguration();
			configuration.setUser("sandbox");
			configuration.setPassword("sandbox");
			configuration.setDatabase("sandbox");
			configuration.setPort(new Integer(3306));
			configuration.setServer("localhost");
			configuration.setDriverClassName("com.mysql.jdbc.Driver");
			
			DBConnectionCreator creator = new DBConnectionCreator(configuration);
			Connection connection = creator.getConnection();
			System.out.println(connection.hashCode());
			
			MetadataGenerator generator = new MetadataGenerator(connection);
			Database database = generator.generate();
			List<Table> tables = database.getTables();
			Iterator<Table> iterator = tables.iterator();
			while(iterator.hasNext()) {
				Table table = iterator.next();
				System.out.println(table.getName());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
