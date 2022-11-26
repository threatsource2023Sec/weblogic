package weblogic.management.patching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.patching.commands.DomainModelIterator;
import weblogic.management.patching.commands.PartitionTargetedRedeployOnExecCommand;
import weblogic.management.patching.commands.PartitionTargetedRedeployOnRevertCommand;
import weblogic.management.patching.commands.ShutdownPartitionCommand;
import weblogic.management.patching.commands.StartPartitionCommand;
import weblogic.management.patching.commands.TargetedRedeployOnExecCommand;
import weblogic.management.patching.commands.TargetedRedeployOnRevertCommand;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.PartitionApps;
import weblogic.management.patching.model.Server;
import weblogic.management.patching.model.ServerGroup;
import weblogic.management.workflow.FailurePlan;
import weblogic.management.workflow.WorkflowBuilder;

public class RolloutUpdateMTPrimitiveFactory extends RolloutUpdatePrimitiveFactory {
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
            PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getRolloutUpdateWorkflow iterating over domain " + domainModel.getDomainName());
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
               PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getRolloutUpdateWorkflow calling getUpdateNodeDirectoryPrimitive for node " + node.getNodeName());
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
               PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getRolloutUpdateWorkflow iterated over " + nodeCount + " nodes, " + nodesRemaining + " nodes remain");
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

   protected WorkflowBuilder getPrerequisitePrimitives(RolloutUpdateSettings rolloutUpdateSettings, DomainModel domainModel) {
      return super.getPrerequisitePrimitives(rolloutUpdateSettings, domainModel);
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
         String debugString = "RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryPrimitive called with params: machineName " + machineName + ", serverNames " + serverNames + ", isAdminServer " + isAdminServer + ", newDirectory " + rolloutUpdateSettings.getPatchedOracleHome() + ", backupDirectory " + rolloutUpdateSettings.getBackupOracleHome() + ", isDryRun " + rolloutUpdateSettings.isDryRun();
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("machineName", machineName);
      this.updateContextForUpdatingNode(node, rolloutContext);
      builder.add(this.ensureClustersInitialized(rolloutContext));
      Map map = new HashMap();
      int sessionTimeout = rolloutUpdateSettings.getShutdownTimeoutSeconds();
      map.put("lastPartitionState", new HashMap());
      map.put("lastServerState", new HashMap());
      map.put("originalMachineName", new HashMap());
      map.put("isTargetedRedeployDone", new HashMap());
      String[] serverNameArray = null;
      if (serverNames != null && !serverNames.isEmpty()) {
         serverNameArray = serverNames.split(",");
         Arrays.sort(serverNameArray);
      }

      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      ServerGroup serverGroup;
      Server server;
      String serverName;
      boolean globalResourceGroupRequiresServerRestart;
      ClusterSessionContext sessionContext;
      List targetedApps;
      String partitionName;
      List targetedApps;
      PartitionApps partitionApps;
      if (iterator.skipToNode(node.getNodeName()) != null) {
         while(iterator.hasNextServerGroup()) {
            serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               server = iterator.nextServer();
               serverName = server.getServerName();
               globalResourceGroupRequiresServerRestart = iterator.hasNextGlobalResourceGroup();
               sessionContext = rolloutContext.getSessionInfoForServer(serverName);
               if (globalResourceGroupRequiresServerRestart) {
                  if (server.isAdmin()) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryBuilder skipping ShutdownServer primitive for admin server");
                     }
                  } else {
                     builder.add(this.getShutdownManagedServerWorkflow(node, server, rolloutContext));
                  }
               }

               if (iterator.hasNextGlobalResourceGroup()) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryBuilder adding TargetedRedeploy onRevert on server " + serverName);
                  }

                  targetedApps = server.getApplicationPropertyList();
                  if (targetedApps != null && !targetedApps.isEmpty()) {
                     builder.add(this.getTargetedRedeployOnRevertPrimitive(targetedApps, adminNode.getNodeName(), serverName));
                  }
               }

               while(iterator.hasNextPartitionApps()) {
                  partitionApps = iterator.nextPartitionApps();
                  partitionName = partitionApps.getPartitionName();
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryBuilder adding ShutdownPartition " + partitionName + " on server " + serverName);
                  }

                  if (!globalResourceGroupRequiresServerRestart) {
                     builder.add(this.getShutdownPartitionPrimitive(partitionName, serverName, sessionTimeout, sessionContext.getWaitForAllSessions()));
                  }

                  targetedApps = partitionApps.getApplicationPropertyList();
                  if (targetedApps != null && !targetedApps.isEmpty()) {
                     builder.add(this.getPartitionTargetedRedeployOnRevertPrimitive(partitionApps.getApplicationPropertyList(), adminNode.getNodeName(), serverName, partitionName));
                  }
               }

               if (globalResourceGroupRequiresServerRestart && !server.isAdmin()) {
                  builder.add(this.getServerSessionHandlingOnRevertPrimitive(serverName, sessionContext));
                  builder.add(this.getStartServerInAdminModeOnRevertPrimitive(machineName, serverName, sessionTimeout, sessionContext.getWaitForAllSessionsOnRevert()));
               }
            }
         }
      }

      if (rolloutUpdateSettings.isUpdateApplications()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Detected aplication updates, adding ApplicationUpdatePrimitive");
         }

         builder.add(this.getApplicationUpdatePrimitive(rolloutUpdateSettings, node, adminNode));
      }

      Iterator var22 = rolloutContext.getTargetedClusters().iterator();

      while(var22.hasNext()) {
         String clusterName = (String)var22.next();
         builder.add(this.getClusterUpdateFailoverGroupsPrimitive(rolloutContext.getSessionInfoForCluster(clusterName)));
      }

      iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(node.getNodeName()) != null) {
         while(iterator.hasNextServerGroup()) {
            serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               server = iterator.nextServer();
               serverName = server.getServerName();
               globalResourceGroupRequiresServerRestart = iterator.hasNextGlobalResourceGroup();
               sessionContext = rolloutContext.getSessionInfoForServer(serverName);
               if (globalResourceGroupRequiresServerRestart) {
                  if (server.isAdmin()) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryBuilder skipping StartServer primitive for admin server");
                     }
                  } else {
                     builder.add(this.getStartupManagedServerWorkflow(node, server, rolloutContext));
                  }
               }

               if (iterator.hasNextGlobalResourceGroup()) {
                  targetedApps = server.getApplicationPropertyList();
                  if (targetedApps != null && !targetedApps.isEmpty()) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryBuilder adding TargetedRedeploy onExec for server " + serverName);
                     }

                     builder.add(this.getTargetedRedeployOnExecPrimitive(targetedApps, adminNode.getNodeName(), serverName));
                  }
               }

               while(iterator.hasNextPartitionApps()) {
                  partitionApps = iterator.nextPartitionApps();
                  partitionName = partitionApps.getPartitionName();
                  targetedApps = partitionApps.getApplicationPropertyList();
                  if (targetedApps != null && !targetedApps.isEmpty()) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryBuilder adding PartitionTargetedRedeploy onExec for" + partitionName + " on server " + serverName);
                     }

                     builder.add(this.getPartitionTargetedRedeployOnExecPrimitive(targetedApps, adminNode.getNodeName(), serverName, partitionName));
                  }

                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getUpdateNodeDirectoryBuilder adding StartPartition for" + partitionName + " on server " + serverName);
                  }

                  if (!globalResourceGroupRequiresServerRestart) {
                     builder.add(this.getStartPartitionPrimitive(partitionName, serverName, sessionTimeout, sessionContext.getWaitForAllSessions()));
                  }
               }

               if (globalResourceGroupRequiresServerRestart && !server.isAdmin()) {
                  builder.add(this.getResumeServerAndShutdownOnRevertPrimitive(serverName, sessionTimeout, sessionContext.getWaitForAllSessionsOnRevert()));
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

   private WorkflowBuilder getShutdownManagedServerWorkflow(Node node, Server server, RolloutContext rolloutContext) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      RolloutUpdateSettings rolloutUpdateSettings = rolloutContext.getRolloutUpdateSettings();
      String serverName = server.getServerName();
      ClusterSessionContext sessionContext = rolloutContext.getSessionInfoForServer(serverName);
      if (sessionContext != null) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getShutdownManagedServerWorkflow adding ShutdownServer for server " + serverName);
         }

         builder.add(this.getShutdownServerAndResumeOnRevertPrimitive(serverName, rolloutUpdateSettings.getShutdownTimeoutSeconds(), sessionContext.getWaitForAllSessions()));
         return builder;
      } else {
         StringBuffer errorServerNames = new StringBuffer();
         Iterator var9 = rolloutContext.serverSessionInfoMap.keySet().iterator();

         while(var9.hasNext()) {
            String tmpServerName = (String)var9.next();
            errorServerNames.append(tmpServerName + ",");
         }

         throw new IllegalArgumentException("null sessionContext for " + serverName + " map contains: " + errorServerNames.toString());
      }
   }

   private WorkflowBuilder getStartupManagedServerWorkflow(Node node, Server server, RolloutContext rolloutContext) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      RolloutUpdateSettings rolloutUpdateSettings = rolloutContext.getRolloutUpdateSettings();
      String serverName = server.getServerName();
      ClusterSessionContext sessionContext = rolloutContext.getSessionInfoForServer(serverName);
      String machineName = node.getNodeName();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutUpdateMTPrimitiveFactory.getStartupManagedServer adding StartServer for server " + serverName);
      }

      boolean isExecuteDirection = true;
      builder.add(this.getStartServerInAdminModePrimitive(machineName, serverName, isExecuteDirection, rolloutUpdateSettings.getShutdownTimeoutSeconds(), sessionContext.getWaitForAllSessionsOnRevert()));
      builder.add(this.getServerSessionHandlingPrimitive(serverName, sessionContext, isExecuteDirection));
      return builder;
   }

   protected WorkflowBuilder getShutdownPartitionPrimitive(String partitionName, String serverName, int sessionTimeout, boolean waitForAllSessions) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdatePrimitiveFactory.getShutdownPartitionPrimitive called with params: partitionName " + partitionName + ", serverName " + serverName + ", sessionTimeout " + sessionTimeout + ", waitForAllSessions" + waitForAllSessions;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("partitionName", partitionName);
      map.put("serverName", serverName);
      map.put("sessionTimeout", sessionTimeout);
      map.put("waitForAllSessions", waitForAllSessions);
      map.put("readyCheckAppsTimeoutInMin", 180);
      builder.add(ShutdownPartitionCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getStartPartitionPrimitive(String partitionName, String serverName, int sessionTimeout, boolean waitForAllSessions) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdateMTPrimitiveFactory.getStartPartitionPrimitive called with params: partitionName " + partitionName + ", serverName " + serverName + ", sessionTimeout " + sessionTimeout + ", waitForAllSessions" + waitForAllSessions;
         PatchingDebugLogger.debug(debugString);
      }

      this.assertString("serverName", serverName);
      Map map = new HashMap();
      map.put("partitionName", partitionName);
      map.put("serverName", serverName);
      map.put("sessionTimeout", sessionTimeout);
      map.put("waitForAllSessions", waitForAllSessions);
      map.put("readyCheckAppsTimeoutInMin", 180);
      builder.add(StartPartitionCommand.class, map);
      return builder;
   }

   protected WorkflowBuilder getPartitionTargetedRedeployOnExecPrimitive(List targetedApps, String adminNodeName, String serverName, String partitionName) {
      return this.getPartitionTargetedRedeployPrimitive(targetedApps, adminNodeName, serverName, partitionName, true);
   }

   protected WorkflowBuilder getPartitionTargetedRedeployOnRevertPrimitive(List targetedApps, String adminNodeName, String serverName, String partitionName) {
      return this.getPartitionTargetedRedeployPrimitive(targetedApps, adminNodeName, serverName, partitionName, false);
   }

   protected WorkflowBuilder getPartitionTargetedRedeployPrimitive(List targetedApps, String adminNodeName, String serverName, String partitionName, boolean isExecuteDirection) {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getPartitionTargetedRedeployPrimitive with targetedApps: " + (targetedApps != null ? "" + targetedApps.size() : " null ") + " adminNode: " + adminNodeName + " serverName: " + serverName + " partitionName: " + partitionName + " and execDirection: " + isExecuteDirection);
      }

      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("partitionName", partitionName);
      map.put("adminMachineName", adminNodeName);
      if (targetedApps != null) {
         Iterator var8 = targetedApps.iterator();

         while(var8.hasNext()) {
            ApplicationProperties applicationProperties = (ApplicationProperties)var8.next();
            String applicationName = applicationProperties.getApplicationName();
            if (applicationProperties.isServerTargeted(serverName)) {
               if (PatchingDebugLogger.isDebugEnabled() && isExecuteDirection) {
                  PatchingDebugLogger.debug("Adding PartitionTargetedRedeployOnExecCommand for app: " + applicationName);
               }

               if (PatchingDebugLogger.isDebugEnabled() && !isExecuteDirection) {
                  PatchingDebugLogger.debug("Adding PartitionTargetedRedeployOnRevertCommand for app: " + applicationName);
               }

               WorkflowBuilder appBuilder = WorkflowBuilder.newInstance();
               Map appMap = new HashMap();
               appMap.put("applicationName", applicationName);
               appMap.put("deploymentPlan", applicationProperties.getPlanLocation());
               appMap.put("removePlanOverride", applicationProperties.getRemovePlanOverride());
               if (isExecuteDirection) {
                  appBuilder.add(PartitionTargetedRedeployOnExecCommand.class, appMap);
               } else {
                  appBuilder.add(PartitionTargetedRedeployOnRevertCommand.class, appMap);
               }

               builder.add(appBuilder);
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Skipping PartitionTargetedRedeploy for not staged app: " + applicationName + " on server: " + serverName);
            }
         }
      }

      builder.add(map);
      return builder;
   }

   private WorkflowBuilder getTargetedRedeployOnExecPrimitive(List targetedApps, String adminNodeName, String serverName) {
      return this.getTargetedRedeployPrimitive(targetedApps, adminNodeName, serverName, true);
   }

   private WorkflowBuilder getTargetedRedeployOnRevertPrimitive(List targetedApps, String adminNodeName, String serverName) {
      return this.getTargetedRedeployPrimitive(targetedApps, adminNodeName, serverName, false);
   }

   protected WorkflowBuilder getTargetedRedeployPrimitive(List targetedApps, String adminNodeName, String serverName, boolean isExecuteDirection) {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getTargetedRedeployPrimitive with targetedApps: " + (targetedApps != null ? "" + targetedApps.size() : " null ") + " adminNode: " + adminNodeName + " serverName: " + serverName + " and execDirection: " + isExecuteDirection);
      }

      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Map map = new HashMap();
      map.put("serverName", serverName);
      map.put("adminMachineName", adminNodeName);
      if (targetedApps != null) {
         Iterator var7 = targetedApps.iterator();

         while(var7.hasNext()) {
            ApplicationProperties applicationProperties = (ApplicationProperties)var7.next();
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
            }
         }
      }

      builder.add(map);
      return builder;
   }
}
