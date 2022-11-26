package weblogic.utils.classloaders.debug;

import java.util.Map;
import java.util.regex.Pattern;
import weblogic.diagnostics.debug.DebugLogger;

public final class FilteredDebugLogger {
   private final DebugLogger delegateDebugLogger;
   private final String[] criterionNames;
   private final Pattern[] criterionPatterns;
   private final String PARAM_DUMP_STACKTRACE = "DST";
   private final boolean dumpStackTrace;

   private FilteredDebugLogger(DebugLogger delegateDebugLogger, String... criterionNames) {
      this.delegateDebugLogger = delegateDebugLogger;
      Map params = delegateDebugLogger.getDebugParameters();
      this.criterionNames = new String[criterionNames.length];
      this.criterionPatterns = new Pattern[criterionNames.length];

      for(int i = 0; i < criterionNames.length; ++i) {
         this.criterionNames[i] = criterionNames[i];
         String pattern = (String)params.get(criterionNames[i]);
         this.criterionPatterns[i] = Pattern.compile(pattern != null ? pattern : ".*");
      }

      this.dumpStackTrace = Boolean.parseBoolean((String)params.get("DST"));
   }

   public static FilteredDebugLogger getDebugLogger(String debugLoggerName, String... criterionNames) {
      return new FilteredDebugLogger(DebugLogger.getDebugLogger(debugLoggerName), criterionNames);
   }

   public final boolean isDebugEnabled(Object... criterion) {
      if (!this.delegateDebugLogger.isDebugEnabled()) {
         return false;
      } else if (criterion.length != this.criterionPatterns.length) {
         throw new IllegalArgumentException("This API must have exactly " + this.criterionPatterns.length + " arguments");
      } else {
         for(int i = 0; i < this.criterionPatterns.length; ++i) {
            if (!this.criterionPatterns[i].matcher(criterion[i].toString()).matches()) {
               return false;
            }
         }

         return true;
      }
   }

   public final void debug(String msg, Object... criterion) {
      if (criterion.length != this.criterionNames.length) {
         throw new IllegalArgumentException("This API must have exactly " + this.criterionNames.length + 1 + " arguments");
      } else {
         if (this.criterionNames.length > 0) {
            StringBuilder builder = new StringBuilder();

            for(int i = 0; i < this.criterionNames.length; ++i) {
               builder.append('[').append(this.criterionNames[i]).append('=').append(criterion[i].toString()).append(']');
            }

            msg = builder.append(' ').append(msg).toString();
         }

         if (this.dumpStackTrace) {
            this.delegateDebugLogger.debug(msg, new TracingException());
         } else {
            this.delegateDebugLogger.debug(msg);
         }

      }
   }

   public static class TracingException extends Exception {
      private TracingException() {
         super("Debug Exception to trace code flow");
      }

      // $FF: synthetic method
      TracingException(Object x0) {
         this();
      }
   }
}
