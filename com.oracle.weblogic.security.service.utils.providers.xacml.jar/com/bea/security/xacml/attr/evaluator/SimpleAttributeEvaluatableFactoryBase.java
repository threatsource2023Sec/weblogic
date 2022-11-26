package com.bea.security.xacml.attr.evaluator;

import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class SimpleAttributeEvaluatableFactoryBase implements AttributeEvaluatableFactory {
   private Map evaluators = Collections.synchronizedMap(new HashMap());

   public SimpleAttributeEvaluatableFactoryBase() {
   }

   public SimpleAttributeEvaluatableFactoryBase(URI id, URI type, AttributeEvaluator eval) {
      this.register(id, type, eval);
   }

   public void register(URI id, URI type, AttributeEvaluator eval) {
      this.evaluators.put(new DesignatorType(id, type), eval);
   }

   protected void register(DesignatorType d, AttributeEvaluator eval) {
      this.evaluators.put(d, eval);
   }

   public AttributeEvaluator getEvaluatable(URI id, URI type) {
      return this.getEvaluatable(id, type, (String)null);
   }

   public AttributeEvaluator getEvaluatable(URI id, URI type, String issuer) {
      return (AttributeEvaluator)this.evaluators.get(new DesignatorType(id, type));
   }

   protected AttributeEvaluator getEvaluatable(DesignatorType d) {
      return (AttributeEvaluator)this.evaluators.get(d);
   }

   protected class DesignatorType {
      private URI type;
      private URI id;

      public DesignatorType(URI id, URI type) {
         this.type = type;
         this.id = id;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof DesignatorType)) {
            return false;
         } else {
            DesignatorType other = (DesignatorType)o;
            return this.type.equals(other.type) && this.id.equals(other.id);
         }
      }

      public int hashCode() {
         return this.id.hashCode();
      }
   }
}
