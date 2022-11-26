package weblogic.management.runtime;

import java.util.Properties;

public interface DeploymentManagerMBean extends RuntimeMBean {
   String APPDEPLOYMENT_CREATED = "weblogic.appdeployment.created";
   String APPDEPLOYMENT_DELETED = "weblogic.appdeployment.deleted";
   String APPDEPLOYMENT_NEW = "weblogic.appdeployment.state.new";
   String APPDEPLOYMENT_PREPARED = "weblogic.appdeployment.state.prepared";
   String APPDEPLOYMENT_ADMIN = "weblogic.appdeployment.state.admin";
   String APPDEPLOYMENT_ACTIVE = "weblogic.appdeployment.state.active";
   String APPDEPLOYMENT_RETIRED = "weblogic.appdeployment.state.retired";
   String APPDEPLOYMENT_FAILED = "weblogic.appdeployment.state.failed";
   String APPDEPLOYMENT_UPDATE_PENDING = "weblogic.appdeployment.state.update.pending";
   String APPDEPLOYMENT_UNKNOWN = "weblogic.appdeployment.state.unknown";
   String LIBDEPLOYMENT_CREATED = "weblogic.libdeployment.created";
   String LIBDEPLOYMENT_DELETED = "weblogic.libdeployment.deleted";
   String ADMIN_MODE = "adminMode";
   String ALT_DD = "altDD";
   String ALT_WLS_DD = "altWlsDD";
   String APP_VERSION = "appVersion";
   String CREATE_PLAN = "createPlan";
   String CLUSTER_DEPLOYMENT_TIMEOUT = "clusterDeploymentTimeout";
   String DEFAULT_SUBMODULE_TARGETS = "defaultSubmoduleTargets";
   String DEPLOYMENT_PRINCIPAL_NAME = "deploymentPrincipalName";
   String DEPLOYMENT_ORDER = "deploymentOrder";
   String FORCE_UNDEPLOYMENT_TIMEOUT = "forceUndeployTimeout";
   String GRACEFUL_IGNORE_SESSIONS = "gracefulIgnoreSessions";
   String GRACEFUL_PRODUCTION_TO_ADMIN = "gracefulProductionToAdmin";
   String NO_VERSION = "noVersion";
   String PLAN_VERSION = "planVersion";
   String LIB_SPEC_VERSION = "libSpecVer";
   String LIB_IMPL_VERSION = "libImplVer";
   String LIBRARY = "library";
   String RETIRE_GRACEFULLY = "retireGracefully";
   String RETIRE_TIMEOUT = "retireTimeout";
   String RMI_GRACE_PERIOD = "rmiGracePeriod";
   String SECURITY_MODEL = "securityModel";
   String SECURITY_VALIDATION_ENABLED = "securityValidationEnabled";
   String STAGE_MODE = "stageMode";
   String SUB_MODULE_TARGETS = "subModuleTargets";
   String TASK_ID = "id";
   String TIMEOUT = "timeout";
   String USE_NONEXCLUSIVE_LOCK = "useNonExclusiveLock";
   String VERSION_IDENTIFIER = "versionIdentifier";
   String STATIC_DEPLOYMENT = "static";
   String CACHE_IN_APP_DIRECTORY = "cacheInAppDirectory";
   String SPECIFIED_TARGETS_ONLY = "specifiedTargetsOnly";
   String PARTITION = "partition";
   String EDIT_SESSION = "editSession";
   String RESOURCE_GROUP = "resourceGroup";
   String RESOURCE_GROUP_TEMPLATE = "resourceGroupTemplate";
   String REMOVE_PLAN_OVERRIDE = "removePlanOverride";
   String START_TASK = "startTask";
   String APP_SHUTDOWN_ON_STOP = "appShutdownOnStop";

   DeploymentProgressObjectMBean deploy(String var1, String var2, String var3) throws RuntimeException;

   DeploymentProgressObjectMBean deploy(String var1, String var2, String[] var3, String var4, Properties var5) throws RuntimeException;

   DeploymentProgressObjectMBean distribute(String var1, String var2, String var3) throws RuntimeException;

   DeploymentProgressObjectMBean distribute(String var1, String var2, String[] var3, String var4, Properties var5) throws RuntimeException;

   DeploymentProgressObjectMBean appendToExtensionLoader(String var1) throws RuntimeException;

   DeploymentProgressObjectMBean appendToExtensionLoader(String var1, String[] var2, Properties var3) throws RuntimeException;

   DeploymentProgressObjectMBean undeploy(String var1, String var2) throws RuntimeException;

   DeploymentProgressObjectMBean undeploy(String var1, Properties var2) throws RuntimeException;

   DeploymentProgressObjectMBean redeploy(String var1, String var2, Properties var3) throws RuntimeException;

   DeploymentProgressObjectMBean redeploy(String var1, String var2, String var3, Properties var4) throws RuntimeException;

   DeploymentProgressObjectMBean update(String var1, String var2, Properties var3) throws RuntimeException;

   AppDeploymentRuntimeMBean[] getAppDeploymentRuntimes();

   AppDeploymentRuntimeMBean lookupAppDeploymentRuntime(String var1);

   AppDeploymentRuntimeMBean lookupAppDeploymentRuntime(String var1, Properties var2);

   LibDeploymentRuntimeMBean[] getLibDeploymentRuntimes();

   LibDeploymentRuntimeMBean lookupLibDeploymentRuntime(String var1);

   DeploymentProgressObjectMBean[] getDeploymentProgressObjects();

   void setMaximumDeploymentProgressObjectsCount(int var1);

   int getMaximumDeploymentProgressObjectsCount();

   void purgeCompletedDeploymentProgressObjects();

   void removeDeploymentProgressObject(String var1);

   String confirmApplicationName(Boolean var1, String var2, String var3, String var4, String var5) throws RuntimeException;

   String confirmApplicationName(Boolean var1, String var2, String var3, String var4, String var5, Properties var6) throws RuntimeException;
}
