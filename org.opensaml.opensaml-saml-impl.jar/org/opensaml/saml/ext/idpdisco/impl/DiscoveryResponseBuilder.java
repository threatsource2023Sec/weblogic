package org.opensaml.saml.ext.idpdisco.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.idpdisco.DiscoveryResponse;

public class DiscoveryResponseBuilder extends AbstractSAMLObjectBuilder {
   public DiscoveryResponse buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "DiscoveryResponse", "md");
   }

   public DiscoveryResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DiscoveryResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
