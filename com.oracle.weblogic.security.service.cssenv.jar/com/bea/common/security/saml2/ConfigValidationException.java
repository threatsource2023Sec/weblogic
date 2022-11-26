package com.bea.common.security.saml2;

public class ConfigValidationException extends Exception {
   public ConfigValidationException(String message) {
      super(message);
   }

   public ConfigValidationException(String message, Throwable th) {
      super(message, th);
   }
}
