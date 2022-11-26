package com.bea.security.xacml;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.GenericBag;

public class AttributeEvaluatorWrapper implements AttributeEvaluator {
   private Type type;

   public AttributeEvaluatorWrapper(URI dataType) {
      this(new Type(dataType, true));
   }

   public AttributeEvaluatorWrapper(Type type) {
      this.type = type;
   }

   public AttributeValue evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      Bag b = this.evaluateToBag(context);
      if (b != null) {
         if (b.size() == 1) {
            return (AttributeValue)b.iterator().next();
         } else {
            throw new IndeterminateEvaluationException("Bags must be size 1 to be treated as scalars");
         }
      } else {
         return null;
      }
   }

   public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
      AttributeValue av = this.evaluate(context);
      return (Bag)(av != null ? av : new GenericBag(this.type));
   }

   public Type getType() throws URISyntaxException {
      return this.type;
   }
}
