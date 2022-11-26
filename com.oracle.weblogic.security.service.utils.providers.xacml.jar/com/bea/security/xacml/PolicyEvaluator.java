package com.bea.security.xacml;

public interface PolicyEvaluator {
   PolicyDecision evaluate(EvaluationCtx var1) throws IndeterminateEvaluationException;
}
