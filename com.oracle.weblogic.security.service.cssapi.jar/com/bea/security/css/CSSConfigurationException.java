package com.bea.security.css;

public class CSSConfigurationException extends Exception {
   public CSSConfigurationException() {
   }

   public CSSConfigurationException(String msg) {
      super(msg);
   }

   public CSSConfigurationException(Throwable nested) {
      super(nested);
   }

   public CSSConfigurationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
