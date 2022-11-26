package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameIDMappingResponse;

public class NameIDMappingResponseBuilder extends AbstractSAMLObjectBuilder {
   public NameIDMappingResponse buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingResponse", "saml2p");
   }

   public NameIDMappingResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NameIDMappingResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
