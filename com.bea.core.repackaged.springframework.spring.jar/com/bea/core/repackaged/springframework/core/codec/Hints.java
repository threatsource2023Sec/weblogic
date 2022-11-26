package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Hints {
   public static final String LOG_PREFIX_HINT = Log.class.getName() + ".PREFIX";
   public static final String SUPPRESS_LOGGING_HINT = Log.class.getName() + ".SUPPRESS_LOGGING";

   public static Map from(String hintName, Object value) {
      return Collections.singletonMap(hintName, value);
   }

   public static Map none() {
      return Collections.emptyMap();
   }

   public static Object getRequiredHint(@Nullable Map hints, String hintName) {
      if (hints == null) {
         throw new IllegalArgumentException("No hints map for required hint '" + hintName + "'");
      } else {
         Object hint = hints.get(hintName);
         if (hint == null) {
            throw new IllegalArgumentException("Hints map must contain the hint '" + hintName + "'");
         } else {
            return hint;
         }
      }
   }

   public static String getLogPrefix(@Nullable Map hints) {
      return hints != null ? (String)hints.getOrDefault(LOG_PREFIX_HINT, "") : "";
   }

   public static boolean isLoggingSuppressed(@Nullable Map hints) {
      return hints != null && (Boolean)hints.getOrDefault(SUPPRESS_LOGGING_HINT, false);
   }

   public static Map merge(Map hints1, Map hints2) {
      if (hints1.isEmpty() && hints2.isEmpty()) {
         return Collections.emptyMap();
      } else if (hints2.isEmpty()) {
         return hints1;
      } else if (hints1.isEmpty()) {
         return hints2;
      } else {
         Map result = new HashMap(hints1.size() + hints2.size());
         result.putAll(hints1);
         result.putAll(hints2);
         return result;
      }
   }

   public static Map merge(Map hints, String hintName, Object hintValue) {
      if (hints.isEmpty()) {
         return Collections.singletonMap(hintName, hintValue);
      } else {
         Map result = new HashMap(hints.size() + 1);
         result.putAll(hints);
         result.put(hintName, hintValue);
         return result;
      }
   }
}
