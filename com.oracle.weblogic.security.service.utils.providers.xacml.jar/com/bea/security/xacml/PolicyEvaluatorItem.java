package com.bea.security.xacml;

public interface PolicyEvaluatorItem {
   PolicyDecision evaluate() throws IndeterminateEvaluationException;
}
