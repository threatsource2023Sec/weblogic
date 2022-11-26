package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowire;
import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
   @AliasFor("name")
   String[] value() default {};

   @AliasFor("value")
   String[] name() default {};

   /** @deprecated */
   @Deprecated
   Autowire autowire() default Autowire.NO;

   boolean autowireCandidate() default true;

   String initMethod() default "";

   String destroyMethod() default "(inferred)";
}
