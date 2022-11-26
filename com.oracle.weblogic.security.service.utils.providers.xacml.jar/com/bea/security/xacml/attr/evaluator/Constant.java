package com.bea.security.xacml.attr.evaluator;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;

public class Constant implements AttributeEvaluator {
   private AttributeValue av;

   public Constant(AttributeValue av) {
      this.av = av;
   }

   public AttributeValue evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      return this.av;
   }

   public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
      return this.av;
   }

   public Type getType() throws URISyntaxException {
      return this.av.getType();
   }

   public AttributeValue getValue() {
      return this.av;
   }
}
