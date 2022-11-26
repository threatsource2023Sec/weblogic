package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.T3Client;
import weblogic.common.T3Exception;
import weblogic.common.T3ServicesDef;
import weblogic.server.GlobalServiceLocator;
import weblogic.time.api.ScheduledTriggerFactory;
import weblogic.time.common.Schedulable;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.time.common.Scheduler;
import weblogic.time.common.TimeServicesDef;
import weblogic.time.common.TimeTriggerException;
import weblogic.time.common.Trigger;
import weblogic.time.common.Triggerable;

@Service
public class T3ServerServices implements T3ServicesDef, TimeServicesDef {
   public TimeServicesDef time() {
      return this;
   }

   private T3ServerServices() {
   }

   public ScheduledTriggerDef getScheduledTrigger(Schedulable scheduler, Triggerable trigger) {
      ScheduledTriggerFactory stf = (ScheduledTriggerFactory)GlobalServiceLocator.getServiceLocator().getService(ScheduledTriggerFactory.class, new Annotation[0]);
      return stf.createScheudledTrigger(scheduler, trigger);
   }

   public ScheduledTriggerDef getScheduledTrigger(Scheduler scheduler, Trigger trigger) throws TimeTriggerException {
      scheduler.private_initialize(this);
      if (trigger.theObject() == null && trigger.className().equals(scheduler.className())) {
         trigger.private_set_instance((Triggerable)scheduler.theObject());
      }

      trigger.private_initialize(this);
      return this.getScheduledTrigger((Schedulable)scheduler.theObject(), (Triggerable)trigger.theObject());
   }

   public long currentTimeMillis() throws T3Exception {
      return System.currentTimeMillis();
   }

   public int getRoundTripDelayMillis() throws T3Exception {
      return 0;
   }

   public int getLocalClockOffsetMillis() throws T3Exception {
      return 0;
   }

   /** @deprecated */
   @Deprecated
   public void private_setT3Client(T3Client t3) {
   }
}
