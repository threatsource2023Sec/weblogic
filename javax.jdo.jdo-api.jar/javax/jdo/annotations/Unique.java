package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
   String name() default "";

   String table() default "";

   String deferred() default "";

   String[] members() default {};

   Column[] columns() default {};

   Extension[] extensions() default {};
}
