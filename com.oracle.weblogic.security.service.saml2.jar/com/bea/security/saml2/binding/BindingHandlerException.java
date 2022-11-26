package com.bea.security.saml2.binding;

import com.bea.security.saml2.service.SAML2DetailedException;

public class BindingHandlerException extends SAML2DetailedException {
   public BindingHandlerException(int httpStatusCode) {
      super(httpStatusCode);
   }

   public BindingHandlerException(String message, int httpCode) {
      super(message, httpCode);
   }

   public BindingHandlerException(String message, Throwable cause, int httpCode) {
      super(message, cause, httpCode);
   }

   public BindingHandlerException(String message, Throwable cause) {
      super(message, cause);
   }

   public BindingHandlerException(String message) {
      super(message);
   }

   public BindingHandlerException(Throwable cause) {
      super(cause);
   }
}
