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
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface LuhnCheck {
   String message() default "{org.hibernate.validator.constraints.LuhnCheck.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   int startIndex() default 0;

   int endIndex() default Integer.MAX_VALUE;

   int checkDigitIndex() default -1;

   boolean ignoreNonDigitCharacters() default true;

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      LuhnCheck[] value();
   }
}
