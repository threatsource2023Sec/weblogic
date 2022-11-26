package javax.faces.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @deprecated */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
@Inherited
@Deprecated
public @interface ReferencedBean {
   String name() default "";
}
