package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ResourceType;

public class ResourceTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ResourceType buildObject() {
      return (ResourceType)this.buildObject(ResourceType.DEFAULT_ELEMENT_NAME);
   }

   public ResourceType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResourceTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
