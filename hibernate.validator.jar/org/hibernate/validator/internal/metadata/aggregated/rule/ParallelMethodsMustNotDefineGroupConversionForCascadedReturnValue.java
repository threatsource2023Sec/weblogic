package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class ParallelMethodsMustNotDefineGroupConversionForCascadedReturnValue extends MethodConfigurationRule {
   public void apply(ConstrainedExecutable method, ConstrainedExecutable otherMethod) {
      boolean isCascaded = method.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() || otherMethod.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements();
      boolean hasGroupConversions = method.getCascadingMetaDataBuilder().hasGroupConversionsOnAnnotatedObjectOrContainerElements() || otherMethod.getCascadingMetaDataBuilder().hasGroupConversionsOnAnnotatedObjectOrContainerElements();
      if (this.isDefinedOnParallelType(method, otherMethod) && isCascaded && hasGroupConversions) {
         throw LOG.getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException(method.getExecutable(), otherMethod.getExecutable());
      }
   }
}
