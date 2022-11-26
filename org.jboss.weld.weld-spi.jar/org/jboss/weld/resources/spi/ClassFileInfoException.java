package org.jboss.weld.resources.spi;

public class ClassFileInfoException extends RuntimeException {
   private static final long serialVersionUID = 930782465793465696L;

   public ClassFileInfoException(String message, Throwable cause) {
      super(message, cause);
   }

   public ClassFileInfoException(String message) {
      super(message);
   }

   public ClassFileInfoException(Throwable cause) {
      super(cause);
   }
}
