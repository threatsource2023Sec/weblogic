package org.hibernate.validator.internal.util.logging;

import java.lang.invoke.MethodHandles;
import org.jboss.logging.Logger;

public final class LoggerFactory {
   public static Log make(MethodHandles.Lookup creationContext) {
      String className = creationContext.lookupClass().getName();
      return (Log)Logger.getMessageLogger(Log.class, className);
   }

   private LoggerFactory() {
   }
}
