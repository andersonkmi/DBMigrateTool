package org.sharpsw.dbmigrate.trace;

import org.apache.log4j.Logger;
import org.aspectj.lang.Signature;

public aspect ToolTracingAspect pertypewithin(org.sharpsw.dbmt..*) {
    private Logger logger;

    pointcut traced(): execution(* *.*(..)) &&
            !within(ToolTracingAspect) &&
            !within(BaseExceptionTraceAspect+);

    after() returning: staticinitialization(*) {
        logger = Logger.getLogger(getWithinTypeName());
    }

    before(): traced() && !execution(* Object.*(..)) {
        Signature signature = thisJoinPointStaticPart.getSignature();
        StringBuffer buffer = new StringBuffer();
        buffer.append("Entering ").append(signature.toLongString());

        if (logger.isTraceEnabled()) {
            logger.trace(buffer.toString());
        }
    }

    after(): traced() && !execution(* Object.*(..)) {
        Signature signature = thisJoinPointStaticPart.getSignature();
        StringBuffer buffer = new StringBuffer();
        buffer.append("Leaving ").append(signature.toLongString());

        if (logger.isTraceEnabled()) {
            logger.trace(buffer.toString());
        }
    }
}
