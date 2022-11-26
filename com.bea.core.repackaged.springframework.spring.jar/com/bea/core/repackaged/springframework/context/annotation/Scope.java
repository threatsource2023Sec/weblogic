package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
   @AliasFor("scopeName")
   String value() default "";

   @AliasFor("value")
   String scopeName() default "";

   ScopedProxyMode proxyMode() default ScopedProxyMode.DEFAULT;
}
