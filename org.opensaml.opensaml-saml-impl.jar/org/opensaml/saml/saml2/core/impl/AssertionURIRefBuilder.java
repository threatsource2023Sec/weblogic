package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AssertionURIRef;

public class AssertionURIRefBuilder extends AbstractSAMLObjectBuilder {
   public AssertionURIRef buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionURIRef", "saml2");
   }

   public AssertionURIRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionURIRefImpl(namespaceURI, localName, namespacePrefix);
   }
}
