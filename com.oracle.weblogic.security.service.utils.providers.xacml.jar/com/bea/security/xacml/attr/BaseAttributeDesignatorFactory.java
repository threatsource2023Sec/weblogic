package com.bea.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.policy.AttributeDesignator;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.MissingAttributeException;
import com.bea.security.xacml.attr.designator.AttributeDesignatorFactory;
import java.util.Iterator;

public abstract class BaseAttributeDesignatorFactory implements AttributeDesignatorFactory {
   public abstract AttributeEvaluatableFactory getFactory();

   public AttributeEvaluator createDesignator(AttributeDesignator attribute, Configuration config, Iterator otherFactories) throws URISyntaxException {
      AttributeEvaluatableFactory fac = this.getFactory();
      final URI id = attribute.getAttributeId();
      final URI dataType = attribute.getDataType();
      String issuer = attribute.getIssuer();
      boolean isMustBePresent = attribute.isMustBePresent();
      if (isMustBePresent) {
         final AttributeEvaluator proxy = this.generateEvaluator(fac, id, dataType, issuer);
         return new AttributeEvaluatorWrapper(proxy.getType()) {
            public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
               Bag bag = proxy.evaluateToBag(context);
               if (bag != null && !bag.isEmpty()) {
                  return bag;
               } else {
                  throw new MissingAttributeException(id, dataType);
               }
            }

            public Type getType() throws URISyntaxException {
               return proxy.getType();
            }
         };
      } else {
         return this.generateEvaluator(fac, id, dataType, issuer);
      }
   }

   private AttributeEvaluator generateEvaluator(AttributeEvaluatableFactory fac, URI id, URI dataType, String issuer) {
      return issuer != null ? fac.getEvaluatable(id, dataType, issuer) : fac.getEvaluatable(id, dataType);
   }
}
