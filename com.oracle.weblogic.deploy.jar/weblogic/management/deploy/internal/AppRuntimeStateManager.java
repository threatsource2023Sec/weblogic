package weblogic.management.deploy.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.DeploymentManager;
import weblogic.application.utils.ApplicationRuntimeStateManager;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.DeploymentVersion;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.OrderedDeployments;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.deploy.service.internal.transport.CommonMessageSender;
import weblogic.deploy.service.internal.transport.CommonMessageSender.DeploymentObject;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.utils.StackTraceUtils;
import weblogic.xml.util.IndentingXMLStreamWriter;

@Service
public class AppRuntimeStateManager implements ApplicationRuntimeStateManager, DescriptorUpdateListener {
   public static final String DEPLOYMENT_STORE_CONN_NAME = "weblogic.deploy.store";
   private final Map appStates = new LinkedHashMap();
   private PersistentStore pStore;
   private PersistentMap psMap;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final String ROOT = "ROOT_MODULE";
   private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
   private static long lastNanoTime = -1L;
   private static int lastNumberOfIds = 0;
   private static String lock = new String();
   private Map applicationCheckers = new ConcurrentHashMap();
   private Map appIdToServerNames = new HashMap();

   public static AppRuntimeStateManager getManager() {
      return AppRuntimeStateManager.AppRuntimeStateManagerInitializer.INSTANCE;
   }

   public ApplicationRuntimeState get(String appId) {
      synchronized(this.appStates) {
         return (ApplicationRuntimeState)this.appStates.get(appId);
      }
   }

   public ApplicationRuntimeState getDirect(String appId) {
      try {
         return (ApplicationRuntimeState)this.psMap.get(appId);
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   protected Set getAppStates() {
      synchronized(this.appStates) {
         return this.appStates.entrySet();
      }
   }

   private void initStore() throws ManagementException {
      if (this.psMap == null) {
         try {
            this.pStore = (PersistentStoreXA)PersistentStoreManager.getManager().getDefaultStore();
            this.psMap = this.pStore.createPersistentMap("weblogic.deploy.store");
         } catch (Exception var3) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug(StackTraceUtils.throwable2StackTrace(var3));
            } else if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Failed to initialize Default Persistence Store '" + (this.pStore == null ? "" : this.pStore.getName()) + "' due to " + var3);
            }

            String msg = DeploymentManagerLogger.storeCreateFailed(var3);
            throw new ManagementException(msg, var3);
         }
      }
   }

   public Map getDeploymentVersions() {
      Map deploymentVersions = new LinkedHashMap();
      synchronized(this.appStates) {
         Iterator var3 = this.appStates.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            String appId = (String)entry.getKey();
            ApplicationRuntimeState appRuntimeState = (ApplicationRuntimeState)entry.getValue();
            DeploymentVersion version = appRuntimeState.getDeploymentVersion();
            if (version != null) {
               deploymentVersions.put(appId, version);
            }
         }

         return deploymentVersions;
      }
   }

   public Map getStartupStateForServer(String server, String partitionName) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (domain.getAdminServerName().equals(server)) {
         return null;
      } else {
         Map newStates = new LinkedHashMap();
         synchronized(this.appStates) {
            Iterator var6 = this.appStates.entrySet().iterator();

            label63:
            while(true) {
               String appId;
               ApplicationRuntimeState state;
               do {
                  if (!var6.hasNext()) {
                     return newStates;
                  }

                  Map.Entry eachEntry = (Map.Entry)var6.next();
                  appId = (String)eachEntry.getKey();
                  state = (ApplicationRuntimeState)eachEntry.getValue();
               } while(state == null);

               ApplicationRuntimeState newState = new ApplicationRuntimeState(state);
               Map targetStates = state.getAppTargetState();
               Map modules = state.getModules();
               Iterator var13 = targetStates.entrySet().iterator();

               while(true) {
                  String target;
                  TargetMBean tmb;
                  do {
                     do {
                        if (!var13.hasNext()) {
                           continue label63;
                        }

                        Map.Entry eachTargetEntry = (Map.Entry)var13.next();
                        target = (String)eachTargetEntry.getKey();
                     } while(target == null);

                     tmb = domain.lookupInAllTargets(target);
                  } while(tmb == null);

                  if (this.isInTarget(tmb, server)) {
                     newState.updateAppTargetState(state.getAppTargetState(target), target);
                     Iterator var17 = modules.keySet().iterator();

                     while(var17.hasNext()) {
                        String moduleId = (String)var17.next();
                        newState.updateState(this.getModuleStates(appId, moduleId, target));
                     }

                     newStates.put(appId, newState);
                  }

                  DeploymentVersion version = state.getDeploymentVersion();
                  if (version != null) {
                     newState.setDeploymentVersion(version);
                  }
               }
            }
         }
      }
   }

   private boolean isInTarget(TargetMBean target, String server) {
      return target.getServerNames().contains(server);
   }

   public void loadStartupState(Map state) throws ManagementException {
      if (state == null) {
         this.readAppStatesFromStore();
      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Reading app states received from admin server ...");
         }

         this.initStore();
         Set removeApps = new LinkedHashSet();
         String appId;
         synchronized(this.appStates) {
            Iterator var4 = this.appStates.keySet().iterator();

            while(true) {
               if (!var4.hasNext()) {
                  break;
               }

               appId = (String)var4.next();
               removeApps.add(appId);
            }
         }

         Iterator var3 = removeApps.iterator();

         while(var3.hasNext()) {
            String s = (String)var3.next();
            this.remove(s);
         }

         ApplicationRuntimeState newState;
         for(var3 = state.entrySet().iterator(); var3.hasNext(); this.save(newState)) {
            Map.Entry entry = (Map.Entry)var3.next();
            appId = (String)entry.getKey();
            newState = (ApplicationRuntimeState)entry.getValue();
            synchronized(this.appStates) {
               this.appStates.put(appId, newState);
            }
         }

      }
   }

   public void updatePartitionStartupState(Map state, String partitionName) throws ManagementException {
   }

   public void readAppStatesFromStore() throws ManagementException {
      this.initStore();

      try {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Reading app states from store...");
         }

         ArrayList removeList = new ArrayList();
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         if (this.psMap.isEmpty() && Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Default Persistence Store '" + (this.pStore == null ? "" : this.pStore.getName()) + "' either does not exist or does not contain any application states.");
         }

         Set psKeys = this.psMap.keySet();
         Iterator var4 = psKeys.iterator();

         String appId;
         while(var4.hasNext()) {
            appId = (String)var4.next();
            if (AppDeploymentHelper.lookupAppOrLib(appId, domain) == null) {
               removeList.add(appId);
            } else {
               try {
                  ApplicationRuntimeState appState = (ApplicationRuntimeState)this.psMap.get(appId);
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("read from store: " + appState);
                  }

                  synchronized(this.appStates) {
                     this.appStates.put(appId, appState);
                  }
               } catch (PersistentStoreException var11) {
               }
            }
         }

         var4 = removeList.iterator();

         while(var4.hasNext()) {
            appId = (String)var4.next();

            try {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("remove from store: " + appId);
               }

               this.psMap.remove(appId);
            } catch (PersistentStoreException var9) {
            }
         }

      } catch (Exception var12) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug(StackTraceUtils.throwable2StackTrace(var12));
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Error reading Default Persistence Store '" + (this.pStore == null ? "" : this.pStore.getName()) + "'' due to " + var12);
         }

         String msg = DeploymentManagerLogger.cannotReadStore(var12);
         throw new ManagementException(msg, var12);
      }
   }

   private ApplicationRuntimeState getOrCreate(String appId) {
      synchronized(this.appStates) {
         ApplicationRuntimeState appState = (ApplicationRuntimeState)this.appStates.get(appId);
         if (appState == null) {
            appState = new ApplicationRuntimeState(appId);
            this.appStates.put(appId, appState);
         }

         return appState;
      }
   }

   private boolean isShuttingDown() {
      return ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown();
   }

   public void save(ApplicationRuntimeState appState) throws ManagementException {
      if (!this.isMSIDStyleApp(appState.getAppId())) {
         if (this.isShuttingDown()) {
            return;
         }

         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("saving: " + appState);
         }

         try {
            this.psMap.put(appState.getAppId(), appState);
         } catch (Exception var4) {
            if (this.isShuttingDown()) {
               return;
            }

            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug(StackTraceUtils.throwable2StackTrace(var4));
            } else if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Failed to save application state for " + appState.getAppId() + " due to " + var4);
            }

            String msg = DeploymentManagerLogger.cannotSaveStore(appState.toString(), var4);
            throw new ManagementException(msg, var4);
         }
      }

   }

   public void remove(String appId) throws ManagementException {
      ApplicationRuntimeState appState = null;
      synchronized(this.appStates) {
         appState = (ApplicationRuntimeState)this.appStates.remove(appId);
      }

      if (appState != null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("deleting: " + appState);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Deleting application state for " + appId);
         }

         try {
            this.psMap.remove(appState.getAppId());
         } catch (Exception var7) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug(StackTraceUtils.throwable2StackTrace(var7));
            } else if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Failed to delete application state for " + appState.getAppId() + " due to " + var7);
            }

            String msg = DeploymentManagerLogger.cannotDeleteStore(appState.toString(), var7);
            throw new ManagementException(msg, var7);
         }

         if (Debug.isDeploymentDebugEnabled()) {
            try {
               ApplicationRuntimeState rs = (ApplicationRuntimeState)this.psMap.get(appId);
               Debug.deploymentDebug("after deleting: " + rs);
            } catch (Exception var5) {
            }
         }
      }

   }

   public void removeTargets(String appId, String[] targets) throws ManagementException {
      if (Debug.isDeploymentDebugEnabled()) {
         for(int n = 0; n < targets.length; ++n) {
            Debug.deploymentDebug("deleting target: " + targets[n] + " for app:  " + appId);
         }
      }

      synchronized(this.appStates) {
         ApplicationRuntimeState rs = (ApplicationRuntimeState)this.appStates.get(appId);
         if (rs != null) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("before deleting application runtime state on target: " + rs);
            }

            int n;
            for(n = 0; n < targets.length; ++n) {
               rs.removeTargetFromModuleState(targets[n]);
               rs.removeAppTargetState(targets[n]);
            }

            if (rs.getAppTargetState().size() != 0 && !rs.isEmptyModuleStates()) {
               try {
                  rs = (ApplicationRuntimeState)this.psMap.get(appId);

                  for(n = 0; n < targets.length; ++n) {
                     rs.removeTargetFromModuleState(targets[n]);
                     rs.removeAppTargetState(targets[n]);
                  }

                  if (Debug.isDeploymentDebugConciseEnabled()) {
                     Debug.deploymentDebugConcise("Saving application state after removing target(s) " + Arrays.toString(targets) + " for application '" + appId + "'");
                  }

                  this.save(rs);
               } catch (PersistentStoreException var8) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("psMap remove error:  " + var8.getMessage());
                  }
               }
            } else {
               this.remove(appId);
            }
         }

         if (Debug.isDeploymentDebugEnabled()) {
            try {
               ApplicationRuntimeState rs2 = (ApplicationRuntimeState)this.psMap.get(appId);
               Debug.deploymentDebug("after deleting application runtime state on target: " + rs2);
            } catch (Exception var7) {
            }
         }

      }
   }

   public String getIntendedState(String appid) {
      ApplicationRuntimeState ars = getManager().get(appid);
      if (ars == null) {
         return null;
      } else {
         Collection arss = ars.getAppTargetState().values();
         String s = "STATE_NEW";
         Iterator var5 = arss.iterator();

         while(var5.hasNext()) {
            AppTargetState o = (AppTargetState)var5.next();
            if (weblogic.deploy.internal.targetserver.DeployHelper.less(s, o.getState())) {
               s = o.getState();
            }
         }

         return s;
      }
   }

   public String getIntendedState(String appid, String target) {
      ApplicationRuntimeState ars = getManager().get(appid);
      return ars == null ? null : ars.getIntendedState(target);
   }

   public int getStagingState(String appid, String target) {
      ApplicationRuntimeState ars = getManager().get(appid);
      return ars == null ? -1 : ars.getStagingState(target);
   }

   private boolean isMSIDStyleApp(String appid) {
      return ConfiguredDeployments.getConfigureDeploymentsHandler().getMultiVersionDeployments().getConfiguredAppId(appid) != null;
   }

   public String getCurrentState(String appid, String target) {
      return this.isMSIDStyleApp(appid) ? this.getCurrentStateOnDemandAggregated(appid, target) : this.getCurrentStateFromLocalData(appid, target);
   }

   public Map getCurrentStateOfAllVersionsFromLocalData(List appids, List libids, String target) {
      Map multiVersionState = new HashMap();
      if (appids != null && !appids.isEmpty()) {
         synchronized(this.appStates) {
            Iterator var6 = appids.iterator();

            label54:
            while(var6.hasNext()) {
               String configuredId = (String)var6.next();
               String configuredIdPrefix = configuredId + (configuredId.contains("#") ? "." : "#");
               Iterator var9 = this.appStates.keySet().iterator();

               while(true) {
                  String inferredId;
                  do {
                     if (!var9.hasNext()) {
                        continue label54;
                     }

                     Object key = var9.next();
                     inferredId = (String)key;
                  } while(!inferredId.equals(configuredId) && !inferredId.startsWith(configuredIdPrefix));

                  Map inferredIdStates = (Map)multiVersionState.get(configuredId);
                  if (inferredIdStates == null) {
                     inferredIdStates = new HashMap();
                     multiVersionState.put(configuredId, inferredIdStates);
                  }

                  ((Map)inferredIdStates).put(inferredId, this.getCurrentStateFromLocalData(inferredId, target));
               }
            }
         }
      }

      if (libids != null && !libids.isEmpty()) {
         Map libraryMultiVersionState = DeploymentManager.getDeploymentManager().getAllVersionsOfLibraries(libids);
         if (!libraryMultiVersionState.isEmpty()) {
            multiVersionState.putAll(libraryMultiVersionState);
         }
      }

      return multiVersionState;
   }

   public String getCurrentStateFromLocalData(String appid, String target) {
      String s = this.getCurrentState(appid, "ROOT_MODULE", target);
      if (s != null && !s.equals("STATE_NEW")) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Returning state for root module: " + s);
         }

         return s;
      } else {
         String[] mods = this.getModuleIds(appid, false);
         if (mods == null) {
            return s;
         } else {
            for(int i = 0; i < mods.length; ++i) {
               String mod = mods[i];
               String s1 = this.getCurrentState(appid, mod, target);
               if (weblogic.deploy.internal.targetserver.DeployHelper.less(s, s1)) {
                  s = s1;
               }
            }

            return s;
         }
      }
   }

   public String getCurrentStateOnClusterSnapshot(String appId, String clusterName) {
      List serverList = this.getClusterMembers(clusterName, ManagementService.getRuntimeAccess(kernelId).getDomain().lookupCluster(clusterName));
      if (serverList != null && !serverList.isEmpty()) {
         MasterDeployerLogger.logServers("getCurrentStateOnClusterSnapshot(" + clusterName + ")", serverList.toString());
         return this.isMSIDStyleApp(appId) ? this.getCurrentStateOnDemandAggregated(appId, serverList, -1L) : this.getCurrentStateOnClusterSnapshotFromLocalData(appId, serverList, clusterName);
      } else {
         return null;
      }
   }

   private String getCurrentStateOnClusterSnapshotFromLocalData(String appId, List serversInCluster, String clusterName) {
      String s = null;
      Iterator var5 = serversInCluster.iterator();

      while(var5.hasNext()) {
         String serverName = (String)var5.next();
         String s1 = this.getCurrentStateFromLocalData(appId, serverName);
         if (s1 != null) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("getCurrentStateOnClusterSnapshot(" + appId + ", " + clusterName + "): State found for server " + serverName + ": " + s1);
            }

            if (s == null) {
               s = "STATE_NEW";
            }

            if (weblogic.deploy.internal.targetserver.DeployHelper.less(s, s1)) {
               s = s1;
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("getCurrentStateOnClusterSnapshot(" + appId + ", " + clusterName + "): Resetting the high water mark from " + s1 + " to " + s1);
               }
            }
         } else if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("getCurrentStateOnClusterSnapshot(" + appId + ", " + clusterName + "): State not found for server " + serverName);
         }
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("getCurrentStateOnClusterSnapshot(" + appId + ", " + clusterName + "): Returning " + s);
      }

      return s;
   }

   public Properties getCurrentStateOnDemand(String appId, String target, long timeout) {
      List serverList = this.getServerList(target);
      if (serverList == null) {
         return null;
      } else {
         ApplicationStateChecker appStateChecker = new ApplicationStateChecker(appId, serverList, timeout);
         return (Properties)appStateChecker.getResults();
      }
   }

   private String getCurrentStateOnDemandAggregated(String appId, String target) {
      List serverList = this.getServerList(target);
      return serverList != null && !serverList.isEmpty() ? this.getCurrentStateOnDemandAggregated(appId, serverList, -1L) : null;
   }

   private String getCurrentStateOnDemandAggregated(String appId, List servers, long timeout) {
      ApplicationStateChecker appStateChecker = new ApplicationStateChecker(appId, servers, timeout);
      return appStateChecker.getAggregatedResult();
   }

   public String getMultiVersionStateOnDemand(String[] configuredIds, long timeout) {
      ApplicationMultiVersionChecker applicationMultiVersionChecker = new ApplicationMultiVersionChecker(configuredIds, timeout);
      return (String)applicationMultiVersionChecker.getResults();
   }

   private List getServerList(String target) {
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupCluster(target);
      List serverList = null;
      if (clusterMBean == null) {
         ServerRuntimeMBean serverRuntimeMBean = this.getServerRuntime(target, ManagementService.getDomainAccess(kernelId).getDomainRuntimeService(), ManagementService.getDomainAccess(kernelId).getDomainRuntime());
         if (serverRuntimeMBean != null) {
            serverList = new ArrayList();
            ((List)serverList).add(target);
         }
      } else {
         serverList = this.getClusterMembers(target, clusterMBean);
      }

      return (List)serverList;
   }

   private List getClusterMembers(String clusterName, ClusterMBean clusterMBean) {
      if (clusterMBean == null) {
         return null;
      } else {
         ClusterRuntimeMBean clusterRuntimeMBean = this.getClusterRuntimeFromAnyRunningMember(clusterMBean, ManagementService.getDomainAccess(kernelId).getDomainRuntimeService(), ManagementService.getDomainAccess(kernelId).getDomainRuntime());
         if (clusterRuntimeMBean == null) {
            return null;
         } else {
            List serverList = Arrays.asList(clusterRuntimeMBean.getServerNames());
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("getClusterMembers(" + clusterName + "): Working with servers " + serverList.toString());
            }

            return serverList;
         }
      }
   }

   private ClusterRuntimeMBean getClusterRuntimeFromAnyRunningMember(ClusterMBean clusterMBean, DomainRuntimeServiceMBean domainRuntimeServiceMBean, DomainRuntimeMBean domainRuntimeMBean) {
      Iterator var4 = clusterMBean.getServerNames().iterator();

      while(var4.hasNext()) {
         Object serverName = var4.next();
         ServerRuntimeMBean serverRuntime = this.getServerRuntime((String)serverName, domainRuntimeServiceMBean, domainRuntimeMBean);
         if (serverRuntime != null) {
            return serverRuntime.getClusterRuntime();
         }

         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("getClusterRuntimeFromAnyRunningMember(" + clusterMBean.getName() + "): serverRuntimeMBean not found or found to be SHUTDOWN for server " + serverName);
         }
      }

      return null;
   }

   private ServerRuntimeMBean getServerRuntime(String serverName, DomainRuntimeServiceMBean domainRuntimeServiceMBean, DomainRuntimeMBean domainRuntimeMBean) {
      ServerLifeCycleRuntimeMBean lifeCycle = domainRuntimeMBean.lookupServerLifeCycleRuntime(serverName);
      if (lifeCycle != null && !"SHUTDOWN".equals(lifeCycle.getState())) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("getServerRuntime(" + serverName + "): Server found and is not SHUTDOWN");
         }

         ServerRuntimeMBean serverRuntimeMBean = domainRuntimeServiceMBean.lookupServerRuntime(serverName);
         return serverRuntimeMBean;
      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("getServerRuntime(" + serverName + "): LifeCycle Runtime MBean not found or found to be SHUTDOWN");
         }

         return null;
      }
   }

   public void updateApplicationChecker(long requestId, String serverName, Object data) {
      ApplicationChecker applicationChecker = null;
      synchronized(this.applicationCheckers) {
         applicationChecker = (ApplicationChecker)this.applicationCheckers.get(new Long(requestId));
      }

      if (applicationChecker != null) {
         applicationChecker.update(serverName, data);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("updateApplicationChecker(" + requestId + ", " + serverName + ", " + data + "): Successfully completed.");
         }
      }

   }

   public void updateApplicationCheckerWithUnresponsiveTarget(long requestId, String serverName) {
      ApplicationChecker applicationChecker = null;
      synchronized(this.applicationCheckers) {
         applicationChecker = (ApplicationChecker)this.applicationCheckers.get(new Long(requestId));
      }

      if (applicationChecker != null) {
         applicationChecker.updateUnresponsiveTarget(serverName);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("updateApplicationCheckerWithUnresponsiveTarget(" + requestId + ", " + serverName + "): Successfully completed.");
         }
      }

   }

   private static long getNewRequestId() {
      long tentativeId = System.nanoTime();
      synchronized(lock) {
         if (tentativeId == lastNanoTime) {
            tentativeId = lastNanoTime / (long)(2 ^ lastNumberOfIds++);
         } else {
            lastNanoTime = tentativeId;
            lastNumberOfIds = 1;
         }

         return tentativeId;
      }
   }

   public String getCurrentStateOnServer(String appid, String target, String server) {
      String[] mods = this.getModuleIds(appid);
      if (mods == null) {
         return null;
      } else {
         String s = "STATE_NEW";

         for(int i = 0; i < mods.length; ++i) {
            String mod = mods[i];
            String s1 = this.getCurrentStateOnServer(appid, mod, target, server);
            if (weblogic.deploy.internal.targetserver.DeployHelper.less(s, s1)) {
               s = s1;
            }
         }

         return s;
      }
   }

   public String[] getModuleIds(String appid) {
      return this.getModuleIds(appid, true);
   }

   private String[] getModuleIds(String appid, boolean modulesOnly) {
      return this.getModuleIds(appid, (String)null, modulesOnly);
   }

   public String[] getModuleIds(String appid, String target, boolean modulesOnly) {
      ApplicationRuntimeState ars = getManager().get(appid);
      if (ars == null) {
         return null;
      } else {
         Map modules = ars.getModules();
         Set mods = new LinkedHashSet();
         if (modules != null) {
            Iterator var7 = modules.keySet().iterator();

            while(true) {
               String m;
               do {
                  if (!var7.hasNext()) {
                     return (String[])mods.toArray(new String[mods.size()]);
                  }

                  m = (String)var7.next();
               } while((!modulesOnly || "ROOT_MODULE".equals(m)) && modulesOnly);

               boolean addModule = true;
               if (target != null) {
                  TargetModuleState[] tmss = this.getModuleStates(appid, m, target);
                  addModule = tmss != null && tmss.length > 0;
               }

               if (addModule) {
                  mods.add(m);
               }
            }
         } else {
            return (String[])mods.toArray(new String[mods.size()]);
         }
      }
   }

   public String getModuleType(String appid, String moduleid) {
      Map tmap = this.getTargetLevelMap(appid, moduleid);
      if (tmap != null && !tmap.isEmpty()) {
         Object o = tmap.values().iterator().next();
         TargetModuleState tms = this.getAnyTMSFromTargetMap(o);
         String type = tms.getType();
         if (type == null) {
            type = WebLogicModuleType.MODULETYPE_UNKNOWN;
         }

         return type;
      } else {
         return WebLogicModuleType.MODULETYPE_UNKNOWN;
      }
   }

   private TargetModuleState getAnyTMSFromTargetMap(Object map) {
      TargetModuleState tms;
      if (map instanceof TargetModuleState) {
         tms = (TargetModuleState)map;
      } else {
         Object o1 = ((Map)map).values().iterator().next();
         if (o1 instanceof TargetModuleState) {
            tms = (TargetModuleState)o1;
         } else {
            tms = (TargetModuleState)((Map)o1).values().iterator().next();
         }
      }

      return tms;
   }

   private TargetModuleState[] getAllTMSFromTargetMap(Object map) {
      if (map instanceof TargetModuleState) {
         return new TargetModuleState[]{(TargetModuleState)map};
      } else {
         List tmss = new ArrayList();
         Iterator var3 = ((Map)map).values().iterator();

         while(var3.hasNext()) {
            Object o1 = var3.next();
            if (o1 instanceof TargetModuleState) {
               tmss.add((TargetModuleState)o1);
            } else {
               tmss.add(((Map)o1).values().iterator().next());
            }
         }

         return (TargetModuleState[])tmss.toArray(new TargetModuleState[tmss.size()]);
      }
   }

   public String getCurrentState(String appid, String moduleid, String target) {
      TargetModuleState[] tmss = this.getModuleStates(appid, moduleid, target);
      if (tmss == null) {
         return null;
      } else {
         String s = "STATE_NEW";

         for(int i = 0; tmss != null && i < tmss.length; ++i) {
            TargetModuleState tms = tmss[i];
            if (weblogic.deploy.internal.targetserver.DeployHelper.less(s, tms.getCurrentState())) {
               s = tms.getCurrentState();
            }
         }

         if (Debug.DEPLOY_STATE_DEBUGGER.isDebugEnabled()) {
            Debug.DEPLOY_STATE_DEBUGGER.debug("app: " + appid + ", module id: " + moduleid + ", target: " + target + ", state: " + s);
         }

         return s;
      }
   }

   public String getCurrentStateOnServer(String appid, String moduleid, String target, String server) {
      TargetModuleState[] tmss = this.getModuleStates(appid, moduleid, target);
      if (tmss == null) {
         return null;
      } else {
         String s = "STATE_NEW";

         for(int i = 0; tmss != null && i < tmss.length; ++i) {
            TargetModuleState tms = tmss[i];
            if (tms.getServerName().equals(server) && weblogic.deploy.internal.targetserver.DeployHelper.less(s, tms.getCurrentState())) {
               s = tms.getCurrentState();
            }
         }

         return s;
      }
   }

   public String getCurrentState(String appid, String moduleid, String submoduleid, String target) {
      return this.getCurrentState(appid, TargetModuleState.createName(moduleid, submoduleid), target);
   }

   public TargetModuleState[] getModuleStates(String appid, String moduleid, String target) {
      boolean debug = Debug.DEPLOY_STATE_DEBUGGER.isDebugEnabled();
      if (debug) {
         Debug.DEPLOY_STATE_DEBUGGER.debug("Computing state for app: " + appid + ", module: moduleid, target:" + target);
      }

      Map tmap = this.getTargetLevelMap(appid, moduleid);
      if (tmap == null) {
         return null;
      } else {
         if (debug) {
            Debug.DEPLOY_STATE_DEBUGGER.debug("module was found");
         }

         Object o = tmap.get(target);
         if (o == null) {
            Iterator var7 = tmap.entrySet().iterator();

            while(var7.hasNext()) {
               Map.Entry entry = (Map.Entry)var7.next();
               if (entry.getValue() instanceof Map) {
                  o = ((Map)entry.getValue()).get(target);
               }

               if (o != null) {
                  break;
               }
            }
         }

         if (o == null) {
            return null;
         } else {
            if (debug) {
               Debug.DEPLOY_STATE_DEBUGGER.debug("module was found");
            }

            TargetModuleState[] allTMSFromTargetMap = this.getAllTMSFromTargetMap(o);
            if (debug) {
               Debug.DEPLOY_STATE_DEBUGGER.debug("All states from target map: " + Arrays.toString(allTMSFromTargetMap));
            }

            return allTMSFromTargetMap;
         }
      }
   }

   public String[] getModuleTargets(String appid, String moduleid) {
      Map tmap = this.getTargetLevelMap(appid, moduleid);
      if (tmap == null) {
         return null;
      } else {
         Set targets = tmap.keySet();
         return (String[])targets.toArray(new String[targets.size()]);
      }
   }

   public String[] getModuleTargets(String appid, String moduleid, String submoduleid) {
      return this.getModuleTargets(appid, TargetModuleState.createName(moduleid, submoduleid));
   }

   private Map getTargetLevelMap(String appid, String moduleid) {
      ApplicationRuntimeState ars = this.get(appid);
      if (ars == null) {
         return null;
      } else {
         Map map = ars.getModules();
         if (map == null) {
            return null;
         } else {
            Map tmap = (Map)map.get(moduleid);
            return tmap;
         }
      }
   }

   public boolean isAdminMode(AppDeploymentMBean appMBean) {
      return this.isAdminMode(appMBean.getName());
   }

   public boolean isAdminMode(String appId) {
      String s = this.getIntendedState(appId);
      return "STATE_ADMIN".equals(s);
   }

   public boolean isAdminMode(String appId, String target) {
      AppTargetState ats = this.getAppTargetState(appId, target);
      return ats == null ? false : "STATE_ADMIN".equals(ats.getState());
   }

   public AppTargetState getAppTargetState(String appId, String target) {
      ApplicationRuntimeState appState = this.get(appId);
      return appState == null ? null : appState.getAppTargetState(target);
   }

   public void setState(String appId, String[] targets, String state) throws ManagementException {
      if (targets != null && state != null) {
         ApplicationRuntimeState appState = null;

         for(int i = 0; i < targets.length; ++i) {
            String target = targets[i];
            appState = this.updateIntendedState(appId, target, state);
         }

         if (appState != null) {
            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Saving application state for intended state of '" + state + "' for application '" + appId + "' on target(s) " + Arrays.toString(targets));
            }

            this.save(appState);
         }

      }
   }

   public void setStagingState(String appId, String[] targets, int stagingState, boolean isInternalApp) throws ManagementException {
      ApplicationRuntimeState appState = null;

      for(int i = 0; i < targets.length; ++i) {
         String target = targets[i];
         appState = this.updateStagingState(appId, target, stagingState);
      }

      if (appState != null && !isInternalApp) {
         if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Saving application state after setting staging state to " + stagingState + " for application '" + appId + "' on target(s) " + Arrays.toString(targets));
         }

         this.save(appState);
      }

   }

   private ApplicationRuntimeState updateIntendedState(String appId, String target, String state) {
      if (appId != null && target != null && state != null) {
         ApplicationRuntimeState appState = this.getOrCreate(appId);
         AppTargetState ats = this.getAppTargetState(appId, target);
         if (ats == null) {
            ats = new AppTargetState(appId, target);
         }

         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Updating intended state for target: " + target + " state - " + state);
         }

         ats.setState(state);
         appState.updateAppTargetState(ats, target);
         return appState;
      } else {
         return null;
      }
   }

   private ApplicationRuntimeState updateStagingState(String appId, String target, int stagingState) {
      ApplicationRuntimeState appState = this.getOrCreate(appId);
      if (target == null) {
         return appState;
      } else {
         AppTargetState ats = this.getAppTargetState(appId, target);
         if (ats == null) {
            ats = new AppTargetState(appId, target);
         }

         if (stagingState > -1) {
            ats.setStagingState(stagingState);
         }

         appState.updateAppTargetState(ats, target);
         return appState;
      }
   }

   public boolean isActiveVersion(AppDeploymentMBean appMBean) {
      return this.isActiveVersion(appMBean.getName());
   }

   public boolean isActiveVersion(String appId) {
      if (ApplicationVersionUtils.getVersionId(appId) == null) {
         return true;
      } else {
         ApplicationRuntimeState state = this.get(appId);
         return state != null && state.isActiveVersion();
      }
   }

   public void setActiveVersion(String appId) throws ManagementException {
      this.setActiveVersion(appId, false);
   }

   public void setActiveVersion(String appId, boolean force) throws ManagementException {
      if (ApplicationVersionUtils.getVersionId(appId) != null) {
         ApplicationRuntimeState appState = this.getOrCreate(appId);
         appState.setActiveVersion(force);
         if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Saving application state after " + (force ? "force" : "unforce") + " setting of application '" + appId + " as active version");
         }

         this.save(appState);
      }
   }

   public boolean isRetiredVersion(AppDeploymentMBean appMBean) {
      return appMBean != null && appMBean.getVersionIdentifier() != null ? "STATE_RETIRED".equals(this.getCurrentState(appMBean)) : false;
   }

   public boolean isFailedVersion(AppDeploymentMBean appMBean) {
      return appMBean != null && appMBean.getVersionIdentifier() != null ? "STATE_FAILED".equals(this.getCurrentState(appMBean)) : false;
   }

   public String getCurrentState(AppDeploymentMBean appMBean) {
      TargetMBean[] targets = appMBean.getTargets();
      if (targets != null && targets.length != 0) {
         String anyTarget = targets[0].getName();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("getCurrentState " + appMBean.getName() + " state " + this.getCurrentState(appMBean.getName(), anyTarget));
         }

         return this.getCurrentState(appMBean.getName(), anyTarget);
      } else {
         return null;
      }
   }

   public int getRetireTimeoutSeconds(String appId) {
      ApplicationRuntimeState appState = this.get(appId);
      return appState == null ? 0 : appState.getRetireTimeoutSeconds();
   }

   public void setRetireTimeoutSeconds(String appId, int retireTimeoutSecs) throws ManagementException {
      if (ApplicationVersionUtils.getVersionId(appId) != null) {
         ApplicationRuntimeState appState = this.getOrCreate(appId);
         appState.setRetireTimeoutSeconds(retireTimeoutSecs);
         if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Saving application state with retireTimeoutSecs of " + retireTimeoutSecs + " for application '" + appId + "'");
         }

         this.save(appState);
      }
   }

   public long getRetireTimeMillis(String appId) {
      ApplicationRuntimeState appState = this.get(appId);
      return appState == null ? 0L : appState.getRetireTimeMillis();
   }

   public void setRetireTimeMillis(String appId, long retireTimeMillis) throws ManagementException {
      if (ApplicationVersionUtils.getVersionId(appId) != null) {
         ApplicationRuntimeState appState = this.getOrCreate(appId);
         appState.setRetireTimeMillis(retireTimeMillis);
         if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Saving application state with retireTimeMillis of " + retireTimeMillis + " for application '" + appId + "'");
         }

         this.save(appState);
      }
   }

   public void addStateListener(PropertyChangeListener listener) {
      this.pcs.addPropertyChangeListener(listener);
   }

   public void removeStateListener(PropertyChangeListener listener) {
      this.pcs.removePropertyChangeListener(listener);
   }

   public void updateState(String appId, DeploymentVersion deploymentVersion) {
      ApplicationRuntimeState appState = this.getOrCreate(appId);
      appState.setDeploymentVersion(deploymentVersion);

      try {
         if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Saving application state with deploymentVersion of [" + deploymentVersion + "] for application '" + appId + "'");
         }

         this.save(appState);
      } catch (ManagementException var5) {
         DeploymentManagerLogger.logStatePersistenceFailed(appId, var5);
      }

   }

   public void updateState(String appId, DeploymentState update) {
      if (appId != null) {
         ApplicationRuntimeState appState = this.getOrCreate(appId);
         String intent = appState.getIntendedState(update.getTarget());
         if ((intent != null || !"__Lifecycle_taskid__".equals(update.getTaskID()) || "STATE_ACTIVE".equals(update.getIntendedState())) && (intent == null || weblogic.deploy.internal.targetserver.DeployHelper.less(intent, update.getIntendedState()))) {
            this.updateIntendedState(appId, update.getTarget(), update.getIntendedState());
         }

         appState = this.updateStagingState(appId, update.getTarget(), update.getStagingState());
         appState.updateState(update);
         boolean moduleStateChange = false;
         TargetModuleState[] moduleStates = update.getTargetModules();
         if (moduleStates != null && moduleStates.length > 0) {
            moduleStateChange = true;
         }

         if (this.pcs.hasListeners((String)null) && (update.getCurrentState() != null || moduleStateChange)) {
            this.pcs.firePropertyChange(new PropertyChangeEvent(this, "State", (Object)null, update));
         }

         try {
            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Saving application state with DeploymentState of [" + update + "] for application '" + appId + "'");
            }

            this.save(appState);
         } catch (ManagementException var8) {
            DeploymentManagerLogger.logStatePersistenceFailed(appId, var8);
         }

      }
   }

   private void resetState(TargetModuleState tms, String target) {
      if (tms.getTargetName().equals(target) && !"STATE_RETIRED".equals(tms.getCurrentState())) {
         tms.setCurrentState("STATE_NEW");
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("reset: " + tms);
         }
      }

   }

   public void resetState(String appId, String[] targets) throws ManagementException {
      if (targets != null) {
         ApplicationRuntimeState appState = null;

         label62:
         for(int i = 0; i < targets.length; ++i) {
            String target = targets[i];
            appState = this.updateIntendedState(appId, target, "STATE_NEW");
            Map moduleMap = appState.getModules();
            Iterator var7 = moduleMap.entrySet().iterator();

            while(true) {
               Map serverMap;
               do {
                  Map targetMap;
                  do {
                     if (!var7.hasNext()) {
                        continue label62;
                     }

                     Map.Entry entry = (Map.Entry)var7.next();
                     targetMap = (Map)entry.getValue();
                  } while(targetMap == null);

                  serverMap = (Map)targetMap.get(target);
               } while(serverMap == null);

               Iterator var11 = serverMap.entrySet().iterator();

               while(var11.hasNext()) {
                  Map.Entry serverEntry = (Map.Entry)var11.next();
                  String server = (String)serverEntry.getKey();
                  Object serverState = serverEntry.getValue();
                  if (serverState != null && serverState instanceof Map) {
                     serverState = ((Map)serverState).get(server);
                  }

                  if (serverState != null && serverState instanceof TargetModuleState) {
                     TargetModuleState tms = (TargetModuleState)serverState;
                     this.resetState(tms, target);
                  }
               }
            }
         }

         if (appState != null) {
            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Saving application state after resetting application/module state for application '" + appId + "' to STATE_NEW on target(s) " + Arrays.toString(targets));
            }

            this.save(appState);
         }

      }
   }

   public void updateStateForRedeployOperationOnCluster(String appId, DeploymentState update) {
      if (appId != null) {
         ApplicationRuntimeState appState = this.getOrCreate(appId);
         String intent = appState.getIntendedState(update.getTarget());
         this.updateIntendedState(appId, update.getTarget(), update.getIntendedState());
         appState = this.updateStagingState(appId, update.getTarget(), update.getStagingState());
         appState.updateState(update);

         try {
            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("Saving application state after redeploy on cluster for application '" + appId + " with DeploymentState of [" + update + "] from original intended state " + intent);
            }

            this.save(appState);
         } catch (ManagementException var6) {
            DeploymentManagerLogger.logStatePersistenceFailed(appId, var6);
         }

      }
   }

   public String[] getSubmoduleIds(String appid, String moduleid) {
      ApplicationRuntimeState ars = this.get(appid);
      if (ars == null) {
         return null;
      } else {
         Map map = ars.getModules();
         if (map == null) {
            return null;
         } else {
            List names = new ArrayList();
            Iterator var6 = map.keySet().iterator();

            while(var6.hasNext()) {
               String extractionName = (String)var6.next();
               String name = TargetModuleState.extractSubmodule(extractionName);
               String modulename = TargetModuleState.extractModule(extractionName);
               if (name != null && modulename != null && modulename.equals(moduleid)) {
                  names.add(name);
               }
            }

            return (String[])((String[])names.toArray(new String[0]));
         }
      }
   }

   public void possiblyFixAppRuntimeState(AppDeploymentMBean appDeploymentMBean, String target, String[] moduleIds) throws ManagementException {
      BasicDeployment deployment = OrderedDeployments.getOrCreateBasicDeployment(appDeploymentMBean);

      try {
         if (deployment != null) {
            deployment.startLifecycleStateManager();
            this.pruneModules(deployment.getDeploymentMBean().getName(), target, moduleIds);
         }
      } finally {
         if (deployment != null) {
            deployment.finishLifecycleStateManager();
         }

      }

   }

   public void pruneModules(String appid, String target, String[] currentModuleIds) throws ManagementException {
      AppRuntimeStateManager arm = getManager();
      String[] moduleIds = arm.getModuleIds(appid, target, true);
      if (moduleIds != null && currentModuleIds != null) {
         List pruneMods = new ArrayList();
         String[] var7 = moduleIds;
         int var8 = moduleIds.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String moduleId = var7[var9];
            boolean found = false;
            String[] var12 = currentModuleIds;
            int var13 = currentModuleIds.length;

            for(int var14 = 0; var14 < var13; ++var14) {
               String cmodId = var12[var14];
               if (moduleId.equals(cmodId)) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Application: " + appid + " has obsolete module " + moduleId + " on target server " + target);
               }

               pruneMods.add(moduleId);
            }
         }

         if (pruneMods.size() >= 0) {
            ApplicationRuntimeState appState = arm.get(appid);
            if (appState != null) {
               Iterator var17 = pruneMods.iterator();

               while(var17.hasNext()) {
                  String pruneMod = (String)var17.next();
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Removing obsolete module " + pruneMod + " from application: " + appid + " on target server " + target);
                  }

                  DeployerRuntimeExtendedLogger.logModuleRemovedFromAppRuntimeStateDuringServerStartup(appid, pruneMod);
                  appState.deleteModule(pruneMod);
               }

               this.save(appState);
            }
         }

      }
   }

   public String[] getStoppedModuleIds(String appid, String target) {
      AppRuntimeStateManager arm = getManager();
      if ("STATE_NEW".equals(arm.getIntendedState(appid))) {
         return null;
      } else {
         String[] moduleIds = arm.getModuleIds(appid, target, true);
         if (moduleIds == null) {
            return null;
         } else {
            List stoppedMods = new ArrayList();

            for(int i = 0; i < moduleIds.length; ++i) {
               String st = this.getCurrentState(appid, moduleIds[i], target);
               if ("STATE_NEW".equals(st)) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Application: " + appid + " has stopped module " + moduleIds[i] + " on target server " + target);
                  }

                  stoppedMods.add(moduleIds[i]);
               }
            }

            if (moduleIds.length == stoppedMods.size()) {
               return null;
            } else if (stoppedMods.size() == 0) {
               return null;
            } else {
               return (String[])((String[])stoppedMods.toArray(new String[0]));
            }
         }
      }
   }

   private Map collectAppIdToServerNamesMapping(DomainMBean domain) {
      Map appIdToServerNames = new HashMap();
      AppDeploymentMBean[] var3 = domain.getAppDeployments();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         AppDeploymentMBean appDeployment = var3[var5];
         Set serverNames = TargetHelper.getAllTargetedServers(appDeployment);
         appIdToServerNames.put(appDeployment.getName(), serverNames);
      }

      return appIdToServerNames;
   }

   public void cleanApplicationRuntimeStateForServer(String serverName) throws ManagementException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      Map appIdToServerNames = this.collectAppIdToServerNamesMapping(domain);
      this.cleanApplicationRuntimeStateForServer(serverName, appIdToServerNames);
   }

   public void cleanApplicationRuntimeStateForServer(String serverName, Map appIdToServerNames) throws ManagementException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Cleaning up application runtime state for server " + serverName);
      }

      Iterator var3 = appIdToServerNames.keySet().iterator();

      while(var3.hasNext()) {
         String appId = (String)var3.next();
         Set serverNames = (Set)appIdToServerNames.get(appId);
         if (serverNames.contains(serverName)) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Cleaning up runtime state for application " + appId + " on server " + serverName);
            }

            this.removeTargets(appId, new String[]{serverName});
         }
      }

   }

   public void prepareUpdate(DescriptorUpdateEvent due) throws DescriptorUpdateRejectedException {
      DomainMBean current = (DomainMBean)due.getSourceDescriptor().getRootBean();
      Iterator var3 = due.getDiff().iterator();

      while(true) {
         while(var3.hasNext()) {
            BeanUpdateEvent event = (BeanUpdateEvent)var3.next();
            BeanUpdateEvent.PropertyUpdate[] updateEvents;
            int i;
            BeanUpdateEvent.PropertyUpdate updateEvent;
            String propertyName;
            if (event.getSourceBean() instanceof DomainMBean) {
               updateEvents = event.getUpdateList();

               for(i = 0; i < updateEvents.length; ++i) {
                  updateEvent = updateEvents[i];
                  propertyName = updateEvent.getPropertyName();
                  if ("Servers".equals(propertyName) && 3 == updateEvent.getUpdateType()) {
                     this.appIdToServerNames = this.collectAppIdToServerNamesMapping(current);
                     return;
                  }
               }
            } else if (event.getSourceBean() instanceof ServerMBean) {
               updateEvents = event.getUpdateList();

               for(i = 0; i < updateEvents.length; ++i) {
                  updateEvent = updateEvents[i];
                  propertyName = updateEvent.getPropertyName();
                  if ("Cluster".equals(propertyName) && 1 == updateEvent.getUpdateType()) {
                     this.appIdToServerNames = this.collectAppIdToServerNamesMapping(current);
                     return;
                  }
               }
            }
         }

         return;
      }
   }

   public void activateUpdate(DescriptorUpdateEvent due) throws DescriptorUpdateFailedException {
      Set serverSet = new HashSet();
      Iterator var3 = due.getDiff().iterator();

      while(true) {
         while(var3.hasNext()) {
            BeanUpdateEvent event = (BeanUpdateEvent)var3.next();
            BeanUpdateEvent.PropertyUpdate[] updateEvents;
            int i;
            BeanUpdateEvent.PropertyUpdate updateEvent;
            String propertyName;
            if (event.getSourceBean() instanceof DomainMBean) {
               updateEvents = event.getUpdateList();

               for(i = 0; i < updateEvents.length; ++i) {
                  updateEvent = updateEvents[i];
                  propertyName = updateEvent.getPropertyName();
                  if ("Servers".equals(propertyName) && 3 == updateEvent.getUpdateType()) {
                     Object obj = updateEvent.getRemovedObject();
                     if (Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("Remove server from domain: " + ((ServerMBean)obj).getName());
                     }

                     serverSet.add((ServerMBean)obj);
                  }
               }
            } else if (event.getSourceBean() instanceof ServerMBean) {
               updateEvents = event.getUpdateList();

               for(i = 0; i < updateEvents.length; ++i) {
                  updateEvent = updateEvents[i];
                  propertyName = updateEvent.getPropertyName();
                  if ("Cluster".equals(propertyName) && 1 == updateEvent.getUpdateType()) {
                     ServerMBean serverMBean = (ServerMBean)event.getSourceBean();
                     if (serverMBean.getCluster() == null && Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("Remove server from cluster: " + serverMBean.getName());
                     }

                     serverSet.add(serverMBean);
                  }
               }
            }
         }

         var3 = serverSet.iterator();

         while(var3.hasNext()) {
            ServerMBean server = (ServerMBean)var3.next();

            try {
               this.cleanApplicationRuntimeStateForServer(server.getName(), this.appIdToServerNames);
            } catch (Exception var10) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Exception in cleaning application state for server " + server.getName() + " : " + var10.getMessage());
               }
            }
         }

         return;
      }
   }

   public void rollbackUpdate(DescriptorUpdateEvent due) {
      this.appIdToServerNames.clear();
   }

   private class ApplicationMultiVersionChecker extends ApplicationChecker {
      private String SUCCESS = "SUCCESS";
      private String FAILED = "FAILED";
      private Map targetsWithConfigIds = new HashMap();
      private Set downServers = new HashSet();
      DomainMBean domain;
      DomainRuntimeServiceMBean domainRuntimeServiceMBean;
      DomainRuntimeMBean domainRuntimeMBean;

      public ApplicationMultiVersionChecker(String[] appIds, long timeout) {
         super(timeout);
         this.domain = ManagementService.getRuntimeAccess(AppRuntimeStateManager.kernelId).getDomain();
         this.domainRuntimeServiceMBean = ManagementService.getDomainAccess(AppRuntimeStateManager.kernelId).getDomainRuntimeService();
         this.domainRuntimeMBean = ManagementService.getDomainAccess(AppRuntimeStateManager.kernelId).getDomainRuntime();
         this.remainingServers = new ArrayList();
         String[] var5 = appIds;
         int var6 = appIds.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String configuredID = var5[var7];
            AppDeploymentMBean app = this.domain.lookupAppDeployment(configuredID);
            if (app != null) {
               this.createRequestParameter(configuredID, app, DeploymentObject.APPLICATION);
            } else {
               LibraryMBean lib = this.domain.lookupLibrary(configuredID);
               if (lib != null) {
                  this.createRequestParameter(configuredID, lib, DeploymentObject.APPLICATION);
               }
            }
         }

         if (!this.targetsWithConfigIds.isEmpty()) {
            CommonMessageSender.getInstance().sendRequestMultiVersionStateMsg(this.requestId, this.targetsWithConfigIds);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ApplicationMultiVersionChecker for requestId '" + this.requestId + "' is queued to process " + this.targetsWithConfigIds + "' with timeout of " + this.timeout + " ms.");
            }
         } else {
            this.remainingServers = null;
            this.removeApplicationCheckerIfDone();
         }

      }

      private void createRequestParameter(String configuredID, AppDeploymentMBean app, CommonMessageSender.DeploymentObject deploymentObject) {
         TargetMBean[] targetMBeans = app.getTargets();
         if (targetMBeans != null && targetMBeans.length > 0) {
            Set targets = new HashSet();
            TargetMBean[] var6 = targetMBeans;
            int var7 = targetMBeans.length;

            label57:
            for(int var8 = 0; var8 < var7; ++var8) {
               TargetMBean targetMBean = var6[var8];
               Iterator var10 = targetMBean.getServerNames().iterator();

               while(true) {
                  while(true) {
                     String targetName;
                     do {
                        if (!var10.hasNext()) {
                           continue label57;
                        }

                        Object serverName = var10.next();
                        targetName = (String)serverName;
                     } while(this.downServers.contains(targetName));

                     boolean isTargetOnline = this.remainingServers.contains(targetName);
                     if (!isTargetOnline && AppRuntimeStateManager.this.getServerRuntime(targetName, this.domainRuntimeServiceMBean, this.domainRuntimeMBean) == null) {
                        this.downServers.add(targetName);
                     } else {
                        targets.add(targetName);
                        if (!isTargetOnline) {
                           this.remainingServers.add(targetName);
                        }
                     }
                  }
               }
            }

            Object configuredIds;
            if (!targets.isEmpty()) {
               for(Iterator var14 = targets.iterator(); var14.hasNext(); ((List)configuredIds).add(configuredID)) {
                  String targetNamex = (String)var14.next();
                  Map configIdList = (Map)this.targetsWithConfigIds.get(targetNamex);
                  if (configIdList == null) {
                     configIdList = new HashMap();
                  }

                  configuredIds = (List)((Map)configIdList).get(deploymentObject);
                  if (configuredIds == null) {
                     configuredIds = new ArrayList();
                     ((Map)configIdList).put(deploymentObject, configuredIds);
                     this.targetsWithConfigIds.put(targetNamex, configIdList);
                  }
               }
            }
         }

      }

      public Object getResults() {
         this.processDataRetrieval();
         return this.appDataFromServers != null && !this.appDataFromServers.isEmpty() ? this.writeXMLString() : null;
      }

      private String writeXMLString() {
         String encoding = "UTF-8";
         String version = "1.0";
         String nsURI = "http://xmlns.oracle.com/weblogic/multi-version-state";
         ByteArrayOutputStream baosForXML = new ByteArrayOutputStream();

         try {
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            xmlOutputFactory.setProperty("javax.xml.stream.isRepairingNamespaces", true);
            XMLStreamWriter writer = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(baosForXML, encoding));
            writer.writeStartDocument(encoding, version);
            writer.setDefaultNamespace(nsURI);
            writer.writeStartElement(nsURI, "multi-version-state");
            synchronized(this.appDataFromServers) {
               Iterator var8 = this.appDataFromServers.entrySet().iterator();

               while(true) {
                  while(var8.hasNext()) {
                     Map.Entry statusEntry = (Map.Entry)var8.next();
                     String status = (String)statusEntry.getKey();
                     Map failedStates;
                     if (status == this.SUCCESS) {
                        failedStates = (Map)statusEntry.getValue();
                        Iterator var27 = failedStates.entrySet().iterator();

                        while(var27.hasNext()) {
                           Map.Entry configuredIdEntry = (Map.Entry)var27.next();
                           String configuredId = (String)configuredIdEntry.getKey();
                           writer.writeStartElement("configured-id");
                           writer.writeAttribute("id", configuredId);
                           Map inferredIds = (Map)configuredIdEntry.getValue();
                           Iterator var16 = inferredIds.entrySet().iterator();

                           while(var16.hasNext()) {
                              Map.Entry inferredIdEntry = (Map.Entry)var16.next();
                              String inferredId = (String)inferredIdEntry.getKey();
                              writer.writeStartElement("inferred-id");
                              writer.writeAttribute("id", inferredId);
                              Map inferredIdStates = (Map)inferredIdEntry.getValue();
                              Iterator var20 = inferredIdStates.entrySet().iterator();

                              while(var20.hasNext()) {
                                 Map.Entry inferredIdStateEntry = (Map.Entry)var20.next();
                                 String inferredIdState = (String)inferredIdStateEntry.getKey();
                                 writer.writeStartElement("state");
                                 writer.writeAttribute("value", inferredIdState);
                                 List targets = (List)inferredIdStateEntry.getValue();
                                 this.writeXMLforTargets(writer, targets);
                                 writer.writeEndElement();
                              }

                              writer.writeEndElement();
                           }

                           writer.writeEndElement();
                        }
                     } else {
                        failedStates = (Map)statusEntry.getValue();
                        List targetsx = (List)failedStates.get("STATE_NOT_RESPONDING");
                        if (targetsx != null) {
                           writer.writeStartElement("unresponsive");
                           this.writeXMLforTargets(writer, targetsx);
                           writer.writeEndElement();
                        }
                     }
                  }
               }
            }

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
         } catch (XMLStreamException var26) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ApplicationMultiVersionChecker writeXMLString() failed with exception: " + var26);
            }

            return null;
         }

         return baosForXML.toString();
      }

      private void writeXMLforTargets(XMLStreamWriter writer, List targets) throws XMLStreamException {
         Iterator var3 = targets.iterator();

         while(var3.hasNext()) {
            String target = (String)var3.next();
            writer.writeStartElement("target");
            writer.writeCharacters(target);
            writer.writeEndElement();
         }

      }

      boolean waitCompleted() {
         return this.timedOut();
      }

      void completedAction() {
         if (this.timedOut()) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ApplicationMultiVersionChecker for requestId '" + this.requestId + "' timed out and setting '" + this.appId + "' for " + this.remainingServers + " to app state of " + "STATE_NOT_RESPONDING");
            }

            Iterator var1 = this.remainingServers.iterator();

            while(var1.hasNext()) {
               String server = (String)var1.next();
               this.updateUnresponsiveTarget(server);
            }
         }

      }

      void updateUnresponsiveTarget(String serverName) {
         synchronized(this.appDataFromServers) {
            Map failedStates = (Map)this.appDataFromServers.get(this.FAILED);
            if (failedStates == null) {
               failedStates = new HashMap();
               this.appDataFromServers.put(this.FAILED, failedStates);
            }

            List servers = (List)((Map)failedStates).get("STATE_NOT_RESPONDING");
            if (servers == null) {
               servers = new ArrayList();
               ((Map)failedStates).put("STATE_NOT_RESPONDING", servers);
            }

            if (((List)servers).isEmpty() || !((List)servers).contains(serverName)) {
               ((List)servers).add(serverName);
            }

         }
      }

      void mainUpdate(String serverName, Object data) {
         synchronized(this.appDataFromServers) {
            Map configuredIds = (Map)this.appDataFromServers.get(this.SUCCESS);
            if (configuredIds == null) {
               configuredIds = new HashMap();
               this.appDataFromServers.put(this.SUCCESS, configuredIds);
            }

            Iterator var5 = ((Map)data).entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry configuredIdEntryFromTarget = (Map.Entry)var5.next();
               String configuredIdFromTarget = (String)configuredIdEntryFromTarget.getKey();
               Map configuredIdInferredIdsFromTarget = (Map)configuredIdEntryFromTarget.getValue();
               Map inferredIds = (Map)((Map)configuredIds).get(configuredIdFromTarget);
               if (inferredIds == null) {
                  inferredIds = new HashMap();
                  ((Map)configuredIds).put(configuredIdFromTarget, inferredIds);
               }

               Object stateTargets;
               for(Iterator var10 = configuredIdInferredIdsFromTarget.entrySet().iterator(); var10.hasNext(); ((List)stateTargets).add(serverName)) {
                  Map.Entry inferredIdEntryFromTarget = (Map.Entry)var10.next();
                  String inferredIdFromTarget = (String)inferredIdEntryFromTarget.getKey();
                  String inferredIdStateFromTarget = (String)inferredIdEntryFromTarget.getValue();
                  Map inferredIdStateTargets = (Map)((Map)inferredIds).get(inferredIdFromTarget);
                  if (inferredIdStateTargets == null) {
                     inferredIdStateTargets = new HashMap();
                     ((Map)inferredIds).put(inferredIdFromTarget, inferredIdStateTargets);
                  }

                  stateTargets = (List)((Map)inferredIdStateTargets).get(inferredIdStateFromTarget);
                  if (stateTargets == null) {
                     stateTargets = new ArrayList();
                     ((Map)inferredIdStateTargets).put(inferredIdStateFromTarget, stateTargets);
                  }
               }
            }

         }
      }
   }

   private class ApplicationStateChecker extends ApplicationChecker {
      private String currentState = null;
      private boolean aggregate = false;

      public ApplicationStateChecker(String appId, List serverList, long timeout) {
         super(timeout);
         this.appId = appId;
         this.remainingServers = new ArrayList(serverList);
         CommonMessageSender.getInstance().sendRequestAppStateMsg(this.requestId, serverList, this.appId);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ApplicationStateChecker for requestId '" + this.requestId + "' is queued to process " + serverList + " for '" + appId + "' with timeout of " + this.timeout + " ms.");
         }

      }

      public String getAggregatedResult() {
         this.aggregate = true;
         this.processDataRetrieval();
         return this.currentState;
      }

      public Object getResults() {
         this.aggregate = false;
         this.processDataRetrieval();
         Properties appStatesFromServers = new Properties();
         appStatesFromServers.putAll(this.appDataFromServers);
         return appStatesFromServers;
      }

      private boolean isAggregateComplete() {
         return this.aggregate && this.currentState != null && this.currentState.equals("STATE_ACTIVE");
      }

      boolean waitCompleted() {
         return this.isAggregateComplete() || this.timedOut();
      }

      void completedAction() {
         if (this.timedOut()) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ApplicationStateChecker for requestId '" + this.requestId + "' timed out and setting '" + this.appId + "' for " + this.remainingServers + " to app state of " + "STATE_NOT_RESPONDING");
            }

            synchronized(this.appDataFromServers) {
               Iterator var2 = this.remainingServers.iterator();

               while(var2.hasNext()) {
                  String serverName = (String)var2.next();
                  this.appDataFromServers.put(serverName, "STATE_NOT_RESPONDING");
               }
            }
         }

      }

      void updateUnresponsiveTarget(String serverName) {
         this.update(serverName, "STATE_NOT_RESPONDING");
      }

      void mainUpdate(String serverName, Object data) {
         if (data != null) {
            synchronized(this.appDataFromServers) {
               this.appDataFromServers.put(serverName, data);
            }
         }

         String incomingState = (String)data;
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Updating ApplicationStateChecker for requestId '" + this.requestId + "' and setting '" + this.appId + "' for " + serverName + " to " + incomingState);
         }

         if (this.currentState == null || weblogic.deploy.internal.targetserver.DeployHelper.less(this.currentState, incomingState)) {
            this.currentState = incomingState;
         }

      }
   }

   private abstract class ApplicationChecker {
      long requestId = AppRuntimeStateManager.getNewRequestId();
      String appId;
      private long DEFAULT_TIMEOUT_IN_MILLIS = 60000L;
      private long startTime = 0L;
      long timeout;
      private boolean timedOut = false;
      List remainingServers;
      private boolean applicationCheckerRemoved = false;
      Map appDataFromServers = new HashMap();

      public ApplicationChecker(long timeout) {
         this.startTime = System.currentTimeMillis();
         this.timeout = timeout < 0L ? this.DEFAULT_TIMEOUT_IN_MILLIS : timeout;
         synchronized(AppRuntimeStateManager.this.applicationCheckers) {
            AppRuntimeStateManager.this.applicationCheckers.put(new Long(this.requestId), this);
         }
      }

      public abstract Object getResults();

      void processDataRetrieval() {
         while(true) {
            if (this.remainingServers != null && !this.remainingServers.isEmpty()) {
               if (!this.waitCompleted()) {
                  try {
                     Thread.sleep(100L);
                  } catch (InterruptedException var2) {
                  }
                  continue;
               }

               this.completedAction();
               this.remainingServers = null;
            }

            this.removeApplicationCheckerIfDone();
            return;
         }
      }

      abstract boolean waitCompleted();

      abstract void completedAction();

      abstract void updateUnresponsiveTarget(String var1);

      boolean timedOut() {
         if (!this.timedOut) {
            this.timedOut = this.timeout > 0L ? System.currentTimeMillis() - this.startTime > this.timeout : false;
         }

         return this.timedOut;
      }

      public void update(String serverName, Object data) {
         this.remainingServers.remove(serverName);
         this.removeApplicationCheckerIfDone();
         this.mainUpdate(serverName, data);
      }

      abstract void mainUpdate(String var1, Object var2);

      void removeApplicationCheckerIfDone() {
         if (!this.applicationCheckerRemoved && (this.remainingServers == null || this.remainingServers.isEmpty())) {
            synchronized(AppRuntimeStateManager.this.applicationCheckers) {
               this.applicationCheckerRemoved = AppRuntimeStateManager.this.applicationCheckers.remove(new Long(this.requestId)) != null;
            }
         }

      }
   }

   private static class AppRuntimeStateManagerInitializer {
      private static final AppRuntimeStateManager INSTANCE = (AppRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(AppRuntimeStateManager.class, new Annotation[0]);
   }
}
