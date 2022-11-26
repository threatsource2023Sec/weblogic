package org.apache.openjpa.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyForeignKey {
   String name() default "";

   boolean enabled() default true;

   boolean deferred() default false;

   ForeignKeyAction deleteAction() default ForeignKeyAction.RESTRICT;

   ForeignKeyAction updateAction() default ForeignKeyAction.RESTRICT;
}
