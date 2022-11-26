package weblogic.management.configuration;

public interface OverloadProtectionMBean extends ConfigurationMBean {
   String NO_ACTION = "no-action";
   String SYSTEM_EXIT = "system-exit";
   String FORCE_SHUTDOWN = "force-shutdown";
   String ADMIN_STATE = "admin-state";

   void setSharedCapacityForWorkManagers(int var1);

   int getSharedCapacityForWorkManagers();

   void setPanicAction(String var1);

   String getPanicAction();

   void setFailureAction(String var1);

   String getFailureAction();

   void setFreeMemoryPercentHighThreshold(int var1);

   int getFreeMemoryPercentHighThreshold();

   void setFreeMemoryPercentLowThreshold(int var1);

   int getFreeMemoryPercentLowThreshold();

   ServerFailureTriggerMBean getServerFailureTrigger();

   ServerFailureTriggerMBean createServerFailureTrigger();

   void destroyServerFailureTrigger();
}
