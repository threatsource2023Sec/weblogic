package weblogic.management.patching;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.patching.commands.CommandException;
import weblogic.management.patching.commands.MachineBasedUtils;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.patching.commands.ServerUtils;
import weblogic.management.patching.model.Cluster;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.patching.model.JMSInfo;
import weblogic.management.patching.model.JTAInfo;
import weblogic.management.patching.model.MachineInfo;
import weblogic.management.patching.model.MigrationInfo;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.NodeManagerRelation;
import weblogic.management.patching.model.Partition;
import weblogic.management.patching.model.PartitionApps;
import weblogic.management.patching.model.ResourceGroup;
import weblogic.management.patching.model.ResourceGroupTemplate;
import weblogic.management.patching.model.Server;
import weblogic.management.patching.model.ServerGroup;
import weblogic.management.patching.model.ServerInfo;
import weblogic.management.patching.model.VirtualTarget;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.DynamicServersProcessor;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.nodemanager.mbean.NodeManagerRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.ArrayUtils;
import weblogic.utils.net.AddressUtils;

public class TopologyInspector {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String EXACTLY_ONCE = "exactly-once";

   private static VirtualTargetMBean[] getAllVirtualTargetsIncludingAvailableAdminTargets(DomainMBean domainMBean) {
      VirtualTargetMBean[] virtualTargetMBeans = domainMBean.getVirtualTargets();
      Collection virtualTargetCollection = new ArrayList();
      ArrayUtils.addAll(virtualTargetCollection, virtualTargetMBeans);
      return (VirtualTargetMBean[])virtualTargetCollection.toArray(new VirtualTargetMBean[virtualTargetCollection.size()]);
   }

   public static DomainModel generateDomainModel() throws ManagementException {
      DomainModel domainModel = null;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String domainName = runtimeAccess.getDomainName();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("TopologyInspector.generateDomainModel creating domain model for " + domainName);
      }

      domainModel = new DomainModel(domainName);
      boolean enforceChecks = true;
      String adminServerName = runtimeAccess.getAdminServerName();
      String adminMWHome = runtimeAccess.getServerRuntime().getMiddlewareHome();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("TopologyInspector.generateDomainModel using " + adminMWHome + " for MWHome");
      }

      DomainMBean domainMBean = runtimeAccess.getDomain();
      MachineMBean[] machineMBeans = domainMBean.getMachines();
      ClusterMBean[] clusterMBeans = domainMBean.getClusters();
      ServerMBean[] serverMBeans = domainMBean.getServers();
      ResourceGroupTemplateMBean[] rgTemplateMBeans = domainMBean.getResourceGroupTemplates();
      ResourceGroupMBean[] globalResourceGroupMBeans = domainMBean.getResourceGroups();
      VirtualTargetMBean[] virtualTargetMBeans = getAllVirtualTargetsIncludingAvailableAdminTargets(domainMBean);
      if (machineMBeans != null && machineMBeans.length > 0) {
         MachineMBean[] var13 = machineMBeans;
         int var14 = machineMBeans.length;

         int var15;
         for(var15 = 0; var15 < var14; ++var15) {
            MachineMBean machineMBean = var13[var15];
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel creating node " + machineMBean.getName());
            }

            if (isNMRunning(machineMBean.getNodeManager())) {
               addNode(domainModel, machineMBean, adminMWHome);
            }
         }

         int var20;
         int var21;
         int var55;
         if (clusterMBeans != null && clusterMBeans.length > 0) {
            ClusterMBean[] var32 = clusterMBeans;
            var14 = clusterMBeans.length;

            for(var15 = 0; var15 < var14; ++var15) {
               ClusterMBean clusterMBean = var32[var15];
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel creating cluster " + clusterMBean.getName());
               }

               Cluster cluster = addCluster(domainModel, clusterMBean);
               MachineMBean[] migrationTargetMachineMBeans = clusterMBean.getCandidateMachinesForMigratableServers();
               MachineMBean[] dynamicClusterMachineMBeans;
               if (migrationTargetMachineMBeans != null && migrationTargetMachineMBeans.length > 0) {
                  dynamicClusterMachineMBeans = migrationTargetMachineMBeans;
                  var20 = migrationTargetMachineMBeans.length;

                  for(var21 = 0; var21 < var20; ++var21) {
                     MachineMBean migrationMBean = dynamicClusterMachineMBeans[var21];
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("TopologyInspector.generateDomainModel for cluster " + cluster.getClusterName() + " found candidate machine for migratable server " + migrationMBean.getName());
                     }

                     addNodeToCluster(domainModel, cluster, migrationMBean);
                  }
               } else if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel for cluster " + cluster.getClusterName() + " found no candidate machines for migratable servers");
               }

               dynamicClusterMachineMBeans = null;
               DynamicServersMBean dynServerMBean = clusterMBean.getDynamicServers();
               if (dynServerMBean != null && dynServerMBean.getDynamicClusterSize() > 0) {
                  if (dynServerMBean.isCalculatedMachineNames()) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("TopologyInspector.generateDomainModel found dynamic cluster " + cluster.getClusterName() + " with calculated machine names");
                     }

                     dynamicClusterMachineMBeans = DynamicServersProcessor.calculateMachineNames(domainMBean, dynServerMBean);
                  } else {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("TopologyInspector.generateDomainModel found dynamic cluster " + cluster.getClusterName() + " with unrestricted machines");
                     }

                     dynamicClusterMachineMBeans = machineMBeans;
                  }
               }

               if (dynamicClusterMachineMBeans != null && dynamicClusterMachineMBeans.length > 0) {
                  MachineMBean[] var52 = dynamicClusterMachineMBeans;
                  var55 = dynamicClusterMachineMBeans.length;

                  for(int var23 = 0; var23 < var55; ++var23) {
                     MachineMBean dynamicClusterMachineMBean = var52[var23];
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("TopologyInspector.generateDomainModel for cluster " + cluster.getClusterName() + " found dynamic machine candidate " + dynamicClusterMachineMBean.getName());
                     }

                     addNodeToCluster(domainModel, cluster, dynamicClusterMachineMBean);
                  }
               } else if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel for cluster " + cluster.getClusterName() + " found no dynamic machine candidates");
               }
            }
         }

         if (serverMBeans != null && serverMBeans.length > 0) {
            ServerMBean[] var33 = serverMBeans;
            var14 = serverMBeans.length;

            for(var15 = 0; var15 < var14; ++var15) {
               ServerMBean serverMBean = var33[var15];
               Server serverModel = createServerModel(serverMBean, adminServerName, domainModel);
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel created server model " + serverModel.getServerName());
               }

               Cluster cluster = null;
               boolean foundServerMigrationCandidates = false;
               String nodeName = serverModel.getServerInfo().getMachineName();
               Node node = domainModel.getNode(nodeName);
               if (node == null) {
                  if (!nodeName.equals("?ORPHANED?")) {
                     throw new IllegalArgumentException("No node found for " + nodeName + "\n" + domainModel.printContents());
                  }

                  node = addOrphanNode(domainModel, serverMBean, adminMWHome);
               }

               String clusterName = serverModel.getServerInfo().getClusterName();
               if (clusterName != null && !clusterName.isEmpty()) {
                  if (domainModel.hasCluster(clusterName)) {
                     cluster = domainModel.getCluster(clusterName);
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("TopologyInspector.generateDomainModel adding server " + serverModel.getServerName() + " to cluster " + cluster.getClusterName());
                     }

                     cluster.addServer(serverModel);
                     if (!cluster.hasNode(nodeName)) {
                        if (PatchingDebugLogger.isDebugEnabled()) {
                           PatchingDebugLogger.debug("TopologyInspector.generateDomainModel adding node " + nodeName + " to cluster " + cluster.getClusterName());
                        }

                        cluster.addNode(node);
                     }
                  } else if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("TopologyInspector.generateDomainModel FutureException - cluster " + clusterName + " not found in domain");
                  }
               }

               String groupName = "DEFAULT_GROUP_NAME";
               if (cluster != null) {
                  groupName = clusterName;
               }

               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel adding serverGroup " + groupName + (cluster != null ? " to cluster " + cluster.getClusterName() : ""));
               }

               ServerGroup serverGroup = addServerGroup(node, groupName, cluster);
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel adding server " + serverModel.getServerName() + " to serverGroup " + groupName);
               }

               serverGroup.addServer(serverModel);
               MachineMBean[] serverMigrationCandidates = serverMBean.getCandidateMachines();
               if (serverMigrationCandidates != null && serverMigrationCandidates.length > 0) {
                  foundServerMigrationCandidates = true;
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("TopologyInspector.generateDomainModel found " + serverMigrationCandidates.length + " migration candidates for server " + serverMBean.getName());
                  }

                  MachineMBean[] var26 = serverMigrationCandidates;
                  int var27 = serverMigrationCandidates.length;

                  for(int var28 = 0; var28 < var27; ++var28) {
                     MachineMBean serverMigrationCandidate = var26[var28];
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("TopologyInspector.generateDomainModel found adding server migration candidate " + serverMigrationCandidate.getName() + " to cluster " + clusterName);
                     }

                     addNodeToCluster(domainModel, cluster, serverMigrationCandidate);
                  }
               } else if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel found NO migration candidates for server " + serverMBean.getName());
               }

               if (serverMBean.isAutoMigrationEnabled()) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("TopologyInspector.generateDomainModel server " + serverModel.getServerName() + " in cluster " + clusterName + " is configured with auto migration");
                  }

                  if (!foundServerMigrationCandidates) {
                     ClusterMBean clusterMBean = domainMBean.lookupCluster(clusterName);
                     if (clusterMBean != null) {
                        MachineMBean[] clusterMigrationCandidateMBeans = clusterMBean.getCandidateMachinesForMigratableServers();
                        if (clusterMigrationCandidateMBeans == null || clusterMigrationCandidateMBeans.length == 0) {
                           if (PatchingDebugLogger.isDebugEnabled()) {
                              PatchingDebugLogger.debug("TopologyInspector.generateDomainModel automigration server " + serverModel.getServerName() + " found no migration candidates in cluster " + clusterName + ", adding all machines to cluster");
                           }

                           MachineMBean[] var71 = machineMBeans;
                           int var72 = machineMBeans.length;

                           for(int var30 = 0; var30 < var72; ++var30) {
                              MachineMBean machineMBean = var71[var30];
                              addNodeToCluster(domainModel, cluster, machineMBean);
                           }
                        }
                     }
                  }
               }
            }
         }

         if (rgTemplateMBeans != null && rgTemplateMBeans.length > 0) {
            ResourceGroupTemplateMBean[] var34 = rgTemplateMBeans;
            var14 = rgTemplateMBeans.length;

            for(var15 = 0; var15 < var14; ++var15) {
               ResourceGroupTemplateMBean rgTemplateMBean = var34[var15];
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel creating resourceGroupTemplate " + rgTemplateMBean.getName());
               }

               addResourceGroupTemplate(domainModel, rgTemplateMBean);
            }
         }

         TargetMBean[] targetMBeans;
         if (virtualTargetMBeans != null && virtualTargetMBeans.length > 0) {
            VirtualTargetMBean[] var35 = virtualTargetMBeans;
            var14 = virtualTargetMBeans.length;

            for(var15 = 0; var15 < var14; ++var15) {
               VirtualTargetMBean virtualTargetMBean = var35[var15];
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel creating VirtualTarget " + virtualTargetMBean.getName());
               }

               VirtualTarget virtualTarget = addVirtualTarget(domainModel, virtualTargetMBean);
               TargetMBean[] targetMBeans = virtualTargetMBean.getTargets();
               if (targetMBeans != null && targetMBeans.length > 0) {
                  targetMBeans = targetMBeans;
                  var20 = targetMBeans.length;

                  for(var21 = 0; var21 < var20; ++var21) {
                     TargetMBean targetMBean = targetMBeans[var21];
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("TopologyInspector.generateDomainModel adding server/cluster : " + targetMBean.getName() + " to VirtualTarget " + virtualTargetMBean.getName());
                     }

                     Set targetedServerNames = targetMBean.getServerNames();
                     Iterator var60 = targetedServerNames.iterator();

                     while(var60.hasNext()) {
                        String serverName = (String)var60.next();
                        ServerMBean serverMBean = domainMBean.lookupServer(serverName);
                        if (serverMBean.getCluster() != null) {
                           virtualTarget.setCluster(domainModel.getCluster(serverMBean.getCluster().getName()));
                        } else {
                           virtualTarget.setServer(domainModel.getServer(serverMBean.getName()));
                        }
                     }
                  }
               }
            }
         }

         if (globalResourceGroupMBeans != null && globalResourceGroupMBeans.length > 0) {
            ResourceGroupMBean[] var36 = globalResourceGroupMBeans;
            var14 = globalResourceGroupMBeans.length;

            for(var15 = 0; var15 < var14; ++var15) {
               ResourceGroupMBean resourceGroupMBean = var36[var15];
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel creating global resourceGroup " + resourceGroupMBean.getName());
               }

               ResourceGroup resourceGroup = addGlobalResourceGroup(domainModel, resourceGroupMBean);
               ResourceGroupTemplateMBean rgt = resourceGroupMBean.getResourceGroupTemplate();
               if (rgt != null) {
                  resourceGroup.setRgt(domainModel.getResourceGroupTemplate(rgt.getName()));
               }

               targetMBeans = resourceGroupMBean.getTargets();
               if (targetMBeans != null && targetMBeans.length > 0) {
                  TargetMBean[] var51 = targetMBeans;
                  var21 = targetMBeans.length;

                  for(var55 = 0; var55 < var21; ++var55) {
                     TargetMBean targetMBean = var51[var55];
                     resourceGroup.addVirtualTarget(domainModel.getVirtualTarget(targetMBean.getName()));
                     Set serverNames = targetMBean.getServerNames();
                     Iterator var65 = serverNames.iterator();

                     while(var65.hasNext()) {
                        String serverName = (String)var65.next();
                        Server server = domainModel.getServer(serverName);
                        server.addResourceGroup(resourceGroup);
                        resourceGroup.addServer(server);
                     }
                  }
               }
            }
         }

         return domainModel;
      } else {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidMachineMBeans(domainName));
      }
   }

   protected static Node addNode(DomainModel domainModel, MachineMBean machine, String adminMWHome) {
      String machineName = machine.getName();
      Node node = null;
      if (domainModel.hasNode(machineName)) {
         node = domainModel.getNode(machineName);
      } else {
         node = new Node(machineName);
         machine.getNodeManager().getListenAddress();
         MachineInfo machineInfo = node.getMachineInfo();
         machineInfo.setPathToOracleHome(adminMWHome);
         NodeManagerMBean nmbean = machine.getNodeManager();
         if (nmbean != null) {
            machineInfo.setListenAddress(nmbean.getListenAddress());
         }

         domainModel.addNode(node);
      }

      return node;
   }

   protected static Node addOrphanNode(DomainModel domainModel, ServerMBean orphanServer, String adminMWHome) {
      Node node = new Node("?ORPHANED?");
      MachineInfo machineInfo = node.getMachineInfo();
      machineInfo.setPathToOracleHome(adminMWHome);
      if (orphanServer != null) {
         machineInfo.setListenAddress(orphanServer.getListenAddress());
      }

      domainModel.addNode(node);
      return node;
   }

   protected static Cluster addCluster(DomainModel domainModel, ClusterMBean clusterMBean) {
      Cluster cluster = null;
      String clusterName = clusterMBean.getName();
      if (domainModel.hasCluster(clusterName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel has cluster " + clusterName);
         }

         cluster = domainModel.getCluster(clusterName);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel does not have cluster " + clusterName + ", creating it");
         }

         cluster = new Cluster(clusterName);
         domainModel.addCluster(cluster);
      }

      return cluster;
   }

   protected static ResourceGroupTemplate addResourceGroupTemplate(DomainModel domainModel, ResourceGroupTemplateMBean resourceGroupTemplateMBean) {
      ResourceGroupTemplate rgt = null;
      String rgtName = resourceGroupTemplateMBean.getName();
      if (domainModel.hasResourceGroupTemplate(rgtName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel has resourceGroupTemplate " + rgtName);
         }

         rgt = domainModel.getResourceGroupTemplate(rgtName);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel does not have virtualTarget " + rgtName + ", creating it");
         }

         rgt = new ResourceGroupTemplate(rgtName);
         domainModel.addResourceGroupTemplate(rgt);
      }

      return rgt;
   }

   protected static ResourceGroup addGlobalResourceGroup(DomainModel domainModel, ResourceGroupMBean resourceGroupMBean) {
      ResourceGroup resourceGroup = null;
      String resourceGroupName = resourceGroupMBean.getName();
      if (domainModel.hasResourceGroup(resourceGroupName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel has resourceGroup " + resourceGroupName);
         }

         resourceGroup = domainModel.getResourceGroup(resourceGroupName);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel does not have resourceGroup " + resourceGroupName + ", creating it");
         }

         resourceGroup = new ResourceGroup(resourceGroupName);
         domainModel.addResourceGroup(resourceGroup);
      }

      return resourceGroup;
   }

   protected static ResourceGroup addPartitionResourceGroup(Partition partition, PartitionMBean partitionMBean, ResourceGroupMBean resourceGroupMBean, DomainModel domainModel) {
      ResourceGroup resourceGroup = null;
      String resourceGroupName = resourceGroupMBean.getName();
      if (partition.hasResourceGroup(resourceGroupName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel partition has resourceGroup " + resourceGroupName);
         }

         resourceGroup = partition.getResourceGroup(resourceGroupName);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel partition does not have resourceGroup " + resourceGroupName + ", creating it");
         }

         resourceGroup = new ResourceGroup(resourceGroupName);
         partition.addResourceGroup(resourceGroup);
      }

      TargetMBean[] targetMBeans = resourceGroupMBean.findEffectiveTargets();
      if (targetMBeans != null && targetMBeans.length > 0) {
         TargetMBean[] var7 = targetMBeans;
         int var8 = targetMBeans.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            TargetMBean targetMBean = var7[var9];
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel for partition " + partitionMBean.getName() + "'s resourceGroup: " + resourceGroupMBean.getName() + " adding virtualTarget: " + targetMBean.getName());
            }

            VirtualTarget virtualTarget = domainModel.getVirtualTarget(targetMBean.getName());
            resourceGroup.addVirtualTarget(virtualTarget);
            partition.addVirtualTarget(virtualTarget);
            Set serverNames = targetMBean.getServerNames();
            Iterator var13 = serverNames.iterator();

            while(var13.hasNext()) {
               String serverName = (String)var13.next();
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel adding partition: " + partition.getPartitionName() + " to server: " + serverName);
               }

               Server server = domainModel.getServer(serverName);
               PartitionApps partitionApps;
               if (server.hasPartitionApps(partition.getPartitionName())) {
                  partitionApps = server.getPartitionApps(partition.getPartitionName());
                  partitionApps.addVirtualTarget(virtualTarget);
                  partitionApps.addResourceGroup(resourceGroup);
                  resourceGroup.addPartitionApps(partitionApps);
               } else {
                  partitionApps = new PartitionApps(partition.getPartitionName());
                  partitionApps.setPartition(partition);
                  partitionApps.setServer(server);
                  partitionApps.addVirtualTarget(virtualTarget);
                  partitionApps.addResourceGroup(resourceGroup);
                  resourceGroup.addPartitionApps(partitionApps);
                  server.addPartitionApps(partitionApps);
               }
            }
         }
      }

      return resourceGroup;
   }

   protected static VirtualTarget addVirtualTarget(DomainModel domainModel, VirtualTargetMBean virtualTargetMBean) {
      VirtualTarget virtualTarget = null;
      String virtualTargetName = virtualTargetMBean.getName();
      if (domainModel.hasVirtualTarget(virtualTargetName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel has virtualTarget " + virtualTargetName);
         }

         virtualTarget = domainModel.getVirtualTarget(virtualTargetName);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel does not have virtualTarget " + virtualTargetName + ", creating it");
         }

         virtualTarget = new VirtualTarget(virtualTargetName, virtualTargetMBean.getUriPrefix());
         domainModel.addVirtualTarget(virtualTarget);
      }

      return virtualTarget;
   }

   protected static Node addNodeToCluster(DomainModel domainModel, Cluster cluster, MachineMBean machineMBean) {
      Node node = null;
      String nodeName = machineMBean.getName();
      if (!cluster.hasNode(nodeName)) {
         if (domainModel.hasNode(nodeName)) {
            node = domainModel.getNode(nodeName);
         }

         cluster.addNode(node);
      }

      return node;
   }

   protected static ServerGroup addServerGroup(Node node, String groupName, Cluster cluster) {
      ServerGroup serverGroup = null;
      if (node.hasServerGroup(groupName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel node has group " + groupName);
         }

         serverGroup = node.getServerGroup(groupName);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel node does not have group " + groupName + ", creating it");
         }

         serverGroup = new ServerGroup(groupName);
         node.addServerGroup(serverGroup);
         if (cluster != null) {
            serverGroup.setCluster(cluster);
         }
      }

      return serverGroup;
   }

   protected static boolean isNMRunning(NodeManagerMBean nmBean) {
      return NodeManagerRuntime.isReachable(nmBean);
   }

   protected static Server createServerModel(ServerMBean serverMBean, String adminServerName, DomainModel domainModel) throws ManagementException {
      Server server = null;
      MachineMBean machineMBean = serverMBean.getMachine();
      String serverName = serverMBean.getName();
      List allLocalAddresses = (ArrayList)Arrays.asList(AddressUtils.getIPAny()).stream().map((n) -> {
         return n.getHostAddress();
      }).filter((n) -> {
         return !n.isEmpty();
      }).collect(Collectors.toList());
      String machineName;
      NodeManagerRelation nmRelation;
      if (machineMBean != null && machineMBean.getNodeManager() != null && isNMRunning(machineMBean.getNodeManager())) {
         machineName = getCurrentMachineName(serverMBean.getName());
         nmRelation = NodeManagerRelation.REGISTERED;
      } else {
         if (!serverName.equals(adminServerName)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector: Error no " + (machineMBean == null ? "machine" : "Node Manager") + " for server: " + serverName);
            }

            throw new ManagementException(PatchingMessageTextFormatter.getInstance().getNoNMForServer(serverName));
         }

         if (DomainModel.isMachineAddressInNodes(domainModel, allLocalAddresses)) {
            machineName = DomainModel.getMachineNameByIP(domainModel, allLocalAddresses);
            nmRelation = NodeManagerRelation.COLLOCATED;
         } else {
            machineName = "?ORPHANED?";
            nmRelation = NodeManagerRelation.ORPHANED;
         }
      }

      ServerInfo serverInfo = generateServerInfo(serverMBean, machineName);
      if (serverName.equals(adminServerName)) {
         serverInfo.setAdminServer(true);
      }

      server = new Server(serverInfo);
      server.setNmRelation(nmRelation);
      return server;
   }

   private static DomainModel generateServerBasedDomainModel() throws ManagementException {
      DomainModel domainModel = null;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String domainName = runtimeAccess.getDomainName();
      domainModel = new DomainModel(domainName);
      boolean enforceChecks = true;
      String adminServerName = runtimeAccess.getAdminServerName();
      ServerMBean[] _servers = runtimeAccess.getDomain().getServers();
      String adminMWHome = runtimeAccess.getServerRuntime().getMiddlewareHome();
      ServerMBean[] var7 = _servers;
      int var8 = _servers.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         ServerMBean server = var7[var9];
         MachineMBean machine = server.getMachine();
         String serverName = server.getName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel inspecting server " + serverName);
         }

         if (enforceChecks && (machine == null || server.getMachine().getNodeManager() == null)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector: Error no " + (machine == null ? "machine" : "Node Manager") + " for server: " + serverName);
            }

            throw new ManagementException(PatchingMessageTextFormatter.getInstance().getNoNMForServer(serverName));
         }

         String machineName = machine.getName();
         Node node = null;
         if (domainModel.hasNode(machineName)) {
            node = domainModel.getNode(machineName);
         } else {
            node = new Node(machineName);
            machine.getNodeManager().getListenAddress();
            MachineInfo machineInfo = node.getMachineInfo();
            machineInfo.setPathToOracleHome(adminMWHome);
            NodeManagerMBean nmbean = machine.getNodeManager();
            if (nmbean != null) {
               machineInfo.setListenAddress(nmbean.getListenAddress());
            }

            domainModel.addNode(node);
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel machineName is " + machineName);
         }

         ServerInfo serverInfo = generateServerInfo(server, machineName);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel serverInfo for " + serverInfo.getServerName() + ", serverinfo.clusterName is " + serverInfo.getClusterName());
         }

         if (serverName.equals(adminServerName)) {
            serverInfo.setAdminServer(true);
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector: found AdminServer: " + adminServerName);
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel " + serverInfo.getServerName() + " is Admin");
            }
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel about to create serverModel, serverinfo.clusterName is " + serverInfo.getClusterName());
         }

         Server serverModel = new Server(serverInfo);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel after creating serverModel, serverinfo.clusterName is " + serverInfo.getClusterName());
         }

         Cluster cluster = null;
         String clusterName = serverInfo.getClusterName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel after copying name, clusterName is " + clusterName + ", serverinfo.clusterName is " + serverInfo.getClusterName());
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            if (clusterName == null) {
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel clusterName is null, cluster is " + cluster);
            }

            if (clusterName != null) {
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel clusterName is \"" + clusterName + "\", cluster is " + cluster);
            }
         }

         if (clusterName != null && !clusterName.isEmpty()) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel clusterName is not empty, querying domainModel for " + clusterName);
            }

            if (domainModel.hasCluster(clusterName)) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel has cluster " + clusterName);
               }

               cluster = domainModel.getCluster(clusterName);
            } else {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.generateDomainModel domainModel does not have cluster " + clusterName + ", creating it");
               }

               cluster = new Cluster(clusterName);
               domainModel.addCluster(cluster);
            }

            cluster.addServer(serverModel);
         }

         String groupName;
         if (clusterName != null && !clusterName.isEmpty()) {
            groupName = clusterName;
         } else {
            groupName = "DEFAULT_GROUP_NAME";
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateDomainModel searching for group " + groupName);
         }

         ServerGroup serverGroup;
         if (node.hasServerGroup(groupName)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel node has group " + groupName);
            }

            serverGroup = node.getServerGroup(groupName);
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector.generateDomainModel node does not have group " + groupName + ", creating it");
            }

            serverGroup = new ServerGroup(groupName);
            node.addServerGroup(serverGroup);
         }

         if (cluster != null) {
            serverGroup.setCluster(cluster);
         }

         serverGroup.addServer(serverModel);
      }

      return domainModel;
   }

   public static ServerInfo generateServerInfo(ServerMBean server, String machineName) throws ManagementException {
      ServerLifeCycleRuntimeMBean slrbean = null;
      String serverName = server.getName();

      try {
         slrbean = ServerUtils.getServerLifeCycleRuntimeMBean(serverName);
         if (slrbean == null) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector: DomainRuntimeServerEnabled returned false. Server: " + serverName);
            }

            return null;
         }
      } catch (CommandException var7) {
         String message = var7.getMessage();
         if (message == null || message.isEmpty()) {
            message = var7.toString();
         }

         ManagementException me = new ManagementException(message);
         me.initCause(var7);
         throw me;
      }

      String stagingDirectory = server.getStagingDirectoryName();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("TopologyInspector.generateServerInfo: calculated staging dir as " + stagingDirectory);
      }

      ServerInfo serverInfo = new ServerInfo(serverName, slrbean.getWeblogicHome(), slrbean.getState(), machineName, stagingDirectory);
      if (server.getCluster() != null) {
         serverInfo.setClusterName(server.getCluster().getName());
      }

      return serverInfo;
   }

   public static HashMap generateMachinesMap() throws ManagementException {
      HashMap machinesMap = new HashMap();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String adminServerName = runtimeAccess.getAdminServerName();
      ServerMBean[] _servers = runtimeAccess.getDomain().getServers();
      ServerMBean[] var4 = _servers;
      int var5 = _servers.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServerMBean server = var4[var6];
         MachineMBean machine = server.getMachine();
         String serverName = server.getName();
         String machineName = null;
         ServerLifeCycleRuntimeMBean slrbean = null;
         if (machine == null || server.getMachine().getNodeManager() == null) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector: Error no " + (machine == null ? "machine" : "Node Manager") + " for server: " + serverName);
            }

            throw new ManagementException(PatchingMessageTextFormatter.getInstance().getNoNMForServer(serverName));
         }

         machineName = machine.getName();

         try {
            slrbean = ServerUtils.getServerLifeCycleRuntimeMBean(serverName);
            if (slrbean == null) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector: DomainRuntimeServerEnabled returned false. Server: " + serverName);
               }

               return null;
            }
         } catch (CommandException var16) {
            String message = var16.getMessage();
            if (message == null || message.isEmpty()) {
               message = var16.toString();
            }

            ManagementException me = new ManagementException(message);
            me.initCause(var16);
            throw me;
         }

         String stagingDirectory = server.getStagingDirectoryName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.generateMachinesMap: calculated staging dir as " + stagingDirectory);
         }

         ServerInfo serverInfo = new ServerInfo(serverName, slrbean.getWeblogicHome(), slrbean.getState(), machineName, stagingDirectory);
         if (serverName.equals(adminServerName)) {
            serverInfo.setAdminServer(true);
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector: found AdminServer: " + adminServerName);
            }
         }

         ClusterMBean clusterMBean = server.getCluster();
         if (clusterMBean != null) {
            serverInfo.setClusterName(clusterMBean.getName());
         }

         MachineInfo machineInfo = null;
         if (machinesMap.containsKey(machineName)) {
            machineInfo = (MachineInfo)machinesMap.get(machineName);
         } else {
            machineInfo = new MachineInfo(machineName);
            machinesMap.put(machineName, machineInfo);
         }

         machineInfo.addServerInfo(serverInfo);
         if (serverInfo.isAdminServer()) {
            machineInfo.setAdminMachine(true);
         }
      }

      return machinesMap;
   }

   public static TargetType calculateTargetType(String targets) throws ManagementException {
      TargetType result = null;
      if (targets != null && targets.length() != 0) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         String domainName = runtimeAccess.getDomain().getName();
         ClusterMBean[] clusterMBeans = runtimeAccess.getDomain().getClusters();
         ServerMBean[] serverMBeans = runtimeAccess.getDomain().getServers();
         Set clusterNames = new HashSet();
         Set serverNames = new HashSet();
         Set partitionNames = new HashSet();
         ClusterMBean[] type;
         int var10;
         int i;
         if (clusterMBeans != null && clusterMBeans.length > 0) {
            type = clusterMBeans;
            var10 = clusterMBeans.length;

            for(i = 0; i < var10; ++i) {
               ClusterMBean clusterMBean = type[i];
               clusterNames.add(clusterMBean.getName().toLowerCase());
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("TopologyInspector.calculateTargets found clusterName: " + clusterMBean.getName());
               }
            }
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.calculateTargets unable to access clusterMBeans");
         }

         if (serverMBeans != null && serverMBeans.length > 0) {
            ServerMBean[] var13 = serverMBeans;
            var10 = serverMBeans.length;

            for(i = 0; i < var10; ++i) {
               ServerMBean serverMBean = var13[i];
               serverNames.add(serverMBean.getName().toLowerCase());
            }
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.calculateTargets unable to access serverMBeans");
         }

         type = null;
         String[] targetArray = targets.split(",");
         if (targetArray != null && targetArray.length > 0) {
            for(i = 0; i < targetArray.length; ++i) {
               String target = targetArray[i];
               TargetType type;
               if (target.trim().equalsIgnoreCase(domainName)) {
                  type = TopologyInspector.TargetType.DOMAIN;
               } else if (clusterNames.contains(target.trim().toLowerCase())) {
                  type = TopologyInspector.TargetType.CLUSTER;
               } else if (serverNames.contains(target.trim().toLowerCase())) {
                  type = TopologyInspector.TargetType.SERVER;
               } else if (partitionNames.contains(target.trim().toLowerCase())) {
                  type = TopologyInspector.TargetType.PARTITION;
               } else {
                  type = null;
               }

               if (type == null) {
                  throw new ManagementException(PatchingMessageTextFormatter.getInstance().invalidTarget(target));
               }

               if (result != null && result != type) {
                  throw new ManagementException(PatchingMessageTextFormatter.getInstance().duplicateTargetName(target, result.toString(), type.toString()));
               }

               result = type;
            }

            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("TopologyInspector.calculateTargets will return " + result + " based on targets: " + targets);
            }

            return result;
         } else {
            throw new ManagementException(PatchingMessageTextFormatter.getInstance().invalidTargetList(targets));
         }
      } else {
         return null;
      }
   }

   public static MachineInfo getAdminNode(HashMap machinesMap) {
      MachineInfo adminMachine = null;
      Iterator var2 = machinesMap.values().iterator();

      while(var2.hasNext()) {
         MachineInfo machineInfo = (MachineInfo)var2.next();
         if (machineInfo.isAdminMachine()) {
            adminMachine = machineInfo;
            break;
         }
      }

      return adminMachine;
   }

   public static DomainMBean getDomainMBean() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domainMBean = runtimeAccess.getDomain();
      return domainMBean;
   }

   public static ServerRuntimeMBean getServerRuntimeMBean() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ServerRuntimeMBean serverRuntimeMBean = runtimeAccess.getServerRuntime();
      return serverRuntimeMBean;
   }

   public static MachineMBean getMachineMBean(String machineName) {
      MachineMBean machine = null;

      try {
         machine = (new MachineBasedUtils()).getMachineMBean(machineName);
      } catch (CommandException var3) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TopologyInspector.getMachineMBean hit exception getting mbean for machine: " + machineName, var3);
         }
      }

      return machine;
   }

   public static void checkSourceDestination(MigrationProperties migrationProperty) {
      DomainMBean domain = getDomainMBean();
      String source = migrationProperty.getSource();
      String destination = migrationProperty.getDestination();
      ServerMBean sourceServerMBean = domain.lookupServer(source);
      if (!migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.JMS) && !migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.JTA) && !migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.ALL)) {
         if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.SERVER)) {
            MachineMBean destinationMachineMBean = domain.lookupMachine(destination);
            if (sourceServerMBean == null && destinationMachineMBean == null) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidServer(source) + PatchingMessageTextFormatter.getInstance().getInvalidMachine(destination));
            }

            if (sourceServerMBean == null) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidServer(source));
            }

            if (destinationMachineMBean == null) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidMachine(destination));
            }
         } else if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.NONE) && sourceServerMBean == null) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidServer(source));
         }
      } else {
         ServerMBean destinationServerMBean = domain.lookupServer(destination);
         if (sourceServerMBean == null && destinationServerMBean == null) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidServer(source) + PatchingMessageTextFormatter.getInstance().getInvalidServer(destination));
         }

         if (sourceServerMBean == null) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidServer(source));
         }

         if (destinationServerMBean == null) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidServer(destination));
         }

         String srcMachine = getCurrentMachineName(source);
         String dstMachine = getCurrentMachineName(destination);
         if (srcMachine != null && dstMachine != null && srcMachine.equals(dstMachine)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug(" checkSourceDestination:JMS Migration and  source and destination machines are same. Invalid Migration scenario ");
            }

            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidMigrationScenarioSrcDstMachineMatch(srcMachine));
         }
      }

   }

   public static Map getSingletonServicesInfo(List migrationPropertiesList) {
      Map serversMap = new HashMap();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getSingletonServicesInfo for " + migrationPropertiesList + " with size: " + (migrationPropertiesList != null ? migrationPropertiesList.size() : "null"));
      }

      DomainMBean domain = getDomainMBean();
      MigratableTargetMBean[] mts = domain.getMigratableTargets();
      List mtn = new ArrayList();
      MigratableTargetMBean[] var5 = mts;
      int var6 = mts.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         MigratableTargetMBean mt = var5[var7];
         if (!mt.getMigrationPolicy().equals("exactly-once") && mt.getHostingServer() != null && isTargetBeingReferenced(mt)) {
            mtn.add(mt.getName());
            serversMap.put(mt.getHostingServer().getName(), new MigrationInfo());
         }
      }

      if (mtn.isEmpty() || migrationPropertiesList != null && !migrationPropertiesList.isEmpty()) {
         if (migrationPropertiesList == null) {
            return serversMap;
         } else {
            Iterator iter = migrationPropertiesList.iterator();

            JTAMigratableTargetMBean jtaMT;
            while(iter.hasNext()) {
               MigrationProperties migrationProperty = (MigrationProperties)iter.next();
               String serverName = migrationProperty.getSource();
               MigrationInfo mInfo = (MigrationInfo)serversMap.get(serverName);
               if (mInfo == null) {
                  mInfo = new MigrationInfo();
               }

               checkSourceDestination(migrationProperty);
               List targets;
               if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.JMS)) {
                  targets = getMigratableTargetsForServer(serverName);
                  updateJMSInfo(migrationProperty, targets, mInfo);
                  serversMap.put(serverName, mInfo);
               } else if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.JTA)) {
                  jtaMT = domain.lookupServer(serverName).getJTAMigratableTarget();
                  updateJTAInfo(migrationProperty, jtaMT, mInfo);
                  serversMap.put(serverName, mInfo);
               } else {
                  JTAMigratableTargetMBean jtaMT;
                  if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.ALL)) {
                     targets = getMigratableTargetsForServer(serverName);
                     updateJMSInfo(migrationProperty, targets, mInfo);
                     serversMap.put(serverName, mInfo);
                     jtaMT = domain.lookupServer(serverName).getJTAMigratableTarget();
                     updateJTAInfo(migrationProperty, jtaMT, mInfo);
                     serversMap.put(serverName, mInfo);
                  } else if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.SERVER)) {
                     updateWSMInfo(migrationProperty, serverName, mInfo);
                     serversMap.put(serverName, mInfo);
                  } else if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.NONE)) {
                     targets = getMigratableTargetsForServer(serverName);
                     jtaMT = domain.lookupServer(serverName).getJTAMigratableTarget();
                     if ((targets != null || jtaMT != null) && PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("MigrationType is none but singleton services detected");
                     }
                  }
               }
            }

            if (PatchingDebugLogger.isDebugEnabled()) {
               ClusterMBean[] var12 = domain.getClusters();
               var6 = var12.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  ClusterMBean cluster = var12[var7];
                  if (cluster.getMigrationBasis().equals("database")) {
                     if (cluster.getDataSourceForAutomaticMigration() != null) {
                        PatchingDebugLogger.debug("Database leasing is configured for cluster " + cluster.getName());
                     }
                  } else if (cluster.getMigrationBasis().equals("consensus")) {
                     PatchingDebugLogger.debug("Consensus leasing is configured for cluster " + cluster.getName());
                  }
               }

               ServerMBean[] var13 = domain.getServers();
               var6 = var13.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  ServerMBean server = var13[var7];
                  jtaMT = server.getJTAMigratableTarget();
                  if (jtaMT == null) {
                     PatchingDebugLogger.debug("No JTA service on server " + server.getName());
                  }
               }
            }

            iter = serversMap.entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               if (((MigrationInfo)entry.getValue()).toString().equals("")) {
                  iter.remove();
               }
            }

            return serversMap;
         }
      } else {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().singletonsExist());
      }
   }

   public static List getMigratableTargetsForServer(String servername) {
      DomainMBean domain = getDomainMBean();
      MigratableTargetMBean[] mts = domain.getMigratableTargets();
      List mtList = new ArrayList();
      MigratableTargetMBean[] var4 = mts;
      int var5 = mts.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         MigratableTargetMBean mt = var4[var6];
         if (!mt.getMigrationPolicy().equals("exactly-once") && mt.getHostingServer() != null && mt.getHostingServer().getName().equals(servername) && isTargetBeingReferenced(mt)) {
            mtList.add(mt);
         }
      }

      return mtList;
   }

   public static void updateJMSInfo(MigrationProperties migrationProperty, List targets, MigrationInfo mInfo) {
      Iterator var3 = targets.iterator();

      while(var3.hasNext()) {
         MigratableTargetMBean m = (MigratableTargetMBean)var3.next();
         if (!m.getMigrationPolicy().equals("exactly-once")) {
            if (migrationProperty.getDestination() == null) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().nullDestination());
            }

            JMSInfo jInfo = mInfo.getJMSInfo();
            if (jInfo == null) {
               jInfo = new JMSInfo();
            }

            jInfo.setUPS(m.getName(), m.getUserPreferredServer().getName());
            jInfo.setDestination(migrationProperty.getDestination());
            jInfo.setFailback(migrationProperty.getFailback());
            mInfo.setJMSInfo(jInfo);
         }
      }

   }

   public static void updateJTAInfo(MigrationProperties migrationProperty, JTAMigratableTargetMBean jtaMT, MigrationInfo mInfo) {
      if (jtaMT != null && !jtaMT.getMigrationPolicy().equals("exactly-once")) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("JTA service " + jtaMT.getName() + " on server " + jtaMT.getHostingServer().getName());
         }

         if (migrationProperty.getDestination() == null) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().nullDestination());
         }

         if ("shutdown-recovery".equalsIgnoreCase(jtaMT.getMigrationPolicy())) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("JTA service " + jtaMT.getName() + " on server " + jtaMT.getHostingServer().getName() + " will be skipped because the migration policy is: " + jtaMT.getMigrationPolicy());
            }

            return;
         }

         JTAInfo jtaInfo = new JTAInfo();
         jtaInfo.setUPS(jtaMT.getUserPreferredServer().getName());
         jtaInfo.setDestination(migrationProperty.getDestination());
         if (migrationProperty.getMigrationType().equals(MigrationProperties.MigrationType.ALL)) {
            jtaInfo.setFailback(false);
         } else {
            jtaInfo.setFailback(migrationProperty.getFailback());
         }

         mInfo.setJTAInfo(jtaInfo);
      }

   }

   private static boolean isMachineListed(String machineName, MachineMBean[] machineMbeans) {
      for(int i = 0; i < machineMbeans.length; ++i) {
         if (machineMbeans[i].getName().equals(machineName)) {
            return true;
         }
      }

      return false;
   }

   public static String getCurrentMachineName(String serverName) {
      ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(serverName);
      if (serverMBean == null) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getInvalidServer(serverName));
      } else {
         ServerRuntimeMBean serverRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().lookupServerRuntime(serverName);
         return serverRuntime != null ? serverRuntime.getCurrentMachine() : serverMBean.getMachine().getName();
      }
   }

   public static void updateWSMInfo(MigrationProperties migrationProperty, String serverName, MigrationInfo mInfo) {
      DomainMBean domain = getDomainMBean();
      ServerMBean serverMBean = domain.lookupServer(serverName);
      if (!serverMBean.isAutoMigrationEnabled()) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getAutoMigrationError(serverName));
      } else {
         String machine = migrationProperty.getDestination();
         boolean validMachine = false;
         ServerRuntimeMBean serverRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().lookupServerRuntime(serverMBean.getName());
         if (serverRuntime != null) {
            String currentMachine = serverRuntime.getCurrentMachine();
            if (machine.equals(currentMachine)) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidMigrationToSameMachine());
            }
         }

         MachineMBean configuredMachine = serverMBean.getMachine();
         if (configuredMachine != null && configuredMachine.getName().equals(machine)) {
            validMachine = true;
         }

         if (!validMachine) {
            MachineMBean[] candidateMachines = serverMBean.getCandidateMachines();
            if (candidateMachines != null && candidateMachines.length > 0) {
               validMachine = isMachineListed(machine, candidateMachines);
            } else {
               ClusterMBean clusterMbean = serverMBean.getCluster();
               if (clusterMbean != null) {
                  MachineMBean[] clusterMachines = clusterMbean.getCandidateMachinesForMigratableServers();
                  if (clusterMachines != null && clusterMachines.length > 0) {
                     validMachine = isMachineListed(machine, clusterMachines);
                  }
               }
            }
         }

         if (!validMachine) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getServerNotConfigured(machine, serverName));
         } else {
            boolean failback = migrationProperty.getFailback();
            if (domain.lookupMachine(machine) == null) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidMachineName(machine));
            } else {
               mInfo.putWSMInfo(machine, String.valueOf(failback));
            }
         }
      }
   }

   public static boolean isTargetBeingReferenced(MigratableTargetMBean mt) {
      DomainMBean domain = getDomainMBean();
      FileStoreMBean[] fileStores = domain.getFileStores();
      ArrayList fStores = new ArrayList();
      FileStoreMBean[] var4 = fileStores;
      int var5 = fileStores.length;

      int var6;
      int var10;
      for(var6 = 0; var6 < var5; ++var6) {
         FileStoreMBean fs = var4[var6];
         fStores.add(fs.getName());
         TargetMBean[] var8 = fs.getTargets();
         int var9 = var8.length;

         for(var10 = 0; var10 < var9; ++var10) {
            TargetMBean t = var8[var10];
            if (t.getName().equals(mt.getName())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("File store " + fs.getName() + " is targeted to MT " + t.getName());
               }

               return true;
            }
         }
      }

      JMSServerMBean[] var13 = domain.getJMSServers();
      var5 = var13.length;

      TargetMBean t;
      PersistentStoreMBean psm;
      TargetMBean[] var20;
      int var21;
      for(var6 = 0; var6 < var5; ++var6) {
         JMSServerMBean jmsServer = var13[var6];
         psm = jmsServer.getPersistentStore();
         if (psm != null && fStores.contains(psm.getName()) && PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("JMS Server " + jmsServer.getName() + " points to existing store " + jmsServer.getPersistentStore().getName());
         }

         var20 = jmsServer.getTargets();
         var10 = var20.length;

         for(var21 = 0; var21 < var10; ++var21) {
            t = var20[var21];
            if (t.getName().equals(mt.getName())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("JMS Server " + jmsServer.getName() + " points to existing MT " + t.getName());
               }

               return true;
            }
         }
      }

      SAFAgentMBean[] var14 = domain.getSAFAgents();
      var5 = var14.length;

      for(var6 = 0; var6 < var5; ++var6) {
         SAFAgentMBean agent = var14[var6];
         psm = agent.getStore();
         if (psm != null && fStores.contains(psm.getName()) && PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("SAF agent " + agent.getName() + " points to existing store " + agent.getStore().getName());
         }

         var20 = agent.getTargets();
         var10 = var20.length;

         for(var21 = 0; var21 < var10; ++var21) {
            t = var20[var21];
            if (t.getName().equals(mt.getName())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("SAF agent " + agent.getName() + " points to existing MT " + t.getName());
               }

               return true;
            }
         }
      }

      PathServiceMBean[] var15 = domain.getPathServices();
      var5 = var15.length;

      for(var6 = 0; var6 < var5; ++var6) {
         PathServiceMBean pathSvc = var15[var6];
         psm = pathSvc.getPersistentStore();
         if (psm != null && fStores.contains(psm.getName()) && PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Path service " + pathSvc.getName() + " points to existing store " + pathSvc.getPersistentStore().getName());
         }

         var20 = pathSvc.getTargets();
         var10 = var20.length;

         for(var21 = 0; var21 < var10; ++var21) {
            t = var20[var21];
            if (t.getName().equals(mt.getName())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Path service " + pathSvc.getName() + " points to existing MT " + t.getName());
               }

               return true;
            }
         }
      }

      return false;
   }

   public static enum TargetType {
      DOMAIN,
      CLUSTER,
      SERVER,
      PARTITION;
   }
}
