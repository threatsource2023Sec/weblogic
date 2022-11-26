package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ResourcesType;

public class ResourcesTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ResourcesType buildObject() {
      return (ResourcesType)this.buildObject(ResourcesType.DEFAULT_ELEMENT_NAME);
   }

   public ResourcesType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResourcesTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
