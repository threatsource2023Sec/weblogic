package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class NotFoundException extends NestedException {
   public NotFoundException(String message, Throwable t) {
      super(message, t);
   }

   public NotFoundException(Throwable t) {
      this("", t);
   }

   public NotFoundException(String message) {
      this(message, (Throwable)null);
   }
}
