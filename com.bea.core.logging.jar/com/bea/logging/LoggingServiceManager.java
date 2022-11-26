package com.bea.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingServiceManager extends LogManager {
   private Map loggerCache = new ConcurrentHashMap();
   private BaseLoggerFactory loggerFactory;

   protected LoggingServiceManager(BaseLoggerFactory lf) {
      this.loggerFactory = lf;
   }

   public Logger getLogger(String name) {
      Logger l = (Logger)this.loggerCache.get(name);
      if (l != null) {
         return l;
      } else {
         synchronized(this) {
            Logger l = super.getLogger(name);
            if (l == null) {
               l = this.loggerFactory.createBaseLogger(name);
               this.loggerCache.put(name, l);
               this.addLogger((Logger)l);
            }

            return (Logger)l;
         }
      }
   }

   public void superReset() {
      this.clearLoggerCache();
      super.reset();
   }

   void clearLoggerCache() {
      this.loggerCache.clear();
   }

   public void reset() {
   }
}
