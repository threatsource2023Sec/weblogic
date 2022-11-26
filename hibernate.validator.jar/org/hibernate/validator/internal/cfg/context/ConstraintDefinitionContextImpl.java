package org.hibernate.validator.internal.cfg.context;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.hibernate.validator.internal.engine.constraintdefinition.ConstraintDefinitionContribution;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;

class ConstraintDefinitionContextImpl extends ConstraintContextImplBase implements ConstraintDefinitionContext {
   private final Class annotationType;
   private boolean includeExistingValidators = true;
   private final Set validatorDescriptors = new HashSet();

   ConstraintDefinitionContextImpl(DefaultConstraintMapping mapping, Class annotationType) {
      super(mapping);
      this.annotationType = annotationType;
   }

   public ConstraintDefinitionContext includeExistingValidators(boolean includeExistingValidators) {
      this.includeExistingValidators = includeExistingValidators;
      return this;
   }

   public ConstraintDefinitionContext validatedBy(Class validator) {
      this.validatorDescriptors.add(ConstraintValidatorDescriptor.forClass(validator, this.annotationType));
      return this;
   }

   public ConstraintDefinitionContext.ConstraintValidatorDefinitionContext validateType(Class type) {
      return new ConstraintValidatorDefinitionContextImpl(type);
   }

   ConstraintDefinitionContribution build() {
      return new ConstraintDefinitionContribution(this.annotationType, CollectionHelper.newArrayList(this.validatorDescriptors), this.includeExistingValidators);
   }

   private class ConstraintValidatorDefinitionContextImpl implements ConstraintDefinitionContext.ConstraintValidatorDefinitionContext {
      private final Class type;

      public ConstraintValidatorDefinitionContextImpl(Class type) {
         this.type = type;
      }

      public ConstraintDefinitionContext with(ConstraintDefinitionContext.ValidationCallable vc) {
         ConstraintDefinitionContextImpl.this.validatorDescriptors.add(ConstraintValidatorDescriptor.forLambda(ConstraintDefinitionContextImpl.this.annotationType, this.type, vc));
         return ConstraintDefinitionContextImpl.this;
      }
   }
}
