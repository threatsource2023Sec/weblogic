package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AuthzService;

public class AuthzServiceBuilder extends AbstractSAMLObjectBuilder {
   public AuthzService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AuthzService", "md");
   }

   public AuthzService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthzServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
