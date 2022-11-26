package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AuthnAuthorityDescriptor;

public class AuthnAuthorityDescriptorBuilder extends AbstractSAMLObjectBuilder {
   public AuthnAuthorityDescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AuthnAuthorityDescriptor", "md");
   }

   public AuthnAuthorityDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnAuthorityDescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
