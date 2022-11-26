package com.bea.security.xacml;

public interface TargetMatchEvaluator {
   boolean evaluate(EvaluationCtx var1) throws IndeterminateEvaluationException;
}
