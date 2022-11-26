package com.bea.httppubsub.security;

import com.bea.httppubsub.Client;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.PubSubContext;
import com.bea.httppubsub.bayeux.BayeuxConstants;

public class DelegateChannelAuthManager implements ChannelAuthorizationManager {
   protected ChannelAuthorizationManager delegate;

   public DelegateChannelAuthManager(ChannelAuthorizationManager authMgr) {
      this.delegate = authMgr;
   }

   public void initialize(PubSubContext context) {
      this.delegate.initialize(context);
   }

   public boolean hasPermission(Client client, String pattern, ChannelAuthorizationManager.Action action) {
      if (client instanceof LocalClient) {
         return true;
      } else {
         return pattern.startsWith("/meta/") && !pattern.equals(BayeuxConstants.META_SUBSCRIBE) ? true : this.delegate.hasPermission(client, pattern, action);
      }
   }

   public void destroy() {
      this.delegate.destroy();
   }
}
