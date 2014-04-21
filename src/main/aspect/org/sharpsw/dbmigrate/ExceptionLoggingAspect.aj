package org.sharpsw.dbmigrate;

public aspect ExceptionLoggingAspect extends BaseExceptionLoggingAspect {

	pointcut exceptionTraced() : execution(* org.sharpsw.dbmigrate..*.*()) || 
	 	                         execution(org.sharpsw.dbmigrate..*.new(..)) && 
	 	                         !within(BaseExceptionLoggingAspect+);

}
