package org.jboss.weld.util;

import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.logging.ValidatorLogger;

public class Preconditions {
   private Preconditions() {
   }

   public static void checkArgumentNotNull(Object reference, String argumentName) {
      if (reference == null) {
         throw ValidatorLogger.LOG.argumentNull(argumentName);
      }
   }

   public static void checkArgumentNotNull(Object reference) {
      if (reference == null) {
         throw ValidatorLogger.LOG.argumentNull();
      }
   }

   public static void checkNotNull(Object reference) {
      if (reference == null) {
         throw new NullPointerException();
      }
   }

   public static void checkArgument(boolean condition, Object argument) {
      if (!condition) {
         throw new IllegalArgumentException("Illegal argument " + (argument == null ? "null" : argument.toString()));
      }
   }

   public static void checkArgument(boolean condition, String message) {
      if (!condition) {
         throw new IllegalArgumentException(message);
      }
   }
}
