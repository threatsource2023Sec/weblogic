package org.opensaml.saml.ext.saml2mdattr.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;

public class EntityAttributesBuilder extends AbstractSAMLObjectBuilder {
   public EntityAttributes buildObject() {
      return (EntityAttributes)this.buildObject(EntityAttributes.DEFAULT_ELEMENT_NAME);
   }

   public EntityAttributes buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EntityAttributesImpl(namespaceURI, localName, namespacePrefix);
   }
}
