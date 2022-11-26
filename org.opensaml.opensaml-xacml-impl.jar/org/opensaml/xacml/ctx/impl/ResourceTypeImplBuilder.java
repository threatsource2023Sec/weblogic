package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ResourceTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ResourceType buildObject() {
      return (ResourceType)this.buildObject(ResourceType.DEFAULT_ELEMENT_NAME);
   }

   public ResourceType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResourceTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
