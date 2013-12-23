package org.sharpsw.dbmigrate.config;

public class InvalidConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 1425593939L;

	public InvalidConfigurationException() {
	}

	public InvalidConfigurationException(String message) {
		super(message);
	}

	public InvalidConfigurationException(Throwable cause) {
		super(cause);
	}

	public InvalidConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
