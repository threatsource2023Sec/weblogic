package com.sun.faces.spi;

public class InjectionProviderException extends Exception {
   public InjectionProviderException(Throwable cause) {
      super(cause);
   }

   public InjectionProviderException(String message, Throwable cause) {
      super(message, cause);
   }
}
