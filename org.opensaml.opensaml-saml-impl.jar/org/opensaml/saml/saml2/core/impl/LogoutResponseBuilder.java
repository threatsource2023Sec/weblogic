package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.LogoutResponse;

public class LogoutResponseBuilder extends AbstractSAMLObjectBuilder {
   public LogoutResponse buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutResponse", "saml2p");
   }

   public LogoutResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new LogoutResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
