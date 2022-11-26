package com.bea.core.repackaged.springframework.jmx.export.annotation;

import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ManagedResource {
   @AliasFor("objectName")
   String value() default "";

   @AliasFor("value")
   String objectName() default "";

   String description() default "";

   int currencyTimeLimit() default -1;

   boolean log() default false;

   String logFile() default "";

   String persistPolicy() default "";

   int persistPeriod() default -1;

   String persistName() default "";

   String persistLocation() default "";
}
