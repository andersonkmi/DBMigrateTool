package org.sharpsw.dbmt.config;

public interface IAppConfiguration {
	public void setConcurrentThreads(int number);
	public int getConcurrentThreads();
	
	public void setLoggingPropertiesFile(String file);
	public String getLoggingPropertiesFile();
	
	public void setLoggingPropertiesFileRefreshInterval(int seconds);
	public int getLoggingPropertiesFileRefreshInterval();
}
