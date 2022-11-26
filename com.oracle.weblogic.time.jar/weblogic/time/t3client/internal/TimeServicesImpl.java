package weblogic.time.t3client.internal;

import org.jvnet.hk2.annotations.Service;
import weblogic.common.DisconnectEvent;
import weblogic.common.DisconnectListener;
import weblogic.common.T3Client;
import weblogic.common.T3Exception;
import weblogic.common.T3ServicesDef;
import weblogic.time.common.Schedulable;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.time.common.Scheduler;
import weblogic.time.common.TimeServices;
import weblogic.time.common.TimeServicesDef;
import weblogic.time.common.TimeTriggerException;
import weblogic.time.common.Trigger;
import weblogic.time.common.Triggerable;
import weblogic.time.common.internal.InternalScheduledTrigger;
import weblogic.time.common.internal.TimeEventGenerator;
import weblogic.time.t3client.ScheduledTrigger;

@Service
public final class TimeServicesImpl implements TimeServicesDef, DisconnectListener, TimeServices {
   T3Client t3;
   T3ServicesDef svc;
   private static final boolean verbose = false;
   boolean needsInitSync = true;
   int roundTripDelayMillis = 0;
   int localClockOffsetMillis = 0;
   long serverTimeDiff = 0L;
   Object initLock = new Object();
   private TimeEventGenerator teg = null;

   public void setT3Client(T3Client t3) {
      this.t3 = t3;
      t3.addDisconnectListener(this);
   }

   public void setT3ServicesDef(T3ServicesDef svc) {
      this.svc = svc;
   }

   public ScheduledTriggerDef getScheduledTrigger(Schedulable scheduler, Triggerable trigger) {
      if (this.teg == null) {
         this.teg = TimeEventGenerator.getOne();
      }

      return new InternalScheduledTrigger(scheduler, trigger, this.teg);
   }

   public ScheduledTriggerDef getScheduledTrigger(Scheduler scheduler, Trigger trigger) throws TimeTriggerException {
      if (scheduler.theObject() != null && trigger.theObject() != null) {
         scheduler.private_initialize(this.svc);
         trigger.private_initialize(this.svc);
         return this.getScheduledTrigger((Schedulable)scheduler.theObject(), (Triggerable)trigger.theObject());
      } else {
         return new ScheduledTrigger(scheduler, trigger, this.t3);
      }
   }

   public void disconnectOccurred(DisconnectEvent de) {
      if (this.teg != null) {
         this.teg.stop();
      }

   }

   private void initTimeSync() throws T3Exception {
      if (this.needsInitSync) {
         synchronized(this.initLock) {
            if (this.needsInitSync) {
               this.needsInitSync = false;

               try {
                  TimeMsg tm = (TimeMsg)this.t3.sendRecv("weblogic.time.t3client.internal.TimeProxy", (new TimeMsg()).doPing());
                  long LlocalClockOffsetMillis = (tm.t2 - tm.t1 + (tm.t3 - tm.t4)) / 2L;
                  long LroundTripDelayMillis = tm.t4 - tm.t1 - (tm.t2 - tm.t3);
                  this.localClockOffsetMillis = (int)LlocalClockOffsetMillis;
                  this.roundTripDelayMillis = (int)LroundTripDelayMillis;
                  this.serverTimeDiff = tm.t2 - tm.t1 - LroundTripDelayMillis / 2L;
               } catch (T3Exception var8) {
                  if (var8.getNestedException() instanceof T3Exception) {
                     throw (T3Exception)var8.getNestedException();
                  }

                  throw new T3Exception("" + var8.getNestedException());
               }
            }
         }
      }

   }

   public long currentTimeMillis() throws T3Exception {
      this.initTimeSync();
      return System.currentTimeMillis() + this.serverTimeDiff;
   }

   public int getRoundTripDelayMillis() throws T3Exception {
      this.initTimeSync();
      return this.roundTripDelayMillis;
   }

   public int getLocalClockOffsetMillis() throws T3Exception {
      this.initTimeSync();
      return this.localClockOffsetMillis;
   }
}
