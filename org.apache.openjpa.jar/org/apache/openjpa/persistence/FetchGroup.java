package org.apache.openjpa.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FetchGroup {
   String name() default "";

   boolean postLoad() default false;

   FetchAttribute[] attributes() default {};

   String[] fetchGroups() default {};
}
