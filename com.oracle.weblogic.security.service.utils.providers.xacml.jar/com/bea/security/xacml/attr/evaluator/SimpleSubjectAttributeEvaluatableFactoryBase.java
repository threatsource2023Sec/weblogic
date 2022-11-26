package com.bea.security.xacml.attr.evaluator;

import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.attr.SubjectAttributeEvaluatableFactory;

public abstract class SimpleSubjectAttributeEvaluatableFactoryBase extends SimpleAttributeEvaluatableFactoryBase implements SubjectAttributeEvaluatableFactory {
   public SimpleSubjectAttributeEvaluatableFactoryBase() {
   }

   public SimpleSubjectAttributeEvaluatableFactoryBase(URI id, URI type, AttributeEvaluator eval) {
      super(id, type, eval);
   }

   public SimpleSubjectAttributeEvaluatableFactoryBase(URI id, URI type, URI category, AttributeEvaluator eval) {
      this.register(id, type, category, eval);
   }

   public void register(URI id, URI type, AttributeEvaluator eval) {
      this.register(id, type, (URI)null, eval);
   }

   public void register(URI id, URI type, URI category, AttributeEvaluator eval) {
      this.register(new SubjectDesignatorType(id, type, category), eval);
   }

   public AttributeEvaluator getEvaluatable(URI id, URI type, URI category) {
      return this.getEvaluatable(id, type, (String)null, category);
   }

   public AttributeEvaluator getEvaluatable(URI id, URI type, String issuer, URI category) {
      return this.getEvaluatable(new SubjectDesignatorType(id, type, category));
   }

   protected class SubjectDesignatorType extends SimpleAttributeEvaluatableFactoryBase.DesignatorType {
      private URI category;

      public SubjectDesignatorType(URI id, URI type) {
         this(id, type, (URI)null);
      }

      public SubjectDesignatorType(URI id, URI type, URI category) {
         super(id, type);
         this.category = category;
      }

      public boolean equals(Object o) {
         if (!super.equals(o)) {
            return false;
         } else if (!(o instanceof SubjectDesignatorType)) {
            return false;
         } else {
            SubjectDesignatorType other = (SubjectDesignatorType)o;
            return this.category == null && other.category == null || this.category.equals(other.category);
         }
      }
   }
}
