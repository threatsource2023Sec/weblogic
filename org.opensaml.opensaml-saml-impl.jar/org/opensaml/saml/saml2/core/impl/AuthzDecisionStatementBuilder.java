package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;

public class AuthzDecisionStatementBuilder extends AbstractSAMLObjectBuilder {
   public AuthzDecisionStatement buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatement", "saml2");
   }

   public AuthzDecisionStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthzDecisionStatementImpl(namespaceURI, localName, namespacePrefix);
   }
}
