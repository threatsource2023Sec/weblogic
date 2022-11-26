package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;

public class PolicySetCombinerParametersTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public PolicySetCombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PolicySetCombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public PolicySetCombinerParametersType buildObject() {
      return (PolicySetCombinerParametersType)this.buildObject(PolicySetCombinerParametersType.DEFAULT_ELEMENT_NAME);
   }
}
