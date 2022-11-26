package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeValueType;

public class AttributeValueTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public AttributeValueType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeValueTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public AttributeValueType buildObject() {
      return (AttributeValueType)this.buildObject(AttributeValueType.DEFAULT_ELEMENT_NAME);
   }
}
