package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.RuleCombinerParametersType;

public class RuleCombinerParametersTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public RuleCombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RuleCombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public RuleCombinerParametersType buildObject() {
      return (RuleCombinerParametersType)this.buildObject(RuleCombinerParametersType.DEFAULT_ELEMENT_NAME);
   }
}
