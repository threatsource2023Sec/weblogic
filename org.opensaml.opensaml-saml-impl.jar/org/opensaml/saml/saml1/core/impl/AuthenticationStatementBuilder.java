package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AuthenticationStatement;

public class AuthenticationStatementBuilder extends AbstractSAMLObjectBuilder {
   public AuthenticationStatement buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement", "saml1");
   }

   public AuthenticationStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthenticationStatementImpl(namespaceURI, localName, namespacePrefix);
   }
}
