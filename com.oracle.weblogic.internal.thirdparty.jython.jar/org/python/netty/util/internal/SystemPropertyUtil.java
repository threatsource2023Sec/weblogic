package org.python.netty.util.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class SystemPropertyUtil {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);

   public static boolean contains(String key) {
      return get(key) != null;
   }

   public static String get(String key) {
      return get(key, (String)null);
   }

   public static String get(final String key, String def) {
      if (key == null) {
         throw new NullPointerException("key");
      } else if (key.isEmpty()) {
         throw new IllegalArgumentException("key must not be empty.");
      } else {
         String value = null;

         try {
            if (System.getSecurityManager() == null) {
               value = System.getProperty(key);
            } else {
               value = (String)AccessController.doPrivileged(new PrivilegedAction() {
                  public String run() {
                     return System.getProperty(key);
                  }
               });
            }
         } catch (SecurityException var4) {
            logger.warn("Unable to retrieve a system property '{}'; default values will be used.", key, var4);
         }

         return value == null ? def : value;
      }
   }

   public static boolean getBoolean(String key, boolean def) {
      String value = get(key);
      if (value == null) {
         return def;
      } else {
         value = value.trim().toLowerCase();
         if (value.isEmpty()) {
            return true;
         } else if (!"true".equals(value) && !"yes".equals(value) && !"1".equals(value)) {
            if (!"false".equals(value) && !"no".equals(value) && !"0".equals(value)) {
               logger.warn("Unable to parse the boolean system property '{}':{} - using the default value: {}", key, value, def);
               return def;
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   public static int getInt(String key, int def) {
      String value = get(key);
      if (value == null) {
         return def;
      } else {
         value = value.trim();

         try {
            return Integer.parseInt(value);
         } catch (Exception var4) {
            logger.warn("Unable to parse the integer system property '{}':{} - using the default value: {}", key, value, def);
            return def;
         }
      }
   }

   public static long getLong(String key, long def) {
      String value = get(key);
      if (value == null) {
         return def;
      } else {
         value = value.trim();

         try {
            return Long.parseLong(value);
         } catch (Exception var5) {
            logger.warn("Unable to parse the long integer system property '{}':{} - using the default value: {}", key, value, def);
            return def;
         }
      }
   }

   private SystemPropertyUtil() {
   }
}
