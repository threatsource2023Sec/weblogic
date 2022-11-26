package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicyCombinerParametersType;

public class PolicyCombinerParametersTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public PolicyCombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PolicyCombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public PolicyCombinerParametersType buildObject() {
      return (PolicyCombinerParametersType)this.buildObject(PolicyCombinerParametersType.DEFAULT_ELEMENT_NAME);
   }
}
