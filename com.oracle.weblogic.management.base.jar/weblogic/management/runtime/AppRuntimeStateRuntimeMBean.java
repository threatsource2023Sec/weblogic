package weblogic.management.runtime;

import java.util.Properties;

public interface AppRuntimeStateRuntimeMBean extends RuntimeMBean {
   String STATE_NEW = "STATE_NEW";
   String STATE_FAILED = "STATE_FAILED";
   String STATE_RETIRED = "STATE_RETIRED";
   String STATE_PREPARED = "STATE_PREPARED";
   String STATE_ADMIN = "STATE_ADMIN";
   String STATE_ACTIVE = "STATE_ACTIVE";
   String STATE_UPDATE_PENDING = "STATE_UPDATE_PENDING";
   String STATE_NOT_RESPONDING = "STATE_NOT_RESPONDING";
   int STAGING_ON = 1;
   int STAGING_OFF = 0;
   String[] appStateDefs = new String[]{"STATE_NOT_RESPONDING", "STATE_NEW", "STATE_FAILED", "STATE_RETIRED", "STATE_PREPARED", "STATE_ADMIN", "STATE_ACTIVE", "STATE_UPDATE_PENDING"};

   String[] getApplicationIds();

   boolean isAdminMode(String var1, String var2);

   boolean isActiveVersion(String var1);

   long getRetireTimeMillis(String var1);

   int getRetireTimeoutSeconds(String var1);

   String getIntendedState(String var1);

   String getIntendedState(String var1, String var2);

   String getCurrentState(String var1, String var2);

   String getCurrentStateOnClusterSnapshot(String var1, String var2);

   Properties getCurrentStateOnDemand(String var1, String var2, long var3);

   String getMultiVersionStateOnDemand(String[] var1, long var2);

   String[] getModuleIds(String var1);

   String[] getSubmoduleIds(String var1, String var2);

   String getModuleType(String var1, String var2);

   String getCurrentState(String var1, String var2, String var3);

   String[] getModuleTargets(String var1, String var2);

   String getCurrentState(String var1, String var2, String var3, String var4);

   String[] getModuleTargets(String var1, String var2, String var3);

   boolean isDeploymentConfigOverridden();
}
