package javax.faces.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @deprecated */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Deprecated
public @interface ManagedProperty {
   String name() default "";

   String value();
}
