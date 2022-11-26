package weblogic.websocket.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @deprecated */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface WebSocket {
   String[] pathPatterns() default {"/ws/*"};

   String dispatchPolicy() default "";

   int timeout() default 30;

   int maxMessageSize() default 4080;

   int maxConnections() default -1;
}
