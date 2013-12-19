package org.sharpsw.dbmt.base;
import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.base.Column;
import org.sharpsw.dbmigrate.base.ForeignKey;
import org.sharpsw.dbmigrate.base.PrimaryKey;
import org.sharpsw.dbmigrate.base.Table;

public aspect TableLoggingAspect {
	private static final Logger logger = Logger.getLogger(Table.class);
	
	pointcut setName(Table table, String name) : set(private String Table.name) && args(name) && target(table);
	before(Table table, String name) : setName(table, name) {
		String current = table.getName();
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Replacing the table name from '").append(current).append("' to '").append(name).append("'");
			logger.debug(log.toString());
		}
	}
	
	pointcut addColumnCall(Column column) : call(public void Table.add(Column)) && args(column);
	before(Column column) : addColumnCall(column) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Adding a column '").append(column.getName()).append("' into the table representation object.");
			logger.debug(log.toString());
		}
	}
	
	pointcut addPrimaryKey(PrimaryKey key) : call(public void Table.add(PrimaryKey)) && args(key);
	before(PrimaryKey key) : addPrimaryKey(key) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Adding the primary key '").append(key.getColumn()).append("' into the table representation object");
			logger.debug(log.toString());
		}
	}
	
	pointcut addForeignKey(ForeignKey key) : call(public void Table.add(ForeignKey)) && args(key);
	before(ForeignKey key) : addForeignKey(key) {
		if(logger.isDebugEnabled()) {
			StringBuffer log = new StringBuffer();
			log.append("Adding the foreign key '").append(key.getName()).append("' into the table representation object");
			logger.debug(log.toString());
		}
	}
}
