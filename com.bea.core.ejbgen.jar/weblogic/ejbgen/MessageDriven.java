package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface MessageDriven {
   String ejbName();

   String destinationJndiName() default "UNSPECIFIED";

   String destinationResourceLink() default "UNSPECIFIED";

   String destinationType();

   String messageSelector() default "UNSPECIFIED";

   Constants.Bool durable() default Constants.Bool.UNSPECIFIED;

   String initialBeansInFreePool() default "UNSPECIFIED";

   Constants.Bool clientsOnSameServer() default Constants.Bool.UNSPECIFIED;

   String jmsPollingIntervalSeconds() default "UNSPECIFIED";

   String removeAsPrincipalName() default "UNSPECIFIED";

   MessageDrivenTransactionType transactionType() default MessageDriven.MessageDrivenTransactionType.UNSPECIFIED;

   String transTimeoutSeconds() default "UNSPECIFIED";

   Constants.Bool useCallerIdentity() default Constants.Bool.UNSPECIFIED;

   String maxBeansInFreePool() default "UNSPECIFIED";

   DefaultTransaction defaultTransaction() default MessageDriven.DefaultTransaction.UNSPECIFIED;

   String runAsIdentityPrincipal() default "UNSPECIFIED";

   String runAsPrincipalName() default "UNSPECIFIED";

   String dispatchPolicy() default "UNSPECIFIED";

   String passivateAsPrincipalName() default "UNSPECIFIED";

   AcknowledgeMode acknowledgeMode() default MessageDriven.AcknowledgeMode.UNSPECIFIED;

   String runAs() default "UNSPECIFIED";

   String jmsClientId() default "UNSPECIFIED";

   String createAsPrincipalName() default "UNSPECIFIED";

   String networkAccessPoint() default "UNSPECIFIED";

   String messagingType() default "UNSPECIFIED";

   int initSuspendSeconds() default 0;

   int maxSuspendSeconds() default 0;

   Constants.Bool generateUniqueJmsClientId() default Constants.Bool.UNSPECIFIED;

   Constants.Bool durableSubscriptionDeletion() default Constants.Bool.UNSPECIFIED;

   int maxMessagesInTransaction() default 0;

   String resourceAdapterJndiName() default "UNSPECIFIED";

   String remoteClientTimeout() default "UNSPECIFIED";

   String timerPersistentStore() default "UNSPECIFIED";

   public static enum AcknowledgeMode {
      UNSPECIFIED,
      AUTO_ACKNOWLEDGE,
      DUPS_OK_ACKNOWLEDGE;
   }

   public static enum DefaultTransaction {
      UNSPECIFIED,
      NOT_SUPPORTED,
      REQUIRED;
   }

   public static enum MessageDrivenTransactionType {
      UNSPECIFIED,
      BEAN,
      CONTAINER;
   }
}
