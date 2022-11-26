package weblogic.messaging.dispatcher;

public class ClientMessagingEnvironmentImpl extends MessagingEnvironment {
   public boolean isServer() {
      return false;
   }

   public Class getUtilClass() {
      return T3ClientCrossDomainUtil.class;
   }
}
