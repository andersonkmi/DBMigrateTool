package org.sharpsw.dbmigrate.data;

public class DataLoadException extends Exception {
	private static final long serialVersionUID = 6260465980979027109L;

	public DataLoadException() {
	}

	public DataLoadException(String message) {
		super(message);
	}

	public DataLoadException(Throwable cause) {
		super(cause);
	}

	public DataLoadException(String message, Throwable cause) {
		super(message, cause);
	}

}
