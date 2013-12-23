package org.sharpsw.dbmigrate.trace;

public aspect ExceptionTraceAspect extends BaseExceptionTraceAspect {

	pointcut exceptionTraced() : execution(* org.sharpsw.dbmt..*.*()) || 
	                             execution(org.sharpsw.dbmt..*.new(..)) && 
	                             !within(BaseExceptionTraceAspect+);

}
