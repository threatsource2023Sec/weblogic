package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface DeploymentConfigurationMBean extends ConfigurationMBean {
   int getMaxAppVersions();

   void setMaxAppVersions(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isRemoteDeployerEJBEnabled();

   void setRemoteDeployerEJBEnabled(boolean var1);

   boolean isRestageOnlyOnRedeploy();

   void setRestageOnlyOnRedeploy(boolean var1);

   DeploymentValidationPluginMBean getDeploymentValidationPlugin();

   void setMaxRetiredTasks(int var1);

   int getMaxRetiredTasks();

   int getDeploymentServiceMessageRetryCount();

   void setDeploymentServiceMessageRetryCount(int var1);

   int getDeploymentServiceMessageRetryInterval();

   void setDeploymentServiceMessageRetryInterval(int var1);

   long getLongRunningRetireThreadDumpStartTime();

   void setLongRunningRetireThreadDumpStartTime(long var1);

   long getLongRunningRetireThreadDumpInterval();

   void setLongRunningRetireThreadDumpInterval(long var1);

   int getLongRunningRetireThreadDumpCount();

   void setLongRunningRetireThreadDumpCount(int var1);

   DeploymentConfigOverridesMBean getDeploymentConfigOverrides();

   int getDefaultMultiVersionAppRetireTimeout();

   void setDefaultMultiVersionAppRetireTimeout(int var1);
}
