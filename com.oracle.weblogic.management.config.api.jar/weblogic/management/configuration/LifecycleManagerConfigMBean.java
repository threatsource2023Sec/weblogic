package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface LifecycleManagerConfigMBean extends ConfigurationMBean {
   String DEPLOYMENT_TYPE_NONE = "none";
   String DEPLOYMENT_TYPE_ADMIN = "admin";
   String DEPLOYMENT_TYPE_HA = "cluster";
   String PERSISTENCE_TYPE_XML = "XML";
   String PERSISTENCE_TYPE_DB = "database";

   int getLCMInitiatedConnectTimeout();

   void setLCMInitiatedConnectTimeout(int var1);

   int getLCMInitiatedReadTimeout();

   void setLCMInitiatedReadTimeout(int var1);

   int getLCMInitiatedConnectTimeoutForElasticity();

   void setLCMInitiatedConnectTimeoutForElasticity(int var1);

   int getLCMInitiatedReadTimeoutForElasticity();

   void setLCMInitiatedReadTimeoutForElasticity(int var1);

   String getDeploymentType();

   void setDeploymentType(String var1);

   TargetMBean getTarget();

   void setTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getPersistenceType();

   void setPersistenceType(String var1);

   String getDataSourceName();

   void setDataSourceName(String var1);

   LifecycleManagerEndPointMBean[] getEndPoints();

   LifecycleManagerEndPointMBean[] getConfiguredEndPoints();

   LifecycleManagerEndPointMBean lookupConfiguredEndPoint(String var1);

   LifecycleManagerEndPointMBean createConfiguredEndPoint(String var1);

   void destroyConfiguredEndPoint(LifecycleManagerEndPointMBean var1);

   boolean isEnabled();

   boolean isOutOfBandEnabled();

   void setOutOfBandEnabled(boolean var1);

   int getPeriodicSyncInterval();

   void setPeriodicSyncInterval(int var1);

   long getConfigFileLockTimeout();

   void setConfigFileLockTimeout(long var1);

   long getPropagationActivateTimeout();

   void setPropagationActivateTimeout(long var1);

   long getServerRuntimeTimeout();

   void setServerRuntimeTimeout(long var1);

   long getServerReadyTimeout();

   void setServerReadyTimeout(long var1);
}
