package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EntityDescriptorBuilder extends AbstractSAMLObjectBuilder {
   public EntityDescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "EntityDescriptor", "md");
   }

   public EntityDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EntityDescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
