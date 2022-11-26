package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XJoinColumn {
   String name() default "";

   String referencedColumnName() default "";

   String referencedAttributeName() default "";

   boolean unique() default false;

   boolean nullable() default true;

   boolean insertable() default true;

   boolean updatable() default true;

   String columnDefinition() default "";

   String table() default "";
}
