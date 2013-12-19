package org.sharpsw.dbmigrate.base;

public class MetadataGenException extends Exception {
	private static final long serialVersionUID = 6260465980979027109L;

	public MetadataGenException() {
	}

	public MetadataGenException(String message) {
		super(message);
	}

	public MetadataGenException(Throwable cause) {
		super(cause);
	}

	public MetadataGenException(String message, Throwable cause) {
		super(message, cause);
	}

}
