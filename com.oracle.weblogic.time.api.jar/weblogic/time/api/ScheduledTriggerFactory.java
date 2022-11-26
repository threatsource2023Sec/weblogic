package weblogic.time.api;

import org.jvnet.hk2.annotations.Contract;
import weblogic.time.common.Schedulable;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.time.common.Triggerable;

@Contract
public interface ScheduledTriggerFactory {
   ScheduledTriggerDef createScheudledTrigger(Schedulable var1, Triggerable var2);
}
