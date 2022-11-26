package javax.resource.spi.work;

import java.util.EventObject;

public class WorkEvent extends EventObject {
   public static final int WORK_ACCEPTED = 1;
   public static final int WORK_REJECTED = 2;
   public static final int WORK_STARTED = 3;
   public static final int WORK_COMPLETED = 4;
   private int type;
   private Work work;
   private WorkException exc;
   private long startDuration;

   public WorkEvent(Object source, int type, Work work, WorkException exc) {
      super(source);
      this.startDuration = -1L;
      this.type = type;
      this.work = work;
      this.exc = exc;
   }

   public WorkEvent(Object source, int type, Work work, WorkException exc, long startDuration) {
      this(source, type, work, exc);
      this.startDuration = startDuration;
   }

   public int getType() {
      return this.type;
   }

   public Work getWork() {
      return this.work;
   }

   public long getStartDuration() {
      return this.startDuration;
   }

   public WorkException getException() {
      return this.exc;
   }
}
