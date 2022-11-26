package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;

public class AttributeConsumingServiceBuilder extends AbstractSAMLObjectBuilder {
   public AttributeConsumingService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeConsumingService", "md");
   }

   public AttributeConsumingService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeConsumingServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
