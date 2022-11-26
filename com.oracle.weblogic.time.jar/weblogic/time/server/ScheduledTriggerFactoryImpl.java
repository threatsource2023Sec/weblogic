package weblogic.time.server;

import org.jvnet.hk2.annotations.Service;
import weblogic.time.api.ScheduledTriggerFactory;
import weblogic.time.common.Schedulable;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.time.common.Triggerable;
import weblogic.time.common.internal.TimeEventGenerator;

@Service
public class ScheduledTriggerFactoryImpl implements ScheduledTriggerFactory {
   public ScheduledTriggerDef createScheudledTrigger(Schedulable scheduler, Triggerable trigger) {
      ScheduledTrigger schedTrigger = new ScheduledTrigger(scheduler, trigger, TimeEventGenerator.getOne());
      return schedTrigger;
   }
}
