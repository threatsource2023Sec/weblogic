package weblogic.management.configuration;

public interface BaseThreadFactoryMBean extends DeploymentMBean {
   int getMaxConcurrentNewThreads();

   void setMaxConcurrentNewThreads(int var1);

   int getPriority();

   void setPriority(int var1);
}
