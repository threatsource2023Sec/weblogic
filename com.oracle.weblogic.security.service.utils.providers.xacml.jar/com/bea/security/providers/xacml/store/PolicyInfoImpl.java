package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.PolicyInfo;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicySetInfo;

public class PolicyInfoImpl implements PolicyInfo, PolicySetInfo {
   private AbstractPolicy aPolicy;
   private PolicyMetaData data;

   public PolicyInfoImpl(AbstractPolicy aPolicy, PolicyMetaData data) {
      this.aPolicy = aPolicy;
      this.data = data;
   }

   public Policy getPolicy() {
      Policy p = null;
      if (this.aPolicy instanceof Policy) {
         p = (Policy)this.aPolicy;
      }

      return p;
   }

   public PolicySet getPolicySet() {
      PolicySet p = null;
      if (this.aPolicy instanceof PolicySet) {
         p = (PolicySet)this.aPolicy;
      }

      return p;
   }

   public PolicyMetaData getMetaDataEntry() {
      return this.data;
   }
}
