package weblogic.management.configuration;

public interface BaseExecutorServiceMBean extends DeploymentMBean {
   String getDispatchPolicy();

   void setDispatchPolicy(String var1);

   int getMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(int var1);

   int getLongRunningPriority();

   void setLongRunningPriority(int var1);
}
