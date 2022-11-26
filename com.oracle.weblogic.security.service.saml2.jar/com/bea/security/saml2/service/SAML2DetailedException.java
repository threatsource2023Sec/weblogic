package com.bea.security.saml2.service;

import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.StatusCode;

public class SAML2DetailedException extends SAML2Exception {
   private StatusCode status;

   public SAML2DetailedException(Throwable cause) {
      super(cause);
   }

   public SAML2DetailedException(int httpStatusCode) {
      super(httpStatusCode);
   }

   public SAML2DetailedException(String message) {
      super(message);
   }

   public SAML2DetailedException(String message, Throwable cause) {
      super(message, cause);
   }

   public SAML2DetailedException(String message, Throwable cause, int httpCode) {
      super(message, cause, httpCode);
   }

   public SAML2DetailedException(String message, int httpCode) {
      super(message, httpCode);
   }

   public SAML2DetailedException setStatusCode(String firstLevel, String secondLevel) {
      this.status = (StatusCode)XMLObjectSupport.buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
      this.status.setValue(firstLevel);
      if (secondLevel != null) {
         StatusCode sencondLevelStatus = (StatusCode)XMLObjectSupport.buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
         sencondLevelStatus.setValue(secondLevel);
         this.status.setStatusCode(sencondLevelStatus);
      }

      return this;
   }

   public SAML2DetailedException setStatusCode(String firstLevel) {
      return this.setStatusCode(firstLevel, (String)null);
   }

   public StatusCode getStatus() {
      if (this.status == null) {
         this.setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Responder");
      }

      return this.status;
   }
}
