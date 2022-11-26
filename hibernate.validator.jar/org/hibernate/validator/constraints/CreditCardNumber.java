package org.hibernate.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.ReportAsSingleViolation;

@Documented
@Constraint(
   validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
@ReportAsSingleViolation
@LuhnCheck
public @interface CreditCardNumber {
   String message() default "{org.hibernate.validator.constraints.CreditCardNumber.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   @OverridesAttribute(
      constraint = LuhnCheck.class,
      name = "ignoreNonDigitCharacters"
   )
   boolean ignoreNonDigitCharacters() default false;

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      CreditCardNumber[] value();
   }
}
