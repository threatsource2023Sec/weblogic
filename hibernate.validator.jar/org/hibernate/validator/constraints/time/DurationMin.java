package org.hibernate.validator.constraints.time;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import org.hibernate.validator.Incubating;

@Documented
@Constraint(
   validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
@ReportAsSingleViolation
@Incubating
public @interface DurationMin {
   String message() default "{org.hibernate.validator.constraints.time.DurationMin.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   long days() default 0L;

   long hours() default 0L;

   long minutes() default 0L;

   long seconds() default 0L;

   long millis() default 0L;

   long nanos() default 0L;

   boolean inclusive() default true;

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      DurationMin[] value();
   }
}
