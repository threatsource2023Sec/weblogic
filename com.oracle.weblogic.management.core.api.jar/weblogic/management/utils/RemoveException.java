package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class RemoveException extends NestedException {
   public RemoveException(String message, Throwable t) {
      super(message, t);
   }

   public RemoveException(Throwable t) {
      this("", t);
   }

   public RemoveException(String message) {
      this(message, (Throwable)null);
   }
}
