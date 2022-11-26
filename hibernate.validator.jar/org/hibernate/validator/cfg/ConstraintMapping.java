package org.hibernate.validator.cfg;

import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;

public interface ConstraintMapping {
   TypeConstraintMappingContext type(Class var1);

   ConstraintDefinitionContext constraintDefinition(Class var1);
}
