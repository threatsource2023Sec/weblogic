package org.jboss.weld.logging;

import java.io.Serializable;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

public class WeldLogger_$logger extends DelegatingBasicLogger implements WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = WeldLogger_$logger.class.getName();
   private static final String catchingDebug = "Catching";

   public WeldLogger_$logger(Logger log) {
      super(log);
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
