package javax.websocket.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ServerEndpoint {
   String value();

   String[] subprotocols() default {};

   Class[] decoders() default {};

   Class[] encoders() default {};

   Class configurator() default ServerEndpointConfig.Configurator.class;
}
