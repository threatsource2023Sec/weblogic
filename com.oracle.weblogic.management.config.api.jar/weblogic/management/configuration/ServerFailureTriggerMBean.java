package weblogic.management.configuration;

public interface ServerFailureTriggerMBean extends ConfigurationMBean {
   int getMaxStuckThreadTime();

   void setMaxStuckThreadTime(int var1);

   int getStuckThreadCount();

   void setStuckThreadCount(int var1);
}
