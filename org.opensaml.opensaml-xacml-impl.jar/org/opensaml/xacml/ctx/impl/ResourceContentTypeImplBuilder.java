package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ResourceContentTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ResourceContentType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResourceContentTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public ResourceContentType buildObject() {
      return (ResourceContentType)this.buildObject(ResourceContentType.DEFAULT_ELEMENT_NAME);
   }
}
