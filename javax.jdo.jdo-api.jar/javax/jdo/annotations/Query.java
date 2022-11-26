package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
   String name();

   String value();

   String language() default "JDOQL";

   String unmodifiable() default "";

   String unique() default "";

   Class resultClass() default void.class;

   String fetchPlan() default "";

   Extension[] extensions() default {};
}
