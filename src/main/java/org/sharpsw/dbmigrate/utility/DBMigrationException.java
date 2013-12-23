package org.sharpsw.dbmigrate.utility;

public class DBMigrationException extends Exception {
	private static final long serialVersionUID = 1L;

	public DBMigrationException() {

	}

	public DBMigrationException(String arg0) {
		super(arg0);
	}

	public DBMigrationException(Throwable arg0) {
		super(arg0);
	}

	public DBMigrationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
