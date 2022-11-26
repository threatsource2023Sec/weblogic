package com.oracle.jrockit.jfr;

import oracle.jrockit.jfr.events.EventHandler;
import oracle.jrockit.jfr.events.JavaEventDescriptor;
import oracle.jrockit.jfr.events.ValueDescriptor;

public class InstantEvent {
   final EventHandler eventInfo;
   final Object receiver;

   protected InstantEvent() {
      EventToken t = Producer.locateToken(this.getClass());
      this.eventInfo = t.getEventInfo();
      this.receiver = t.receiverFor(this);
   }

   protected InstantEvent(EventToken eventToken) throws IllegalArgumentException {
      EventHandler info = eventToken.getEventInfo();
      Class c = info.getDescriptor().getEventClass();
      if (c != this.getClass()) {
         throw new IllegalArgumentException("Expected class: " + this.getClass() + ", got:" + c);
      } else {
         this.eventInfo = info;
         this.receiver = eventToken.receiverFor(this);
      }
   }

   public EventInfo getEventInfo() {
      return this.eventInfo;
   }

   public final void commit() {
      if (this.shouldWrite()) {
         this.write();
      }
   }

   void write() {
      this.eventInfo.write(this.receiver, 0L, this.eventInfo.counterTime());
   }

   public void reset() {
   }

   public final void setValue(String valueId, Object value) {
      this.setValue(this.indexOf(valueId), value);
   }

   public final int indexOf(String valueId) {
      return this.eventInfo.getDescriptor().valueIndex(valueId);
   }

   public void setValue(int valueIndex, Object value) {
      ValueDescriptor d = this.eventInfo.getDescriptor().getValues()[valueIndex];
      d.setValue(this.receiver, value);
   }

   public boolean shouldWrite() {
      return this.eventInfo.isEnabled();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      JavaEventDescriptor d = this.eventInfo.getDescriptor();
      sb.append(d.getName() + "=[");
      sb.append("id=" + d.getId() + ", ");
      ValueDescriptor[] arr$ = d.getValues();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueDescriptor v = arr$[i$];
         sb.append('\'').append(v.getName()).append('\'').append("=");
         sb.append(v.loadValue(this.receiver));
         sb.append(", ");
      }

      sb.setLength(sb.length() - 2);
      sb.append("]");
      return sb.toString();
   }
}
