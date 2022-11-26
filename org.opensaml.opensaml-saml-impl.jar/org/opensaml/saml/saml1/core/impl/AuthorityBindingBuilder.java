package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AuthorityBinding;

public class AuthorityBindingBuilder extends AbstractSAMLObjectBuilder {
   public AuthorityBinding buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding", "saml1");
   }

   public AuthorityBinding buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthorityBindingImpl(namespaceURI, localName, namespacePrefix);
   }
}
