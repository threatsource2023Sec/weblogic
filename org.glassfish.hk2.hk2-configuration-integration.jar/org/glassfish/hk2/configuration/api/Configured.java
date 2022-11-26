package org.glassfish.hk2.configuration.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.glassfish.hk2.api.InjectionPointIndicator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@InjectionPointIndicator
public @interface Configured {
   String BEAN_KEY = "$bean";
   String INSTANCE = "$instance";
   String TYPE = "$type";

   String value() default "";

   Dynamicity dynamicity() default Dynamicity.STATIC;
}
