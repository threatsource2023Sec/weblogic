package com.oracle.jrockit.jfr;

public class TimedEvent extends DurationEvent {
   protected TimedEvent() {
   }

   protected TimedEvent(EventToken eventToken) {
      super(eventToken);
   }

   public boolean shouldWrite() {
      if (this.end == 0L) {
         this.end();
      }

      if (!super.shouldWrite()) {
         return false;
      } else {
         long duration = this.end - this.start;
         return duration > this.eventInfo.getThresholdTicks();
      }
   }
}
