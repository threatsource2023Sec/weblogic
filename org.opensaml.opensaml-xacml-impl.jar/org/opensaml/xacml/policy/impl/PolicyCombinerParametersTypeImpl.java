package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.policy.PolicyCombinerParametersType;

public class PolicyCombinerParametersTypeImpl extends CombinerParametersTypeImpl implements PolicyCombinerParametersType {
   private String policyIdRef;

   protected PolicyCombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getPolicyIdRef() {
      return this.policyIdRef;
   }

   public void setPolicyIdRef(String ref) {
      this.policyIdRef = this.prepareForAssignment(this.policyIdRef, ref);
   }
}
