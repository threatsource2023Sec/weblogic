package com.bea.httppubsub.internal;

import com.bea.httppubsub.BayeuxMessage;
import java.util.Iterator;

public class ChannelEvent {
   BayeuxMessage message;
   Iterator interestedClients;

   public ChannelEvent(BayeuxMessage message, Iterator clients) {
      this.message = message;
      this.interestedClients = clients;
   }

   public BayeuxMessage getMessage() {
      return this.message;
   }

   public Iterator getInterestedClients() {
      return this.interestedClients;
   }
}
