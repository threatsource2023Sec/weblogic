package org.jboss.weld.logging;

import java.io.Serializable;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

public class VersionLogger_$logger extends DelegatingBasicLogger implements VersionLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = VersionLogger_$logger.class.getName();
   private static final String version = "WELD-000900: {0}";
   private static final String catchingDebug = "Catching";

   public VersionLogger_$logger(Logger log) {
      super(log);
   }

   public final void version(Object info) {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.version$str(), info);
   }

   protected String version$str() {
      return "WELD-000900: {0}";
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
