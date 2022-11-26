package com.bea.security.xacml;

public interface RuleCombinerEvaluator {
   RuleDecision evaluate(EvaluationCtx var1) throws IndeterminateEvaluationException;
}
