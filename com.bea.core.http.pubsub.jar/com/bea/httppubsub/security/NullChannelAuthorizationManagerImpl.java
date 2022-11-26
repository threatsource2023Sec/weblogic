package com.bea.httppubsub.security;

import com.bea.httppubsub.Client;
import com.bea.httppubsub.PubSubContext;

public class NullChannelAuthorizationManagerImpl implements ChannelAuthorizationManager {
   public void initialize(PubSubContext context) {
   }

   public boolean hasPermission(Client client, String channelPattern, ChannelAuthorizationManager.Action action) {
      return true;
   }

   public void destroy() {
   }
}
