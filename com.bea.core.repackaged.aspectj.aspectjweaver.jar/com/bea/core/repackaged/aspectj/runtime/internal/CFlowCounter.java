package com.bea.core.repackaged.aspectj.runtime.internal;

import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadCounter;
import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadStackFactory;
import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl;
import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl11;

public class CFlowCounter {
   private static ThreadStackFactory tsFactory;
   private ThreadCounter flowHeightHandler;

   public CFlowCounter() {
      this.flowHeightHandler = tsFactory.getNewThreadCounter();
   }

   public void inc() {
      this.flowHeightHandler.inc();
   }

   public void dec() {
      this.flowHeightHandler.dec();
      if (!this.flowHeightHandler.isNotZero()) {
         this.flowHeightHandler.removeThreadCounter();
      }

   }

   public boolean isValid() {
      return this.flowHeightHandler.isNotZero();
   }

   private static ThreadStackFactory getThreadLocalStackFactory() {
      return new ThreadStackFactoryImpl();
   }

   private static ThreadStackFactory getThreadLocalStackFactoryFor11() {
      return new ThreadStackFactoryImpl11();
   }

   private static void selectFactoryForVMVersion() {
      String override = getSystemPropertyWithoutSecurityException("aspectj.runtime.cflowstack.usethreadlocal", "unspecified");
      boolean useThreadLocalImplementation = false;
      if (override.equals("unspecified")) {
         String v = System.getProperty("java.class.version", "0.0");
         useThreadLocalImplementation = v.compareTo("46.0") >= 0;
      } else {
         useThreadLocalImplementation = override.equals("yes") || override.equals("true");
      }

      if (useThreadLocalImplementation) {
         tsFactory = getThreadLocalStackFactory();
      } else {
         tsFactory = getThreadLocalStackFactoryFor11();
      }

   }

   private static String getSystemPropertyWithoutSecurityException(String aPropertyName, String aDefaultValue) {
      try {
         return System.getProperty(aPropertyName, aDefaultValue);
      } catch (SecurityException var3) {
         return aDefaultValue;
      }
   }

   public static String getThreadStackFactoryClassName() {
      return tsFactory.getClass().getName();
   }

   static {
      selectFactoryForVMVersion();
   }
}
