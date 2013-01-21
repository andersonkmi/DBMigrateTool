package org.sharpsw.dbmt.trace;

import org.apache.log4j.Logger;
import org.aspectj.lang.Signature;

public abstract aspect BaseExceptionTraceAspect {
	private static final Logger logger = Logger.getLogger("org.sharpsw.core.ExceptionLogging");
	private ThreadLocal<Throwable> lastLoggedException = new ThreadLocal<Throwable>();
	
	abstract pointcut exceptionTraced();
	
	after() throwing(Throwable exception) : exceptionTraced() {
		if(lastLoggedException.get() != exception) {
			lastLoggedException.set(exception);
			Signature sig = thisJoinPointStaticPart.getSignature();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("Exception raised when invoking [").append(sig.toShortString()).append("]");
			logger.error(buffer.toString(), exception);
		}
	}
}
