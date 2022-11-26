package com.oracle.jrockit.jfr;

import oracle.jrockit.jfr.events.EventHandler;
import oracle.jrockit.jfr.events.JavaEventDescriptor;

public final class DynamicEventToken extends EventToken {
   private final JavaEventDescriptor descriptor;

   DynamicEventToken(EventHandler eventInfo, RequestDelegate requestDelegate) {
      super(eventInfo, requestDelegate);
      this.descriptor = eventInfo.getDescriptor();
   }

   Object receiverFor(InstantEvent event) {
      return new Object[this.descriptor.getValues().length];
   }

   public InstantEvent newInstantEvent() throws UnsupportedOperationException {
      if (this.descriptor.getEventClass() != InstantEvent.class) {
         throw new UnsupportedOperationException("Not an InstantEvent");
      } else {
         return new InstantEvent(this);
      }
   }

   public DurationEvent newDurationEvent() throws UnsupportedOperationException {
      if (this.descriptor.getEventClass() != DurationEvent.class) {
         throw new UnsupportedOperationException("Not a DurationEvent");
      } else {
         return new DurationEvent(this);
      }
   }

   public TimedEvent newTimedEvent() throws UnsupportedOperationException {
      if (this.descriptor.getEventClass() != TimedEvent.class) {
         throw new UnsupportedOperationException("Not a TimedEvent");
      } else {
         return new TimedEvent(this);
      }
   }

   public RequestableEvent newRequestableEvent() throws UnsupportedOperationException {
      if (this.descriptor.getEventClass() != DelgatingDynamicRequestableEvent.class) {
         throw new UnsupportedOperationException("Not a RequestableEvent");
      } else {
         return new DelgatingDynamicRequestableEvent(this);
      }
   }
}
