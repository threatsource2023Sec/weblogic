package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
   String name() default "";

   String target() default "";

   String targetMember() default "";

   String jdbcType() default "";

   String sqlType() default "";

   int length() default -1;

   int scale() default -1;

   String allowsNull() default "";

   String defaultValue() default "";

   String insertValue() default "";

   Extension[] extensions() default {};

   int position() default -1;
}
