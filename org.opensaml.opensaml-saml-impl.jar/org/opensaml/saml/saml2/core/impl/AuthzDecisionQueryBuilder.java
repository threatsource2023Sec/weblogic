package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthzDecisionQuery;

public class AuthzDecisionQueryBuilder extends AbstractSAMLObjectBuilder {
   public AuthzDecisionQuery buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthzDecisionQuery", "saml2p");
   }

   public AuthzDecisionQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthzDecisionQueryImpl(namespaceURI, localName, namespacePrefix);
   }
}
