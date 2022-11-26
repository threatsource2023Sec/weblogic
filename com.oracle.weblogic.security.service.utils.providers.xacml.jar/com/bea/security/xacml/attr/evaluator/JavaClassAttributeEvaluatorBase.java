package com.bea.security.xacml.attr.evaluator;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;

public abstract class JavaClassAttributeEvaluatorBase implements AttributeEvaluator {
   public Type getType() throws URISyntaxException {
      return Type.CLASS;
   }

   public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
      return this.evaluate(context);
   }
}
