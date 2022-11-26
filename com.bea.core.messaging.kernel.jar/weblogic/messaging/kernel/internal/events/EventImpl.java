package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.Event;
import weblogic.utils.time.Timer;

public class EventImpl implements Event {
   private String subjectName;
   private Xid xid;
   private long milliseconds;
   private long nanoseconds;
   private static Timer timer = Timer.createTimer();

   public EventImpl(String subjectName, Xid xid) {
      this.subjectName = subjectName;
      this.xid = xid;
      this.setTime();
   }

   public void setTime() {
      this.milliseconds = timer.isNative() ? 0L : System.currentTimeMillis();
      this.nanoseconds = timer.isNative() ? timer.timestamp() : 0L;
   }

   public String getSubjectName() {
      return this.subjectName;
   }

   public Xid getXid() {
      return this.xid;
   }

   void setXid(Xid xid) {
      this.xid = xid;
   }

   public long getMilliseconds() {
      return this.milliseconds;
   }

   public long getNanoseconds() {
      return this.nanoseconds;
   }
}
