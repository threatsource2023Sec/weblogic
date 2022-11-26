package com.sun.faces.spi;

public class InjectionProviderException extends Exception {
   private static final long serialVersionUID = -9118556608529051203L;

   public InjectionProviderException(Throwable cause) {
      super(cause);
   }

   public InjectionProviderException(String message, Throwable cause) {
      super(message, cause);
   }
}
