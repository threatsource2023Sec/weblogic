package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthenticatingAuthority;

public class AuthenticatingAuthorityBuilder extends AbstractSAMLObjectBuilder {
   public AuthenticatingAuthority buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthenticatingAuthority", "saml2");
   }

   public AuthenticatingAuthority buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthenticatingAuthorityImpl(namespaceURI, localName, namespacePrefix);
   }
}
