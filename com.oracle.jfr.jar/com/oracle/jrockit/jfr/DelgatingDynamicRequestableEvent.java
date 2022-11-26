package com.oracle.jrockit.jfr;

final class DelgatingDynamicRequestableEvent extends RequestableEvent {
   private final RequestDelegate delegate;

   public DelgatingDynamicRequestableEvent(EventToken eventToken) {
      super(eventToken);
      this.delegate = eventToken.getRequestDelegate();
   }

   public void request() {
      this.delegate.onRequest(this);
   }
}
