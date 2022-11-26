package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;

public class EntitiesDescriptorBuilder extends AbstractSAMLObjectBuilder {
   public EntitiesDescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptor", "md");
   }

   public EntitiesDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EntitiesDescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
