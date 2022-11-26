package org.apache.openjpa.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Persistent {
   String mappedBy() default "";

   CascadeType[] cascade() default {};

   boolean optional() default true;

   boolean embedded() default false;

   FetchType fetch() default FetchType.EAGER;
}
