package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class OverridingMethodMustNotAlterParameterConstraints extends MethodConfigurationRule {
   public void apply(ConstrainedExecutable method, ConstrainedExecutable otherMethod) {
      if (this.isDefinedOnSubType(method, otherMethod) && otherMethod.hasParameterConstraints() && !method.isEquallyParameterConstrained(otherMethod)) {
         throw LOG.getParameterConfigurationAlteredInSubTypeException(method.getExecutable(), otherMethod.getExecutable());
      }
   }
}
