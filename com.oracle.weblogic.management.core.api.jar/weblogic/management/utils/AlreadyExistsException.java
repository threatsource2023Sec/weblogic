package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class AlreadyExistsException extends NestedException {
   public AlreadyExistsException(String message, Throwable t) {
      super(message, t);
   }

   public AlreadyExistsException(Throwable t) {
      this("", t);
   }

   public AlreadyExistsException(String message) {
      this(message, (Throwable)null);
   }
}
