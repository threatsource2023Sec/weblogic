package com.bea.security.saml2.service;

public class SAML2Exception extends Exception {
   private int httpStatusCode = 400;

   public SAML2Exception(String message, int httpCode) {
      super(message);
      this.httpStatusCode = httpCode;
   }

   public SAML2Exception(String message, Throwable cause, int httpCode) {
      super(message, cause);
      this.httpStatusCode = httpCode;
   }

   public SAML2Exception(Throwable cause) {
      super(cause);
   }

   public SAML2Exception(String message) {
      super(message);
   }

   public SAML2Exception(String message, Throwable cause) {
      super(message, cause);
   }

   public SAML2Exception(int httpStatusCode) {
      this.httpStatusCode = httpStatusCode;
   }

   public SAML2Exception() {
   }

   public int getHttpStatusCode() {
      return this.httpStatusCode;
   }
}
