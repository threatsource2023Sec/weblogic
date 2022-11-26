package weblogic.messaging.dispatcher;

public abstract class MessagingEnvironment {
   private static MessagingEnvironment singleton;

   public static MessagingEnvironment getMessagingEnvironment() {
      if (singleton == null) {
         try {
            singleton = (MessagingEnvironment)Class.forName("weblogic.messaging.dispatcher.WLSMessagingtEnvironmentImpl").newInstance();
         } catch (Exception var3) {
            try {
               singleton = (MessagingEnvironment)Class.forName("weblogic.messaging.dispatcher.ClientMessagingEnvironmentImpl").newInstance();
            } catch (Exception var2) {
               throw new IllegalArgumentException(var2.toString());
            }
         }
      }

      return singleton;
   }

   static void setMessagingEnvironment(MessagingEnvironment helper) {
      singleton = helper;
   }

   public abstract boolean isServer();

   public abstract Class getUtilClass();
}
