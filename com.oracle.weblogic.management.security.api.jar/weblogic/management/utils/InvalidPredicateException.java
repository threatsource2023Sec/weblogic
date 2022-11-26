package weblogic.management.utils;

import weblogic.utils.NestedException;

public final class InvalidPredicateException extends NestedException {
   public InvalidPredicateException(String message, Throwable t) {
      super(message, t);
   }

   public InvalidPredicateException(Throwable t) {
      super(t);
   }

   public InvalidPredicateException(String message) {
      super(message);
   }
}
