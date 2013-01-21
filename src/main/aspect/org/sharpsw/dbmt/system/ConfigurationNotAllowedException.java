package org.sharpsw.dbmt.system;

public class ConfigurationNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConfigurationNotAllowedException() {
	}

	public ConfigurationNotAllowedException(String arg0) {
		super(arg0);
	}

	public ConfigurationNotAllowedException(Throwable arg0) {
		super(arg0);
	}

	public ConfigurationNotAllowedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
