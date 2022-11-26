package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {
   @AliasFor("classes")
   Class[] value() default {};

   @AliasFor("value")
   Class[] classes() default {};

   String condition() default "";
}
