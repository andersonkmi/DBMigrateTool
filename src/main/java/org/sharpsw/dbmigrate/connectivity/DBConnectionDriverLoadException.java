package org.sharpsw.dbmigrate.connectivity;

public class DBConnectionDriverLoadException extends Exception {
	private static final long serialVersionUID = -4673666213217853337L;

	public DBConnectionDriverLoadException() {
	}

	public DBConnectionDriverLoadException(String message) {
		super(message);
	}

	public DBConnectionDriverLoadException(Throwable cause) {
		super(cause);
	}

	public DBConnectionDriverLoadException(String message, Throwable cause) {
		super(message, cause);
	}

}
