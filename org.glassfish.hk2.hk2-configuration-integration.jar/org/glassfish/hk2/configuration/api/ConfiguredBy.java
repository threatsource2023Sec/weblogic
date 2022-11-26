package org.glassfish.hk2.configuration.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Scope;

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ConfiguredBy {
   String value();

   CreationPolicy creationPolicy() default ConfiguredBy.CreationPolicy.ON_DEMAND;

   public static enum CreationPolicy {
      ON_DEMAND,
      EAGER;
   }
}
