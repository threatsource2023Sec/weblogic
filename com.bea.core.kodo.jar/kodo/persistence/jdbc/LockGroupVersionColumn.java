package kodo.persistence.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockGroupVersionColumn {
   String name() default "";

   boolean nullable() default true;

   boolean insertable() default true;

   boolean updatable() default true;

   String columnDefinition() default "";

   int length() default 255;

   int precision() default 0;

   int scale() default 0;

   String lockGroup() default "";
}
