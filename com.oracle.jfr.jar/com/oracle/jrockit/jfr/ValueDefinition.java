package com.oracle.jrockit.jfr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueDefinition {
   String id() default "";

   String name() default "";

   String description() default "";

   String relationKey() default "";

   ContentType contentType() default ContentType.None;

   Transition transition() default Transition.None;
}
