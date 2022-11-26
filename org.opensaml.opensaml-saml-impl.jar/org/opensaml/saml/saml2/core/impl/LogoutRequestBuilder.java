package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.LogoutRequest;

public class LogoutRequestBuilder extends AbstractSAMLObjectBuilder {
   public LogoutRequest buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutRequest", "saml2p");
   }

   public LogoutRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new LogoutRequestImpl(namespaceURI, localName, namespacePrefix);
   }
}
