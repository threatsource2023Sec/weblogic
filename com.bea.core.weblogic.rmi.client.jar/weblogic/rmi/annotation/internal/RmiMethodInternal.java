package weblogic.rmi.annotation.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RmiMethodInternal {
   boolean oneway() default false;

   boolean asynchronousResult() default false;

   boolean transactional() default true;

   String dispatchPolicy() default "";

   int timeout() default 0;

   boolean idempotent() default false;

   boolean requiresTransaction() default false;

   boolean onewayTransactionalRequest() default false;

   boolean onewayTransactionalResponse() default false;

   boolean future() default false;

   DispatchContext dispatchContext() default DispatchContext.NONE;

   Class remoteExceptionWrapper() default Exception.class;
}
