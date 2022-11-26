package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicyType;

public class PolicyTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public PolicyType buildObject() {
      return (PolicyType)this.buildObject(PolicyType.DEFAULT_ELEMENT_NAME);
   }

   public PolicyType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PolicyTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
