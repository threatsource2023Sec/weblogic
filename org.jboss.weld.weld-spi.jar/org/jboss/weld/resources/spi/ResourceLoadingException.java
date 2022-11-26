package org.jboss.weld.resources.spi;

public class ResourceLoadingException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public ResourceLoadingException() {
   }

   public ResourceLoadingException(String message, Throwable throwable) {
      super(message, throwable);
   }

   public ResourceLoadingException(String message) {
      super(message);
   }

   public ResourceLoadingException(Throwable throwable) {
      super(throwable);
   }
}
