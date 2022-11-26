package com.oracle.jrockit.jfr;

public abstract class RequestableEvent extends InstantEvent {
   protected RequestableEvent() {
   }

   protected RequestableEvent(EventToken eventToken) {
      super(eventToken);
   }

   public abstract void request();
}
