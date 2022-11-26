package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;

public class NameIDMappingRequestBuilder extends AbstractSAMLObjectBuilder {
   public NameIDMappingRequest buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingRequest", "saml2p");
   }

   public NameIDMappingRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NameIDMappingRequestImpl(namespaceURI, localName, namespacePrefix);
   }
}
