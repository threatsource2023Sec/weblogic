package weblogic.management.patching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedStoreKeys {
   public static final String DOMAIN_MODEL = "domainModel";
   public static final String NODE = "node";
   public static final String NODE_NAME = "nodeName";
   public static final String MACHINE_NAME = "machineName";
   public static final String CURRENT_MACHINE = "currentMachine";
   public static final String ORIG_MACHINE_NAME = "originalMachineName";
   public static final String SERVER_NAME = "serverName";
   public static final String CLUSTER_NAME = "clusterName";
   public static final String PARTITION_NAME = "partitionName";
   public static final String RESOURCEGROUP_NAME = "resourceGroup";
   public static final String ADMIN_MACHINE_NAME = "adminMachineName";
   public static final String APP_NAME = "applicationName";
   public static final String APP_CURRENT_SRC = "currentSource";
   public static final String APP_PATCHED_LOC = "patched";
   public static final String APP_BACKUP_LOC = "backup";
   public static final String DEPLOYMENT_PLAN = "deploymentPlan";
   public static final String REMOVE_PLAN_OVERRIDE = "removePlanOverride";
   public static final String APP_SOURCE_UPDATED = "isAppSourceUpdated";
   public static final String TARGETED_REDEPLOY_DONE = "isTargetedRedeployDone";
   public static final String NEW_DIRECTORY = "newDirectory";
   public static final String BACKUP_DIRECTORY = "backupDirectory";
   public static final String NEW_JAVAHOME = "newJavaHome";
   public static final String IS_UPDATE_JAVA_HOME = "isUpdateJavaHome";
   public static final String LAST_PARTITION_STATE = "lastPartitionState";
   public static final String LAST_SERVER_STATE = "lastServerState";
   public static final String IS_ADMINSERVER = "isAdminServer";
   public static final String IGNORE_SESSIONS = "ignoreSessions";
   public static final String WAIT_FOR_ALL_SESSIONS = "waitForAllSessions";
   public static final String WAIT_FOR_ALL_SESSIONS_ON_REVERT = "waitForAllSessionsOnRevert";
   public static final String IS_DRY_RUN = "isDryRun";
   public static final String DIRECTORY_SWITCH_PERFORMED = "directorySwitchPerformed";
   public static final String FAILOVER_GROUPS = "failoverGroups";
   public static final String ORIG_FAILOVER_GROUPS = "origFailoverGroups";
   public static final String UPDATING_SERVERS = "updatingServers";
   public static final String PENDING_SERVERS = "pendingServers";
   public static final String SCRIPT_NAME = "scriptName";
   public static final String SCRIPT_ENV_PROPS = "scriptEnvProps";
   public static final String SCRIPT_TIMEOUT_MIN = "scriptTimeoutInMin";
   public static final String ELASTICITY_MBEAN_MAP = "elasticityMBeanMap";
   public static final String TIMEOUT = "timeoutMillis";
   public static final String DISABLE_TIMEOUT = "disableAfterTime";
   public static final String SESSION_TIMEOUT = "sessionTimeout";
   public static final String SERVER_ACTIVATION_TIME = "serverActivationTime";
   public static final String READY_CHECK_APPS_TIMEOUT = "readyCheckAppsTimeoutInMin";
   public static final String JMSINFO = "jmsInfo";
   public static final String DESTINATION = "destination";
   public static final String IS_ROLLING_RESTART = "isRestart";
   public static final String HASTATUSTARGET = "haStatusTarget";
   public static final String HASTATUSWAITTIMEOUT = "haStatusWaitTimeout";
   public static final String TASKSTARTTIME = "startTime";
   public static final String EXTENSION_JAR_LOCATION = "extensionJarLocation";
   public static final String EXTENSION_JAR_EXTRACTION_DIR = "jarExtractionDir";
   public static final String EXTENSION_SCRIPT_DIR = "EXTENSION_SCRIPT_DIR";
   public static final String APPLICATION_INFO = "applicationInfo";
   public static final String EXTENSION_JARS = "extensionJars";
   public static final String OFFLINE_BEFORE_UPDATE_EXTENSION_SCRIPTS = "beforeUpdateExtensions";
   public static final String OFFLINE_AFTER_UPDATE_EXTENSION_SCRIPTS = "afterUpdateExtensions";
   protected List keyCollection = new ArrayList(Arrays.asList("domainModel", "node", "nodeName", "machineName", "currentMachine", "originalMachineName", "serverName", "clusterName", "partitionName", "resourceGroup", "adminMachineName", "applicationName", "currentSource", "patched", "backup", "deploymentPlan", "removePlanOverride", "isAppSourceUpdated", "isTargetedRedeployDone", "newDirectory", "backupDirectory", "newJavaHome", "lastPartitionState", "lastServerState", "isAdminServer", "ignoreSessions", "waitForAllSessions", "waitForAllSessionsOnRevert", "isDryRun", "directorySwitchPerformed", "failoverGroups", "origFailoverGroups", "updatingServers", "pendingServers", "scriptName", "scriptEnvProps", "scriptTimeoutInMin", "elasticityMBeanMap", "timeoutMillis", "disableAfterTime", "sessionTimeout", "serverActivationTime", "readyCheckAppsTimeoutInMin", "jmsInfo", "destination", "haStatusTarget", "haStatusWaitTimeout", "startTime"));

   public List getKeyCollection() {
      return this.keyCollection;
   }
}
