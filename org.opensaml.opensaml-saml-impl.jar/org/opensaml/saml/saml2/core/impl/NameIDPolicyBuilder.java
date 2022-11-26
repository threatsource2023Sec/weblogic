package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameIDPolicy;

public class NameIDPolicyBuilder extends AbstractSAMLObjectBuilder {
   public NameIDPolicy buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDPolicy", "saml2p");
   }

   public NameIDPolicy buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NameIDPolicyImpl(namespaceURI, localName, namespacePrefix);
   }
}
