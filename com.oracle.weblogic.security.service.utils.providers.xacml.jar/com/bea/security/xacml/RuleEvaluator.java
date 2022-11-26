package com.bea.security.xacml;

public interface RuleEvaluator extends RuleCombinerEvaluator {
   boolean hasPermitEffect();
}
