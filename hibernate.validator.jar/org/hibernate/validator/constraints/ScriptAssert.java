package org.hibernate.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Documented
@Constraint(
   validatedBy = {}
)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface ScriptAssert {
   String message() default "{org.hibernate.validator.constraints.ScriptAssert.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   String lang();

   String script();

   String alias() default "_this";

   String reportOn() default "";

   @Target({ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      ScriptAssert[] value();
   }
}
