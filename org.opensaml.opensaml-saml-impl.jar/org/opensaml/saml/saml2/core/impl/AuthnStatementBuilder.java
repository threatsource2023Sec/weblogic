package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnStatement;

public class AuthnStatementBuilder extends AbstractSAMLObjectBuilder {
   public AuthnStatement buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement", "saml2");
   }

   public AuthnStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnStatementImpl(namespaceURI, localName, namespacePrefix);
   }
}
