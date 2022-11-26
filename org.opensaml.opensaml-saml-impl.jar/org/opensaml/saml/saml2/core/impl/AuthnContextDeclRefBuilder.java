package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public class AuthnContextDeclRefBuilder extends AbstractSAMLObjectBuilder {
   public AuthnContextDeclRef buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDeclRef", "saml2");
   }

   public AuthnContextDeclRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnContextDeclRefImpl(namespaceURI, localName, namespacePrefix);
   }
}
