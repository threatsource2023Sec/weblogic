package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicySetType;

public class PolicySetTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public PolicySetType buildObject() {
      return (PolicySetType)this.buildObject(PolicySetType.DEFAULT_ELEMENT_NAME);
   }

   public PolicySetType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PolicySetTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
