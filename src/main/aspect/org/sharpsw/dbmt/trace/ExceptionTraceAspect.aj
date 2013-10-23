package org.sharpsw.dbmt.trace;

public aspect ExceptionTraceAspect extends BaseExceptionTraceAspect {

	pointcut exceptionTraced() : execution(* org.sharpsw.dbmt..*.*()) || 
	                             execution(org.sharpsw.dbmt..*.new(..)) && 
	                             !within(BaseExceptionTraceAspect+);

}
