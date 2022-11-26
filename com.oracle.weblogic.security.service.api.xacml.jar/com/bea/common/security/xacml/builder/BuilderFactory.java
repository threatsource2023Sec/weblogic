package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.policy.Obligation;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.Rule;

public class BuilderFactory {
   private BuilderFactory() {
   }

   public static PolicyBuilder createPolicyBuilder() {
      return new PolicyBuilderImpl();
   }

   public static PolicyBuilder createPolicyBuilder(String policy) throws XACMLException {
      return new PolicyBuilderImpl(policy);
   }

   public static PolicyBuilder createPolicyBuilder(URI policyId) throws XACMLException {
      return new PolicyBuilderImpl(policyId);
   }

   public static PolicyBuilder createPolicyBuilder(Policy policy) throws XACMLException {
      return new PolicyBuilderImpl(policy);
   }

   public static PolicySetBuilder createPolicySetBuilder() {
      return new PolicySetBuilderImpl();
   }

   public static PolicySetBuilder createPolicySetBuilder(String policySet) throws XACMLException {
      return new PolicySetBuilderImpl(policySet);
   }

   public static PolicySetBuilder createPolicySetBuilder(URI policySetId) throws XACMLException {
      return new PolicySetBuilderImpl(policySetId);
   }

   public static PolicySetBuilder createPolicySetBuilder(PolicySet policySet) throws XACMLException {
      return new PolicySetBuilderImpl(policySet);
   }

   public static RuleBuilder createRuleBuilder() {
      return new RuleBuilderImpl();
   }

   public static RuleBuilder createRuleBuilder(Rule rule) throws XACMLException {
      return new RuleBuilderImpl(rule);
   }

   public static RuleBuilder createRuleBuilder(String ruleId, boolean isEffectPermit) throws XACMLException {
      return new RuleBuilderImpl(ruleId, isEffectPermit);
   }

   public static ObligationBuilder createObligationBuilder() {
      return new ObligationBuilderImpl();
   }

   public static ObligationBuilder createObligationBuilder(Obligation obligation) throws XACMLException {
      return new ObligationBuilderImpl(obligation);
   }

   public static ObligationBuilder createObligationBuilder(URI obligationId) throws XACMLException {
      return new ObligationBuilderImpl(obligationId);
   }
}
