package org.sharpsw.dbmigrate.data;

public class MetadataGenerationException extends Exception {
	private static final long serialVersionUID = 6260465980979027109L;

	public MetadataGenerationException() {
	}

	public MetadataGenerationException(String message) {
		super(message);
	}

	public MetadataGenerationException(Throwable cause) {
		super(cause);
	}

	public MetadataGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

}
