package com.bea.httppubsub;

import java.io.IOException;
import java.util.List;

public interface PubSubServer {
   String getName();

   Channel findChannel(String var1);

   Channel findOrCreateChannel(Client var1, String var2) throws PubSubSecurityException;

   void deleteChannel(Client var1, String var2) throws PubSubSecurityException;

   void publishToChannel(LocalClient var1, String var2, String var3) throws PubSubSecurityException;

   void subscribeToChannel(LocalClient var1, String var2) throws PubSubSecurityException;

   void unsubscribeToChannel(LocalClient var1, String var2);

   boolean routeMessages(List var1, Transport var2) throws PubSubServerException, IOException;

   ClientManager getClientManager();

   MessageFactory getMessageFactory();

   int getConnectionIdleTimeout();

   int getClientTimeout();

   int getReconnectInterval(boolean var1);

   boolean isMultiFrameSupported();

   int getPersistentClientTimeout();

   String[] getSupportedConnectionTypes();

   boolean isAllowPublishDirectly();

   PubSubContext getContext();
}
