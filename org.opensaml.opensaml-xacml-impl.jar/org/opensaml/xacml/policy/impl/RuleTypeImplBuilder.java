package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.RuleType;

public class RuleTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public RuleType buildObject() {
      return (RuleType)this.buildObject(RuleType.DEFAULT_ELEMENT_NAME);
   }

   public RuleType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RuleTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
