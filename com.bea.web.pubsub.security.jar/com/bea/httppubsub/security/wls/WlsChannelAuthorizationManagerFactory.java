package com.bea.httppubsub.security.wls;

import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.security.ChannelAuthorizationManagerFactory;

public class WlsChannelAuthorizationManagerFactory implements ChannelAuthorizationManagerFactory {
   private final ChannelAuthorizationManager authManager = new ChannelResourceAuthorizationManager();

   public ChannelAuthorizationManager getChannelAuthorizationManager() {
      return this.authManager;
   }
}
