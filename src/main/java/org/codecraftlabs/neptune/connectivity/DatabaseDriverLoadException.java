package org.codecraftlabs.neptune.connectivity;

public class DatabaseDriverLoadException extends Exception {
	private static final long serialVersionUID = -4673666213217853337L;

	public DatabaseDriverLoadException() {
	}

	public DatabaseDriverLoadException(String message) {
		super(message);
	}

	public DatabaseDriverLoadException(Throwable cause) {
		super(cause);
	}

	public DatabaseDriverLoadException(String message, Throwable cause) {
		super(message, cause);
	}

}
