package com.bea.httppubsub.internal;

import com.bea.httppubsub.BayeuxMessage;

public interface BayeuxHandlerFactory {
   BayeuxHandler getMessageHandler(BayeuxMessage var1);

   void destroy();
}
