package com.oracle.jrockit.jfr;

public class DurationEvent extends InstantEvent {
   long start;
   long end;
   private long startThreadId;

   protected DurationEvent() {
   }

   protected DurationEvent(EventToken eventToken) {
      super(eventToken);
   }

   public void begin() {
      if (this.start != 0L) {
         throw new IllegalStateException("Event time period already started.");
      } else {
         this.start = this.eventInfo.counterTime();
         this.startThreadId = Thread.currentThread().getId();
      }
   }

   public void end() throws IllegalStateException {
      if (this.start == 0L) {
         throw new IllegalStateException("Not started");
      } else {
         this.end = this.eventInfo.counterTime();
         long id = Thread.currentThread().getId();
         if (id != this.startThreadId) {
            throw new IllegalStateException("Thread started in thread " + this.startThreadId + ", ended in " + id);
         }
      }
   }

   void write() {
      this.eventInfo.write(this.receiver, this.start, this.end);
   }

   public void reset() {
      this.start = 0L;
      this.startThreadId = 0L;
      super.reset();
   }
}
