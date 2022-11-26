package weblogic.management.patching;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.descriptor.WLDFScaleDownActionBean;
import weblogic.diagnostics.descriptor.WLDFScaleUpActionBean;
import weblogic.diagnostics.descriptor.WLDFWatchNotificationBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.patching.commands.ClusterDisableSessionHandlingCommand;
import weblogic.management.patching.commands.ClusterEnableSessionHandlingCommand;
import weblogic.management.patching.commands.DomainModelIterator;
import weblogic.management.patching.commands.ExecScriptCommand;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.patching.commands.RestartAdminServerCommand;
import weblogic.management.patching.commands.RestartNodeManagerCommand;
import weblogic.management.patching.commands.ShutdownServerCommand;
import weblogic.management.patching.commands.StartServerCommand;
import weblogic.management.patching.commands.TopologyConsistencyCommand;
import weblogic.management.patching.model.Cluster;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.patching.model.MigrationInfo;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.PartitionApps;
import weblogic.management.patching.model.Server;
import weblogic.management.patching.model.ServerGroup;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RolloutPrimitiveFactory {
   private static RolloutPrimitiveFactory instance = null;
   static final int UPDATED_SERVERS_INDEX = 1;
   static final int PENDING_SERVERS_INDEX = 0;
   protected static final String ROLLBACK_SUFFIX = "Rollback";
   public static final String DOMAIN = "Domain";
   public static final String DOMAIN_ROLLBACK = "DomainRollback";
   public static final String CLUSTER = "Cluster";
   public static final String SERVER = "Server";
   public static final String ROLLOUT_OPTION_DRY_RUN = "isDryRun";
   public static final String ROLLOUT_OPTION_SESSION_COMPATIBILITY = "SessionCompatibility";

   public static RolloutPrimitiveFactory getInstance() {
      if (instance == null) {
         instance = new RolloutPrimitiveFactory();
      }

      return instance;
   }

   protected HashMap generateMachinesMap() throws ManagementException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutPrimitiveFactory.getRolloutDirectoryPrimitive calling TopologyInspector");
      }

      HashMap machinesMap = TopologyInspector.generateMachinesMap();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutPrimitiveFactory.getRolloutDirectoryPrimitive TopologyInspector found " + machinesMap.size() + " machines");
      }

      return machinesMap;
   }

   protected String getCommaSeparatedServerNames(Node node) {
      StringBuilder builder = new StringBuilder();
      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(node.getNodeName()) != null) {
         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               builder.append(server.getServerName());
               if (iterator.hasNextServer()) {
                  builder.append(",");
               }
            }
         }
      }

      return builder.toString();
   }

   protected WorkflowBuilder getShutdownServerPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessions, ArrayList origPendingServers, ArrayList origUpdatingServers) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance().meta("TYPE", "shutdownServer").meta("TARGET_TYPE", "SERVER").meta("TARGETS", serverName);
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutPrimitiveFactory.getShutDownServerPrimitive called with params: serverName " + serverName + ", sessionTimeout " + sessionTimeout + ", ignoreSessions " + ignoreSessions + ", waitForAllSessions" + waitForAllSessions;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("sessionTimeout", sessionTimeout);
      map.put("ignoreSessions", ignoreSessions);
      map.put("waitForAllSessions", waitForAllSessions);
      map.put("readyCheckAppsTimeoutInMin", 3);
      map.put("updatingServers", this.serverListToArray(origUpdatingServers));
      map.put("pendingServers", this.serverListToArray(origPendingServers));
      builder.add(ShutdownServerCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getStartServerPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessionsOnRevert) {
      return this.getStartServerPrimitive(serverName, sessionTimeout, ignoreSessions, waitForAllSessionsOnRevert, (ArrayList)null, (ArrayList)null);
   }

   protected WorkflowBuilder getStartServerPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessionsOnRevert, ArrayList pendingServers, ArrayList updatingServers) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance().meta("TYPE", "startServer").meta("TARGET_TYPE", "SERVER").meta("TARGETS", serverName);
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutPrimitiveFactory.getStartServerPrimitive called with params: serverName " + serverName + ", sessionTimeout " + sessionTimeout + ", ignoreSessions " + ignoreSessions + ", waitForAllSessionsOnRevert " + waitForAllSessionsOnRevert;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("sessionTimeout", sessionTimeout);
      map.put("ignoreSessions", ignoreSessions);
      map.put("waitForAllSessions", waitForAllSessionsOnRevert);
      map.put("waitForAllSessionsOnRevert", waitForAllSessionsOnRevert);
      map.put("readyCheckAppsTimeoutInMin", 3);
      map.put("updatingServers", this.serverListToArray(updatingServers));
      map.put("pendingServers", this.serverListToArray(pendingServers));
      builder.add(StartServerCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getRestartNodeManagerPrimitive(String machineName, Boolean isAdminServer, long timeoutMillis) {
      return this.getRestartNodeManagerPrimitive(machineName, isAdminServer, timeoutMillis, false);
   }

   protected WorkflowBuilder getRestartNodeManagerPrimitive(String machineName, Boolean isAdminServer, long timeoutMillis, boolean isRollingRestart) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance().meta("TYPE", "restartNodeManager").meta("TARGET_TYPE", "NODE_MANAGER").meta("TARGETS", machineName);
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutPrimitiveFactory.getRestartNodeManagerPrimitive called with params: machineName " + machineName + ", isAdminServer " + isAdminServer + ", timeoutMillis" + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("isAdminServer", isAdminServer);
      map.put("timeoutMillis", timeoutMillis);
      map.put("isRestart", isRollingRestart);
      builder.add(RestartNodeManagerCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getRestartAdminServerPrimitive() {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutPrimitiveFactory.getRestartAdminServerPrimitive called";
         PatchingDebugLogger.debug(debugString);
      }

      Map map = new HashMap();
      builder.add(RestartAdminServerCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getExecScriptPrimitive(String machineName, String scriptName, long timeoutMillis) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance().meta("TYPE", "execScript").meta("TARGET_TYPE", "MACHINE").meta("TARGETS", machineName);
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutPrimitiveFactory.getExecScriptPrimitive called with params: machineName " + machineName + ", scriptName " + scriptName + ", timeoutMillis " + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      this.assertString("scriptName", scriptName);
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("scriptName", scriptName);
      map.put("timeoutMillis", timeoutMillis);
      builder.add(ExecScriptCommand.class, map);
      return builder;
   }

   public void assertString(String paramName, String str) {
      if (str == null || str.length() == 0) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().undefinedParameter(paramName));
      }
   }

   protected WorkflowBuilder getResetClusterPrimitive(ClusterSessionContext clusterSessionContext, int sessionTimeout) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutPrimitiveFactory.getResetClusterPrimitive called with clusterSessionContext: " + clusterSessionContext.toString() + " and null disableFailoverGroups";
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("clusterName", clusterSessionContext.getClusterName());
      Map map = new HashMap();
      map.put("clusterName", clusterSessionContext.getClusterName());
      ArrayList disableFailoverGroups = new ArrayList();
      disableFailoverGroups.add((Object)null);
      disableFailoverGroups.add((Object)null);
      map.put("failoverGroups", disableFailoverGroups);
      map.put("origFailoverGroups", clusterSessionContext.getFailoverGroups());
      map.put("disableAfterTime", !clusterSessionContext.getWaitForAllSessions());
      map.put("sessionTimeout", sessionTimeout);
      builder.add(ClusterDisableSessionHandlingCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getInitClusterPrimitive(ClusterSessionContext clusterSessionContext) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutPrimitiveFactory.getInitClusterPrimitive called with clusterSessionContext: " + clusterSessionContext.toString() + "\n and null disableFailoverGroups";
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("clusterName", clusterSessionContext.getClusterName());
      Map map = new HashMap();
      map.put("clusterName", clusterSessionContext.getClusterName());
      ArrayList disableFailoverGroups = new ArrayList();
      disableFailoverGroups.add((Object)null);
      disableFailoverGroups.add((Object)null);
      map.put("failoverGroups", clusterSessionContext.getOrigFailoverGroups());
      map.put("origFailoverGroups", disableFailoverGroups);
      builder.add(ClusterEnableSessionHandlingCommand.class, map);
      return builder;
   }

   private String[] serverListToArray(List serverList) {
      String[] serverArray = null;
      if (serverList != null) {
         serverArray = new String[serverList.size()];
         serverList.toArray(serverArray);
      }

      return serverArray;
   }

   private void copyGroups(ArrayList groupsToCopy, ArrayList copy) {
      copy.clear();
      Iterator var3 = groupsToCopy.iterator();

      while(var3.hasNext()) {
         ArrayList group = (ArrayList)var3.next();
         ArrayList copiedGroup = new ArrayList();
         copiedGroup.addAll(group);
         copy.add(copiedGroup);
      }

   }

   WorkflowBuilder ensureClustersInitialized(RolloutContext rolloutContext) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Set startingClusters = rolloutContext.getClustersToInitialize();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutPrimitiveFactory.ensureClustersInitialized with clusters: " + startingClusters);
      }

      Iterator var4 = startingClusters.iterator();

      while(var4.hasNext()) {
         String clusterName = (String)var4.next();
         builder.add(this.getInitClusterPrimitive(rolloutContext.getSessionInfoForCluster(clusterName)));
      }

      startingClusters.clear();
      return builder;
   }

   private boolean checkIsElasticityEnabledForCluster(String clusterName) {
      boolean isElasticityEnabled = false;
      DomainMBean domainMBean = TopologyInspector.getDomainMBean();
      WLDFSystemResourceMBean[] wldfSystemResourceMBeans = domainMBean.getWLDFSystemResources();
      WLDFSystemResourceMBean[] var5 = wldfSystemResourceMBeans;
      int var6 = wldfSystemResourceMBeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WLDFSystemResourceMBean wldfSystemResourceMBean = var5[var7];
         WLDFWatchNotificationBean wldfWatchNotificationBean = wldfSystemResourceMBean.getWLDFResource().getWatchNotification();
         WLDFScaleUpActionBean[] wldfScaleUpActionBeans = wldfWatchNotificationBean.getScaleUpActions();
         WLDFScaleUpActionBean[] var11 = wldfScaleUpActionBeans;
         int var12 = wldfScaleUpActionBeans.length;

         int var13;
         for(var13 = 0; var13 < var12; ++var13) {
            WLDFScaleUpActionBean wldfScaleUpActionBean = var11[var13];
            if (wldfScaleUpActionBean.getClusterName().equals(clusterName)) {
               isElasticityEnabled = true;
            }
         }

         WLDFScaleDownActionBean[] wldfScaleDownActionBeans = wldfWatchNotificationBean.getScaleDownActions();
         WLDFScaleDownActionBean[] var17 = wldfScaleDownActionBeans;
         var13 = wldfScaleDownActionBeans.length;

         for(int var18 = 0; var18 < var13; ++var18) {
            WLDFScaleDownActionBean wldfScaleDownActionBean = var17[var18];
            if (wldfScaleDownActionBean.getClusterName().equals(clusterName)) {
               isElasticityEnabled = true;
            }
         }
      }

      return isElasticityEnabled;
   }

   WorkflowBuilder resetFinishedClusters(RolloutContext rolloutContext) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Set finishedClusters = rolloutContext.getClustersToFinalize();
      Iterator var4 = finishedClusters.iterator();

      while(var4.hasNext()) {
         String clusterName = (String)var4.next();
         builder.add(this.getResetClusterPrimitive(rolloutContext.getSessionInfoForCluster(clusterName), rolloutContext.getRolloutUpdateSettings().getShutdownTimeoutSeconds()));
      }

      finishedClusters.clear();
      return builder;
   }

   WorkflowBuilder ensureConsistentTopology(Node node) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      HashMap hashMap = new HashMap();
      hashMap.put("domainModel", node.getDomainModel());
      hashMap.put("nodeName", node.getNodeName());
      builder.add(TopologyConsistencyCommand.class, hashMap);
      return builder;
   }

   void updateContextForFinishedNode(Node node, RolloutContext rolloutContext) {
      Set pendingServers = rolloutContext.getPendingServers();
      Set updatedServers = rolloutContext.getUpdatedServers();
      Set remainingServers = rolloutContext.getRemainingServers();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("updateContextForFinishedNode: " + node.getNodeName() + " with clustersToInitialize: " + rolloutContext.getClustersToInitialize() + " and clustersToFinalize: " + rolloutContext.getClustersToFinalize());
      }

      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(node.getNodeName()) != null) {
         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               this.updateContextForFinishedServer(pendingServers, updatedServers, remainingServers, server);
            }
         }
      }

   }

   private void updateContextForFinishedServer(Set pendingServers, Set updatedServers, Set remainingServers, Server server) {
      String serverName = server.getServerName();
      remainingServers.remove(serverName);
      pendingServers.remove(serverName);
      updatedServers.add(serverName);
   }

   void updateContextForPatchedServer(RolloutContext rolloutContext, Server server) {
      this.updateSessionContextForPatchedServer(rolloutContext, (Set)null, server, (Set)null, true);
   }

   void updateContextForFinishedPatchedServer(RolloutContext rolloutContext, Server server) {
      this.updateContextForFinishedServer(rolloutContext.getPendingServers(), rolloutContext.getUpdatedServers(), rolloutContext.getRemainingServers(), server);
   }

   void updateContextForUpdatingNode(Node node, RolloutContext rolloutContext) {
      Set clustersUpdatedList = new HashSet();
      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(node.getNodeName()) != null) {
         Set buildTimeUpdatedNodes = rolloutContext.getBuildTimeUpdatedNodes();
         Set buildTimeRemainingNodes = rolloutContext.getBuildTimeRemainingNodes();
         String machineBeingPatched = node.getMachineInfo().getMachineName();
         buildTimeUpdatedNodes.add(machineBeingPatched);
         buildTimeRemainingNodes.remove(machineBeingPatched);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("updateContextForUpdatingNode: " + node.getNodeName() + " with clustersToInitialize: " + rolloutContext.getClustersToInitialize() + " and clustersToFinalize: " + rolloutContext.getClustersToFinalize());
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("The updatedNodes are now: " + buildTimeUpdatedNodes + " and the remainingNodes are now: " + buildTimeRemainingNodes + " after adjusting for node: " + machineBeingPatched);
         }

         while(iterator.hasNextServerGroup()) {
            iterator.nextServerGroup();

            HashSet partitionNames;
            Server server;
            boolean patchingServer;
            for(; iterator.hasNextServer(); this.updateSessionContextForPatchedServer(rolloutContext, clustersUpdatedList, server, partitionNames, patchingServer)) {
               partitionNames = new HashSet();
               server = iterator.nextServer();
               String serverName = server.getServerName();
               Map migrationInfoMap = rolloutContext.getMigrationInfo();
               MigrationInfo serverMigrationInfo = (MigrationInfo)migrationInfoMap.get(serverName);
               patchingServer = true;
               if (serverMigrationInfo != null && serverMigrationInfo.getWSMInfo() != null) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("ServerMigrationInfo : " + serverMigrationInfo.getWSMInfo() + " for : " + serverName + " shows Whole Server Migration so session Handling info is skipped...");
                  }

                  patchingServer = false;
               }

               if (!iterator.hasNextGlobalResourceGroup()) {
                  while(iterator.hasNextPartitionApps()) {
                     PartitionApps partitionApps = iterator.nextPartitionApps();
                     partitionNames.add(partitionApps.getPartitionName());
                  }
               }
            }
         }
      }

   }

   private void updateSessionContextForPatchedServer(RolloutContext rolloutContext, Set clustersUpdatedList, Server server, Set partitionNames, boolean patchingServer) {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("updateSessionContextForPatchedServer: " + server.getServerName() + " and cluster: " + server.getCluster() + " and clustersUpdated: " + clustersUpdatedList + " and partitionNames: " + partitionNames);
      }

      String serverName = server.getServerName();
      Cluster cluster = server.getCluster();
      Set pendingServers = rolloutContext.getPendingServers();
      pendingServers.add(serverName);
      if (clustersUpdatedList == null) {
         clustersUpdatedList = new HashSet();
      }

      String clusterName = null;
      if (cluster != null) {
         clusterName = cluster.getClusterName();
      }

      if (clusterName != null) {
         ClusterSessionContext clusterSessionContext = rolloutContext.getSessionInfoForCluster(clusterName);
         ArrayList failoverGroups = clusterSessionContext.getFailoverGroups();
         boolean initializeOrigFailoverGroups = false;
         ArrayList unpatchedServers;
         if (!((Set)clustersUpdatedList).contains(clusterName)) {
            ((Set)clustersUpdatedList).add(clusterName);
            unpatchedServers = clusterSessionContext.getOrigFailoverGroups();
            if (unpatchedServers == null) {
               unpatchedServers = new ArrayList(2);
               clusterSessionContext.setOrigFailoverGroups(unpatchedServers);
               initializeOrigFailoverGroups = true;
            }

            this.copyGroups(failoverGroups, unpatchedServers);
         }

         unpatchedServers = (ArrayList)failoverGroups.get(0);
         ArrayList patchedServers = (ArrayList)failoverGroups.get(1);
         RolloutUpdateSettings settings;
         boolean shouldWaitForAllSessions;
         if (patchedServers.isEmpty() && initializeOrigFailoverGroups) {
            rolloutContext.getClustersToInitialize().add(clusterName);
            settings = rolloutContext.getRolloutUpdateSettings();
            shouldWaitForAllSessions = (settings.isUpdateApplications() || settings.isUpdateOracleHome()) && !settings.isSessionCompatible();
            clusterSessionContext.setWaitForAllSessionsOnRevert(shouldWaitForAllSessions);
         } else {
            clusterSessionContext.setWaitForAllSessionsOnRevert(false);
         }

         if (patchingServer) {
            unpatchedServers.remove(serverName);
            patchedServers.add(serverName);
         }

         if (unpatchedServers.isEmpty()) {
            settings = rolloutContext.getRolloutUpdateSettings();
            shouldWaitForAllSessions = (settings.isUpdateApplications() || settings.isUpdateOracleHome()) && !settings.isSessionCompatible();
            clusterSessionContext.setWaitForAllSessions(shouldWaitForAllSessions);
            rolloutContext.getClustersToFinalize().add(clusterName);
         }

         if (partitionNames != null && !partitionNames.isEmpty()) {
            clusterSessionContext.setPartitions(partitionNames);
         }
      }

   }

   RolloutContext createRolloutContext(RolloutUpdateSettings rolloutUpdateSettings, DomainModel domainModel) {
      RolloutContext rolloutContext = new RolloutContext(rolloutUpdateSettings);
      this.populateState(rolloutContext, domainModel);
      if (rolloutContext.getRolloutUpdateSettings().getRolloutTargetType() == RolloutUpdateSettings.RolloutTargetType.SERVER) {
         this.populateUntargetedClusterMemberState(rolloutContext, domainModel);
      }

      return rolloutContext;
   }

   private void populateState(RolloutContext rolloutContext, DomainModel domainModel) {
      DomainModelIterator iterator = new DomainModelIterator(domainModel);

      while(iterator.hasNextNode()) {
         Node node = iterator.nextNode();
         rolloutContext.addTargetedMachine(node.getNodeName());

         while(iterator.hasNextServerGroup()) {
            iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               String serverName = server.getServerName();
               rolloutContext.addTargetedServer(serverName);
               Cluster cluster = server.getCluster();
               if (cluster != null) {
                  String clusterName = server.getCluster().getClusterName();
                  ClusterSessionContext clusterSessionContext = rolloutContext.getSessionInfoForCluster(clusterName);
                  rolloutContext.addClusterForMachine(node.getNodeName(), clusterName);
                  ArrayList serversInCluster;
                  ArrayList initialFailoverGroups;
                  if (clusterSessionContext == null) {
                     if (PatchingDebugLogger.isDebugEnabled() && rolloutContext.getSessionInfoForServer(serverName) != null) {
                        PatchingDebugLogger.debug("Should not happen! The serverSessionInfoMap already contains entry for server: " + serverName + " with: " + rolloutContext.getSessionInfoForServer(serverName));
                     }

                     clusterSessionContext = new ClusterSessionContext();
                     rolloutContext.addClusterSessionContext(clusterName, serverName, clusterSessionContext);
                     if (clusterName != null) {
                        clusterSessionContext.setClusterName(clusterName);
                        serversInCluster = new ArrayList();
                        serversInCluster.add(serverName);
                        initialFailoverGroups = new ArrayList();
                        initialFailoverGroups.add(0, serversInCluster);
                        initialFailoverGroups.add(1, new ArrayList());
                        clusterSessionContext.setFailoverGroups(initialFailoverGroups);
                     }
                  } else {
                     rolloutContext.addServerSessionContext(serverName, clusterSessionContext);
                     if (clusterName != null) {
                        serversInCluster = clusterSessionContext.getFailoverGroups();
                        initialFailoverGroups = (ArrayList)serversInCluster.get(0);
                        initialFailoverGroups.add(serverName);
                     }
                  }
               }
            }
         }
      }

   }

   private void populateUntargetedClusterMemberState(RolloutContext rolloutContext, DomainModel domainModel) {
      Map clusterMap = domainModel.getClusters();
      if (clusterMap != null && clusterMap.size() > 0) {
         Iterator var4 = clusterMap.values().iterator();

         while(true) {
            Cluster cluster;
            do {
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  cluster = (Cluster)var4.next();
               } while(!cluster.isTargeted());
            } while(cluster.getNumUntargetedMembers() <= 0);

            ClusterSessionContext clusterSessionContext = rolloutContext.getSessionInfoForCluster(cluster.getClusterName());
            ArrayList initialFailoverGroups = clusterSessionContext.getFailoverGroups();
            ArrayList alreadyPatchedServers = (ArrayList)initialFailoverGroups.get(1);
            Iterator var9 = cluster.getServers().values().iterator();

            while(var9.hasNext()) {
               Server server = (Server)var9.next();
               if (!server.isTargeted()) {
                  alreadyPatchedServers.add(server.getServerName());
               }
            }

            rolloutContext.getClustersToInitialize().add(cluster.getClusterName());
         }
      }
   }
}
