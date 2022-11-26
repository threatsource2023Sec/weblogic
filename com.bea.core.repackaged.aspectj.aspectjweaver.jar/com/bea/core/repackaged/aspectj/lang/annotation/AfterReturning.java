package com.bea.core.repackaged.aspectj.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AfterReturning {
   String value() default "";

   String pointcut() default "";

   String returning() default "";

   String argNames() default "";
}
