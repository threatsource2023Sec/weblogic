package org.opensaml.saml.saml2.binding.encoding.impl;

import org.opensaml.saml.common.binding.encoding.SAMLMessageEncoder;

public class HTTPSOAP11Encoder extends org.opensaml.soap.soap11.encoder.http.impl.HTTPSOAP11Encoder implements SAMLMessageEncoder {
   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:SOAP";
   }

   protected String getSOAPAction() {
      return "http://www.oasis-open.org/committees/security";
   }
}
