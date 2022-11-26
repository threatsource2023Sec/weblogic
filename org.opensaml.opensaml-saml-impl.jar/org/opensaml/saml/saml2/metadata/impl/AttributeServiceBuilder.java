package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeService;

public class AttributeServiceBuilder extends AbstractSAMLObjectBuilder {
   public AttributeService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeService", "md");
   }

   public AttributeService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
