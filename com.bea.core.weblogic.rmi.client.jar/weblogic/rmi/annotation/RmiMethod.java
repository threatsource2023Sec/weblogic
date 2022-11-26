package weblogic.rmi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RmiMethod {
   boolean oneway() default false;

   boolean asynchronousResult() default false;

   boolean transactional() default false;

   String dispatchPolicy() default "";

   int timeout() default 0;

   boolean idempotent() default false;
}
