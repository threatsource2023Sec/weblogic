package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class InvalidCursorException extends NestedException {
   public InvalidCursorException(String message, Throwable t) {
      super(message, t);
   }

   public InvalidCursorException(Throwable t) {
      this("", t);
   }

   public InvalidCursorException(String message) {
      this(message, (Throwable)null);
   }
}
