package weblogic.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WLFilter {
   String description() default "";

   String icon() default "";

   String displayName() default "";

   String name() default "";

   WLInitParam[] initParams() default {};

   String[] mapping() default {};
}
