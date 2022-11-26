package kodo.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @deprecated */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyJoinColumn {
   String name() default "";

   String referencedColumnName() default "";

   String referencedAttributeName() default "";

   boolean unique() default false;

   boolean nullable() default true;

   boolean insertable() default true;

   boolean updatable() default true;

   String columnDefinition() default "";
}
