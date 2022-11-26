package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XTable {
   String name();

   String catalog() default "";

   String schema() default "";

   Index[] indexes() default {};

   ForeignKey[] foreignKeys() default {};

   Unique[] uniqueConstraints() default {};
}
