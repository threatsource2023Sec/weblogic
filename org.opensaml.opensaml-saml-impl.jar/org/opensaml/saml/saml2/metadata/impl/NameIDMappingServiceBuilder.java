package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.NameIDMappingService;

public class NameIDMappingServiceBuilder extends AbstractSAMLObjectBuilder {
   public NameIDMappingService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "NameIDMappingService", "md");
   }

   public NameIDMappingService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NameIDMappingServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
