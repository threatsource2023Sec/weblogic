package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnRequest;

public class AuthnRequestBuilder extends AbstractSAMLObjectBuilder {
   public AuthnRequest buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequest", "saml2p");
   }

   public AuthnRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnRequestImpl(namespaceURI, localName, namespacePrefix);
   }
}
