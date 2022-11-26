package org.apache.openjpa.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataCache {
   boolean enabled() default true;

   String name() default "";

   int timeout() default Integer.MIN_VALUE;
}
