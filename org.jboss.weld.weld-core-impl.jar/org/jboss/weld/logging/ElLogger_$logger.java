package org.jboss.weld.logging;

import java.io.Serializable;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

public class ElLogger_$logger extends DelegatingBasicLogger implements ElLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ElLogger_$logger.class.getName();
   private static final String nullExpressionFactory = "WELD-001001: Cannot pass null expressionFactory";
   private static final String propertyLookup = "WELD-001002: Looking for EL property {0}";
   private static final String propertyResolved = "WELD-001003: EL property {0} resolved to {1}";
   private static final String catchingDebug = "Catching";

   public ElLogger_$logger(Logger log) {
      super(log);
   }

   protected String nullExpressionFactory$str() {
      return "WELD-001001: Cannot pass null expressionFactory";
   }

   public final IllegalArgumentException nullExpressionFactory() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.nullExpressionFactory$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void propertyLookup(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.propertyLookup$str(), param1);
   }

   protected String propertyLookup$str() {
      return "WELD-001002: Looking for EL property {0}";
   }

   public final void propertyResolved(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.propertyResolved$str(), param1, param2);
   }

   protected String propertyResolved$str() {
      return "WELD-001003: EL property {0} resolved to {1}";
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
