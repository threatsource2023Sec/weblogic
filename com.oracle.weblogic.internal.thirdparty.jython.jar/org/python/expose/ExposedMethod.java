package org.python.expose;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ExposedMethod {
   String[] names() default {};

   String[] defaults() default {};

   MethodType type() default MethodType.DEFAULT;

   String doc() default "";
}
