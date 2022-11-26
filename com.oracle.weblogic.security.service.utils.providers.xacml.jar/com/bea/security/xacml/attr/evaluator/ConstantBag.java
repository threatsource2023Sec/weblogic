package com.bea.security.xacml.attr.evaluator;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;

public class ConstantBag implements AttributeEvaluator {
   private Bag b;

   public ConstantBag(Bag b) {
      this.b = b;
   }

   public AttributeValue evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      if (this.b != null) {
         if (this.b.size() == 1) {
            return (AttributeValue)this.b.iterator().next();
         } else {
            throw new IndeterminateEvaluationException("Bags must be size 1 to be treated as scalars");
         }
      } else {
         return null;
      }
   }

   public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
      return this.b;
   }

   public Type getType() throws URISyntaxException {
      return this.b.getType();
   }

   public Bag getValue() {
      return this.b;
   }
}
