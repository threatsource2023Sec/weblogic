package weblogic.javaee;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import weblogic.jndi.WLInitialContextFactory;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageDestinationConfiguration {
   String connectionFactoryJNDIName() default "";

   Class initialContextFactory() default WLInitialContextFactory.class;

   String providerURL() default "";
}
