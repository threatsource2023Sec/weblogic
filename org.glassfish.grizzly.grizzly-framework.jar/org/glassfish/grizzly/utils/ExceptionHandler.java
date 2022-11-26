package org.glassfish.grizzly.utils;

public interface ExceptionHandler {
   void notifyException(Severity var1, Throwable var2);

   public static enum Severity {
      UNKNOWN,
      CONNECTION,
      TRANSPORT,
      FATAL;
   }
}
