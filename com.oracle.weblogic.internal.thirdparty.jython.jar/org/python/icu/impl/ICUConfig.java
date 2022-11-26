package org.python.icu.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.Properties;

public class ICUConfig {
   public static final String CONFIG_PROPS_FILE = "/org/python/icu/ICUConfig.properties";
   private static final Properties CONFIG_PROPS = new Properties();

   public static String get(String name) {
      return get(name, (String)null);
   }

   public static String get(String name, String def) {
      String val = null;
      final String fname = name;
      if (System.getSecurityManager() != null) {
         try {
            val = (String)AccessController.doPrivileged(new PrivilegedAction() {
               public String run() {
                  return System.getProperty(fname);
               }
            });
         } catch (AccessControlException var5) {
         }
      } else {
         val = System.getProperty(name);
      }

      if (val == null) {
         val = CONFIG_PROPS.getProperty(name, def);
      }

      return val;
   }

   static {
      try {
         InputStream is = ICUData.getStream("/org/python/icu/ICUConfig.properties");
         if (is != null) {
            try {
               CONFIG_PROPS.load(is);
            } finally {
               is.close();
            }
         }
      } catch (MissingResourceException var6) {
      } catch (IOException var7) {
      }

   }
}
