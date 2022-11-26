package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderColumn {
   String name() default "";

   boolean enabled() default true;

   boolean insertable() default true;

   boolean updatable() default true;

   String columnDefinition() default "";

   int precision() default 0;
}
