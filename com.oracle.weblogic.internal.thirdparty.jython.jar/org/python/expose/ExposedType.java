package org.python.expose;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExposedType {
   String name() default "";

   Class base() default Object.class;

   boolean isBaseType() default true;

   String doc() default "";
}
