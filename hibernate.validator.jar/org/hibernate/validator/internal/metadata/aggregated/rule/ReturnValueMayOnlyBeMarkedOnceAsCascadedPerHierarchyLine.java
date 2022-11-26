package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class ReturnValueMayOnlyBeMarkedOnceAsCascadedPerHierarchyLine extends MethodConfigurationRule {
   public void apply(ConstrainedExecutable method, ConstrainedExecutable otherMethod) {
      if (method.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() && otherMethod.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() && (this.isDefinedOnSubType(method, otherMethod) || this.isDefinedOnSubType(otherMethod, method))) {
         throw LOG.getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException(method.getExecutable(), otherMethod.getExecutable());
      }
   }
}
