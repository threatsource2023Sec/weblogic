package weblogic.management.configuration;

public interface DynamicDeploymentMBean extends DeploymentMBean {
   long DEFAULT_INITIAL_BOOT_INTERVAL = 60L;
   long DEFAULT_DEPLOYMENT_STATE_INTERVAL = 120L;

   String getDistributionPolicy();

   void setDistributionPolicy(String var1);

   String getMigrationPolicy();

   void setMigrationPolicy(String var1);

   boolean getRestartInPlace();

   void setRestartInPlace(boolean var1);

   int getSecondsBetweenRestarts();

   void setSecondsBetweenRestarts(int var1);

   int getNumberOfRestartAttempts();

   void setNumberOfRestartAttempts(int var1);

   long getInitialBootDelaySeconds();

   void setInitialBootDelaySeconds(long var1);

   long getPartialClusterStabilityDelaySeconds();

   void setPartialClusterStabilityDelaySeconds(long var1);

   long getFailbackDelaySeconds();

   void setFailbackDelaySeconds(long var1);

   int getFailOverLimit();

   void setFailOverLimit(int var1);
}
