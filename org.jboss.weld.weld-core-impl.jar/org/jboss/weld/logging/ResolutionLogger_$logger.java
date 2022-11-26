package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.WeldException;

public class ResolutionLogger_$logger extends DelegatingBasicLogger implements ResolutionLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ResolutionLogger_$logger.class.getName();
   private static final String cannotExtractRawType = "WELD-001601: Cannot extract rawType from {0}";
   private static final String cannotCreateQualifierInstanceValues = "WELD-001602: Cannot create qualifier instance model for {0}\n\tat {1}\n  StackTrace:";
   private static final String catchingDebug = "Catching";

   public ResolutionLogger_$logger(Logger log) {
      super(log);
   }

   protected String cannotExtractRawType$str() {
      return "WELD-001601: Cannot extract rawType from {0}";
   }

   public final IllegalArgumentException cannotExtractRawType(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.cannotExtractRawType$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotCreateQualifierInstanceValues$str() {
      return "WELD-001602: Cannot create qualifier instance model for {0}\n\tat {1}\n  StackTrace:";
   }

   public final WeldException cannotCreateQualifierInstanceValues(Object annotation, Object stackElement, Exception cause) {
      WeldException result = new WeldException(MessageFormat.format(this.cannotCreateQualifierInstanceValues$str(), annotation, stackElement), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
