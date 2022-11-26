package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AuthenticationQuery;

public class AuthenticationQueryBuilder extends AbstractSAMLObjectBuilder {
   public AuthenticationQuery buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery", "saml1p");
   }

   public AuthenticationQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthenticationQueryImpl(namespaceURI, localName, namespacePrefix);
   }
}
