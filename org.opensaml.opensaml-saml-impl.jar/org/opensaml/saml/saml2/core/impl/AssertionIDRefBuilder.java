package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AssertionIDRef;

public class AssertionIDRefBuilder extends AbstractSAMLObjectBuilder {
   public AssertionIDRef buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionIDRef", "saml2");
   }

   public AssertionIDRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionIDRefImpl(namespaceURI, localName, namespacePrefix);
   }
}
