package com.oracle.jrockit.jfr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventDefinition {
   String name() default "";

   String path() default "";

   String description() default "";

   boolean stacktrace() default true;

   boolean thread() default true;
}
