package org.glassfish.hk2.configuration.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.glassfish.hk2.api.InjectionPointIndicator;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@InjectionPointIndicator
public @interface ChildInject {
   String value() default "";

   String separator() default ".";
}
