package javax.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@Inherited
@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
   TxType value() default Transactional.TxType.REQUIRED;

   @Nonbinding
   Class[] rollbackOn() default {};

   @Nonbinding
   Class[] dontRollbackOn() default {};

   public static enum TxType {
      REQUIRED,
      REQUIRES_NEW,
      MANDATORY,
      SUPPORTS,
      NOT_SUPPORTED,
      NEVER;
   }
}
