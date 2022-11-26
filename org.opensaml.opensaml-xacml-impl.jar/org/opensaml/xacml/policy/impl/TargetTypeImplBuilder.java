package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.TargetType;

public class TargetTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public TargetType buildObject() {
      return (TargetType)this.buildObject(TargetType.DEFAULT_ELEMENT_NAME);
   }

   public TargetType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TargetTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
