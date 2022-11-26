package org.glassfish.hk2.xml.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PluralOf {
   String USE_NORMAL_PLURAL_PATTERN = "*";

   String value() default "*";

   String add() default "*";

   String remove() default "*";

   String lookup() default "*";
}
