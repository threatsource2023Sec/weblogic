package org.glassfish.grizzly.memory;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

class MemoryManagerInitializer {
   private static final String DMM_PROP_NAME = "org.glassfish.grizzly.DEFAULT_MEMORY_MANAGER";
   private static final Logger LOGGER = Grizzly.logger(MemoryManagerInitializer.class);

   static MemoryManager initManager() {
      MemoryManager mm = initMemoryManagerViaFactory();
      return mm != null ? mm : initMemoryManagerFallback();
   }

   private static MemoryManager initMemoryManagerViaFactory() {
      String dmmfClassName = System.getProperty("org.glassfish.grizzly.MEMORY_MANAGER_FACTORY");
      if (dmmfClassName != null) {
         DefaultMemoryManagerFactory mmf = (DefaultMemoryManagerFactory)newInstance(dmmfClassName);
         if (mmf != null) {
            return mmf.createMemoryManager();
         }
      }

      return null;
   }

   private static MemoryManager initMemoryManagerFallback() {
      String className = System.getProperty("org.glassfish.grizzly.DEFAULT_MEMORY_MANAGER");
      MemoryManager mm = (MemoryManager)newInstance(className);
      return (MemoryManager)(mm != null ? mm : new HeapMemoryManager());
   }

   private static Object newInstance(String className) {
      if (className == null) {
         return null;
      } else {
         try {
            Class clazz = Class.forName(className, true, MemoryManager.class.getClassLoader());
            return clazz.newInstance();
         } catch (Exception var2) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Unable to load or create a new instance of Class {0}.  Cause: {1}", new Object[]{className, var2.getMessage()});
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, var2.toString(), var2);
            }

            return null;
         }
      }
   }
}
