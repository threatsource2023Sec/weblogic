package com.bea.security.xacml;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;

public interface AttributeEvaluator {
   AttributeValue evaluate(EvaluationCtx var1) throws IndeterminateEvaluationException;

   Bag evaluateToBag(EvaluationCtx var1) throws IndeterminateEvaluationException;

   Type getType() throws URISyntaxException;
}
