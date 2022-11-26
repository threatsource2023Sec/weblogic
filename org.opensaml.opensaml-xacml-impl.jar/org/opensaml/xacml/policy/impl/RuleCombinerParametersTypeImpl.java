package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.policy.RuleCombinerParametersType;

public class RuleCombinerParametersTypeImpl extends CombinerParametersTypeImpl implements RuleCombinerParametersType {
   private String ruleIdRef;

   protected RuleCombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getRuleIdRef() {
      return this.ruleIdRef;
   }

   public void setRuleIdRef(String ref) {
      this.ruleIdRef = this.prepareForAssignment(this.ruleIdRef, ref);
   }
}
