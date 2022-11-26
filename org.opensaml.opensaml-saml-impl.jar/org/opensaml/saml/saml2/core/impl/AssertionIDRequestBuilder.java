package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AssertionIDRequest;

public class AssertionIDRequestBuilder extends AbstractSAMLObjectBuilder {
   public AssertionIDRequest buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AssertionIDRequest", "saml2p");
   }

   public AssertionIDRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionIDRequestImpl(namespaceURI, localName, namespacePrefix);
   }
}
