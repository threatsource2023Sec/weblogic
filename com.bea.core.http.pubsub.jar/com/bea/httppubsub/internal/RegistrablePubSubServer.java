package com.bea.httppubsub.internal;

import com.bea.httppubsub.ClientManager;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;

public interface RegistrablePubSubServer extends PubSubServer {
   void registerClientManager(ClientManager var1);

   void registerBayeuxHandlerFactory(BayeuxHandlerFactory var1);

   WeblogicPubsubBean getConfiguration();

   String getCookiePath();
}
