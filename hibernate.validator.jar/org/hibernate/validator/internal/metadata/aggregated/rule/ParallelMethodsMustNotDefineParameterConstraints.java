package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class ParallelMethodsMustNotDefineParameterConstraints extends MethodConfigurationRule {
   public void apply(ConstrainedExecutable method, ConstrainedExecutable otherMethod) {
      if (this.isDefinedOnParallelType(method, otherMethod) && (method.hasParameterConstraints() || otherMethod.hasParameterConstraints())) {
         throw LOG.getParameterConstraintsDefinedInMethodsFromParallelTypesException(method.getExecutable(), otherMethod.getExecutable());
      }
   }
}
