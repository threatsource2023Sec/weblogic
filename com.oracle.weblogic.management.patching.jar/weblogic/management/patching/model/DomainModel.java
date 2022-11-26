package weblogic.management.patching.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import weblogic.management.patching.ApplicationProperties;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.RolloutUpdateSettings;
import weblogic.management.patching.commands.CommandException;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;

public class DomainModel implements Serializable, Target {
   private static final long serialVersionUID = -7429637876314090619L;
   private String domainName;
   private SortedMap clusters;
   private SortedMap nodes;
   private boolean isTargeted;
   private Map resourceGroupTemplates;
   private Map resourceGroups;
   private Map virtualTargets;
   private Map partitions;

   public DomainModel(String domainName) {
      this.domainName = domainName;
      this.clusters = new TreeMap();
      this.nodes = new TreeMap();
      this.isTargeted = false;
      this.resourceGroupTemplates = new HashMap();
      this.resourceGroups = new HashMap();
      this.virtualTargets = new HashMap();
      this.partitions = new HashMap();
   }

   public void applyTargetedAndPropagateValue() {
      this.setTargeted(true);
   }

   public void addCluster(Cluster cluster) {
      if (this.clusters.containsKey(cluster.getClusterName())) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().domainContainsCluster(this.domainName, cluster.getClusterName()));
      } else {
         this.clusters.put(cluster.getClusterName(), cluster);
         cluster.setDomainModel(this);
      }
   }

   public Map getClusters() {
      return this.clusters;
   }

   public Cluster getCluster(String clusterName) {
      return (Cluster)this.clusters.get(clusterName);
   }

   public boolean hasCluster(String clusterName) {
      return this.clusters.containsKey(clusterName);
   }

   public void addResourceGroupTemplate(ResourceGroupTemplate rgt) {
      this.resourceGroupTemplates.put(rgt.getName(), rgt);
   }

   public Server getServer(String serverName) {
      Server server = null;
      Iterator var3 = this.nodes.values().iterator();

      while(var3.hasNext()) {
         Node node = (Node)var3.next();
         Iterator var5 = node.getServerGroups().values().iterator();

         while(var5.hasNext()) {
            ServerGroup serverGroup = (ServerGroup)var5.next();
            if (serverGroup.hasServer(serverName)) {
               server = serverGroup.getServer(serverName);
               return server;
            }
         }
      }

      return server;
   }

   public ResourceGroupTemplate getResourceGroupTemplate(String rgtName) {
      return (ResourceGroupTemplate)this.resourceGroupTemplates.get(rgtName);
   }

   public boolean hasResourceGroupTemplate(String rgtName) {
      return this.resourceGroupTemplates.containsKey(rgtName);
   }

   public void addResourceGroup(ResourceGroup resourceGroup) {
      this.resourceGroups.put(resourceGroup.getResourceGroupName(), resourceGroup);
   }

   public ResourceGroup getResourceGroup(String resourceGroupName) {
      return (ResourceGroup)this.resourceGroups.get(resourceGroupName);
   }

   public boolean hasResourceGroup(String resourceGroupName) {
      return this.resourceGroups.containsKey(resourceGroupName);
   }

   public void addVirtualTarget(VirtualTarget virtualTarget) {
      this.virtualTargets.put(virtualTarget.getVtName(), virtualTarget);
   }

   public VirtualTarget getVirtualTarget(String virtualTargetName) {
      return (VirtualTarget)this.virtualTargets.get(virtualTargetName);
   }

   public boolean hasVirtualTarget(String virtualTargetName) {
      return this.virtualTargets.containsKey(virtualTargetName);
   }

   public Partition getPartition(String partitionName) {
      return (Partition)this.partitions.get(partitionName);
   }

   public void addPartition(Partition partition) {
      this.partitions.put(partition.getPartitionName(), partition);
   }

   public boolean hasPartition(String partitionName) {
      return this.partitions.containsKey(partitionName);
   }

   public Collection getResourceGroups() {
      return this.resourceGroups.values();
   }

   public Collection getPartitionResourceGroups() {
      Collection partitionResourceGroups = new HashSet();
      Iterator var2 = this.partitions.values().iterator();

      while(var2.hasNext()) {
         Partition partition = (Partition)var2.next();
         partitionResourceGroups.addAll(partition.getResourceGroups());
      }

      return partitionResourceGroups;
   }

   public void addNode(Node node) {
      if (this.nodes.containsKey(node.getNodeName())) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().domainContainsNode(this.domainName, node.getNodeName()));
      } else {
         this.nodes.put(node.getNodeName(), node);
         node.setDomain(this);
      }
   }

   public Map getNodes() {
      return this.nodes;
   }

   public Node getNode(String nodeName) {
      return (Node)this.nodes.get(nodeName);
   }

   public boolean hasNode(String nodeName) {
      return this.nodes.containsKey(nodeName);
   }

   public String printContents() {
      StringBuilder sb1 = new StringBuilder();
      sb1.append("DomainModel " + this.domainName + " Contents \n");
      StringBuilder sb2 = new StringBuilder();
      sb2.append(" \n");
      Iterator var3 = this.resourceGroupTemplates.values().iterator();

      while(var3.hasNext()) {
         ResourceGroupTemplate rgt = (ResourceGroupTemplate)var3.next();
         sb2.append("ResourceGroupTemplate " + rgt.getName());
         sb2.append("\n");
      }

      var3 = this.virtualTargets.values().iterator();

      while(var3.hasNext()) {
         VirtualTarget virtualTarget = (VirtualTarget)var3.next();
         sb2.append("VirtualTarget " + virtualTarget.getVtName() + "\t" + virtualTarget.getURI());
         Cluster cluster = virtualTarget.getCluster();
         Server server = virtualTarget.getServer();
         sb2.append("\t" + (cluster != null ? " cluster: " + virtualTarget.getCluster().getClusterName() : "") + (server != null ? " server: " + virtualTarget.getServer().getServerName() : ""));
         sb2.append("\n");
      }

      var3 = this.resourceGroups.values().iterator();

      while(var3.hasNext()) {
         ResourceGroup rg = (ResourceGroup)var3.next();
         sb2.append("ResourceGroup " + rg.getResourceGroupName());
         sb2.append("\n");
      }

      var3 = this.partitions.values().iterator();

      while(var3.hasNext()) {
         Partition partition = (Partition)var3.next();
         sb2.append("Partition " + partition.getPartitionName());
         sb2.append("\n");
      }

      for(var3 = this.clusters.values().iterator(); var3.hasNext(); sb2.append(" \n")) {
         Cluster cluster = (Cluster)var3.next();
         sb2.append("Cluster " + cluster.getClusterName());
         if (cluster.isTargeted()) {
            sb2.append(" (targeted)");
         }
      }

      int nodeCount = 0;
      int serverCount = 0;
      Iterator var15 = this.nodes.values().iterator();

      while(var15.hasNext()) {
         Node node = (Node)var15.next();
         ++nodeCount;
         sb2.append("Node " + nodeCount + ", " + node.getNodeName());
         if (node.isTargeted()) {
            sb2.append(" (targeted)");
         }

         sb2.append(": \n");
         Iterator var7 = node.getNoStageApplicationProperties().iterator();

         while(var7.hasNext()) {
            ApplicationProperties appProperties = (ApplicationProperties)var7.next();
            sb2.append("\t");
            sb2.append("No Stage Application " + appProperties.getApplicationName());
            sb2.append(" \n");
         }

         var7 = node.getServerGroups().values().iterator();

         while(var7.hasNext()) {
            ServerGroup serverGroup = (ServerGroup)var7.next();
            sb2.append("\t");
            sb2.append("Group " + serverGroup.getGroupName());
            if (serverGroup.isTargeted()) {
               sb2.append(" (targeted)");
            }

            sb2.append(": \n");

            for(Iterator var9 = serverGroup.getServers().values().iterator(); var9.hasNext(); ++serverCount) {
               Server server = (Server)var9.next();
               sb2.append("\t\t");
               sb2.append("Server " + server.getServerName());
               if (server.isTargeted()) {
                  sb2.append(" (targeted)");
               }

               sb2.append(" \n");
               this.printServer(server, sb2, "\t\t\t");
            }
         }
      }

      int numNodes = this.nodes.size();
      int numClusters = this.clusters.size();
      sb1.append(numClusters + " clusters, and " + numNodes + " nodes, and " + serverCount + " servers \n");
      sb1.append(sb2);
      return sb1.toString();
   }

   private void printServer(Server server, StringBuilder sb2, String srvr_indent) {
      Iterator var4 = server.getApplicationPropertyList().iterator();

      while(var4.hasNext()) {
         ApplicationProperties appProperties = (ApplicationProperties)var4.next();
         sb2.append(srvr_indent);
         sb2.append("Application " + appProperties.getApplicationName());
         sb2.append(" \n");
      }

      this.printResourceGroupList(server.getResourceGroups(), sb2, srvr_indent);
      var4 = server.getPartitionApps().iterator();

      while(var4.hasNext()) {
         PartitionApps partitionApps = (PartitionApps)var4.next();
         sb2.append(srvr_indent);
         sb2.append("PartitionApps: " + partitionApps.getPartitionName());
         if (partitionApps.isTargeted()) {
            sb2.append(" (targeted)");
         }

         sb2.append(" \n");
         this.printResourceGroupList(partitionApps.getResourceGroups(), sb2, srvr_indent + "\t");
      }

   }

   private void printResourceGroupList(Collection resourceGroups, StringBuilder sb2, String baseIndent) {
      Iterator var4 = resourceGroups.iterator();

      while(var4.hasNext()) {
         ResourceGroup resourceGroup = (ResourceGroup)var4.next();
         sb2.append(baseIndent);
         sb2.append("ResourceGroup " + resourceGroup.getResourceGroupName());
         if (resourceGroup.isTargeted()) {
            sb2.append(" (targeted)");
         }

         sb2.append(" \n");
         ResourceGroupTemplate rgt = resourceGroup.getRgt();
         if (rgt != null) {
            sb2.append(baseIndent);
            sb2.append("\tExtends ResourceGroupTemplate: " + rgt.getName());
            sb2.append("\n");
         }

         Iterator var7 = resourceGroup.getVirtualTargets().iterator();

         while(var7.hasNext()) {
            VirtualTarget virtualTarget = (VirtualTarget)var7.next();
            sb2.append(baseIndent);
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Got virtualTarget: " + virtualTarget + " from resourceGroup: " + resourceGroup.getResourceGroupName());
            }

            sb2.append("\tVirtualTarget: " + virtualTarget.getVtName() + "\t" + virtualTarget.getURI());
            Cluster cluster = virtualTarget.getCluster();
            Server server = virtualTarget.getServer();
            sb2.append("\t" + (cluster != null ? " cluster: " + virtualTarget.getCluster().getClusterName() : "") + (server != null ? " server: " + virtualTarget.getServer().getServerName() : ""));
            sb2.append("\n");
         }

         var7 = resourceGroup.getApplicationPropertyList().iterator();

         while(var7.hasNext()) {
            ApplicationProperties appProps = (ApplicationProperties)var7.next();
            sb2.append(baseIndent);
            sb2.append("\tApplication: " + appProps.getApplicationName());
            sb2.append(" \n");
         }
      }

   }

   public void setTarget(RolloutUpdateSettings rolloutUpdateSettings) {
      RolloutUpdateSettings.RolloutTargetType rolloutTargetType = rolloutUpdateSettings.getRolloutTargetType();
      String target = rolloutUpdateSettings.getTarget();
      this.addApplicationsToDomainModel(rolloutUpdateSettings);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("DomainModel setting target " + target + ", type " + rolloutTargetType);
      }

      String[] targets = target.split("[,\\s]+");
      String[] var5;
      int var6;
      int var7;
      String serverTarget;
      switch (rolloutTargetType) {
         case PARTITION:
            var5 = targets;
            var6 = targets.length;

            for(var7 = 0; var7 < var6; ++var7) {
               serverTarget = var5[var7];
               this.setPartitionTarget(serverTarget, rolloutUpdateSettings);
            }

            return;
         case CLUSTER:
            var5 = targets;
            var6 = targets.length;

            for(var7 = 0; var7 < var6; ++var7) {
               serverTarget = var5[var7];
               this.setClusterTarget(serverTarget, rolloutUpdateSettings);
            }

            return;
         case SERVER:
            var5 = targets;
            var6 = targets.length;

            for(var7 = 0; var7 < var6; ++var7) {
               serverTarget = var5[var7];
               this.setServerTarget(serverTarget, rolloutUpdateSettings);
            }

            return;
         default:
            this.setDomainTarget(rolloutUpdateSettings);
      }
   }

   private void addApplicationsToDomainModel(RolloutUpdateSettings rolloutUpdateSettings) {
      RolloutUpdateSettings.RolloutTargetType rolloutTargetType = rolloutUpdateSettings.getRolloutTargetType();
      String target = rolloutUpdateSettings.getTarget();
      if (rolloutUpdateSettings.isUpdateApplications()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("DomainModel setting target " + target + ", type " + rolloutTargetType + " isUpdateApplications: " + rolloutUpdateSettings.isUpdateApplications());
         }

         List applicationPropertiesList = rolloutUpdateSettings.getApplicationPropertyList();
         Iterator var5 = applicationPropertiesList.iterator();

         label91:
         while(var5.hasNext()) {
            ApplicationProperties appProps = (ApplicationProperties)var5.next();
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("DomainModel setting targets of application: " + appProps.getApplicationName());
            }

            String rgtName = appProps.getResourceGroupTemplateName();
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("AppProperties has rgt name: " + rgtName);
            }

            if (rgtName != null && !rgtName.isEmpty()) {
               this.getResourceGroupTemplate(rgtName).addApplicationProperties(appProps);
            }

            Iterator var8 = appProps.getPartitionNames().iterator();

            String partitionName;
            while(var8.hasNext()) {
               partitionName = (String)var8.next();
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("AppProperties has partition name: " + partitionName + " and rg: " + appProps.getPartitionResourceGroup(partitionName));
               }

               Partition partition = this.getPartition(partitionName);
               if (partition == null) {
                  throw new IllegalArgumentException("Could not find Partition: " + partitionName + " in our domainModel: \n" + this.printContents());
               }

               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Setting partition : " + partition.getPartitionName() + " to targeted");
               }

               ResourceGroup rg = partition.getResourceGroup(appProps.getPartitionResourceGroup(partitionName));
               if (rg == null) {
                  throw new IllegalArgumentException("Could not find  ResourceGropup " + appProps.getPartitionResourceGroup(partitionName) + " for Partition: " + partitionName + " in our domainModel: \n" + this.printContents());
               }

               rg.addApplicationProperties(appProps);
            }

            var8 = appProps.getGlobalResourceGroups().iterator();

            while(var8.hasNext()) {
               partitionName = (String)var8.next();
               ResourceGroup rg = this.getResourceGroup(partitionName);
               if (rg == null) {
                  throw new IllegalArgumentException("Could not find ResourceGroup: " + partitionName + " in our domainModel: \n" + this.printContents());
               }

               rg.addApplicationProperties(appProps);
            }

            var8 = appProps.getServerNames().iterator();

            while(true) {
               Server server;
               do {
                  if (!var8.hasNext()) {
                     continue label91;
                  }

                  partitionName = (String)var8.next();
                  server = this.getServer(partitionName);
                  if (server == null) {
                     throw new IllegalArgumentException("Could not find server: " + partitionName + " in our domainModel: \n" + this.printContents());
                  }
               } while(!appProps.getPartitionNames().isEmpty() && appProps.getGlobalResourceGroups().isEmpty());

               server.addApplicationProperties(appProps);
               ServerGroup serverGroup = server.getServerGroup();
               Node node = server.getServerGroup().getNode();
               if (appProps.isNoStaged()) {
                  node.addNoStageApplicationProperties(appProps);
               }
            }
         }
      }

   }

   protected void setPartitionTarget(String partitionTarget, RolloutUpdateSettings rolloutUpdateSettings) {
      boolean found = false;
      Partition partition = this.getPartition(partitionTarget);
      if (partition != null) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("DomainModel.setPartitionTarget found partition");
         }

         found = true;
         Iterator var5 = this.getNodes().values().iterator();

         while(var5.hasNext()) {
            Node node = (Node)var5.next();
            Iterator var7 = node.getServerGroups().values().iterator();

            while(var7.hasNext()) {
               ServerGroup serverGroup = (ServerGroup)var7.next();
               Iterator var9 = serverGroup.getServers().values().iterator();

               while(var9.hasNext()) {
                  Server server = (Server)var9.next();
                  Iterator var11 = server.getPartitionApps().iterator();

                  while(var11.hasNext()) {
                     PartitionApps partitionApps = (PartitionApps)var11.next();
                     if (partitionApps.getPartitionName().equals(partitionTarget)) {
                        this.setTargetForPartitionApps(partitionApps, rolloutUpdateSettings);
                     }
                  }
               }
            }
         }
      }

      if (!found && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("DomainModel.setPartitionTarget could not find " + partitionTarget);
      }

   }

   protected void setClusterTarget(String clusterTarget, RolloutUpdateSettings rolloutUpdateSettings) {
      boolean found = false;
      Cluster cluster = this.getCluster(clusterTarget);
      if (cluster != null) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("DomainModel.setClusterTarget found cluster");
         }

         found = true;
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("DomainModel.setClusterTarget getting servers");
         }

         Iterator var5 = cluster.getServers().values().iterator();

         while(var5.hasNext()) {
            Server server = (Server)var5.next();
            this.setServerTarget(server.getServerName(), rolloutUpdateSettings);
         }
      }

      if (!found && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("DomainModel.setClusterTarget could not find " + clusterTarget);
      }

   }

   protected void setServerTarget(String serverTarget, RolloutUpdateSettings rolloutUpdateSettings) {
      boolean targetThisServer = true;
      boolean found = false;
      Server server = this.getServer(serverTarget);
      if (server != null) {
         found = true;
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("DomainModel.setServerTarget found server");
         }

         if (rolloutUpdateSettings.isUpdateApplications() && !rolloutUpdateSettings.isUpdateJavaHome() && !rolloutUpdateSettings.isUpdateOracleHome() && !rolloutUpdateSettings.isRollingRestart()) {
            List partitionAppsList;
            Iterator var7;
            if (server.getApplicationPropertyList() != null && !server.getApplicationPropertyList().isEmpty()) {
               if (server.getResourceGroups() != null && !server.getResourceGroups().isEmpty()) {
                  partitionAppsList = server.getResourceGroups();
                  var7 = partitionAppsList.iterator();

                  while(var7.hasNext()) {
                     ResourceGroup rg = (ResourceGroup)var7.next();
                     if (!rg.getApplicationPropertyList().isEmpty()) {
                        rg.applyTargetedAndPropagateValue();
                     }
                  }
               }
            } else {
               targetThisServer = false;
            }

            if (server.getPartitionApps() != null && !server.getPartitionApps().isEmpty()) {
               partitionAppsList = server.getPartitionApps();
               var7 = partitionAppsList.iterator();

               while(var7.hasNext()) {
                  PartitionApps partitionApps = (PartitionApps)var7.next();
                  if (partitionApps.getApplicationPropertyList() != null && !partitionApps.getApplicationPropertyList().isEmpty()) {
                     partitionApps.applyTargetedAndPropagateValue();
                  }
               }
            }
         }

         if (targetThisServer) {
            server.applyTargetedAndPropagateValue();
         }
      }

      if (!found && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("DomainModel.setServerTarget could not find " + serverTarget);
      }

   }

   protected void setDomainTarget(RolloutUpdateSettings rolloutUpdateSettings) {
      this.setTargeted(true);
      Iterator var2 = this.getNodes().values().iterator();

      while(var2.hasNext()) {
         Node node = (Node)var2.next();
         Iterator var4 = node.getServerGroups().values().iterator();

         while(var4.hasNext()) {
            ServerGroup serverGroup = (ServerGroup)var4.next();
            Iterator var6 = serverGroup.getServers().values().iterator();

            while(var6.hasNext()) {
               Server server = (Server)var6.next();
               this.setServerTarget(server.getServerName(), rolloutUpdateSettings);
            }
         }
      }

   }

   protected void setTargetForPartitionApps(PartitionApps partitionApps, RolloutUpdateSettings rolloutUpdateSettings) {
      boolean targetThisPartition = true;
      if (rolloutUpdateSettings.isUpdateApplications() && !rolloutUpdateSettings.isUpdateJavaHome() && !rolloutUpdateSettings.isUpdateOracleHome() && !rolloutUpdateSettings.isRollingRestart()) {
         if (partitionApps.getApplicationPropertyList() != null && !partitionApps.getApplicationPropertyList().isEmpty()) {
            Iterator var4 = partitionApps.getResourceGroups().iterator();

            while(var4.hasNext()) {
               ResourceGroup rg = (ResourceGroup)var4.next();
               if (!rg.getApplicationPropertyList().isEmpty()) {
                  rg.applyTargetedAndPropagateValue();
               }
            }
         } else {
            targetThisPartition = false;
         }
      }

      if (targetThisPartition) {
         partitionApps.applyTargetedAndPropagateValue();
      }

   }

   public void setTargeted(boolean isTargeted) {
      this.isTargeted = isTargeted;
   }

   public boolean isTargeted() {
      return this.isTargeted;
   }

   public Server getAdminServer() {
      Server adminServer = null;
      Node adminNode = this.getAdminNode();
      if (adminNode != null) {
         adminServer = adminNode.getAdminServer();
      }

      return adminServer;
   }

   public Node getAdminNode() {
      Node adminNode = null;
      Iterator var2 = this.nodes.values().iterator();

      while(var2.hasNext()) {
         Node node = (Node)var2.next();
         if (node.isAdmin()) {
            adminNode = node;
            break;
         }
      }

      return adminNode;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public int getNumTargetedNodes() {
      int targetedNodeCount = 0;
      Iterator var2 = this.nodes.values().iterator();

      while(var2.hasNext()) {
         Node node = (Node)var2.next();
         if (node.isTargeted()) {
            ++targetedNodeCount;
         }
      }

      return targetedNodeCount;
   }

   public static InetAddress getIpFromListenAddr(String listenAddr) {
      InetAddress nodeAddr = null;

      try {
         nodeAddr = InetAddress.getByName(listenAddr);
      } catch (Exception var3) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Unable to create InetAddress for " + listenAddr);
         }
      }

      return nodeAddr;
   }

   public static boolean isMachineAddressInNodes(DomainModel domainModel, List allLocalAddresses) {
      Iterator var2 = domainModel.getNodes().values().iterator();

      InetAddress nodeAddr;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         Node node = (Node)var2.next();
         String machineAddress = node.getMachineInfo().getListenAddress();
         nodeAddr = getIpFromListenAddr(machineAddress);
      } while(!allLocalAddresses.contains(nodeAddr.getHostAddress()));

      return true;
   }

   public static String getMachineNameByIP(DomainModel domainModel, List allLocalAddresses) {
      Iterator var2 = domainModel.getNodes().values().iterator();

      Node node;
      InetAddress nodeAddr;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         node = (Node)var2.next();
         String machineAddress = node.getMachineInfo().getListenAddress();
         nodeAddr = getIpFromListenAddr(machineAddress);
      } while(!allLocalAddresses.contains(nodeAddr.getHostAddress()));

      return node.getMachineInfo().getMachineName();
   }

   public static void compareOriginalTopologyWithCurrentTopology(DomainModel originalModel, DomainModel currentModel) throws CommandException {
      if (originalModel != null && currentModel != null) {
         Map originalClusterMap = originalModel.getClusters();
         Map currentClusterMap = currentModel.getClusters();
         if (originalClusterMap != null && currentClusterMap != null && originalClusterMap.size() == currentClusterMap.size()) {
            Iterator var4 = originalClusterMap.keySet().iterator();

            label44:
            while(var4.hasNext()) {
               String clusterName = (String)var4.next();
               Cluster originalCluster = (Cluster)originalClusterMap.get(clusterName);
               Cluster currentCluster = (Cluster)currentClusterMap.get(clusterName);
               if (currentCluster == null) {
                  throw new CommandException(PatchingMessageTextFormatter.getInstance().getTopologyChangeDetectedAtClusterLevel());
               }

               if (originalCluster.countNodes() != currentCluster.countNodes()) {
                  throw new CommandException(PatchingMessageTextFormatter.getInstance().getTopologyChangeDetectedAtClusterLevel());
               }

               Map originalServers = originalCluster.getServers();
               Map currentServers = currentCluster.getServers();
               if (originalServers != null && currentServers != null && originalServers.size() == currentServers.size()) {
                  Iterator var10 = originalServers.keySet().iterator();

                  String serverName;
                  do {
                     if (!var10.hasNext()) {
                        continue label44;
                     }

                     serverName = (String)var10.next();
                  } while(currentServers.containsKey(serverName));

                  throw new CommandException(PatchingMessageTextFormatter.getInstance().getTopologyChangeDetectedAtServerLevel());
               }

               throw new CommandException(PatchingMessageTextFormatter.getInstance().getTopologyChangeDetectedAtClusterLevel());
            }

         } else {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().getTopologyChangeDetectedAtClusterLevel());
         }
      } else {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getTopologyChangeDetectedAtDomainLevel());
      }
   }
}
