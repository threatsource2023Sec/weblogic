package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;

public class AssertionBuilder extends AbstractSAMLObjectBuilder {
   public AssertionImpl buildObject() {
      return new AssertionImpl("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion", "saml1");
   }

   public AssertionImpl buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionImpl(namespaceURI, localName, namespacePrefix);
   }
}
