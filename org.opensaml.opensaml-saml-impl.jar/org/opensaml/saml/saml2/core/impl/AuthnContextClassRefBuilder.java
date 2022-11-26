package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;

public class AuthnContextClassRefBuilder extends AbstractSAMLObjectBuilder {
   public AuthnContextClassRef buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextClassRef", "saml2");
   }

   public AuthnContextClassRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnContextClassRefImpl(namespaceURI, localName, namespacePrefix);
   }
}
