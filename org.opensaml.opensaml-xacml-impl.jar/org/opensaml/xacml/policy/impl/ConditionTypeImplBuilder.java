package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ConditionType;

public class ConditionTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ConditionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ConditionTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public ConditionType buildObject() {
      return (ConditionType)this.buildObject(ConditionType.DEFAULT_ELEMENT_NAME);
   }
}
