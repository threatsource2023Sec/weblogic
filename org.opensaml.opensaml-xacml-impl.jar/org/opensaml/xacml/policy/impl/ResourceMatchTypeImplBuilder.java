package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ResourceMatchType;

public class ResourceMatchTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ResourceMatchType buildObject() {
      return (ResourceMatchType)this.buildObject(ResourceMatchType.DEFAULT_ELEMENT_NAME);
   }

   public ResourceMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResourceMatchTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
