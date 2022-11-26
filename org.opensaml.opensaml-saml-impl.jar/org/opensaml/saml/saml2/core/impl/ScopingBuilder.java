package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Scoping;

public class ScopingBuilder extends AbstractSAMLObjectBuilder {
   public Scoping buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Scoping", "saml2p");
   }

   public Scoping buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ScopingImpl(namespaceURI, localName, namespacePrefix);
   }
}
