package org.sharpsw.dbmigrate.data;

public class DatabaseSchemaParseException extends Exception {
	private static final long serialVersionUID = 6260465980979027109L;

	public DatabaseSchemaParseException() {
	}

	public DatabaseSchemaParseException(String message) {
		super(message);
	}

	public DatabaseSchemaParseException(Throwable cause) {
		super(cause);
	}

	public DatabaseSchemaParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
