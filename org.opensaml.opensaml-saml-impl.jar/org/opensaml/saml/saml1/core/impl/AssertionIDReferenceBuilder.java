package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AssertionIDReference;

public class AssertionIDReferenceBuilder extends AbstractSAMLObjectBuilder {
   public AssertionIDReference buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference", "saml1");
   }

   public AssertionIDReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionIDReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
