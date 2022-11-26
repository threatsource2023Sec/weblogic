package org.opensaml.saml.saml1.binding.encoding.impl;

import org.opensaml.saml.common.binding.encoding.SAMLMessageEncoder;

public class HTTPSOAP11Encoder extends org.opensaml.soap.soap11.encoder.http.impl.HTTPSOAP11Encoder implements SAMLMessageEncoder {
   protected String getSOAPAction() {
      return "http://www.oasis-open.org/committees/security";
   }

   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:1.0:bindings:SOAP-binding";
   }
}
