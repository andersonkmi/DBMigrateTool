package org.codecraftlabs.neptune.connectivity;

public class DatabaseConnectionDriverLoadException extends Exception {
	private static final long serialVersionUID = -4673666213217853337L;

	public DatabaseConnectionDriverLoadException() {
	}

	public DatabaseConnectionDriverLoadException(String message) {
		super(message);
	}

	public DatabaseConnectionDriverLoadException(Throwable cause) {
		super(cause);
	}

	public DatabaseConnectionDriverLoadException(String message, Throwable cause) {
		super(message, cause);
	}

}
