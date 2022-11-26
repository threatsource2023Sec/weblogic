package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class InvalidParameterException extends NestedException {
   public InvalidParameterException(String message, Throwable t) {
      super(message, t);
   }

   public InvalidParameterException(Throwable t) {
      this("", t);
   }

   public InvalidParameterException(String message) {
      this(message, (Throwable)null);
   }
}
