package com.oracle.weblogic.lifecycle.config.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Constraint(
   validatedBy = {ReferenceValidator.class}
)
public @interface ReferenceConstraint {
   String message() default "{com.oracle.weblogic.lifecycle.config.validators.ReferenceConstraint.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   Class type();
}
