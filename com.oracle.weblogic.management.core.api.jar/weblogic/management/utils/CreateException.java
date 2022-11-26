package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class CreateException extends NestedException {
   public CreateException(String message, Throwable t) {
      super(message, t);
   }

   public CreateException(Throwable t) {
      this("", t);
   }

   public CreateException(String message) {
      this(message, (Throwable)null);
   }
}
