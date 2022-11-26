package com.bea.security.xacml;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.security.xacml.attr.designator.AttributeDesignatorRegistry;
import com.bea.security.xacml.attr.designator.SubjectAttributeDesignatorRegistry;
import com.bea.security.xacml.combinator.PolicyCombinerEvaluatorRegistry;
import com.bea.security.xacml.combinator.RuleCombinerEvaluatorRegistry;
import com.bea.security.xacml.expression.ExpressionRegistry;
import com.bea.security.xacml.function.FunctionRegistry;
import com.bea.security.xacml.policy.PolicyEvaluatorRegistry;
import com.bea.security.xacml.rule.RuleEvaluatorRegistry;
import com.bea.security.xacml.store.PolicyRegistry;
import com.bea.security.xacml.target.TargetEvaluatorRegistry;

public class Configuration {
   private final PolicyRegistry policies;
   private final PolicyEvaluatorRegistry pRegistry;
   private final PolicyCombinerEvaluatorRegistry pcRegistry;
   private final RuleEvaluatorRegistry rRegistry;
   private final RuleCombinerEvaluatorRegistry rcRegistry;
   private final TargetEvaluatorRegistry tRegistry;
   private final FunctionRegistry fRegistry;
   private final AttributeRegistry aRegistry;
   private final AttributeDesignatorRegistry rdRegistry;
   private final AttributeDesignatorRegistry adRegistry;
   private final AttributeDesignatorRegistry edRegistry;
   private final SubjectAttributeDesignatorRegistry sdRegistry;
   private final ExpressionRegistry eRegistry;
   private final LoggerSpi log;

   public Configuration(PolicyRegistry policies, PolicyEvaluatorRegistry pRegistry, PolicyCombinerEvaluatorRegistry pcRegistry, RuleEvaluatorRegistry rRegistry, RuleCombinerEvaluatorRegistry rcRegistry, TargetEvaluatorRegistry tRegistry, AttributeRegistry aRegistry, FunctionRegistry fRegistry, AttributeDesignatorRegistry rdRegistry, AttributeDesignatorRegistry adRegistry, AttributeDesignatorRegistry edRegistry, SubjectAttributeDesignatorRegistry sdRegistry, ExpressionRegistry eRegistry, LoggerSpi log) {
      this.policies = policies;
      this.pRegistry = pRegistry;
      this.pcRegistry = pcRegistry;
      this.rRegistry = rRegistry;
      this.rcRegistry = rcRegistry;
      this.tRegistry = tRegistry;
      this.aRegistry = aRegistry;
      this.fRegistry = fRegistry;
      this.rdRegistry = rdRegistry;
      this.adRegistry = adRegistry;
      this.edRegistry = edRegistry;
      this.sdRegistry = sdRegistry;
      this.eRegistry = eRegistry;
      this.log = log;
   }

   public PolicyRegistry getPolicyRegistry() {
      return this.policies;
   }

   public PolicyEvaluatorRegistry getPolicyEvaluatorRegistry() {
      return this.pRegistry;
   }

   public PolicyCombinerEvaluatorRegistry getPolicyCombinerEvaluatorRegistry() {
      return this.pcRegistry;
   }

   public RuleEvaluatorRegistry getRuleEvaluatorRegistry() {
      return this.rRegistry;
   }

   public RuleCombinerEvaluatorRegistry getRuleCombinerEvaluatorRegistry() {
      return this.rcRegistry;
   }

   public TargetEvaluatorRegistry getTargetEvaluatorRegistry() {
      return this.tRegistry;
   }

   public FunctionRegistry getFunctionRegistry() {
      return this.fRegistry;
   }

   public AttributeRegistry getAttributeRegistry() {
      return this.aRegistry;
   }

   public AttributeDesignatorRegistry getResourceAttributeDesignatorRegistry() {
      return this.rdRegistry;
   }

   public AttributeDesignatorRegistry getActionAttributeDesignatorRegistry() {
      return this.adRegistry;
   }

   public AttributeDesignatorRegistry getEnvironmentAttributeDesignatorRegistry() {
      return this.edRegistry;
   }

   public SubjectAttributeDesignatorRegistry getSubjectAttributeDesignatorRegistry() {
      return this.sdRegistry;
   }

   public ExpressionRegistry getExpressionRegistry() {
      return this.eRegistry;
   }

   public LoggerSpi getLog() {
      return this.log;
   }
}
