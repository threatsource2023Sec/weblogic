package com.bea.logging;

import java.util.logging.Filter;

public abstract class LogFilterFactory {
   private static final boolean DEBUG = false;

   private static LogFilterFactory createSingleton() {
      try {
         Class clz = Class.forName("weblogic.diagnostics.logging.QueryFilterFactory");
         return (LogFilterFactory)clz.newInstance();
      } catch (Exception var1) {
         return null;
      }
   }

   static LogFilterFactory getInstance() {
      return LogFilterFactory.SingletonInitializer.INSTANCE;
   }

   public abstract Filter createFilter(String var1);

   private static class SingletonInitializer {
      private static final LogFilterFactory INSTANCE = LogFilterFactory.createSingleton();
   }
}
