package org.sharpsw.dbmigrate;

import org.apache.log4j.Logger;
import org.aspectj.lang.Signature;

public abstract aspect BaseExceptionLoggingAspect {
	private static final Logger logger = Logger.getLogger(BaseExceptionLoggingAspect.class);
	private ThreadLocal<Throwable> lastLoggedException = new ThreadLocal<Throwable>();

	abstract pointcut exceptionTraced();

	after() throwing(Throwable exception) : exceptionTraced() {
		if (lastLoggedException.get() != exception) {
			lastLoggedException.set(exception);
			Signature sig = thisJoinPointStaticPart.getSignature();

			logger.error(String.format("Exception raised when invoking '%s': %s", sig.toShortString(), exception.getMessage()), exception);
		}
	}
}
