package org.python.google.common.eventbus;

import org.python.google.common.annotations.Beta;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;

@Beta
public class DeadEvent {
   private final Object source;
   private final Object event;

   public DeadEvent(Object source, Object event) {
      this.source = Preconditions.checkNotNull(source);
      this.event = Preconditions.checkNotNull(event);
   }

   public Object getSource() {
      return this.source;
   }

   public Object getEvent() {
      return this.event;
   }

   public String toString() {
      return MoreObjects.toStringHelper((Object)this).add("source", this.source).add("event", this.event).toString();
   }
}
