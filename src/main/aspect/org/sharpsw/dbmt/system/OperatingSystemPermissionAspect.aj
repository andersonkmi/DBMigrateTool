package org.sharpsw.dbmt.system;
import org.sharpsw.dbmt.config.MSAccessConfigurationImpl;

public aspect OperatingSystemPermissionAspect {
	private String name = System.getProperty("os.name");
	
	pointcut msAccessConfigurationCreation() : initialization(public MSAccessConfigurationImpl.new(..));
	before() throws ConfigurationNotAllowedException : msAccessConfigurationCreation() {
		if(!this.name.contains("Windows")) {
			StringBuffer message = new StringBuffer();
			message.append("The MS Access configuration is not allowed in this operating system.");
			throw new ConfigurationNotAllowedException(message.toString());
		}
	}
}
