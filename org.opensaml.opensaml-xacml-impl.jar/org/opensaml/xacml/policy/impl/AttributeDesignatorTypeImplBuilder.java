package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeDesignatorType;

public class AttributeDesignatorTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public AttributeDesignatorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeDesignatorTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public AttributeDesignatorType buildObject() {
      return (AttributeDesignatorType)this.buildObject(AttributeDesignatorType.DEFAULT_ELEMENT_NAME_XACML20);
   }
}
