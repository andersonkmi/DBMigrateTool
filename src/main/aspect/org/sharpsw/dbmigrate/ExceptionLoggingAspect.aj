package org.sharpsw.dbmigrate;

import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreatorLoggingAspect;
import org.sharpsw.dbmigrate.data.DatabaseDataLoaderLoggingAspect;

public aspect ExceptionLoggingAspect extends BaseExceptionLoggingAspect {

	pointcut exceptionTraced() : execution(* org.sharpsw.dbmigrate..*.*(..)) ||	                           
	 	                         execution(org.sharpsw.dbmigrate..*.new(..)) && 
	 	                         !within(BaseExceptionLoggingAspect+) &&
	 	                         !within(DatabaseDataLoaderLoggingAspect) &&
	 	                         !within(DatabaseConnectionCreatorLoggingAspect);

}
