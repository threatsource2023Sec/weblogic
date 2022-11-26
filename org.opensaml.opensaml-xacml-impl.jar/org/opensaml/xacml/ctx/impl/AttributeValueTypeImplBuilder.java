package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class AttributeValueTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public AttributeValueType buildObject() {
      return (AttributeValueType)this.buildObject(AttributeValueType.DEFAULT_ELEMENT_NAME);
   }

   public AttributeValueType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeValueTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
