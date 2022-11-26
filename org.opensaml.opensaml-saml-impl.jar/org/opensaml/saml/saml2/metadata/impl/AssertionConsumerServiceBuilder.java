package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;

public class AssertionConsumerServiceBuilder extends AbstractSAMLObjectBuilder {
   public AssertionConsumerService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AssertionConsumerService", "md");
   }

   public AssertionConsumerService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionConsumerServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
