package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class AppRuntimeStateRuntimeMBeanImpl extends DomainRuntimeMBeanDelegate implements AppRuntimeStateRuntimeMBean {
   public static final String NAME = "AppRuntimeStateRuntime";
   private static final long TARGET_STATE_CACHE_EXPIRY = 15000L;
   private static volatile AppRuntimeStateRuntimeMBeanImpl singleton = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Map targetStateCache;
   private Map targetTimestampCache;
   private String partitionName = null;
   private boolean globalDomain;

   public AppRuntimeStateRuntimeMBeanImpl(RuntimeMBean parent, String partitionName) throws ManagementException {
      super(parent.getName(), parent);
      this.init(partitionName);
   }

   private AppRuntimeStateRuntimeMBeanImpl() throws ManagementException {
      super("AppRuntimeStateRuntime");
      this.init("DOMAIN");
   }

   private void init(String partitionName) {
      this.targetStateCache = new ConcurrentHashMap();
      this.targetTimestampCache = new ConcurrentHashMap();
      this.partitionName = partitionName;
      this.globalDomain = "DOMAIN".equals(partitionName);
   }

   public static final synchronized void initialize() throws ManagementException {
      if (singleton == null) {
         if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
            String msg = DeploymentManagerLogger.logMBeanUnavailable();
            throw new ManagementException(msg);
         }

         singleton = new AppRuntimeStateRuntimeMBeanImpl();
         DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
         domainAccess.setAppRuntimeStateRuntime(singleton);
      }

   }

   public String[] getApplicationIds() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      AppDeploymentMBean[] apps = null;
      if (this.globalDomain) {
         apps = AppDeploymentHelper.getAppsAndLibs(domain);
      } else {
         PartitionMBean partition = domain.lookupPartition(this.partitionName);
         if (partition == null) {
            throw new IllegalArgumentException("Incorrect partition: " + this.partitionName);
         }

         apps = AppDeploymentHelper.getAppsAndLibs(partition);
      }

      List appids = new ArrayList();

      for(int i = 0; i < apps.length; ++i) {
         AppDeploymentMBean app = apps[i];
         if (!app.isInternalApp()) {
            appids.add(app.getName());
         }
      }

      return (String[])appids.toArray(new String[appids.size()]);
   }

   public boolean isAdminMode(String appid, String target) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().isAdminMode(appid, target);
   }

   public boolean isActiveVersion(String appid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().isActiveVersion(appid);
   }

   public long getRetireTimeMillis(String appid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getRetireTimeMillis(appid);
   }

   public int getRetireTimeoutSeconds(String appid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getRetireTimeoutSeconds(appid);
   }

   public String getIntendedState(String appid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getIntendedState(appid);
   }

   public String getIntendedState(String appid, String target) {
      appid = this.getGlobalAppId(appid);
      String state = AppRuntimeStateManager.getManager().getIntendedState(appid, target);
      return this.fixState(state, target);
   }

   public String getCurrentState(String appid, String target) {
      appid = this.getGlobalAppId(appid);
      String state = AppRuntimeStateManager.getManager().getCurrentState(appid, target);
      return this.fixState(state, target);
   }

   public String getCurrentStateOnClusterSnapshot(String appId, String clusterName) {
      String state = AppRuntimeStateManager.getManager().getCurrentStateOnClusterSnapshot(appId, clusterName);
      return this.fixState(state, clusterName);
   }

   public Properties getCurrentStateOnDemand(String appId, String target, long timeout) {
      return AppRuntimeStateManager.getManager().getCurrentStateOnDemand(appId, target, timeout);
   }

   public String getMultiVersionStateOnDemand(String[] configuredIds, long timeout) {
      return AppRuntimeStateManager.getManager().getMultiVersionStateOnDemand(configuredIds, timeout);
   }

   public String[] getModuleIds(String appid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getModuleIds(appid);
   }

   public String[] getSubmoduleIds(String appid, String moduleid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getSubmoduleIds(appid, moduleid);
   }

   public String getModuleType(String appid, String moduleid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getModuleType(appid, moduleid);
   }

   public String getCurrentState(String appid, String moduleid, String target) {
      appid = this.getGlobalAppId(appid);
      String state = AppRuntimeStateManager.getManager().getCurrentState(appid, moduleid, target);
      return this.fixState(state, target);
   }

   public TargetModuleState[] getModuleStates(String appid, String moduleid, String target) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getModuleStates(appid, moduleid, target);
   }

   public String[] getModuleTargets(String appid, String moduleid) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getModuleTargets(appid, moduleid);
   }

   public String getCurrentState(String appid, String moduleid, String subModuleId, String target) {
      appid = this.getGlobalAppId(appid);
      if (Debug.DEPLOY_STATE_DEBUGGER.isDebugEnabled()) {
         Debug.DEPLOY_STATE_DEBUGGER.debug("Getting current state for app: " + appid + ", module: " + moduleid + ", sub module: " + subModuleId + ", target: " + target + ". Called from: " + Arrays.toString(Thread.currentThread().getStackTrace()));
      }

      String state = AppRuntimeStateManager.getManager().getCurrentState(appid, moduleid, subModuleId, target);
      return this.fixState(state, target);
   }

   public String[] getModuleTargets(String appid, String moduleid, String subModuleId) {
      appid = this.getGlobalAppId(appid);
      return AppRuntimeStateManager.getManager().getModuleTargets(appid, moduleid, subModuleId);
   }

   private String getGlobalAppId(String appid) {
      return this.globalDomain ? appid : ApplicationVersionUtils.getApplicationIdWithPartition(appid, this.partitionName);
   }

   private String fixState(String state, String targetName) {
      if ("STATE_NEW".equals(state)) {
         return state;
      } else if (this.isTargetShutDown(targetName)) {
         if (Debug.DEPLOY_STATE_DEBUGGER.isDebugEnabled()) {
            Debug.DEPLOY_STATE_DEBUGGER.debug("target " + targetName + " is down. Downgrading state to NEW");
         }

         return "STATE_NEW";
      } else {
         return state;
      }
   }

   private boolean isTargetShutDown(String targetName) {
      if (this.targetTimestampCache.containsKey(targetName)) {
         long timeStamp = (Long)this.targetTimestampCache.get(targetName);
         if (System.currentTimeMillis() < timeStamp + 15000L) {
            return (Boolean)this.targetStateCache.get(targetName);
         }
      }

      try {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         Set serverNames = null;
         TargetMBean target = null;
         if (this.globalDomain) {
            target = domain.lookupInAllTargets(targetName);
         } else {
            PartitionMBean partition = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(this.partitionName);
            if (partition == null) {
               throw new IllegalArgumentException("Incorrect partition: " + this.partitionName);
            }

            TargetMBean[] var6 = partition.findEffectiveTargets();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               TargetMBean tm = var6[var8];
               if (targetName.equals(tm.getName())) {
                  target = tm;
                  break;
               }
            }
         }

         if (target != null) {
            serverNames = target.getServerNames();
         }

         if (serverNames != null && !serverNames.isEmpty()) {
            DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
            DomainRuntimeServiceMBean domainRuntimeService = domainAccess.getDomainRuntimeService();
            ServerRuntimeMBean[] serverRuntimes = domainRuntimeService.getServerRuntimes();
            List serverRuntimeNames = new ArrayList(serverRuntimes.length);
            if (serverRuntimes != null) {
               ServerRuntimeMBean[] var22 = serverRuntimes;
               int var10 = serverRuntimes.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  ServerRuntimeMBean serverRuntime = var22[var11];
                  serverRuntimeNames.add(serverRuntime.getName());
               }
            }

            DomainRuntimeMBean domainRuntime = null;
            DomainPartitionRuntimeMBean domainPartitionRuntime = null;
            if (this.globalDomain) {
               domainRuntime = domainAccess.getDomainRuntime();
            } else {
               domainPartitionRuntime = domainAccess.lookupDomainPartitionRuntime(this.partitionName);
            }

            Iterator var25 = serverNames.iterator();

            boolean shutdown;
            do {
               boolean serverRuntimeIsAvailable;
               String serverName;
               do {
                  if (!var25.hasNext()) {
                     this.targetStateCache.put(targetName, Boolean.TRUE);
                     this.targetTimestampCache.put(targetName, System.currentTimeMillis());
                     return true;
                  }

                  serverName = (String)var25.next();
                  serverRuntimeIsAvailable = serverRuntimeNames.contains(serverName);
               } while(!serverRuntimeIsAvailable);

               shutdown = false;
               if (this.globalDomain) {
                  ServerLifeCycleRuntimeMBean lifeCycle = domainRuntime.lookupServerLifeCycleRuntime(serverName);
                  shutdown = lifeCycle == null || "SHUTDOWN".equals(lifeCycle.getState());
               } else {
                  PartitionLifeCycleRuntimeMBean lifeCycle = domainPartitionRuntime.getPartitionLifeCycleRuntime();
                  shutdown = lifeCycle == null || State.isShutdown(lifeCycle.getState());
               }
            } while(shutdown);

            this.targetStateCache.put(targetName, Boolean.FALSE);
            this.targetTimestampCache.put(targetName, System.currentTimeMillis());
            return false;
         }
      } catch (Exception var16) {
      }

      return false;
   }

   public boolean isDeploymentConfigOverridden() {
      return ConfiguredDeployments.getConfigureDeploymentsHandler().getMultiVersionDeployments().isEnabled();
   }
}
