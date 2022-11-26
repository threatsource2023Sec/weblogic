package com.bea.httppubsub.internal;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;

public interface BayeuxHandler {
   void initialize();

   void handle(BayeuxMessage var1, Transport var2) throws PubSubServerException;

   void destroy();
}
