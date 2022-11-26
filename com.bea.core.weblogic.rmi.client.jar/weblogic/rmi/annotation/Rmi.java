package weblogic.rmi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rmi {
   boolean clusterable() default false;

   LoadAlgorithmType loadAlgorithm() default LoadAlgorithmType.DEFAULT;

   String callRouterClassname() default "";

   boolean stickToFirstServer() default false;

   Class[] remoteInterfaces();

   RmiMethod defaultRMIMethodParams() default @RmiMethod;
}
