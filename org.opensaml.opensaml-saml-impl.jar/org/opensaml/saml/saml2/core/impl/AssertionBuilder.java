package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Assertion;

public class AssertionBuilder extends AbstractSAMLObjectBuilder {
   public Assertion buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion", "saml2");
   }

   public Assertion buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionImpl(namespaceURI, localName, namespacePrefix);
   }
}
