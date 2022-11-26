package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnContext;

public class AuthnContextBuilder extends AbstractSAMLObjectBuilder {
   public AuthnContext buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContext", "saml2");
   }

   public AuthnContext buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnContextImpl(namespaceURI, localName, namespacePrefix);
   }
}
