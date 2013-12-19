package org.sharpsw.dbmt.base;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.base.Database;
import org.sharpsw.dbmigrate.base.Table;

public aspect DatabaseLoggingAspect {
	private static final Logger logger = Logger.getLogger(Database.class);
	
	pointcut initDatabaseObject(String schema) : call(public Database.new(String)) && args(schema);
	before(String schema) : initDatabaseObject(schema) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Creating a new instance of 'Database' class identified by '").append(schema).append("'");
			logger.debug(log.toString());
		}
	}
	
	pointcut setDatabaseProductName(Database db, String name) : set(private String Database.productName) && args(name) && target(db);
	before(Database db, String name) : setDatabaseProductName(db, name) {
		String currentName = db.getProductName();
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Replacing the database product name from '").append(currentName).append("' to '").append(name).append("'");
			logger.debug(log.toString());
		}
	}
	
	pointcut setDatabaseProductVersion(Database db, String name) : set(private String Database.productVersion) && args(name) && target(db);
	before(Database db, String name) : setDatabaseProductVersion(db, name) {
		String current = db.getProductVersion();
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Replacing the database product version from '").append(current).append("' to '").append(name).append("'");
			logger.debug(log.toString());
		}
	}	
	
	pointcut setDatabaseMajorVersion(Database db, int version) : set(private int Database.majorVersion) && args(version) && target(db);
	before(Database db, int version) : setDatabaseMajorVersion(db, version) {
		int current = db.getMajorVersion();
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Replacing the database major version from '").append(current).append("' to '").append(version).append("'");
			logger.debug(log.toString());
		}
	}
	
	pointcut setDatabaseMinorVersion(Database db, int version) : set(private int Database.minorVersion) && args(version) && target(db);
	before(Database db, int version) : setDatabaseMajorVersion(db, version) {
		int current = db.getMinorVersion();
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Replacing the database minor version from '").append(current).append("' to '").append(version).append("'");
			logger.debug(log.toString());
		}
	}
	
	pointcut setDatabaseMajorJDBCVersion(Database db, int version) : set(private int Database.majorJDBCVersion) && args(version) && target(db);
	before(Database db, int version) : setDatabaseMajorJDBCVersion(db, version) {
		int current = db.getMajorJDBCVersion();
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Replacing the database major JDBC version from '").append(current).append("' to '").append(version).append("'");
			logger.debug(log.toString());
		}
	}
	
	pointcut setDatabaseMinorJDBCVersion(Database db, int version) : set(private int Database.minorJDBCVersion) && args(version) && target(db);
	before(Database db, int version) : setDatabaseMinorJDBCVersion(db, version) {
		int current = db.getMinorJDBCVersion();
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Replacing the database minor JDBC version from '").append(current).append("' to '").append(version).append("'");
			logger.debug(log.toString());
		}
	}
	
	pointcut tableAddCall(Database db, Table table) : call(public void Database.add(Table)) && args(table) && target(db);
	before(Database db, Table table) : tableAddCall(db, table) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Adding the table '").append(table.getName()).append("' into the database object");
			logger.debug(log.toString());
		}
	}
}
