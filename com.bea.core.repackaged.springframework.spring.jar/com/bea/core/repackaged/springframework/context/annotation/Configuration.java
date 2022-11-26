package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import com.bea.core.repackaged.springframework.stereotype.Component;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {
   @AliasFor(
      annotation = Component.class
   )
   String value() default "";
}
