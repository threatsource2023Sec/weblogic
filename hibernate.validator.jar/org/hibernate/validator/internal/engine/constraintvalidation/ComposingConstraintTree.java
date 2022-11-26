package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.internal.engine.ValidationContext;
import org.hibernate.validator.internal.engine.ValueContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

class ComposingConstraintTree extends ConstraintTree {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final List children;

   public ComposingConstraintTree(ConstraintDescriptorImpl descriptor, Type validatedValueType) {
      super(descriptor, validatedValueType);
      this.children = (List)descriptor.getComposingConstraintImpls().stream().map((desc) -> {
         return this.createConstraintTree(desc);
      }).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionHelper::toImmutableList));
   }

   private ConstraintTree createConstraintTree(ConstraintDescriptorImpl composingDescriptor) {
      return (ConstraintTree)(composingDescriptor.getComposingConstraintImpls().isEmpty() ? new SimpleConstraintTree(composingDescriptor, this.getValidatedValueType()) : new ComposingConstraintTree(composingDescriptor, this.getValidatedValueType()));
   }

   protected void validateConstraints(ValidationContext validationContext, ValueContext valueContext, Set constraintViolations) {
      CompositionResult compositionResult = this.validateComposingConstraints(validationContext, valueContext, constraintViolations);
      Set localViolations;
      if (this.mainConstraintNeedsEvaluation(validationContext, constraintViolations)) {
         if (LOG.isTraceEnabled()) {
            LOG.tracef("Validating value %s against constraint defined by %s.", valueContext.getCurrentValidatedValue(), this.descriptor);
         }

         ConstraintValidator validator = this.getInitializedConstraintValidator(validationContext, valueContext);
         ConstraintValidatorContextImpl constraintValidatorContext = new ConstraintValidatorContextImpl(validationContext.getParameterNames(), validationContext.getClockProvider(), valueContext.getPropertyPath(), this.descriptor, validationContext.getConstraintValidatorPayload());
         localViolations = this.validateSingleConstraint(validationContext, valueContext, constraintValidatorContext, validator);
         if (localViolations.isEmpty()) {
            compositionResult.setAtLeastOneTrue(true);
         } else {
            compositionResult.setAllTrue(false);
         }
      } else {
         localViolations = Collections.emptySet();
      }

      if (!this.passesCompositionTypeRequirement(constraintViolations, compositionResult)) {
         this.prepareFinalConstraintViolations(validationContext, valueContext, constraintViolations, localViolations);
      }

   }

   private boolean mainConstraintNeedsEvaluation(ValidationContext executionContext, Set constraintViolations) {
      if (!this.descriptor.getComposingConstraints().isEmpty() && this.descriptor.getMatchingConstraintValidatorDescriptors().isEmpty()) {
         return false;
      } else if (constraintViolations.isEmpty()) {
         return true;
      } else if (this.descriptor.isReportAsSingleViolation() && this.descriptor.getCompositionType() == CompositionType.AND) {
         return false;
      } else {
         return !executionContext.isFailFastModeEnabled();
      }
   }

   private void prepareFinalConstraintViolations(ValidationContext executionContext, ValueContext valueContext, Set constraintViolations, Set localViolations) {
      if (this.reportAsSingleViolation()) {
         constraintViolations.clear();
         if (localViolations.isEmpty()) {
            String message = this.getDescriptor().getMessageTemplate();
            ConstraintViolationCreationContext constraintViolationCreationContext = new ConstraintViolationCreationContext(message, valueContext.getPropertyPath());
            ConstraintViolation violation = executionContext.createConstraintViolation(valueContext, constraintViolationCreationContext, this.descriptor);
            constraintViolations.add(violation);
         }
      }

      constraintViolations.addAll(localViolations);
   }

   private CompositionResult validateComposingConstraints(ValidationContext executionContext, ValueContext valueContext, Set constraintViolations) {
      CompositionResult compositionResult = new CompositionResult(true, false);
      Iterator var5 = this.children.iterator();

      while(var5.hasNext()) {
         ConstraintTree tree = (ConstraintTree)var5.next();
         Set tmpViolations = CollectionHelper.newHashSet(5);
         tree.validateConstraints(executionContext, valueContext, tmpViolations);
         constraintViolations.addAll(tmpViolations);
         if (tmpViolations.isEmpty()) {
            compositionResult.setAtLeastOneTrue(true);
            if (this.descriptor.getCompositionType() == CompositionType.OR) {
               break;
            }
         } else {
            compositionResult.setAllTrue(false);
            if (this.descriptor.getCompositionType() == CompositionType.AND && (executionContext.isFailFastModeEnabled() || this.descriptor.isReportAsSingleViolation())) {
               break;
            }
         }
      }

      return compositionResult;
   }

   private boolean passesCompositionTypeRequirement(Set constraintViolations, CompositionResult compositionResult) {
      CompositionType compositionType = this.getDescriptor().getCompositionType();
      boolean passedValidation = false;
      switch (compositionType) {
         case OR:
            passedValidation = compositionResult.isAtLeastOneTrue();
            break;
         case AND:
            passedValidation = compositionResult.isAllTrue();
            break;
         case ALL_FALSE:
            passedValidation = !compositionResult.isAtLeastOneTrue();
      }

      assert !passedValidation || compositionType != CompositionType.AND || constraintViolations.isEmpty();

      if (passedValidation) {
         constraintViolations.clear();
      }

      return passedValidation;
   }

   private boolean reportAsSingleViolation() {
      return this.getDescriptor().isReportAsSingleViolation() || this.getDescriptor().getCompositionType() == CompositionType.ALL_FALSE;
   }

   private static final class CompositionResult {
      private boolean allTrue;
      private boolean atLeastOneTrue;

      CompositionResult(boolean allTrue, boolean atLeastOneTrue) {
         this.allTrue = allTrue;
         this.atLeastOneTrue = atLeastOneTrue;
      }

      public boolean isAllTrue() {
         return this.allTrue;
      }

      public boolean isAtLeastOneTrue() {
         return this.atLeastOneTrue;
      }

      public void setAllTrue(boolean allTrue) {
         this.allTrue = allTrue;
      }

      public void setAtLeastOneTrue(boolean atLeastOneTrue) {
         this.atLeastOneTrue = atLeastOneTrue;
      }
   }
}
