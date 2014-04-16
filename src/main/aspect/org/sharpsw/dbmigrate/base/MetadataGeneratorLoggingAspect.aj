package org.sharpsw.dbmigrate.base;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.DatabaseDataLoader;
import org.sharpsw.dbmigrate.data.Table;

public aspect MetadataGeneratorLoggingAspect {
	private static final Logger logger = Logger.getLogger(DatabaseDataLoader.class);
	
	pointcut generateCall(DatabaseDataLoader generator) : call(public Database DatabaseDataLoader.generate()) && target(generator);
	
	after(DatabaseDataLoader generator) returning : generateCall(generator) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("A database representation has been created");
			logger.debug(log.toString());
		}
	}
	
	pointcut generateTableListCall(Database database) : call(private void DatabaseDataLoader.generateTableList(Database)) && args(database);
	before(Database database) : generateTableListCall(database) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Generating table information for database");
			logger.debug(log.toString());
		}
	}
	
	after(Database database) : generateTableListCall(database) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Table information generation has been performed.");
			logger.debug(log.toString());
		}
	}
	
	pointcut generateColumnListCall(Table table) : call(private void DatabaseDataLoader.generateColumnList(Table)) && args(table);
	before(Table table) : generateColumnListCall(table) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Generating column list information for table '").append(table.getName()).append("'");
			logger.debug(log.toString());
		}
	}
	after(Table table) : generateColumnListCall(table) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Column information generation has been performed");
			logger.debug(log.toString());
		}
	}
	
	pointcut getPrimaryKeysCall(Table table) : call(private void DatabaseDataLoader.getPrimaryKeys(Table)) && args(table);
	before(Table table) : getPrimaryKeysCall(table) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Getting primary keys information for table '").append(table.getName()).append("'");
			logger.debug(log.toString());
		}
	}
	after(Table table) : getPrimaryKeysCall(table) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Primary key information has been performed");
			logger.debug(log.toString());
		}
	}
	
	pointcut getForeignKeysCall(Table table) : call(private void DatabaseDataLoader.getForeignKeys(Table)) && args(table);
	before(Table table) : getForeignKeysCall(table) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Getting foreign keys information for table '").append(table.getName()).append("'");
			logger.debug(log.toString());
		}
	}
	
	after(Table table) : getForeignKeysCall(table) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Foreign keys information generation has been performed");
			logger.debug(log.toString());
		}
	}
}
