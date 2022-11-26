package com.bea.core.repackaged.springframework.transaction.event;

import com.bea.core.repackaged.springframework.context.event.EventListener;
import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EventListener
public @interface TransactionalEventListener {
   TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;

   boolean fallbackExecution() default false;

   @AliasFor(
      annotation = EventListener.class,
      attribute = "classes"
   )
   Class[] value() default {};

   @AliasFor(
      annotation = EventListener.class,
      attribute = "classes"
   )
   Class[] classes() default {};

   String condition() default "";
}
