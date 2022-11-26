package com.bea.adaptive.harvester.jmx;

public class LogSupport {
   public static void logUnexpectedException(String harvesterName, String context, Throwable x) {
      HarvesterJMXLogger.logUnexpectedException(harvesterName, context, x);
   }

   public static void logUnexpectedCondition(String harvesterName, String context) {
      HarvesterJMXLogger.logUnexpectedCondition(harvesterName, context);
   }

   public static void assertCondition(boolean value, String debugText) {
      if (!value) {
         throw new AssertionError(debugText);
      }
   }
}
