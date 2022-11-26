package org.hibernate.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

/** @deprecated */
@Documented
@Constraint(
   validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@NotNull
@Repeatable(List.class)
@Deprecated
public @interface NotBlank {
   String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      NotBlank[] value();
   }
}
