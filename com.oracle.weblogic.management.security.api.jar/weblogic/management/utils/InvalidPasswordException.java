package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class InvalidPasswordException extends NestedException {
   public InvalidPasswordException(String message, Throwable t) {
      super(message, t);
   }

   public InvalidPasswordException(Throwable t) {
      this("", t);
   }

   public InvalidPasswordException(String message) {
      this(message, (Throwable)null);
   }
}
