package org.opensaml.xacml.policy.impl;

import java.util.List;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;

public class PolicySetCombinerParametersTypeImpl extends CombinerParametersTypeImpl implements PolicySetCombinerParametersType {
   private String policySetIdRef;

   protected PolicySetCombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getPolicySetIdRef() {
      return this.policySetIdRef;
   }

   public void setPolicySetIdRef(String ref) {
      this.policySetIdRef = this.prepareForAssignment(this.policySetIdRef, ref);
   }

   public List getOrderedChildren() {
      return super.getOrderedChildren();
   }
}
