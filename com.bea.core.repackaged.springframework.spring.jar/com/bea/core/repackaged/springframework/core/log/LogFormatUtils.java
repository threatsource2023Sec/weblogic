package com.bea.core.repackaged.springframework.core.log;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.function.Function;

public abstract class LogFormatUtils {
   public static String formatValue(@Nullable Object value, boolean limitLength) {
      if (value == null) {
         return "";
      } else {
         String str;
         if (value instanceof CharSequence) {
            str = "\"" + value + "\"";
         } else {
            try {
               str = value.toString();
            } catch (Throwable var4) {
               str = var4.toString();
            }
         }

         return limitLength && str.length() > 100 ? str.substring(0, 100) + " (truncated)..." : str;
      }
   }

   public static void traceDebug(Log logger, Function messageFactory) {
      if (logger.isDebugEnabled()) {
         boolean traceEnabled = logger.isTraceEnabled();
         String logMessage = (String)messageFactory.apply(traceEnabled);
         if (traceEnabled) {
            logger.trace(logMessage);
         } else {
            logger.debug(logMessage);
         }
      }

   }
}
