package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class AttributeTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public AttributeType buildObject() {
      return (AttributeType)this.buildObject(AttributeType.DEFAULT_ELEMENT_NAME);
   }

   public AttributeType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
