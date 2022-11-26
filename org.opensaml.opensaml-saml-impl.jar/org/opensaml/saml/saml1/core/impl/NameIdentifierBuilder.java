package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.NameIdentifier;

public class NameIdentifierBuilder extends AbstractSAMLObjectBuilder {
   public NameIdentifier buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier", "saml1");
   }

   public NameIdentifier buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NameIdentifierImpl(namespaceURI, localName, namespacePrefix);
   }
}
