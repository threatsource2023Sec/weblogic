package weblogic.time.common;

import weblogic.work.WorkManager;

/** @deprecated */
@Deprecated
public interface ScheduledTriggerDef {
   /** @deprecated */
   @Deprecated
   int schedule() throws TimeTriggerException;

   /** @deprecated */
   @Deprecated
   boolean cancel() throws TimeTriggerException;

   /** @deprecated */
   @Deprecated
   void setDaemon(boolean var1) throws TimeTriggerException;

   /** @deprecated */
   @Deprecated
   void setWorkManager(WorkManager var1);

   /** @deprecated */
   @Deprecated
   boolean isDaemon() throws TimeTriggerException;
}
