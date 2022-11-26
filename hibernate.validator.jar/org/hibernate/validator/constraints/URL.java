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
import javax.validation.constraints.Pattern;

@Documented
@Constraint(
   validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
@ReportAsSingleViolation
@Pattern(
   regexp = ""
)
public @interface URL {
   String message() default "{org.hibernate.validator.constraints.URL.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   String protocol() default "";

   String host() default "";

   int port() default -1;

   @OverridesAttribute(
      constraint = Pattern.class,
      name = "regexp"
   )
   String regexp() default ".*";

   @OverridesAttribute(
      constraint = Pattern.class,
      name = "flags"
   )
   Pattern.Flag[] flags() default {};

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      URL[] value();
   }
}
