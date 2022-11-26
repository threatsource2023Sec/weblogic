package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnQuery;

public class AuthnQueryBuilder extends AbstractSAMLObjectBuilder {
   public AuthnQuery buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnQuery", "saml2p");
   }

   public AuthnQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnQueryImpl(namespaceURI, localName, namespacePrefix);
   }
}
