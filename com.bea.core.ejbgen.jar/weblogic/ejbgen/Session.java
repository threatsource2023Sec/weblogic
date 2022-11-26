package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface Session {
   String ejbName();

   Constants.Bool isClusterable() default Constants.Bool.UNSPECIFIED;

   ReplicationType replicationType() default Session.ReplicationType.UNSPECIFIED;

   Constants.Bool clientsOnSameServer() default Constants.Bool.UNSPECIFIED;

   String serviceEndpoint() default "UNSPECIFIED";

   Constants.Bool useCallerIdentity() default Constants.Bool.UNSPECIFIED;

   String persistentStoreDir() default "UNSPECIFIED";

   String callRouterClassName() default "UNSPECIFIED";

   Constants.TransactionAttribute defaultTransaction() default Constants.TransactionAttribute.UNSPECIFIED;

   Constants.Bool methodsAreIdempotent() default Constants.Bool.UNSPECIFIED;

   String runAsIdentityPrincipal() default "UNSPECIFIED";

   String runAsPrincipalName() default "UNSPECIFIED";

   Constants.Bool enableCallByReference() default Constants.Bool.UNSPECIFIED;

   String dispatchPolicy() default "UNSPECIFIED";

   String passivateAsPrincipalName() default "UNSPECIFIED";

   String maxBeansInCache() default "UNSPECIFIED";

   String initialBeansInFreePool() default "UNSPECIFIED";

   Constants.Bool allowRemoveDuringTransaction() default Constants.Bool.UNSPECIFIED;

   String idleTimeoutSeconds() default "UNSPECIFIED";

   SessionType type() default Session.SessionType.UNSPECIFIED;

   String removeAsPrincipalName() default "UNSPECIFIED";

   String transTimeoutSeconds() default "UNSPECIFIED";

   SessionTransactionType transactionType() default Session.SessionTransactionType.UNSPECIFIED;

   Constants.Bool allowConcurrentCalls() default Constants.Bool.UNSPECIFIED;

   String maxBeansInFreePool() default "UNSPECIFIED";

   String sessionTimeoutSeconds() default "UNSPECIFIED";

   String beanLoadAlgorithm() default "UNSPECIFIED";

   Constants.HomeLoadAlgorithm homeLoadAlgorithm() default Constants.HomeLoadAlgorithm.UNSPECIFIED;

   CacheType cacheType() default Session.CacheType.UNSPECIFIED;

   String runAs() default "UNSPECIFIED";

   Constants.Bool homeIsClusterable() default Constants.Bool.UNSPECIFIED;

   String homeCallRouterClassName() default "UNSPECIFIED";

   String createAsPrincipalName() default "UNSPECIFIED";

   String networkAccessPoint() default "UNSPECIFIED";

   String timerPersistentStore() default "UNSPECIFIED";

   String remoteClientTimeout() default "UNSPECIFIED";

   public static enum CacheType {
      UNSPECIFIED,
      N_R_U,
      L_R_U;
   }

   public static enum SessionTransactionType {
      UNSPECIFIED,
      BEAN,
      CONTAINER;
   }

   public static enum SessionType {
      UNSPECIFIED,
      STATELESS,
      STATEFUL;
   }

   public static enum ReplicationType {
      UNSPECIFIED,
      IN_MEMORY,
      NONE;
   }
}
