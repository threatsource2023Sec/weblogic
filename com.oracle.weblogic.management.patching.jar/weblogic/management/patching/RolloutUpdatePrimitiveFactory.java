package weblogic.management.patching;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.patching.commands.AssertUpdateOracleHomeDirectoryCommand;
import weblogic.management.patching.commands.CheckCoherencePrerequisitesCommand;
import weblogic.management.patching.commands.CheckRolloutApplicationPrerequisitesCommand;
import weblogic.management.patching.commands.CheckRolloutGeneralPrerequisitesCommand;
import weblogic.management.patching.commands.CheckRolloutJavaHomePrerequisitesCommand;
import weblogic.management.patching.commands.CheckRolloutOracleHomePrerequisitesCommand;
import weblogic.management.patching.commands.CheckSingletonsPrerequisitesCommand;
import weblogic.management.patching.commands.CleanupExtensionArtifactsCommand;
import weblogic.management.patching.commands.ClusterUpdateFailoverGroupCommand;
import weblogic.management.patching.commands.ClusterUpdateFailoverGroupOnExecCommand;
import weblogic.management.patching.commands.ClusterUpdateFailoverGroupOnRevertCommand;
import weblogic.management.patching.commands.CommandException;
import weblogic.management.patching.commands.CreateFileLockCommand;
import weblogic.management.patching.commands.DelayBetweenNodesCommand;
import weblogic.management.patching.commands.DisableElasticityCommand;
import weblogic.management.patching.commands.DomainModelIterator;
import weblogic.management.patching.commands.EnableElasticityCommand;
import weblogic.management.patching.commands.InitExtensionsForUpdateApplicationCommand;
import weblogic.management.patching.commands.InitExtensionsForUpdateOracleHomeCommand;
import weblogic.management.patching.commands.MigrateJMSCommand;
import weblogic.management.patching.commands.MigrateJMSFailbackCommand;
import weblogic.management.patching.commands.MigrateJTACommand;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.patching.commands.PrepareUpdateOracleHomeDirectoryCommand;
import weblogic.management.patching.commands.RemoveFileLockCommand;
import weblogic.management.patching.commands.RestartNodeManagerAsyncOnExecCommand;
import weblogic.management.patching.commands.RestartNodeManagerAsyncOnRevertCommand;
import weblogic.management.patching.commands.ResumeServerOnExecCommand;
import weblogic.management.patching.commands.ResumeServerOnRevertCommand;
import weblogic.management.patching.commands.ResumeServerShutdownOnRevertCommand;
import weblogic.management.patching.commands.ServerSessionHandlingOnExecCommand;
import weblogic.management.patching.commands.ServerSessionHandlingOnRevertCommand;
import weblogic.management.patching.commands.ServerUtils;
import weblogic.management.patching.commands.ShutdownServerResumeOnRevertCommand;
import weblogic.management.patching.commands.StartServerAdminModeOnExecCommand;
import weblogic.management.patching.commands.StartServerAdminModeOnRevertCommand;
import weblogic.management.patching.commands.TargetedRedeployOnExecCommand;
import weblogic.management.patching.commands.TargetedRedeployOnRevertCommand;
import weblogic.management.patching.commands.UpdateApplicationSourceCommand;
import weblogic.management.patching.commands.UpdateNodeListCommand;
import weblogic.management.patching.commands.VerifyNodeManagerRestartOnExecCommand;
import weblogic.management.patching.commands.VerifyNodeManagerRestartOnRevertCommand;
import weblogic.management.patching.commands.WSMToAdminModeCommand;
import weblogic.management.patching.commands.WSMToAdminModeFailbackCommand;
import weblogic.management.patching.commands.WaitForCoherenceServicesHAStatusExecCommand;
import weblogic.management.patching.commands.WaitForCoherenceServicesHAStatusRevertCommand;
import weblogic.management.patching.model.Cluster;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.patching.model.JTAInfo;
import weblogic.management.patching.model.MigrationInfo;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.NodeManagerRelation;
import weblogic.management.patching.model.PartitionApps;
import weblogic.management.patching.model.Server;
import weblogic.management.patching.model.ServerGroup;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.workflow.FailurePlan;
import weblogic.management.workflow.MutableBoolean;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.WorkflowBuilder;

public class RolloutUpdatePrimitiveFactory extends RolloutPrimitiveFactory {
   public static final int DEFAULT_READY_APPS_TIMEOUT = 180;
   public static final int DEFAULT_SCRIPT_TIMEOUT = 2;
   public static final String binPatchingDirPath;
   public static final boolean cleanupExtensions;

   public WorkflowBuilder getRolloutUpdatePrimitive(RolloutUpdateSettings rolloutUpdateSettings, DomainModel domainModel) throws ManagementException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      ArrayList targetedNodes = new ArrayList();
      ArrayList updatedNodes = new ArrayList();
      Node adminNode = domainModel.getAdminNode();
      map.put("targetedNodes", targetedNodes);
      map.put("updatedNodes", updatedNodes);
      map.put("isAppSourceUpdated", new HashMap());
      map.put("isDryRun", rolloutUpdateSettings.isDryRun());
      if (!rolloutUpdateSettings.isAutoRevert()) {
         FailurePlan failurePlan = new FailurePlan();
         failurePlan.setShouldRevert(false);
         builder.failurePlan(failurePlan);
      }

      builder.add(this.getPrerequisitePrimitives(rolloutUpdateSettings, domainModel));
      if (rolloutUpdateSettings.hasExtensions()) {
         if (cleanupExtensions) {
            builder.add(this.getCleanupExtensionArtifactsPrimitive(rolloutUpdateSettings, adminNode));
         }

         builder.add(this.getExtensionJarExtractorPrimitives(rolloutUpdateSettings, domainModel));
         builder.add(this.getOnlineBeforeUpdateExtensionPrimitives(rolloutUpdateSettings, adminNode));
      }

      if (rolloutUpdateSettings.isDryRun()) {
         this.populateTargetedNodesForDryRun(domainModel, rolloutUpdateSettings, targetedNodes);
      } else {
         RolloutContext rolloutContext = this.createRolloutContext(rolloutUpdateSettings, domainModel);
         rolloutContext.setMigrationInfo(TopologyInspector.getSingletonServicesInfo(rolloutUpdateSettings.getMigrationPropertyList()));
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getRolloutUpdateWorkflow iterating over domain " + domainModel.getDomainName());
         }

         map.put("elasticityMBeanMap", new HashMap());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Adding disable elasticity primitive");
         }

         builder.add(this.getDisableElasticityPrimitive(domainModel));
         int nodeCount = 0;
         int nodesRemaining = domainModel.getNumTargetedNodes();
         DomainModelIterator iterator = new DomainModelIterator(domainModel, rolloutUpdateSettings);

         Node node;
         while(iterator.hasNextNode()) {
            node = iterator.nextNode();
            targetedNodes.add(node.getMachineInfo());
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getRolloutUpdateWorkflow calling getUpdateNodeDirectoryPrimitive for node " + node.getNodeName());
            }

            WorkflowBuilder nodeBuilder = this.getUpdateNodeDirectoryPrimitive(node, rolloutContext);
            if (rolloutUpdateSettings.hasExtensions()) {
               if (cleanupExtensions) {
                  builder.add(this.getCleanupExtensionArtifactsPrimitive(rolloutUpdateSettings, node));
               }

               builder.add(this.getInitExtensionPrimitive(rolloutUpdateSettings, node));
               builder.add(this.getExtensionPrimitives(rolloutUpdateSettings, node));
            }

            builder.add(nodeBuilder);
            ++nodeCount;
            --nodesRemaining;
            boolean adminOnlyNode = node.isAdmin() && node.getNumTargetedMembers() > 1;
            if (rolloutUpdateSettings.hasExtensions()) {
               builder.add(this.getOnlineAfterUpdateExtensionPrimitives(rolloutUpdateSettings, node));
            }

            if (nodesRemaining > 0 && !adminOnlyNode) {
               builder.add(this.getDelayBetweenNodesPrimitive(node, rolloutContext));
            }

            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getRolloutUpdateWorkflow iterated over " + nodeCount + " nodes, " + nodesRemaining + " nodes remain");
            }
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Adding enable elasticity primitive");
         }

         builder.add(this.getEnableElasticityPrimitive(domainModel));
         if (rolloutUpdateSettings.hasExtensions()) {
            builder.add(this.getRolloutSuccessExtensionPrimitives(rolloutUpdateSettings, adminNode));
            if (cleanupExtensions) {
               iterator = new DomainModelIterator(domainModel, rolloutUpdateSettings);

               while(iterator.hasNextNode()) {
                  node = iterator.nextNode();
                  if (!node.isAdmin()) {
                     builder.add(this.getCleanupExtensionArtifactsPrimitive(rolloutUpdateSettings, node));
                  }
               }

               builder.add(this.getCleanupExtensionArtifactsPrimitive(rolloutUpdateSettings, adminNode));
            }
         }
      }

      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getExtensionJarExtractorPrimitives(RolloutUpdateSettings rolloutUpdateSettings, DomainModel domainModel) throws ManagementException {
      ServerMBean adminServer = ServerUtils.getServer(domainModel.getAdminServer().getServerName());
      return rolloutUpdateSettings.getExtensionManager().getJarExtractionCommandBuilder(adminServer.getRootDirectory() + File.separator + binPatchingDirPath);
   }

   protected Map getCommonAvailableSharedStates(RolloutUpdateSettings rolloutUpdateSettings, Node node) throws ManagementException {
      String anyServerOnNode;
      if (node.isAdmin()) {
         anyServerOnNode = node.getAdminServer().getServerName();
      } else {
         anyServerOnNode = ((Server)((ServerGroup)node.getServerGroups().values().stream().findFirst().get()).getCluster().getServers().values().stream().findFirst().get()).getServerName();
      }

      List serversOnNode = (List)node.getServerGroups().values().stream().filter(ServerGroup::isTargeted).flatMap((sgrp) -> {
         return sgrp.getServers().values().stream();
      }).filter(Server::isTargeted).map(Server::getServerName).collect(Collectors.toList());
      ServerMBean server = ServerUtils.getServer(anyServerOnNode);

      ServerLifeCycleRuntimeMBean serverLifeCycleRuntimeMBean;
      try {
         serverLifeCycleRuntimeMBean = ServerUtils.getServerLifeCycleRuntimeMBean(anyServerOnNode);
      } catch (CommandException var14) {
         throw new ManagementException(var14.getMessage(), var14);
      }

      String machineName = node.getMachineInfo().getMachineName();
      Map hashMap = new HashMap();
      hashMap.put("machineName", machineName);
      hashMap.put("timeoutMillis", 0);
      hashMap.put("EXTENSION_SCRIPT_DIR", "patching");
      Properties scriptEnvProp = new Properties();
      scriptEnvProp.put("javaHome", System.getenv("JAVA_HOME"));
      scriptEnvProp.put("mwHome", serverLifeCycleRuntimeMBean.getMiddlewareHome());
      scriptEnvProp.put("domainDir", server.getRootDirectory());
      scriptEnvProp.put("domainTmp", server.getRootDirectory() + File.separator + "tmp");
      scriptEnvProp.put("currentNodeName", machineName);
      scriptEnvProp.put("currentServerNames", String.join(", ", serversOnNode));
      if (rolloutUpdateSettings.isMTRollout() && rolloutUpdateSettings.rolloutTargetType.equals(RolloutUpdateSettings.RolloutTargetType.PARTITION)) {
         scriptEnvProp.put("partitionName", rolloutUpdateSettings.getTarget());
      }

      hashMap.put("scriptTimeoutInMin", 2);
      if (rolloutUpdateSettings.isUpdateApplications()) {
         List appInfoList = new ArrayList();
         Iterator var11 = rolloutUpdateSettings.getApplicationPropertyList().iterator();

         while(var11.hasNext()) {
            ApplicationProperties appProps = (ApplicationProperties)var11.next();
            List list = Arrays.asList(appProps.getApplicationName(), appProps.getPatchedLocation(), appProps.getBackupLocation());
            appInfoList.add(String.join(",", list));
         }

         hashMap.put("applicationInfo", String.join(":", appInfoList));
         scriptEnvProp.put("applicationInfo", String.join(":", appInfoList));
      }

      if (rolloutUpdateSettings.isUpdateJavaHome()) {
         hashMap.put("newJavaHome", rolloutUpdateSettings.getJavaHome());
         scriptEnvProp.put("newJavaHome", rolloutUpdateSettings.getJavaHome());
      }

      if (rolloutUpdateSettings.isUpdateOracleHome()) {
         hashMap.put("newDirectory", rolloutUpdateSettings.getPatchedOracleHome());
         hashMap.put("backupDirectory", rolloutUpdateSettings.getBackupOracleHome());
         scriptEnvProp.put("patched", rolloutUpdateSettings.getPatchedOracleHome());
         scriptEnvProp.put("backupDir", rolloutUpdateSettings.getBackupOracleHome());
      }

      hashMap.put("scriptEnvProps", scriptEnvProp);
      return hashMap;
   }

   protected WorkflowBuilder getOnlineAfterUpdateExtensionPrimitives(RolloutUpdateSettings rolloutUpdateSettings, Node node) throws ManagementException {
      Map availableSharedStates = this.getCommonAvailableSharedStates(rolloutUpdateSettings, node);

      try {
         return rolloutUpdateSettings.getExtensionManager().getExtensionsForOnlineAfterUpdate(availableSharedStates);
      } catch (ClassNotFoundException var5) {
         throw new ManagementException(var5.getMessage(), var5);
      }
   }

   protected WorkflowBuilder getRolloutSuccessExtensionPrimitives(RolloutUpdateSettings rolloutUpdateSettings, Node node) throws ManagementException {
      Map availableSharedStates = this.getCommonAvailableSharedStates(rolloutUpdateSettings, node);

      try {
         return rolloutUpdateSettings.getExtensionManager().getExtensionsForRolloutSuccess(availableSharedStates);
      } catch (ClassNotFoundException var5) {
         throw new ManagementException(var5.getMessage(), var5);
      }
   }

   protected WorkflowBuilder getCleanupExtensionArtifactsPrimitive(RolloutUpdateSettings rolloutUpdateSettings, Node node) throws ManagementException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String machineName = node.getMachineInfo().getMachineName();
      String extensionJars = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionJarsForExtensionPoints());
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getCleanupExtensionArtifactsPrimitive called for node " + node.getNodeName() + " with params: machineName " + machineName + ", extensionJars " + extensionJars;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("extensionJars", extensionJars);
      map.put("timeoutMillis", 0);
      map.put("useNM", Boolean.TRUE);
      builder.add(CleanupExtensionArtifactsCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getInitExtensionPrimitive(RolloutUpdateSettings rolloutUpdateSettings, Node node) throws ManagementException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String machineName = node.getMachineInfo().getMachineName();
      String extensionJars = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionJarsForExtensionPoints());
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getInitExtensionPrimitive called with params: machineName " + machineName + ", extensionJars " + extensionJars;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("extensionJars", extensionJars);
      map.put("timeoutMillis", 0);
      map.put("useNM", Boolean.TRUE);
      if (!rolloutUpdateSettings.isUpdateOracleHome() && !rolloutUpdateSettings.isUpdateJavaHome()) {
         if (rolloutUpdateSettings.isUpdateApplications()) {
            builder.add(InitExtensionsForUpdateApplicationCommand.class, map);
         }
      } else {
         builder.add(InitExtensionsForUpdateOracleHomeCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getExtensionPrimitives(RolloutUpdateSettings rolloutUpdateSettings, Node node) throws ManagementException {
      Map availableSharedStates = this.getCommonAvailableSharedStates(rolloutUpdateSettings, node);

      try {
         return rolloutUpdateSettings.getExtensionManager().getExtensionsForEachNode(availableSharedStates);
      } catch (ClassNotFoundException var5) {
         throw new ManagementException(var5.getMessage(), var5);
      }
   }

   protected WorkflowBuilder getOnlineAfterServerStartExtensionPrimitives(RolloutUpdateSettings rolloutUpdateSettings, Node node, Server server) throws ManagementException {
      Map availableSharedStates = this.getCommonAvailableSharedStates(rolloutUpdateSettings, node);
      availableSharedStates.put("serverName", server.getServerName());

      try {
         return rolloutUpdateSettings.getExtensionManager().getExtensionsForOnlineAfterServerStart(availableSharedStates);
      } catch (ClassNotFoundException var6) {
         throw new ManagementException(var6.getMessage(), var6);
      }
   }

   protected WorkflowBuilder getOnlineBeforeUpdateExtensionPrimitives(RolloutUpdateSettings rolloutUpdateSettings, Node node) throws ManagementException {
      Map availableSharedStates = this.getCommonAvailableSharedStates(rolloutUpdateSettings, node);

      try {
         return rolloutUpdateSettings.getExtensionManager().getExtensionsForOnlineBeforeUpdate(availableSharedStates);
      } catch (ClassNotFoundException var5) {
         throw new ManagementException(var5.getMessage(), var5);
      }
   }

   protected WorkflowBuilder getDisableElasticityPrimitive(DomainModel domainModel) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map clusters = domainModel.getClusters();
      if (clusters != null && clusters.size() > 0) {
         Iterator var4 = clusters.values().iterator();

         while(var4.hasNext()) {
            Cluster cluster = (Cluster)var4.next();
            if (cluster.isTargeted()) {
               String clusterName = cluster.getClusterName();
               WorkflowBuilder disableElasticityPrimitive = this.getDisableElasticityPrimitive(clusterName);
               builder.add(disableElasticityPrimitive);
            }
         }
      }

      return builder;
   }

   protected WorkflowBuilder getEnableElasticityPrimitive(DomainModel domainModel) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map clusters = domainModel.getClusters();
      if (clusters != null && clusters.size() > 0) {
         Iterator var4 = clusters.values().iterator();

         while(var4.hasNext()) {
            Cluster cluster = (Cluster)var4.next();
            if (cluster.isTargeted()) {
               String clusterName = cluster.getClusterName();
               WorkflowBuilder enableElasticityPrimitive = this.getEnableElasticityPrimitive(clusterName);
               builder.add(enableElasticityPrimitive);
            }
         }
      }

      return builder;
   }

   private WorkflowBuilder getDisableElasticityPrimitive(String clusterName) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      HashMap hashMap = new HashMap();
      hashMap.put("clusterName", clusterName);
      builder.add(DisableElasticityCommand.class, hashMap);
      return builder;
   }

   private WorkflowBuilder getEnableElasticityPrimitive(String clusterName) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      HashMap hashMap = new HashMap();
      hashMap.put("clusterName", clusterName);
      builder.add(EnableElasticityCommand.class, hashMap);
      return builder;
   }

   protected WorkflowBuilder getDelayBetweenNodesPrimitive(Node node, RolloutContext rolloutContext) {
      String machineName = node.getNodeName();
      long pauseTime = rolloutContext.getRolloutUpdateSettings().getDelayBetweenNodesMillis();
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getDelayBetweenNodesPrimitive adding DelayBetweenNodesCommand with " + machineName + " and " + pauseTime);
      }

      map.put("machineName", machineName);
      map.put("delayTimeMillis", pauseTime);
      builder.add(DelayBetweenNodesCommand.class);
      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getPrerequisitePrimitives(RolloutUpdateSettings rolloutUpdateSettings, DomainModel domainModel) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getPrerequisitePrimitives adding CheckRolloutGeneralPrerequisitesCommand");
      }

      boolean enforceHA = true;
      if (rolloutUpdateSettings.getRolloutTargetType() == RolloutUpdateSettings.RolloutTargetType.SERVER) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("getPrerequisitePrimitives CheckRolloutGeneralPrerequisitesCommand ignoring HA requirements");
         }

         enforceHA = false;
      }

      map.put("domainModel", domainModel);
      map.put("enforceHA", enforceHA);
      map.put("logTarget", rolloutUpdateSettings.getTarget());
      if (domainModel.getAdminServer().getNmRelation().equals(NodeManagerRelation.REGISTERED)) {
         map.put("useNM", Boolean.TRUE);
      } else {
         map.put("useNM", Boolean.FALSE);
      }

      builder.add(CheckRolloutGeneralPrerequisitesCommand.class);
      Map migrationInfo = TopologyInspector.getSingletonServicesInfo(rolloutUpdateSettings.getMigrationPropertyList());
      if (!migrationInfo.isEmpty()) {
         map.put("migrationInfo", (HashMap)migrationInfo);
         builder.add(CheckSingletonsPrerequisitesCommand.class);
      }

      if (rolloutUpdateSettings.isUpdateOracleHome()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("getPrerequisitePrimitives adding CheckRolloutOracleHomePrerequisitesCommand");
         }

         map.put("newDirectory", rolloutUpdateSettings.getPatchedOracleHome());
         map.put("backupDirectory", rolloutUpdateSettings.getBackupOracleHome());
         if (rolloutUpdateSettings.isUpdateJavaHome()) {
            map.put("newJavaHome", rolloutUpdateSettings.getJavaHome());
         } else {
            map.put("newJavaHome", (Object)null);
         }

         builder.add(CheckRolloutOracleHomePrerequisitesCommand.class);
      } else if (rolloutUpdateSettings.isUpdateJavaHome()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("getPrerequisitePrimitives adding CheckRolloutJavaHomePrerequisitesCommand");
         }

         map.put("newJavaHome", rolloutUpdateSettings.getJavaHome());
         builder.add(CheckRolloutJavaHomePrerequisitesCommand.class);
      }

      if (rolloutUpdateSettings.isUpdateApplications()) {
         List applicationPropertyList = rolloutUpdateSettings.getApplicationPropertyList();

         HashMap applicationMap;
         for(Iterator var8 = applicationPropertyList.iterator(); var8.hasNext(); builder.add(CheckRolloutApplicationPrerequisitesCommand.class, applicationMap)) {
            ApplicationProperties applicationProperties = (ApplicationProperties)var8.next();
            applicationMap = new HashMap();
            applicationMap.put("applicationProperties", applicationProperties);
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("getPrerequisitePrimitives adding CheckRolloutApplicationPrerequisitesCommand for " + applicationProperties.getApplicationName());
            }
         }
      }

      String coherenceServiceStatusHATarget = rolloutUpdateSettings.getCoherenceServiceStatusHATarget();
      if (coherenceServiceStatusHATarget != null) {
         map.put("haStatusTarget", coherenceServiceStatusHATarget);
         builder.add(CheckCoherencePrerequisitesCommand.class);
      }

      builder.add(map);
      return builder;
   }

   protected void populateTargetedNodesForDryRun(DomainModel domainModel, RolloutUpdateSettings rolloutUpdateSettings, ArrayList targetedNodes) {
      DomainModelIterator iterator = new DomainModelIterator(domainModel, rolloutUpdateSettings);

      while(iterator.hasNextNode()) {
         Node node = iterator.nextNode();
         targetedNodes.add(node.getMachineInfo());
      }

   }

   private void addOfflineExtensionsForApplicationUpdate(Map appMap, RolloutUpdateSettings rolloutUpdateSettings) {
      appMap.put("beforeUpdateExtensions", "");
      appMap.put("afterUpdateExtensions", "");
      if (rolloutUpdateSettings.hasExtensions() && !rolloutUpdateSettings.isUpdateOracleHome() && !rolloutUpdateSettings.isUpdateJavaHome()) {
         String offlineBeforeUpdateExtensionScripts = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionScriptsForOfflineBeforeUpdateExtension());
         String offlineAfterUpdateExtensionScripts = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionScriptsForOfflineAfterUpdateExtension());
         appMap.put("beforeUpdateExtensions", offlineBeforeUpdateExtensionScripts);
         if (!rolloutUpdateSettings.isMTRollout()) {
            appMap.put("afterUpdateExtensions", offlineAfterUpdateExtensionScripts);
         }

         if (rolloutUpdateSettings.isMTRollout() && offlineAfterUpdateExtensionScripts != null && !offlineAfterUpdateExtensionScripts.isEmpty()) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidExtensionPoint("ep_OfflineAfterUpdate"));
         }
      }

   }

   protected WorkflowBuilder getApplicationUpdatePrimitive(RolloutUpdateSettings rolloutUpdateSettings, Node node, Node adminNode) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      map.put("machineName", node.getNodeName());
      map.put("timeoutMillis", 0);
      this.addOfflineExtensionsForApplicationUpdate(map, rolloutUpdateSettings);
      List applicationPropertiesList = rolloutUpdateSettings.getApplicationPropertyList();
      if (applicationPropertiesList != null && applicationPropertiesList.size() > 0) {
         Iterator var7 = applicationPropertiesList.iterator();

         label67:
         while(true) {
            while(true) {
               if (!var7.hasNext()) {
                  break label67;
               }

               ApplicationProperties applicationProperties = (ApplicationProperties)var7.next();
               if (applicationProperties.isDefaultStaged()) {
                  DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
                  boolean requiresUpdatingStagedSource = false;
                  if (iterator.skipToNode(node.getNodeName()) != null) {
                     while(iterator.hasNextServerGroup()) {
                        ServerGroup serverGroup = iterator.nextServerGroup();
                        boolean noStageUpdatedOnce = Boolean.FALSE;
                        ServerUtils serverUtils = new ServerUtils();

                        while(iterator.hasNextServer()) {
                           Server server = iterator.nextServer();
                           String serverName = server.getServerName();
                           if (applicationProperties.isServerTargeted(serverName)) {
                              String serverDefaultStageMode = serverUtils.getStagingModeForServer(serverName);
                              if (serverDefaultStageMode == null && PatchingDebugLogger.isDebugEnabled()) {
                                 PatchingDebugLogger.debug("getApplicationUpdatePrimitive: server default stage mode is NULL for server=" + serverName);
                              }

                              Objects.requireNonNull(serverDefaultStageMode);
                              WorkflowBuilder noStagePrimitive;
                              if (serverDefaultStageMode.equals("nostage")) {
                                 if (!noStageUpdatedOnce) {
                                    noStagePrimitive = this.getNoStageApplicationUpdatePrimitiveForServer(applicationProperties, server, rolloutUpdateSettings);
                                    Objects.requireNonNull(noStagePrimitive);
                                    builder.add(noStagePrimitive);
                                    noStageUpdatedOnce = true;
                                 }
                              } else if (serverDefaultStageMode.equals("external_stage")) {
                                 noStagePrimitive = this.getExternalStageApplicationUpdatePrimitiveForServer(applicationProperties, server, iterator, serverUtils, rolloutUpdateSettings);
                                 Objects.requireNonNull(noStagePrimitive);
                                 builder.add(noStagePrimitive);
                              } else {
                                 requiresUpdatingStagedSource = true;
                              }
                           }
                        }
                     }
                  }

                  if (requiresUpdatingStagedSource) {
                     WorkflowBuilder stagedPrimitive = this.getStagedApplicationUpdatePrimitive(applicationProperties, adminNode, rolloutUpdateSettings);
                     Objects.requireNonNull(stagedPrimitive);
                     builder.add(stagedPrimitive);
                  }
               } else if (applicationProperties.isExternalStaged()) {
                  builder.add(this.getExternalStageApplicationUpdatePrimitive(applicationProperties, node, rolloutUpdateSettings));
               } else if (applicationProperties.isNoStaged()) {
                  builder.add(this.getNoStageApplicationUpdatePrimitive(applicationProperties, node, rolloutUpdateSettings));
               } else if (applicationProperties.isStaged()) {
                  builder.add(this.getStagedApplicationUpdatePrimitive(applicationProperties, adminNode, rolloutUpdateSettings));
               }
            }
         }
      }

      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getCreateFileLockPrimitive(String machineName, boolean isUpdateJavaHome) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("isUpdateJavaHome", isUpdateJavaHome);
      builder.add(CreateFileLockCommand.class);
      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getRemoveFileLockPrimitive(String machineName, boolean isUpdateJavaHome) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("isUpdateJavaHome", isUpdateJavaHome);
      builder.add(RemoveFileLockCommand.class);
      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getStagedApplicationUpdatePrimitive(ApplicationProperties applicationProperties, Node adminNode, RolloutUpdateSettings rolloutUpdateSettings) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      map.put("machineName", adminNode.getNodeName());
      map.put("timeoutMillis", 0);
      ServerUtils serverUtils = new ServerUtils();
      String applicationName = applicationProperties.getApplicationName();
      String currentLoc = applicationProperties.getCurrentLocation();
      String patchedLoc = applicationProperties.getPatchedLocation();
      String backupLoc = applicationProperties.getBackupLocation();
      if (applicationProperties.getResourceGroupTemplateName() == null || applicationProperties.getResourceGroupTemplateName().isEmpty()) {
         Set partitionNames = applicationProperties.getPartitionNames();
         Iterator iterator = partitionNames.iterator();
         if (partitionNames.size() == 1) {
            String partitionName = (String)iterator.next();
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("UpdateApplicationSourceCommand: " + applicationName + " for partition " + partitionName + " on machine: " + adminNode.getNodeName() + " because it is targeted to " + partitionName);
            }

            patchedLoc = serverUtils.resolvePath(adminNode.getAdminServer().getServerName(), partitionName, applicationProperties.getPatchedLocation());
            backupLoc = serverUtils.resolvePath(adminNode.getAdminServer().getServerName(), partitionName, applicationProperties.getBackupLocation());
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Only one partition expected, instead got : " + (String)partitionNames.stream().collect(Collectors.joining(", ")));
         }
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Adding UpdateApplicationSourceCommand on AdminServer for app: " + applicationName);
      }

      WorkflowBuilder appBuilder = WorkflowBuilder.newInstance();
      Map appMap = new HashMap();
      appMap.put("applicationName", applicationName);
      appMap.put("currentSource", currentLoc);
      appMap.put("patched", patchedLoc);
      appMap.put("backup", backupLoc);
      appMap.put("deploymentPlan", applicationProperties.getPlanLocation());
      appMap.put("removePlanOverride", applicationProperties.getRemovePlanOverride());
      appMap.put("useNM", Boolean.FALSE);
      appBuilder.add(UpdateApplicationSourceCommand.class, appMap);
      builder.add(appBuilder);
      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getExternalStageApplicationUpdatePrimitive(ApplicationProperties applicationProperties, Node node, RolloutUpdateSettings rolloutUpdateSettings) {
      ServerUtils serverUtils = new ServerUtils();
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(node.getNodeName()) != null) {
         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               builder.add(this.getExternalStageApplicationUpdatePrimitiveForServer(applicationProperties, server, iterator, serverUtils, rolloutUpdateSettings));
            }
         }
      }

      return builder;
   }

   protected WorkflowBuilder getExternalStageApplicationUpdatePrimitiveForServer(ApplicationProperties applicationProperties, Server server, DomainModelIterator iterator, ServerUtils serverUtils, RolloutUpdateSettings rolloutUpdateSettings) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String applicationName = applicationProperties.getApplicationName();
      String machineName = server.getServerInfo().getMachineName();
      String serverName = server.getServerName();
      String serverStagingDirectory = server.getServerInfo().getStagingDirectory();
      String partitionName;
      String currentLoc;
      if (iterator.hasNextPartitionApps()) {
         while(iterator.hasNextPartitionApps()) {
            PartitionApps partitionApps = iterator.nextPartitionApps();
            partitionName = partitionApps.getPartitionName();
            if (applicationProperties.isPartitionTargeted(partitionName)) {
               currentLoc = serverUtils.getRootDirectoryForServer(serverName);
               String rootDir = serverUtils.getPartitionSystemRoot(partitionName);
               String stagingDir = rootDir + File.separator + serverUtils.getRelativePath(serverStagingDirectory, currentLoc);
               String relativeStagingDir = serverUtils.getRelativePath(stagingDir, currentLoc);
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Adding UpdateApplicationSourceCommand for external stage app: " + applicationName + " for partition " + partitionName + " on machine: " + machineName + " because it is not targeted to " + partitionName);
               }

               String currentLoc = relativeStagingDir + File.separator + applicationProperties.getStagingSubdirectory();
               String patchedLoc = serverUtils.resolvePath(serverName, partitionName, applicationProperties.getPatchedLocation());
               String backupLoc = serverUtils.resolvePath(serverName, partitionName, applicationProperties.getBackupLocation());
               builder.add(this.getUpdateApplicationSourcePrimitive(applicationProperties.getApplicationName(), currentLoc, patchedLoc, backupLoc, rolloutUpdateSettings));
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Skipping UpdateApplicationSource for external stage app: " + applicationName + " for partition " + partitionName + " on machine: " + machineName + " because it is not targeted to " + partitionName);
            }
         }
      } else if (applicationProperties.isServerTargeted(serverName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Adding UpdateApplicationSourceCommand for external stage app: " + applicationName + " for server " + serverName + " on node " + machineName);
         }

         String rootDirectory = serverUtils.getRootDirectoryForServer(serverName);
         partitionName = serverUtils.getRelativePath(serverStagingDirectory, rootDirectory);
         currentLoc = partitionName + File.separator + applicationProperties.getStagingSubdirectory();
         builder.add(this.getUpdateApplicationSourcePrimitive(applicationProperties.getApplicationName(), currentLoc, applicationProperties.getPatchedLocation(), applicationProperties.getBackupLocation(), rolloutUpdateSettings));
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Skipping UpdateApplicationSource for external stage app: " + applicationName + " for server " + serverName + " on machine: " + machineName + " because it is not targeted to " + serverName);
      }

      return builder;
   }

   protected WorkflowBuilder getUpdateApplicationSourcePrimitive(String appName, String currentLocation, String patchedLocation, String backupLocation, RolloutUpdateSettings rolloutUpdateSettings) {
      WorkflowBuilder appBuilder = WorkflowBuilder.newInstance();
      Map appMap = new HashMap();
      appMap.put("applicationName", appName);
      appMap.put("currentSource", currentLocation);
      appMap.put("patched", patchedLocation);
      appMap.put("backup", backupLocation);
      appMap.put("useNM", Boolean.TRUE);
      appBuilder.add(UpdateApplicationSourceCommand.class, appMap);
      return appBuilder;
   }

   protected WorkflowBuilder getExternalStageApplicationUpdateForServerOnNodePrimitive(ApplicationProperties applicationProperties, Node node, Server server, RolloutUpdateSettings rolloutUpdateSettings) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String applicationName = applicationProperties.getApplicationName();
      String machineName = node.getNodeName();
      ServerUtils serverUtils = new ServerUtils();
      String serverName = server.getServerName();
      String serverStagingDirectory = server.getServerInfo().getStagingDirectory();
      String rootDirectory = serverUtils.getRootDirectoryForServer(serverName);
      String relativeStagingDirectory = serverUtils.getRelativePath(serverStagingDirectory, rootDirectory);
      if (applicationProperties.isServerTargeted(serverName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Adding UpdateApplicationSourceCommand for external stage app: " + applicationName + " for server " + serverName + " on node " + machineName);
         }

         String currentLoc = relativeStagingDirectory + File.separator + applicationProperties.getStagingSubdirectory();
         builder.add(this.getUpdateApplicationSourcePrimitive(applicationProperties.getApplicationName(), currentLoc, applicationProperties.getPatchedLocation(), applicationProperties.getBackupLocation(), rolloutUpdateSettings));
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Skipping UpdateApplicationSource for external stage app: " + applicationName + " for server " + serverName + " on machine: " + machineName + " because it is not targeted to " + serverName);
      }

      return builder;
   }

   protected WorkflowBuilder getNoStageApplicationUpdatePrimitive(ApplicationProperties applicationProperties, Node node, RolloutUpdateSettings rolloutUpdateSettings) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(node.getNodeName()) != null) {
         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               if (applicationProperties.isServerTargeted(server.getServerName())) {
                  WorkflowBuilder noStagePrimitive = this.getNoStageApplicationUpdatePrimitiveForServer(applicationProperties, server, rolloutUpdateSettings);
                  Objects.requireNonNull(builder);
                  builder.add(noStagePrimitive);
                  break;
               }
            }
         }
      }

      return builder;
   }

   protected WorkflowBuilder getNoStageApplicationUpdatePrimitiveForServer(ApplicationProperties applicationProperties, Server server, RolloutUpdateSettings rolloutUpdateSettings) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String applicationName = applicationProperties.getApplicationName();
      String machineName = server.getServerInfo().getMachineName();
      String serverName = server.getServerName();
      if (applicationProperties.isServerTargeted(serverName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Adding UpdateApplicationSourceCommand for app: " + applicationName + " on node " + machineName);
         }

         builder.add(this.getUpdateApplicationSourcePrimitive(applicationName, applicationProperties.getCurrentLocation(), applicationProperties.getPatchedLocation(), applicationProperties.getBackupLocation(), rolloutUpdateSettings));
      }

      return builder;
   }

   protected WorkflowBuilder getTargetedRedeployOnExecPrimitive(RolloutUpdateSettings rolloutUpdateSettings, String adminNodeName, String serverName) {
      return this.getTargetedRedeployPrimitive(rolloutUpdateSettings, adminNodeName, serverName, true);
   }

   protected WorkflowBuilder getTargetedRedeployOnRevertPrimitive(RolloutUpdateSettings rolloutUpdateSettings, String adminNodeName, String serverName) {
      return this.getTargetedRedeployPrimitive(rolloutUpdateSettings, adminNodeName, serverName, false);
   }

   protected WorkflowBuilder getTargetedRedeployPrimitive(RolloutUpdateSettings rolloutUpdateSettings, String adminNodeName, String serverName, boolean isExecuteDirection) {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getTargetedRedeployPrimitive with settings:  " + rolloutUpdateSettings + " adminNode: " + adminNodeName + " serverName: " + serverName + " and execDirection: " + isExecuteDirection);
      }

      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("adminMachineName", adminNodeName);
      List applicationPropertiesList = rolloutUpdateSettings.getApplicationPropertyList();
      if (applicationPropertiesList != null && applicationPropertiesList.size() > 0) {
         Iterator var8 = applicationPropertiesList.iterator();

         while(var8.hasNext()) {
            ApplicationProperties applicationProperties = (ApplicationProperties)var8.next();
            String applicationName = applicationProperties.getApplicationName();
            if (applicationProperties.isServerTargeted(serverName)) {
               if (PatchingDebugLogger.isDebugEnabled() && isExecuteDirection) {
                  PatchingDebugLogger.debug("Adding TargetedRedeployOnExecCommand for app: " + applicationName);
               }

               if (PatchingDebugLogger.isDebugEnabled() && !isExecuteDirection) {
                  PatchingDebugLogger.debug("Adding TargetedRedeployOnRevertCommand for app: " + applicationName);
               }

               WorkflowBuilder appBuilder = WorkflowBuilder.newInstance();
               Map appMap = new HashMap();
               appMap.put("applicationName", applicationName);
               appMap.put("deploymentPlan", applicationProperties.getPlanLocation());
               appMap.put("removePlanOverride", applicationProperties.getRemovePlanOverride());
               if (isExecuteDirection) {
                  appBuilder.add(TargetedRedeployOnExecCommand.class, appMap);
               } else {
                  appBuilder.add(TargetedRedeployOnRevertCommand.class, appMap);
               }

               builder.add(appBuilder);
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Skipping TargetedRedeploy for not staged app: " + applicationName + " on server: " + serverName);
            }
         }
      }

      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getUpdateNodeDirectoryPrimitive(Node node, RolloutContext rolloutContext) throws ManagementException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String machineName = node.getNodeName();
      String serverNames = this.getCommaSeparatedServerNames(node);
      boolean isAdminServer = node.isAdmin();
      RolloutUpdateSettings rolloutUpdateSettings = rolloutContext.getRolloutUpdateSettings();
      Node adminNode = null;
      if (isAdminServer) {
         adminNode = node;
      } else {
         adminNode = node.getDomainModel().getAdminNode();
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getUpdateNodeDirectoryPrimitive called with params: machineName " + machineName + ", serverNames " + serverNames + ", isAdminServer " + isAdminServer + ", newDirectory " + rolloutUpdateSettings.getPatchedOracleHome() + ", backupDirectory " + rolloutUpdateSettings.getBackupOracleHome() + ", isDryRun " + rolloutUpdateSettings.isDryRun();
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      this.updateContextForUpdatingNode(node, rolloutContext);
      builder.add(this.ensureClustersInitialized(rolloutContext));
      Map map = new HashMap();
      int sessionTimeout = rolloutUpdateSettings.getShutdownTimeoutSeconds();
      boolean ignoreSessions = false;
      map.put("lastServerState", new HashMap());
      String[] serverNameArray = null;
      if (serverNames != null && !serverNames.isEmpty()) {
         serverNameArray = serverNames.split(",");
         Arrays.sort(serverNameArray);
      }

      map.put("originalMachineName", new HashMap());
      map.put("isTargetedRedeployDone", new HashMap());
      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      Map migrationInfo = rolloutContext.getMigrationInfo();
      boolean isExecuteDirection;
      if (iterator.skipToNode(node.getNodeName()) != null) {
         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               String serverName = server.getServerName();
               if (server.isAdmin()) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateNodeDirectoryBuilder skipping ShutdownServer primitive for admin server");
                  }
               } else {
                  ClusterSessionContext sessionContext = rolloutContext.getSessionInfoForServer(serverName);
                  if (sessionContext == null) {
                     StringBuffer errorServerNames = new StringBuffer();
                     Iterator var34 = rolloutContext.serverSessionInfoMap.keySet().iterator();

                     while(var34.hasNext()) {
                        String tmpServerName = (String)var34.next();
                        errorServerNames.append(tmpServerName + ",");
                     }

                     throw new IllegalArgumentException("null sessionContext for " + serverName + " map contains: " + errorServerNames.toString());
                  }

                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateNodeDirectoryBuilder adding ShutdownServer for server " + serverName);
                  }

                  boolean isExecuteDirection = false;
                  MigrationInfo migInfo = (MigrationInfo)migrationInfo.get(serverName);
                  isExecuteDirection = false;
                  if (migInfo != null) {
                     isExecuteDirection = migInfo.getWSMInfo() != null;
                     if (migInfo.getJMSInfo() != null) {
                        if (isExecuteDirection) {
                           throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidMigrationInfo(serverName));
                        }

                        builder.add(this.getMigrateJMSPrimitive(serverName, migInfo));
                        if (PatchingDebugLogger.isDebugEnabled()) {
                           PatchingDebugLogger.debug("Checking remainingNodes: " + rolloutContext.getBuildTimeRemainingNodes() + " for machine: " + this.lookupMachineForServer(node.getDomainModel(), migInfo.getJMSInfo().getDestination()));
                        }

                        if (rolloutContext.getBuildTimeRemainingNodes().contains(this.lookupMachineForServer(node.getDomainModel(), migInfo.getJMSInfo().getDestination()))) {
                           migInfo.setJmsFailbackAfterMigrationToUnpatched(true);
                        }
                     }
                  }

                  if (isExecuteDirection) {
                     if (migInfo != null && migInfo.getJTAInfo() != null) {
                        throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidMigrationInfo(serverName));
                     }

                     builder.add(this.getServerMigrationPrimitive(node, rolloutContext, migInfo, server));
                  } else {
                     if (rolloutUpdateSettings.getCoherenceServiceStatusHATarget() != null) {
                        builder.add(this.getWaitForCoherenceServiceHAStatusPrimitive(serverName, rolloutUpdateSettings.getCoherenceServiceStatusHATarget(), rolloutUpdateSettings.getCoherenceServiceStatusHAWaitTimeout(), false));
                     }

                     builder.add(this.getShutdownServerAndResumeOnRevertPrimitive(serverName, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessions()));
                     if (migInfo != null && migInfo.getJTAInfo() != null) {
                        builder.add(this.getMigrateJTAPrimitive(serverName, migInfo));
                        if (PatchingDebugLogger.isDebugEnabled()) {
                           PatchingDebugLogger.debug("Checking remainingNodes: " + rolloutContext.getBuildTimeRemainingNodes() + " for machine: " + this.lookupMachineForServer(node.getDomainModel(), migInfo.getJTAInfo().getDestination()));
                        }

                        if (rolloutContext.getBuildTimeRemainingNodes().contains(this.lookupMachineForServer(node.getDomainModel(), migInfo.getJTAInfo().getDestination()))) {
                           migInfo.setJtaFailbackAfterMigrationToUnpatched(true);
                           JTAInfo jtaInfo = migInfo.getJTAInfo();
                           MigrationInfo failbackInfo = (MigrationInfo)migrationInfo.get(jtaInfo.getDestination());
                           if (failbackInfo == null) {
                              failbackInfo = new MigrationInfo();
                           }

                           if (failbackInfo.getWSMInfo() != null) {
                              throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidJTAMigration(serverName, jtaInfo.getDestination()));
                           }

                           JTAInfo failbackJTAInfo = new JTAInfo();
                           failbackJTAInfo.setDestination(serverName);
                           failbackJTAInfo.setUPS(jtaInfo.getUPS());
                           failbackInfo.setJTAInfo(failbackJTAInfo);
                           migrationInfo.put(jtaInfo.getDestination(), failbackInfo);
                        }
                     }

                     builder.add(this.getServerSessionHandlingPrimitive(serverName, sessionContext, isExecuteDirection));
                     builder.add(this.getTargetedRedeployPrimitive(rolloutUpdateSettings, adminNode.getNodeName(), serverName, isExecuteDirection));
                     builder.add(this.getStartServerInAdminModePrimitive(machineName, serverName, isExecuteDirection, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessionsOnRevert()));
                  }
               }
            }
         }
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateNodeDirectoryBuilder adding SwitchOracleHomeDirectory for machine " + machineName);
      }

      if (rolloutUpdateSettings.isUpdateApplications()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Detected application updates, adding ApplicationUpdatePrimitive");
         }

         builder.add(this.getApplicationUpdatePrimitive(rolloutUpdateSettings, node, adminNode));
      }

      long timeoutMillis = 0L;
      if (!rolloutUpdateSettings.isUpdateOracleHome() && !rolloutUpdateSettings.isUpdateJavaHome()) {
         if (rolloutUpdateSettings.isRollingRestart()) {
            boolean isRollingRestart = true;
            if (isAdminServer && !node.getAdminServer().getNmRelation().equals(NodeManagerRelation.REGISTERED)) {
               if (node.getAdminServer().getNmRelation().equals(NodeManagerRelation.COLLOCATED)) {
                  builder.add(this.getRestartCollocatedNodeManager(machineName, isAdminServer, timeoutMillis));
               }

               builder.add(this.getRestartAdminWoNodeManager());
            } else {
               builder.add(this.getRestartAdminAndNodeManager(machineName, isAdminServer, timeoutMillis, isRollingRestart));
            }
         }
      } else {
         builder.add(this.getUpdateOracleHomeDirectoryBuilder(rolloutUpdateSettings, machineName, isAdminServer, timeoutMillis, node));
      }

      if (isAdminServer && rolloutUpdateSettings.hasExtensions()) {
         builder.add(this.getOnlineAfterServerStartExtensionPrimitives(rolloutUpdateSettings, node, adminNode.getAdminServer()));
      }

      Iterator var28 = rolloutContext.getTargetedClusters().iterator();

      while(var28.hasNext()) {
         String clusterName = (String)var28.next();
         builder.add(this.getClusterUpdateFailoverGroupsPrimitive(rolloutContext.getSessionInfoForCluster(clusterName)));
      }

      iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(node.getNodeName()) != null) {
         label195:
         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(true) {
               while(true) {
                  if (!iterator.hasNextServer()) {
                     continue label195;
                  }

                  Server server = iterator.nextServer();
                  String serverName = server.getServerName();
                  if (server.isAdmin()) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateNodeDirectoryBuilder skipping StartServer primitive for admin server");
                     }
                  } else {
                     ClusterSessionContext sessionContext = rolloutContext.getSessionInfoForServer(serverName);
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateNodeDirectoryBuilder adding StartServer for server " + serverName);
                     }

                     isExecuteDirection = true;
                     MigrationInfo migInfo = (MigrationInfo)migrationInfo.get(serverName);
                     boolean wsm = migInfo != null && migInfo.getWSMInfo() != null;
                     if (!wsm) {
                        builder.add(this.getStartServerInAdminModePrimitive(machineName, serverName, isExecuteDirection, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessionsOnRevert()));
                        builder.add(this.getServerSessionHandlingPrimitive(serverName, sessionContext, isExecuteDirection));
                        builder.add(this.getTargetedRedeployPrimitive(rolloutUpdateSettings, adminNode.getNodeName(), serverName, isExecuteDirection));
                        if (rolloutUpdateSettings.hasExtensions()) {
                           builder.add(this.getOnlineAfterServerStartExtensionPrimitives(rolloutUpdateSettings, node, server));
                        }

                        builder.add(this.getResumeServerAndShutdownOnRevertPrimitive(serverName, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessionsOnRevert()));
                        if (rolloutUpdateSettings.getCoherenceServiceStatusHATarget() != null) {
                           builder.add(this.getWaitForCoherenceServiceHAStatusPrimitive(serverName, rolloutUpdateSettings.getCoherenceServiceStatusHATarget(), rolloutUpdateSettings.getCoherenceServiceStatusHAWaitTimeout(), true));
                        }

                        if (migInfo != null && migInfo.getJMSInfo() != null && (migInfo.getJMSInfo().getFailback() || migInfo.isJmsFailbackAfterMigrationToUnpatched())) {
                           builder.add(this.getMigrateJMSFailbackPrimitive(serverName, migInfo));
                        }
                     } else if (migInfo.isWsmFailbackAfterMigrationToUnpatched() || migInfo.isWSMInfoFailback()) {
                        builder.add(this.getServerMigrationFailbackPrimitive(node, rolloutContext, migInfo, server));
                     }
                  }
               }
            }
         }
      }

      builder.add(this.getUpdateNodeListPrimitive(node));
      this.updateContextForFinishedNode(node, rolloutContext);
      builder.add(this.resetFinishedClusters(rolloutContext));
      builder.add(map);
      return builder;
   }

   private String lookupMachineForServer(DomainModel domainModel, String serverName) {
      Iterator var3 = domainModel.getNodes().values().iterator();

      while(var3.hasNext()) {
         Node node = (Node)var3.next();
         Iterator var5 = node.getServerGroups().values().iterator();

         while(var5.hasNext()) {
            ServerGroup serverGroup = (ServerGroup)var5.next();
            if (serverGroup.hasServer(serverName)) {
               return serverGroup.getServer(serverName).getServerInfo().getMachineName();
            }
         }
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Could not find a server with name: " + serverName);
      }

      return null;
   }

   protected WorkflowBuilder getUpdateOracleHomeDirectoryBuilder(RolloutUpdateSettings rolloutUpdateSettings, String machineName, Boolean isAdminServer, long timeoutMillis, Node node) throws ManagementException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getUpdateOracleHomeDirectoryBuilder called with params: newDirectory " + rolloutUpdateSettings.getPatchedOracleHome() + ", backupDirectory " + rolloutUpdateSettings.getBackupOracleHome() + ", newJavaHome " + rolloutUpdateSettings.getJavaHome() + ", machineName " + machineName + ", isAdminServer " + isAdminServer + ", timeoutMillis " + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      if (!rolloutUpdateSettings.isUpdateOracleHome() && !rolloutUpdateSettings.isUpdateJavaHome()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateOracleHomeDirectoryBuilder returning empty builder because neither OracleHome nor JavaHome is being updated");
         }

         return builder;
      } else {
         this.assertString("machineName", machineName);
         Map map = new HashMap();
         map.put("newDirectory", rolloutUpdateSettings.getPatchedOracleHome());
         map.put("backupDirectory", rolloutUpdateSettings.getBackupOracleHome());
         map.put("machineName", machineName);
         map.put("timeoutMillis", timeoutMillis);
         map.put("scriptEnvProps", new Properties());
         map.put("directorySwitchPerformed", new MutableBoolean(false));
         String serverActivationTime = "" + ServerUtils.getServerActivationTime();
         map.put("serverActivationTime", new MutableString(serverActivationTime));
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateOracleHomeDirectoryBuilder calling getPrepareUpdateOracleHomeDirectoryPrimitive");
         }

         builder.add(this.getPrepareUpdateOracleHomeDirectoryPrimitive(rolloutUpdateSettings, machineName, isAdminServer, timeoutMillis, node));
         if (isAdminServer && !node.getAdminServer().getNmRelation().equals(NodeManagerRelation.REGISTERED)) {
            if (node.getAdminServer().getNmRelation().equals(NodeManagerRelation.COLLOCATED)) {
               builder.add(this.getVerifyNodeManagerRestartOnRevertPrimitive(machineName, isAdminServer, timeoutMillis));
               builder.add(this.getCreateFileLockPrimitive(machineName, rolloutUpdateSettings.isUpdateJavaHome()));
               builder.add(this.getRestartNodeManagerAsyncOnExecPrimitive(machineName, isAdminServer, timeoutMillis));
            }

            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateOracleHomeDirectoryBuilder calling getRestartAdminServerPrimitive");
            }

            builder.add(this.getRestartAdminServerPrimitive());
            if (node.getAdminServer().getNmRelation().equals(NodeManagerRelation.COLLOCATED)) {
               builder.add(this.getRestartNodeManagerAsyncOnRevertPrimitive(machineName, isAdminServer, timeoutMillis));
               builder.add(this.getRemoveFileLockPrimitive(machineName, rolloutUpdateSettings.isUpdateJavaHome()));
               builder.add(this.getVerifyNodeManagerRestartOnExecPrimitive(machineName, isAdminServer, timeoutMillis));
            }
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateOracleHomeDirectoryBuilder calling getRestartNodeManagerPrimitive");
            }

            builder.add(this.getRestartNodeManagerPrimitive(machineName, isAdminServer, timeoutMillis));
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getUpdateOracleHomeDirectoryBuilder calling getAssertUpdateOracleHomeDirectoryPrimitive");
         }

         builder.add(this.getAssertUpdateOracleHomeDirectoryPrimitive(rolloutUpdateSettings, machineName, isAdminServer, timeoutMillis, node));
         builder.add(map);
         return builder;
      }
   }

   protected WorkflowBuilder getRestartCollocatedNodeManager(String machineName, Boolean isAdminServer, long timeoutMillis) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getRestartCollocatedNodeManager called with params: machineName " + machineName + ", isAdminServer " + isAdminServer + ", timeoutMillis " + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("directorySwitchPerformed", new MutableBoolean(false));
      String serverActivationTime = "" + ServerUtils.getServerActivationTime();
      map.put("serverActivationTime", new MutableString(serverActivationTime));
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getRestartCollocatedNodeManager calling getRestartNodeManagerPrimitive");
      }

      builder.add(this.getRestartNodeManagerPrimitive(machineName, isAdminServer, timeoutMillis));
      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getRestartAdminAndNodeManager(String machineName, Boolean isAdminServer, long timeoutMillis, boolean isRollingRestart) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getRestartAdminAndNodeManager called with params: machineName " + machineName + ", isAdminServer " + isAdminServer + ", timeoutMillis " + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("directorySwitchPerformed", new MutableBoolean(false));
      String serverActivationTime = "" + ServerUtils.getServerActivationTime();
      map.put("serverActivationTime", new MutableString(serverActivationTime));
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getRestartAdminAndNodeManager calling getRestartNodeManagerPrimitive");
      }

      builder.add(this.getRestartNodeManagerPrimitive(machineName, isAdminServer, timeoutMillis, isRollingRestart));
      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getRestartAdminWoNodeManager() {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getRestartAdminWoNodeManager called ";
         PatchingDebugLogger.debug(debugString);
      }

      Map map = new HashMap();
      map.put("directorySwitchPerformed", new MutableBoolean(false));
      String serverActivationTime = "" + ServerUtils.getServerActivationTime();
      map.put("serverActivationTime", new MutableString(serverActivationTime));
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutUpdatePrimitiveFactory.getRestartAdminWoNodeManager calling getRestartAdminServerPrimitive");
      }

      builder.add(this.getRestartAdminServerPrimitive());
      builder.add(map);
      return builder;
   }

   protected WorkflowBuilder getVerifyNodeManagerRestartOnExecPrimitive(String machineName, boolean isAdminServer, long timeoutMillis) {
      return this.getVerifyNodeManagerRestartPrimitive(machineName, isAdminServer, timeoutMillis, true);
   }

   protected WorkflowBuilder getVerifyNodeManagerRestartOnRevertPrimitive(String machineName, boolean isAdminServer, long timeoutMillis) {
      return this.getVerifyNodeManagerRestartPrimitive(machineName, isAdminServer, timeoutMillis, false);
   }

   protected WorkflowBuilder getVerifyNodeManagerRestartPrimitive(String machineName, boolean isAdminServer, long timeoutMillis, boolean isExecuteDirection) {
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getVerifyNodeManagerRestartPrimitive called with params: machineName " + machineName + ", isAdminServer " + isAdminServer + ", timeoutMillis" + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("isAdminServer", isAdminServer);
      map.put("timeoutMillis", timeoutMillis);
      if (isExecuteDirection) {
         builder.add(VerifyNodeManagerRestartOnExecCommand.class, map);
      } else {
         builder.add(VerifyNodeManagerRestartOnRevertCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getRestartNodeManagerAsyncOnExecPrimitive(String machineName, boolean isAdminServer, long timeoutMillis) {
      return this.getRestartNodeManagerAsyncPrimitive(machineName, isAdminServer, timeoutMillis, true);
   }

   protected WorkflowBuilder getRestartNodeManagerAsyncOnRevertPrimitive(String machineName, boolean isAdminServer, long timeoutMillis) {
      return this.getRestartNodeManagerAsyncPrimitive(machineName, isAdminServer, timeoutMillis, false);
   }

   protected WorkflowBuilder getRestartNodeManagerAsyncPrimitive(String machineName, boolean isAdminServer, long timeoutMillis, boolean isExecuteDirection) {
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getRestartNodeManagerAsyncPrimitive called with params: machineName " + machineName + ", isAdminServer " + isAdminServer + ", timeoutMillis" + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("machineName", machineName);
      map.put("isAdminServer", isAdminServer);
      map.put("timeoutMillis", timeoutMillis);
      if (isExecuteDirection) {
         builder.add(RestartNodeManagerAsyncOnExecCommand.class, map);
      } else {
         builder.add(RestartNodeManagerAsyncOnRevertCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getPrepareUpdateOracleHomeDirectoryPrimitive(RolloutUpdateSettings rolloutUpdateSettings, String machineName, Boolean isAdminServer, long timeoutMillis, Node node) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getPrepareUpdateOracleHomeDirectoryPrimitive called with params: newDirectory" + rolloutUpdateSettings.getPatchedOracleHome() + ", backupDirectory " + rolloutUpdateSettings.getBackupOracleHome() + ", newJavaHome " + rolloutUpdateSettings.getJavaHome() + ", machineName " + machineName + ", isAdminServer " + isAdminServer + ", timeoutMillis " + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("newDirectory", rolloutUpdateSettings.getPatchedOracleHome() == null ? "" : rolloutUpdateSettings.getPatchedOracleHome());
      map.put("backupDirectory", rolloutUpdateSettings.getBackupOracleHome() == null ? "" : rolloutUpdateSettings.getBackupOracleHome());
      map.put("newJavaHome", rolloutUpdateSettings.getJavaHome() == null ? "" : rolloutUpdateSettings.getJavaHome());
      map.put("machineName", machineName);
      map.put("isAdminServer", isAdminServer);
      map.put("timeoutMillis", timeoutMillis);
      map.put("scriptEnvProps", new Properties());
      if (rolloutUpdateSettings.hasExtensions()) {
         String offlineBeforeUpdateExtensionScripts = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionScriptsForOfflineBeforeUpdateExtension());
         String offlineAfterUpdateExtensionScripts = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionScriptsForOfflineAfterUpdateExtension());
         map.put("beforeUpdateExtensions", offlineBeforeUpdateExtensionScripts);
         map.put("afterUpdateExtensions", offlineAfterUpdateExtensionScripts);
      } else {
         map.put("beforeUpdateExtensions", "");
         map.put("afterUpdateExtensions", "");
      }

      if (isAdminServer && !node.getAdminServer().getNmRelation().equals(NodeManagerRelation.REGISTERED)) {
         map.put("useNM", Boolean.FALSE);
      } else {
         map.put("useNM", Boolean.TRUE);
      }

      builder.add(PrepareUpdateOracleHomeDirectoryCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getAssertUpdateOracleHomeDirectoryPrimitive(RolloutUpdateSettings rolloutUpdateSettings, String machineName, Boolean isAdminServer, long timeoutMillis, Node node) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getAssertUpdateOracleHomeDirectoryPrimitive called with params: newDirectory" + rolloutUpdateSettings.getPatchedOracleHome() + ", backupDirectory " + rolloutUpdateSettings.getBackupOracleHome() + ", isAdminServer " + isAdminServer + ", newJavaHome " + rolloutUpdateSettings.getJavaHome() + ", machineName " + machineName + ", timeoutMillis " + timeoutMillis;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      Map map = new HashMap();
      map.put("newDirectory", rolloutUpdateSettings.getPatchedOracleHome() == null ? "" : rolloutUpdateSettings.getPatchedOracleHome());
      map.put("backupDirectory", rolloutUpdateSettings.getBackupOracleHome() == null ? "" : rolloutUpdateSettings.getBackupOracleHome());
      map.put("newJavaHome", rolloutUpdateSettings.getJavaHome() == null ? "" : rolloutUpdateSettings.getJavaHome());
      map.put("machineName", machineName);
      map.put("isAdminServer", isAdminServer);
      map.put("timeoutMillis", timeoutMillis);
      map.put("scriptEnvProps", new Properties());
      if (rolloutUpdateSettings.hasExtensions()) {
         String offlineBeforeUpdateExtensionScripts = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionScriptsForOfflineBeforeUpdateExtension());
         String offlineAfterUpdateExtensionScripts = String.join(",", rolloutUpdateSettings.getExtensionManager().getExtensionScriptsForOfflineAfterUpdateExtension());
         map.put("beforeUpdateExtensions", offlineBeforeUpdateExtensionScripts);
         map.put("afterUpdateExtensions", offlineAfterUpdateExtensionScripts);
      } else {
         map.put("beforeUpdateExtensions", "");
         map.put("afterUpdateExtensions", "");
      }

      if (isAdminServer && !node.getAdminServer().getNmRelation().equals(NodeManagerRelation.REGISTERED)) {
         map.put("useNM", Boolean.FALSE);
      } else {
         map.put("useNM", Boolean.TRUE);
      }

      builder.add(AssertUpdateOracleHomeDirectoryCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getResumeServerOnExecPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessionsOnRevert) {
      return this.getResumeServerPrimitive(serverName, sessionTimeout, ignoreSessions, waitForAllSessionsOnRevert, true);
   }

   protected WorkflowBuilder getResumeServerOnRevertPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessionsOnRevert) {
      return this.getResumeServerPrimitive(serverName, sessionTimeout, ignoreSessions, waitForAllSessionsOnRevert, false);
   }

   protected WorkflowBuilder getResumeServerPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessionsOnRevert, boolean isExecuteDirection) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getStartServerPrimitive called with params: serverName " + serverName + ", sessionTimeout " + sessionTimeout + ", ignoreSessions " + ignoreSessions + ", waitForAllSessionsOnRevert " + waitForAllSessionsOnRevert;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("sessionTimeout", sessionTimeout);
      map.put("ignoreSessions", ignoreSessions);
      map.put("waitForAllSessions", waitForAllSessionsOnRevert);
      map.put("readyCheckAppsTimeoutInMin", 180);
      if (isExecuteDirection) {
         builder.add(ResumeServerOnExecCommand.class, map);
      } else {
         builder.add(ResumeServerOnRevertCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getServerSessionHandlingOnExecPrimitive(String serverName, ClusterSessionContext sessionContext) {
      return this.getServerSessionHandlingPrimitive(serverName, sessionContext, true);
   }

   protected WorkflowBuilder getServerSessionHandlingOnRevertPrimitive(String serverName, ClusterSessionContext sessionContext) {
      return this.getServerSessionHandlingPrimitive(serverName, sessionContext, false);
   }

   protected WorkflowBuilder getServerSessionHandlingPrimitive(String serverName, ClusterSessionContext sessionContext, boolean isExecuteDirection) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getServerSessionHandlingPrimitive called with serverName: " + serverName + " and ClusterSessionHandlingContext: " + sessionContext.toString();
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("failoverGroups", sessionContext.getFailoverGroups());
      map.put("origFailoverGroups", sessionContext.getOrigFailoverGroups());
      if (isExecuteDirection) {
         builder.add(ServerSessionHandlingOnExecCommand.class, map);
      } else {
         builder.add(ServerSessionHandlingOnRevertCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getStartServerInAdminModeOnExecPrimitive(String currentMachine, String serverName, int sessionTimeout, boolean waitForAllSessionsOnRevert) {
      return this.getStartServerInAdminModePrimitive(currentMachine, serverName, true, sessionTimeout, waitForAllSessionsOnRevert);
   }

   protected WorkflowBuilder getStartServerInAdminModeOnRevertPrimitive(String currentMachine, String serverName, int sessionTimeout, boolean waitForAllSessionsOnRevert) {
      return this.getStartServerInAdminModePrimitive(currentMachine, serverName, false, sessionTimeout, waitForAllSessionsOnRevert);
   }

   protected WorkflowBuilder getStartServerInAdminModePrimitive(String currentMachine, String serverName, boolean isExecuteDirection, int sessionTimeout, boolean waitForAllSessionsOnRevert) {
      boolean ignoreSessions = false;
      return this.getStartServerInAdminModePrimitive(currentMachine, serverName, isExecuteDirection, sessionTimeout, ignoreSessions, waitForAllSessionsOnRevert);
   }

   private WorkflowBuilder getStartServerInAdminModePrimitive(String currentMachine, String serverName, boolean isExecuteDirection, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessionsOnRevert) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getStartServerInAdminModePrimitive called with serverName: " + serverName;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("currentMachine", currentMachine);
      map.put("sessionTimeout", sessionTimeout);
      map.put("ignoreSessions", ignoreSessions);
      map.put("waitForAllSessions", waitForAllSessionsOnRevert);
      map.put("readyCheckAppsTimeoutInMin", 180);
      if (isExecuteDirection) {
         builder.add(StartServerAdminModeOnExecCommand.class, map);
      } else {
         builder.add(StartServerAdminModeOnRevertCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getShutdownServerAndResumeOnRevertPrimitive(String serverName, int sessionTimeout, boolean waitForAllSessions) {
      boolean ignoreSessions = false;
      return this.getShutdownServerAndResumeOnRevertPrimitive(serverName, sessionTimeout, ignoreSessions, waitForAllSessions);
   }

   protected WorkflowBuilder getWaitForCoherenceServiceHAStatusPrimitive(String serverName, String coherenceHAStatusTarget, int coherenceHAStatusWaitTimeout, boolean isRevert) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.WaitForCoherenceServicesHAStatusCommand called with params: serverName " + serverName + "coherenceHAStatusTarget " + coherenceHAStatusTarget + "coherenceHAStatusWaitTimeout " + coherenceHAStatusWaitTimeout;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("haStatusTarget", coherenceHAStatusTarget);
      map.put("haStatusWaitTimeout", coherenceHAStatusWaitTimeout);
      map.put("startTime", new MutableString());
      if (isRevert) {
         builder.add(WaitForCoherenceServicesHAStatusRevertCommand.class, map);
      } else {
         builder.add(WaitForCoherenceServicesHAStatusExecCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getShutdownServerAndResumeOnRevertPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessions) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getShutDownServerAndResumeOnRevertPrimitive called with params: serverName " + serverName + ", sessionTimeout " + sessionTimeout + ", ignoreSessions " + ignoreSessions + ", waitForAllSessions" + waitForAllSessions;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("sessionTimeout", sessionTimeout);
      map.put("ignoreSessions", ignoreSessions);
      map.put("waitForAllSessions", waitForAllSessions);
      map.put("readyCheckAppsTimeoutInMin", 180);
      builder.add(ShutdownServerResumeOnRevertCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getMigrateJMSPrimitive(String serverName, MigrationInfo migInfo) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String destination;
      if (PatchingDebugLogger.isDebugEnabled()) {
         destination = "RolloutUpdatePrimitiveFactory.getMigrateJMSPrimitive called with params: migrationInfo " + migInfo.toString();
         PatchingDebugLogger.debug(destination);
      }

      destination = migInfo.getJMSInfo().getDestination();
      if (destination != null && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Destination for all services running on " + serverName + " is " + destination);
      }

      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("jmsInfo", migInfo.getJMSInfo());
      map.put("destination", destination);
      builder.add(MigrateJMSCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getMigrateJMSFailbackPrimitive(String serverName, MigrationInfo migInfo) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String destination;
      if (PatchingDebugLogger.isDebugEnabled()) {
         destination = "RolloutUpdatePrimitiveFactory.getMigrateJMSFailbackPrimitive called with params: serverName " + serverName + ", migrationInfo " + migInfo;
         PatchingDebugLogger.debug(destination);
      }

      destination = migInfo.getJMSInfo().getDestination();
      if (destination != null && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Destination for all services running on " + serverName + " is " + destination);
      }

      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("jmsInfo", migInfo.getJMSInfo());
      map.put("destination", destination);
      builder.add(MigrateJMSFailbackCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getMigrateJTAPrimitive(String serverName, MigrationInfo migInfo) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String destination;
      if (PatchingDebugLogger.isDebugEnabled()) {
         destination = "RolloutUpdatePrimitiveFactory.getMigrateJTAPrimitive called with params: migrationInfo " + migInfo.toString();
         PatchingDebugLogger.debug(destination);
      }

      destination = migInfo.getJTAInfo().getDestination();
      if (destination != null && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Destination for JTS running on " + serverName + " is " + destination);
      }

      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("destination", destination);
      builder.add(MigrateJTACommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getWSMToAdminModePrimitive(String serverName, MigrationInfo migInfo) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String machine;
      if (PatchingDebugLogger.isDebugEnabled()) {
         machine = "RolloutUpdatePrimitiveFactory.getWSMToAdminModePrimitive called with params: migrationInfo " + migInfo.toString();
         PatchingDebugLogger.debug(machine);
      }

      machine = migInfo.getWSMInfoDestinationMachine();
      if (machine != null && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Destination machine for all services running on " + serverName + " is " + machine);
      }

      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("destination", machine);
      builder.add(WSMToAdminModeCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getServerMigrationPrimitive(Node node, RolloutContext rolloutContext, MigrationInfo migInfo, Server server) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getServerMigrationPrimitive called with params: migrationInfo " + migInfo.toString();
         PatchingDebugLogger.debug(debugString);
      }

      RolloutUpdateSettings rolloutUpdateSettings = rolloutContext.getRolloutUpdateSettings();
      String serverName = server.getServerName();
      ClusterSessionContext sessionContext = rolloutContext.getSessionInfoForServer(serverName);
      Node adminNode = null;
      if (node.isAdmin()) {
         adminNode = node;
      } else {
         adminNode = node.getDomainModel().getAdminNode();
      }

      int sessionTimeout = rolloutUpdateSettings.getShutdownTimeoutSeconds();
      boolean ignoreSessions = false;
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Checking remainingNodes: " + rolloutContext.getBuildTimeRemainingNodes() + " with size: " + rolloutContext.getBuildTimeRemainingNodes() + " for machine: " + migInfo.getWSMInfoDestinationMachine());
      }

      boolean migrateToUnpatched = rolloutContext.getBuildTimeRemainingNodes().contains(migInfo.getWSMInfoDestinationMachine());
      if (rolloutUpdateSettings.isUpdateApplications() && !migrateToUnpatched) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Detected application updates where we will migrate to, adding ExternalStagedApplicationUpdatePrimitive");
         }

         List applicationPropertiesList = rolloutUpdateSettings.getApplicationPropertyList();
         Node destinationNode = null;
         if (applicationPropertiesList != null && applicationPropertiesList.size() > 0) {
            Iterator var15 = applicationPropertiesList.iterator();

            while(var15.hasNext()) {
               ApplicationProperties applicationProperties = (ApplicationProperties)var15.next();
               if (applicationProperties.isExternalStaged()) {
                  if (destinationNode == null) {
                     destinationNode = this.lookupNodeForDestination(node.getDomainModel(), migInfo.getWSMInfoDestinationMachine());
                  }

                  builder.add(this.getExternalStageApplicationUpdateForServerOnNodePrimitive(applicationProperties, destinationNode, server, rolloutUpdateSettings));
               }
            }
         }
      }

      builder.add(this.getResumeServerOnRevertPrimitive(serverName, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessionsOnRevert()));
      builder.add(this.getServerSessionHandlingOnRevertPrimitive(serverName, sessionContext));
      builder.add(this.getTargetedRedeployOnRevertPrimitive(rolloutUpdateSettings, adminNode.getNodeName(), serverName));
      builder.add(this.getClusterUpdateFailoverGroupsOnRevertPrimitive(rolloutContext.getSessionInfoForCluster(sessionContext.getClusterName())));
      if (migrateToUnpatched) {
         migInfo.setWsmFailbackAfterMigrationToUnpatched(true);
      } else {
         this.updateContextForPatchedServer(rolloutContext, server);
      }

      builder.add(this.getWSMToAdminModePrimitive(serverName, migInfo));
      if (!migrateToUnpatched) {
         this.updateContextForFinishedPatchedServer(rolloutContext, server);
      }

      builder.add(this.getClusterUpdateFailoverGroupsOnExecPrimitive(rolloutContext.getSessionInfoForCluster(sessionContext.getClusterName())));
      builder.add(this.getServerSessionHandlingOnExecPrimitive(serverName, sessionContext));
      builder.add(this.getTargetedRedeployOnExecPrimitive(rolloutUpdateSettings, adminNode.getNodeName(), serverName));
      builder.add(this.getResumeServerOnExecPrimitive(serverName, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessionsOnRevert()));
      return builder;
   }

   protected WorkflowBuilder getServerMigrationFailbackPrimitive(Node node, RolloutContext rolloutContext, MigrationInfo migInfo, Server server) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getServerMigrationPrimitive called with params: migrationInfo " + migInfo.toString();
         PatchingDebugLogger.debug(debugString);
      }

      RolloutUpdateSettings rolloutUpdateSettings = rolloutContext.getRolloutUpdateSettings();
      String serverName = server.getServerName();
      ClusterSessionContext sessionContext = rolloutContext.getSessionInfoForServer(serverName);
      Node adminNode = null;
      if (node.isAdmin()) {
         adminNode = node;
      } else {
         adminNode = node.getDomainModel().getAdminNode();
      }

      int sessionTimeout = rolloutUpdateSettings.getShutdownTimeoutSeconds();
      boolean ignoreSessions = false;
      boolean migrateToUnpatched = rolloutContext.getBuildTimeRemainingNodes().contains(migInfo.getWSMInfoDestinationMachine());
      if (rolloutUpdateSettings.isUpdateApplications()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Detected application updates where we will migrate to, adding ExternalStagedApplicationUpdatePrimitive");
         }

         List applicationPropertiesList = rolloutUpdateSettings.getApplicationPropertyList();
         if (applicationPropertiesList != null && applicationPropertiesList.size() > 0) {
            Iterator var14 = applicationPropertiesList.iterator();

            while(var14.hasNext()) {
               ApplicationProperties applicationProperties = (ApplicationProperties)var14.next();
               if (applicationProperties.isExternalStaged()) {
                  builder.add(this.getExternalStageApplicationUpdateForServerOnNodePrimitive(applicationProperties, node, server, rolloutUpdateSettings));
               }
            }
         }
      }

      builder.add(this.getResumeServerOnRevertPrimitive(serverName, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessionsOnRevert()));
      builder.add(this.getServerSessionHandlingOnRevertPrimitive(serverName, sessionContext));
      builder.add(this.getTargetedRedeployOnRevertPrimitive(rolloutUpdateSettings, adminNode.getNodeName(), serverName));
      builder.add(this.getClusterUpdateFailoverGroupsOnRevertPrimitive(rolloutContext.getSessionInfoForCluster(sessionContext.getClusterName())));
      if (migrateToUnpatched) {
         this.updateContextForPatchedServer(rolloutContext, server);
      }

      builder.add(this.getWSMToAdminModeFailbackPrimitive(serverName, migInfo));
      if (migrateToUnpatched) {
         this.updateContextForFinishedPatchedServer(rolloutContext, server);
      }

      builder.add(this.getClusterUpdateFailoverGroupsOnExecPrimitive(rolloutContext.getSessionInfoForCluster(sessionContext.getClusterName())));
      builder.add(this.getServerSessionHandlingOnExecPrimitive(serverName, sessionContext));
      builder.add(this.getTargetedRedeployOnExecPrimitive(rolloutUpdateSettings, adminNode.getNodeName(), serverName));
      builder.add(this.getResumeServerOnExecPrimitive(serverName, sessionTimeout, ignoreSessions, sessionContext.getWaitForAllSessionsOnRevert()));
      return builder;
   }

   private Node lookupNodeForDestination(DomainModel model, String destinationMachineName) {
      Iterator var3 = model.getNodes().values().iterator();

      Node node;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         node = (Node)var3.next();
      } while(!node.getMachineInfo().getMachineName().equalsIgnoreCase(destinationMachineName));

      return node;
   }

   protected WorkflowBuilder getWSMToAdminModeFailbackPrimitive(String serverName, MigrationInfo migInfo) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String machine;
      if (PatchingDebugLogger.isDebugEnabled()) {
         machine = "RolloutUpdatePrimitiveFactory.getWSMToAdminModeFailbackPrimitive called with params: migrationInfo " + migInfo.toString();
         PatchingDebugLogger.debug(machine);
      }

      machine = migInfo.getWSMInfoDestinationMachine();
      if (machine != null && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Current machine for all services running on " + serverName + " is " + machine);
      }

      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("destination", machine);
      builder.add(WSMToAdminModeFailbackCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getResumeServerAndShutdownOnRevertPrimitive(String serverName, int sessionTimeout, boolean waitForAllSessions) {
      return this.getResumeServerAndShutdownOnRevertPrimitive(serverName, sessionTimeout, false, waitForAllSessions);
   }

   protected WorkflowBuilder getResumeServerAndShutdownOnRevertPrimitive(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessions) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getResumeServerAndShutdownOnRevertPrimitive called with params: serverName " + serverName + ", sessionTimeout " + sessionTimeout + ", ignoreSessions " + ignoreSessions + ", waitForAllSessions" + waitForAllSessions;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("sessionTimeout", sessionTimeout);
      map.put("ignoreSessions", ignoreSessions);
      map.put("waitForAllSessions", waitForAllSessions);
      map.put("readyCheckAppsTimeoutInMin", 180);
      builder.add(ResumeServerShutdownOnRevertCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getClusterUpdateFailoverGroupsPrimitive(ClusterSessionContext sessionHandlingContext) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getClusterUpdateFailoverGroupsPrimitive called with clusterInfo: " + sessionHandlingContext.toString();
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("clusterName", sessionHandlingContext.getClusterName());
      Map map = new HashMap();
      map.put("clusterName", sessionHandlingContext.getClusterName());
      map.put("origFailoverGroups", (Object)null);
      map.put("failoverGroups", sessionHandlingContext.getFailoverGroups());
      map.put("origFailoverGroups", sessionHandlingContext.getOrigFailoverGroups());
      builder.add(ClusterUpdateFailoverGroupCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getClusterUpdateFailoverGroupsOnExecPrimitive(ClusterSessionContext sessionHandlingContext) {
      return this.getClusterUpdateFailoverGroupsPrimitive(sessionHandlingContext, true);
   }

   protected WorkflowBuilder getClusterUpdateFailoverGroupsOnRevertPrimitive(ClusterSessionContext sessionHandlingContext) {
      return this.getClusterUpdateFailoverGroupsPrimitive(sessionHandlingContext, false);
   }

   protected WorkflowBuilder getClusterUpdateFailoverGroupsPrimitive(ClusterSessionContext sessionHandlingContext, boolean isExecuteDirection) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getClusterUpdateFailoverGroupsPrimitive called with clusterInfo: " + sessionHandlingContext.toString();
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("clusterName", sessionHandlingContext.getClusterName());
      Map map = new HashMap();
      map.put("clusterName", sessionHandlingContext.getClusterName());
      map.put("origFailoverGroups", (Object)null);
      map.put("failoverGroups", sessionHandlingContext.getFailoverGroups());
      map.put("origFailoverGroups", sessionHandlingContext.getOrigFailoverGroups());
      if (isExecuteDirection) {
         builder.add(ClusterUpdateFailoverGroupOnExecCommand.class, map);
      } else {
         builder.add(ClusterUpdateFailoverGroupOnRevertCommand.class, map);
      }

      return builder;
   }

   protected WorkflowBuilder getUpdateNodeListPrimitive(Node node) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getUpdateNodeListPrimitive called with node: " + node.getNodeName();
         PatchingDebugLogger.debug(debugString);
      }

      Map map = new HashMap();
      map.put("node", node);
      builder.add(UpdateNodeListCommand.class, map);
      return builder;
   }

   static {
      binPatchingDirPath = "bin" + File.separator + "patching";
      cleanupExtensions = new Boolean(System.getProperty("weblogic.management.patching.cleanupExtensions", "true"));
   }
}
