package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.weaver.Dump;
import java.io.File;

public class SimpleCacheFactory {
   public static final String CACHE_ENABLED_PROPERTY = "aj.weaving.cache.enabled";
   public static final String CACHE_DIR = "aj.weaving.cache.dir";
   public static final String CACHE_IMPL = "aj.weaving.cache.impl";
   public static final String PATH_DEFAULT = "/tmp/";
   public static final boolean BYDEFAULT = false;
   public static String path = "/tmp/";
   public static Boolean enabled = false;
   private static boolean determinedIfEnabled = false;
   private static SimpleCache lacache = null;

   public static synchronized SimpleCache createSimpleCache() {
      if (lacache == null) {
         if (!determinedIfEnabled) {
            determineIfEnabled();
         }

         if (!enabled) {
            return null;
         }

         try {
            path = System.getProperty("aj.weaving.cache.dir");
            if (path == null) {
               path = "/tmp/";
            }
         } catch (Throwable var1) {
            path = "/tmp/";
            var1.printStackTrace();
            Dump.dumpWithException(var1);
         }

         File f = new File(path);
         if (!f.exists()) {
            f.mkdir();
         }

         lacache = new SimpleCache(path, enabled);
      }

      return lacache;
   }

   private static void determineIfEnabled() {
      try {
         String property = System.getProperty("aj.weaving.cache.enabled");
         if (property == null) {
            enabled = false;
         } else if (property.equalsIgnoreCase("true")) {
            String impl = System.getProperty("aj.weaving.cache.impl");
            if ("shared".equals(impl)) {
               enabled = true;
            } else {
               enabled = false;
            }
         } else {
            enabled = false;
         }
      } catch (Throwable var2) {
         enabled = false;
         System.err.println("Error creating cache");
         var2.printStackTrace();
         Dump.dumpWithException(var2);
      }

      determinedIfEnabled = true;
   }

   public static boolean isEnabled() {
      if (!determinedIfEnabled) {
         determineIfEnabled();
      }

      return enabled;
   }
}
