package org.hibernate.validator.constraintvalidation;

import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.Incubating;

public interface HibernateConstraintValidatorContext extends ConstraintValidatorContext {
   HibernateConstraintValidatorContext addMessageParameter(String var1, Object var2);

   HibernateConstraintValidatorContext addExpressionVariable(String var1, Object var2);

   HibernateConstraintValidatorContext withDynamicPayload(Object var1);

   @Incubating
   Object getConstraintValidatorPayload(Class var1);
}
