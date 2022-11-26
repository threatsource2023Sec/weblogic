package weblogic.management.configuration;

public interface MaxThreadsConstraintMBean extends DeploymentMBean {
   int getCount();

   void setCount(int var1);

   String getConnectionPoolName();

   void setConnectionPoolName(String var1);

   void setQueueSize(int var1);

   int getQueueSize();
}
