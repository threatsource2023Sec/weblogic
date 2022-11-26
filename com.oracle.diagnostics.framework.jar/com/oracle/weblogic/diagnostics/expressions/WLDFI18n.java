package com.oracle.weblogic.diagnostics.expressions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface WLDFI18n {
   String value() default "";

   String displayName() default "";

   String fullDescription() default "";

   String name() default "";

   String defaultValue() default "";

   String resourceBundle() default "";
}
