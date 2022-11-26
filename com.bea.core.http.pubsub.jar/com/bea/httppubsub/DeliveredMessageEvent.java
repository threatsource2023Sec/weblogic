package com.bea.httppubsub;

import java.util.EventObject;

public class DeliveredMessageEvent extends EventObject {
   private static final long serialVersionUID = -1880058676699690387L;
   private final EventMessage message;

   public DeliveredMessageEvent(LocalClient client, EventMessage message) {
      super(client);
      this.message = message;
   }

   public LocalClient getLocalClient() {
      return (LocalClient)this.getSource();
   }

   public EventMessage getMessage() {
      return this.message;
   }
}
