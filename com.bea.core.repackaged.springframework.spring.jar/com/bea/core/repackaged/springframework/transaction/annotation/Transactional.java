package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {
   @AliasFor("transactionManager")
   String value() default "";

   @AliasFor("value")
   String transactionManager() default "";

   Propagation propagation() default Propagation.REQUIRED;

   Isolation isolation() default Isolation.DEFAULT;

   int timeout() default -1;

   boolean readOnly() default false;

   Class[] rollbackFor() default {};

   String[] rollbackForClassName() default {};

   Class[] noRollbackFor() default {};

   String[] noRollbackForClassName() default {};
}
