package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataStoreIdColumn {
   String name() default "";

   boolean insertable() default true;

   boolean updatable() default true;

   String columnDefinition() default "";

   int precision() default 0;
}
