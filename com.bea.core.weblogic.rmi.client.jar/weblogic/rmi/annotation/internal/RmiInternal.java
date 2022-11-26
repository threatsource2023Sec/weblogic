package weblogic.rmi.annotation.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import weblogic.rmi.annotation.LoadAlgorithmType;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RmiInternal {
   boolean clusterable() default false;

   LoadAlgorithmType loadAlgorithm() default LoadAlgorithmType.DEFAULT;

   String callRouterClassname() default "";

   boolean stickToFirstServer() default false;

   boolean isActivatable() default false;

   boolean useServerSideStubs() default false;

   boolean enableCallByReference() default true;

   String remoteRefClassname() default "";

   String serverRefClassname() default "";

   int initialReference() default -1;

   String networkAccessPoint() default "";

   DgcPolicy dgcPolicy() default DgcPolicy.DEFAULT;

   Security confidentiality() default Security.DEFAULT;

   Security clientAuthentication() default Security.DEFAULT;

   Security clientCertAuthentication() default Security.DEFAULT;

   Security integrity() default Security.DEFAULT;

   Security identityAssertion() default Security.DEFAULT;

   boolean statefulAuthentication() default false;

   String replicaHandlerClassname() default "";

   boolean propagateEnvironment() default false;

   String activationIdentifierClassname() default "";

   String activationHelperClassname() default "";

   Class[] remoteInterfaces();

   RmiMethodInternal defaultRMIMethodParams() default @RmiMethodInternal;
}
