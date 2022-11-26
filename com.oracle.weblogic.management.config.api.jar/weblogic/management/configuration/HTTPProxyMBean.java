package weblogic.management.configuration;

public interface HTTPProxyMBean extends DeploymentMBean {
   int getInitialConnections();

   void setInitialConnections(int var1);

   int getMaxConnections();

   void setMaxConnections(int var1);

   String getServerList();

   void setServerList(String var1);

   int getHealthCheckInterval();

   void setHealthCheckInterval(int var1);

   int getMaxRetries();

   void setMaxRetries(int var1);

   int getMaxHealthCheckInterval();

   void setMaxHealthCheckInterval(int var1);
}
