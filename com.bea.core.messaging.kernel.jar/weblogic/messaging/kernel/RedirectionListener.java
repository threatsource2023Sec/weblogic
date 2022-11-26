package weblogic.messaging.kernel;

import weblogic.messaging.Message;

public interface RedirectionListener {
   void expirationTimeReached(Info var1, boolean var2);

   void deliveryLimitReached(Info var1);

   public interface Info {
      Message getMessage();

      MessageElement getMessageElement();

      void setRedirectDestination(Destination var1);

      void setSendOptions(SendOptions var1);

      void setRedeliveryDelay(long var1);

      void setRedirectDestinationName(String var1);
   }
}
