package javax.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ClientEndpoint {
   String[] subprotocols() default {};

   Class[] decoders() default {};

   Class[] encoders() default {};

   Class configurator() default ClientEndpointConfig.Configurator.class;
}
