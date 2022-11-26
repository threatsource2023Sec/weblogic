package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeSelectorType;

public class AttributeSelectorTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public AttributeSelectorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeSelectorTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public AttributeSelectorType buildObject() {
      return (AttributeSelectorType)this.buildObject(AttributeSelectorType.DEFAULT_ELEMENT_NAME);
   }
}
