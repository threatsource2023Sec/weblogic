package com.bea.security.xacml.policy;

import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.security.xacml.IndeterminateEvaluationException;

public interface VariableContext {
   AttributeValue getVariable(String var1) throws IndeterminateEvaluationException;

   Bag getVariableAsBag(String var1) throws IndeterminateEvaluationException;
}
