package weblogic.time.common;

import org.jvnet.hk2.annotations.Contract;
import weblogic.common.T3Exception;

/** @deprecated */
@Contract
@Deprecated
public interface TimeServicesDef {
   /** @deprecated */
   @Deprecated
   ScheduledTriggerDef getScheduledTrigger(Schedulable var1, Triggerable var2) throws TimeTriggerException;

   /** @deprecated */
   @Deprecated
   ScheduledTriggerDef getScheduledTrigger(Scheduler var1, Trigger var2) throws TimeTriggerException;

   /** @deprecated */
   @Deprecated
   long currentTimeMillis() throws T3Exception;

   /** @deprecated */
   @Deprecated
   int getRoundTripDelayMillis() throws T3Exception;

   /** @deprecated */
   @Deprecated
   int getLocalClockOffsetMillis() throws T3Exception;
}
