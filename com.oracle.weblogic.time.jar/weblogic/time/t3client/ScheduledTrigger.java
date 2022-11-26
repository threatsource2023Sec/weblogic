package weblogic.time.t3client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.T3Client;
import weblogic.common.T3Exception;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.time.common.Scheduler;
import weblogic.time.common.TimeTriggerException;
import weblogic.time.common.Trigger;
import weblogic.time.t3client.internal.TimeMsg;
import weblogic.work.WorkManager;

public final class ScheduledTrigger implements ScheduledTriggerDef, Externalizable {
   static final long serialVersionUID = 265454191653137961L;
   private transient T3Client t3;
   private Scheduler scheduler;
   private Trigger trigger;
   private transient int regID = -1;
   private transient boolean isDaemon = false;

   public ScheduledTrigger(Scheduler scheduler, Trigger trigger, T3Client t3) {
      this.scheduler = scheduler;
      this.trigger = trigger;
      this.t3 = t3;
   }

   public ScheduledTrigger() {
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.scheduler = (Scheduler)in.readObject();
      this.trigger = (Trigger)in.readObject();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.scheduler);
      out.writeObject(this.trigger);
   }

   public void initialize() {
      this.scheduler = null;
      this.trigger = null;
      this.t3 = null;
   }

   public void destroy() {
      this.scheduler = null;
      this.trigger = null;
   }

   public Trigger getTrigger() {
      return this.trigger;
   }

   public Scheduler getScheduler() {
      return this.scheduler;
   }

   public int schedule() throws TimeTriggerException {
      String err = "(unknown)";

      try {
         this.regID = (Integer)this.t3.sendRecv("weblogic.time.t3client.internal.TimeProxy", (new TimeMsg()).doSchedule(this, this.isDaemon));
         if (this.isDaemon) {
            this.setDaemon(this.isDaemon);
         }
      } catch (T3Exception var3) {
         if (var3.getNestedException() instanceof TimeTriggerException) {
            throw (TimeTriggerException)var3.getNestedException();
         }

         throw new TimeTriggerException("" + var3.getNestedException());
      }

      return this.regID;
   }

   public static boolean cancel(T3Client t3, int registrationID) throws TimeTriggerException {
      try {
         Boolean b = (Boolean)t3.sendRecv("weblogic.time.t3client.internal.TimeProxy", (new TimeMsg()).doCancel(registrationID));
         return b;
      } catch (T3Exception var3) {
         if (var3.getNestedException() instanceof TimeTriggerException) {
            throw (TimeTriggerException)var3.getNestedException();
         } else {
            throw new TimeTriggerException("" + var3.getNestedException());
         }
      }
   }

   public void setDaemon(boolean isDaemon) throws TimeTriggerException {
      if (this.regID == -1) {
         this.isDaemon = isDaemon;
      } else {
         try {
            this.t3.sendRecv("weblogic.time.t3client.internal.TimeProxy", (new TimeMsg()).doSetDaemon(isDaemon, this.regID));
            this.isDaemon = isDaemon;
         } catch (T3Exception var3) {
            if (var3.getNestedException() instanceof TimeTriggerException) {
               throw (TimeTriggerException)var3.getNestedException();
            }

            throw new TimeTriggerException("" + var3.getNestedException());
         }
      }

   }

   public boolean isDaemon() {
      return this.isDaemon;
   }

   public void setWorkManager(WorkManager manager) {
   }

   public boolean cancel() throws TimeTriggerException {
      return cancel(this.t3, this.regID);
   }
}
