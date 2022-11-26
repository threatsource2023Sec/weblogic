package com.oracle.weblogic.lifecycle.provisioning.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.glassfish.hk2.api.InjectionPointIndicator;

@Documented
@InjectionPointIndicator
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface ConfigurableAttribute {
   String name() default "";

   String description() default "";

   String defaultValue() default "";

   boolean isSensitive() default false;
}
