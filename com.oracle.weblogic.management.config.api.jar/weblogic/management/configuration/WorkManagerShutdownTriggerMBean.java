package weblogic.management.configuration;

public interface WorkManagerShutdownTriggerMBean extends ConfigurationMBean {
   int getMaxStuckThreadTime();

   void setMaxStuckThreadTime(int var1);

   int getStuckThreadCount();

   void setStuckThreadCount(int var1);

   boolean isResumeWhenUnstuck();

   void setResumeWhenUnstuck(boolean var1);
}
