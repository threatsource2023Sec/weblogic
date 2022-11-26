package weblogic.management.patching.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.RolloutUpdateSettings;
import weblogic.management.patching.TopologyInspector;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.patching.model.MigrationInfo;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.PartitionApps;
import weblogic.management.patching.model.ResourceGroup;
import weblogic.management.patching.model.Server;
import weblogic.management.patching.model.ServerGroup;

public class DomainModelIterator {
   DomainModel domainModel;
   RolloutUpdateSettings.RolloutTargetType rolloutTargetType;
   String targetString;
   Node adminNode;
   int nodeIndex;
   Vector nodeVector;
   int serverGroupIndex;
   Vector serverGroupVector;
   int serverIndex;
   Vector serverVector;
   int globalRGIndex;
   Vector globalRGVector;
   int partitionAppsIndex;
   Vector partitionAppsVector;
   int partitionRGIndex;
   Vector partitionRGVector;

   public DomainModelIterator(DomainModel domainModel) {
      this(domainModel, RolloutUpdateSettings.RolloutTargetType.DOMAIN, (String)null);
   }

   public DomainModelIterator(DomainModel domainModel, RolloutUpdateSettings rolloutUpdateSettings) {
      this(domainModel, rolloutUpdateSettings.getRolloutTargetType(), rolloutUpdateSettings.getTarget(), TopologyInspector.getSingletonServicesInfo(rolloutUpdateSettings.getMigrationPropertyList()));
   }

   public DomainModelIterator(DomainModel domainModel, RolloutUpdateSettings.RolloutTargetType rolloutTargetType, String targetString) {
      this(domainModel, rolloutTargetType, targetString, (Map)null);
   }

   public DomainModelIterator(DomainModel domainModel, RolloutUpdateSettings.RolloutTargetType rolloutTargetType, String targetString, Map migrationInfo) {
      this.adminNode = null;
      this.domainModel = domainModel;
      this.rolloutTargetType = rolloutTargetType;
      this.targetString = targetString;
      this.nodeVector = new Vector(domainModel.getNodes().values());
      int insertIndex = 0;
      if (rolloutTargetType == RolloutUpdateSettings.RolloutTargetType.DOMAIN) {
         this.adminNode = domainModel.getAdminNode();
         this.nodeVector.remove(this.adminNode);
         this.nodeVector.add(0, this.adminNode);
         insertIndex = 1;
      } else if (rolloutTargetType == RolloutUpdateSettings.RolloutTargetType.DOMAIN_ROLLBACK) {
         this.adminNode = domainModel.getAdminNode();
         this.nodeVector.remove(this.adminNode);
         this.nodeVector.add(this.adminNode);
      }

      if (migrationInfo != null && !migrationInfo.isEmpty()) {
         ArrayList machineNameOrder = new ArrayList();
         Set nodesWithoutDependencies = new HashSet();
         Map machinesByServerName = new HashMap();
         Map sourcesByDestinationName = new HashMap();
         Map destinationsBySourceName = new HashMap();
         this.allMachinesForDependencyGraphInit(domainModel, nodesWithoutDependencies, machinesByServerName);
         this.createDependencyGraphFromMigrationInfo(migrationInfo, nodesWithoutDependencies, machinesByServerName, sourcesByDestinationName, destinationsBySourceName);
         this.topologicalSortDependencyGraph(machineNameOrder, nodesWithoutDependencies, sourcesByDestinationName, destinationsBySourceName);

         for(int i = 0; i < machineNameOrder.size(); ++i) {
            String machine = (String)machineNameOrder.get(i);
            Node node = domainModel.getNode(machine);
            if (node.isAdmin()) {
               --insertIndex;
            } else {
               this.nodeVector.remove(node);
               this.nodeVector.add(insertIndex + i, node);
            }
         }
      }

      this.nodeIndex = 0;
   }

   private void topologicalSortDependencyGraph(ArrayList sortedNodes, Set nodesWithoutDependencies, Map sourcesByDestinationName, Map destinationsBySourceName) {
      label27:
      while(true) {
         if (!nodesWithoutDependencies.isEmpty()) {
            String priorityMachine = (String)nodesWithoutDependencies.iterator().next();
            nodesWithoutDependencies.remove(priorityMachine);
            sortedNodes.add(priorityMachine);
            if (!sourcesByDestinationName.containsKey(priorityMachine)) {
               continue;
            }

            Iterator var6 = ((Set)sourcesByDestinationName.get(priorityMachine)).iterator();

            while(true) {
               if (!var6.hasNext()) {
                  continue label27;
               }

               String sourceDependingOnUs = (String)var6.next();
               Set dependencies = (Set)destinationsBySourceName.get(sourceDependingOnUs);
               dependencies.remove(priorityMachine);
               if (dependencies.isEmpty()) {
                  nodesWithoutDependencies.add(sourceDependingOnUs);
                  this.printValues(nodesWithoutDependencies);
                  destinationsBySourceName.remove(sourceDependingOnUs);
               }
            }
         }

         if (!destinationsBySourceName.isEmpty()) {
            sortedNodes.addAll(destinationsBySourceName.keySet());
         }

         return;
      }
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

   private void printValues(Set nodesWithoutDependencies) {
      StringBuffer sb = new StringBuffer();
      Iterator var3 = nodesWithoutDependencies.iterator();

      while(var3.hasNext()) {
         String macName = (String)var3.next();
         sb.append(macName);
         sb.append(",");
      }

      PatchingDebugLogger.debug("priorityDestinationMachines: " + sb.toString());
   }

   private void printValues(String dataName, Map data) {
      StringBuffer sb = new StringBuffer();
      Iterator var4 = data.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry dataEntry = (Map.Entry)var4.next();
         sb.append((String)dataEntry.getKey());
         sb.append(" - ");
         Iterator var6 = ((Set)dataEntry.getValue()).iterator();

         while(var6.hasNext()) {
            String values = (String)var6.next();
            sb.append(values);
            sb.append(",");
         }

         sb.append("\n");
      }

      PatchingDebugLogger.debug(dataName + ": " + sb.toString());
   }

   private void printValues(Map machinesByServerName) {
      StringBuffer sb = new StringBuffer();
      Iterator var3 = machinesByServerName.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry dataEntry = (Map.Entry)var3.next();
         sb.append((String)dataEntry.getKey());
         sb.append(" - ");
         sb.append((String)dataEntry.getValue());
         sb.append("\n");
      }

      PatchingDebugLogger.debug("machinesByServerName: " + sb.toString());
   }

   private void printValues(Set nodesWithoutDependencies, Map sourcesByDestinationName, Map destinationsBySourceName) {
      this.printValues(nodesWithoutDependencies);
      this.printValues("sourcesByDestinationName", sourcesByDestinationName);
      this.printValues("destinationsBySourceName", destinationsBySourceName);
   }

   private void createDependencyGraphFromMigrationInfo(Map migrationInfo, Set nodesWithoutDependencies, Map machinesByServerName, Map sourcesByDestinationName, Map destinationsBySourceName) {
      Iterator var6 = migrationInfo.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry migrationInfoEntry = (Map.Entry)var6.next();
         String serverName = (String)migrationInfoEntry.getKey();
         MigrationInfo migInfo = (MigrationInfo)migrationInfoEntry.getValue();
         if (migInfo != null) {
            String destination;
            String destination;
            if (migInfo.getJMSInfo() != null) {
               destination = (String)machinesByServerName.get(migInfo.getJMSInfo().getDestination());
               destination = (String)machinesByServerName.get(serverName);
               this.addToDependencyGraph(nodesWithoutDependencies, sourcesByDestinationName, destinationsBySourceName, serverName, destination, destination);
            }

            if (migInfo.getJTAInfo() != null) {
               destination = (String)machinesByServerName.get(migInfo.getJTAInfo().getDestination());
               destination = (String)machinesByServerName.get(serverName);
               this.addToDependencyGraph(nodesWithoutDependencies, sourcesByDestinationName, destinationsBySourceName, serverName, destination, destination);
            }

            if (migInfo.getWSMInfo() != null) {
               Map wsmInfo = migInfo.getWSMInfo();
               destination = migInfo.getWSMInfoDestinationMachine();
               String source = (String)machinesByServerName.get(serverName);
               this.addToDependencyGraph(nodesWithoutDependencies, sourcesByDestinationName, destinationsBySourceName, serverName, destination, source);
            }
         }
      }

   }

   private void addToDependencyGraph(Set nodesWithoutDependencies, Map sourcesByDestinationName, Map destinationsBySourceName, String serverName, String destination, String source) {
      Set sources = (Set)sourcesByDestinationName.get(destination);
      Set destinations = (Set)destinationsBySourceName.get(source);
      if (sources == null) {
         sources = new HashSet();
         sourcesByDestinationName.put(destination, sources);
      }

      if (destinations == null) {
         destinations = new HashSet();
         destinationsBySourceName.put(source, destinations);
      }

      ((Set)sources).add(source);
      ((Set)destinations).add(destination);
      nodesWithoutDependencies.remove(source);
      if (!destinationsBySourceName.containsKey(destination)) {
      }

   }

   private void allMachinesForDependencyGraphInit(DomainModel domainModel, Set nodesWithoutDependencies, Map machinesByServerName) {
      Iterator var4 = domainModel.getNodes().values().iterator();

      label36:
      while(true) {
         Node node;
         do {
            if (!var4.hasNext()) {
               return;
            }

            node = (Node)var4.next();
         } while(!node.isTargeted());

         nodesWithoutDependencies.add(node.getMachineInfo().getMachineName());
         Iterator var6 = node.getServerGroups().values().iterator();

         while(true) {
            ServerGroup serverGroup;
            do {
               if (!var6.hasNext()) {
                  continue label36;
               }

               serverGroup = (ServerGroup)var6.next();
            } while(!serverGroup.isTargeted());

            Iterator var8 = serverGroup.getServers().values().iterator();

            while(var8.hasNext()) {
               Server server = (Server)var8.next();
               machinesByServerName.put(server.getServerName(), server.getServerInfo().getMachineName());
            }
         }
      }
   }

   public boolean hasNextNode() {
      boolean hasNext = false;
      int i = this.nodeIndex;

      while(i < this.nodeVector.size()) {
         Node n = (Node)this.nodeVector.get(i++);
         if (n != null && n.isTargeted()) {
            hasNext = true;
            break;
         }
      }

      return hasNext;
   }

   public boolean hasNextServerGroup() {
      boolean hasNext = false;
      int i = this.serverGroupIndex;

      while(i < this.serverGroupVector.size()) {
         ServerGroup sg = (ServerGroup)this.serverGroupVector.get(i++);
         if (sg != null && sg.isTargeted()) {
            hasNext = true;
            break;
         }
      }

      return hasNext;
   }

   public boolean hasNextServer() {
      boolean hasNext = false;
      int i = this.serverIndex;

      while(i < this.serverVector.size()) {
         Server server = (Server)this.serverVector.get(i++);
         if (server != null && server.isTargeted()) {
            hasNext = true;
            break;
         }
      }

      return hasNext;
   }

   public Server nextServer() {
      Server next = null;

      while(this.serverIndex < this.serverVector.size()) {
         Server peek = (Server)this.serverVector.get(this.serverIndex++);
         if (peek != null && peek.isTargeted()) {
            next = peek;
            break;
         }
      }

      this.globalRGVector = new Vector(next.getResourceGroups());
      this.sortResourceGroupVector(this.globalRGVector);
      this.globalRGIndex = 0;
      this.partitionAppsVector = new Vector(next.getPartitionApps());
      this.sortPartitionAppsVector(this.partitionAppsVector);
      this.partitionAppsIndex = 0;
      return next;
   }

   public boolean hasNextGlobalResourceGroup() {
      boolean hasNext = false;
      int i = this.globalRGIndex;

      while(i < this.globalRGVector.size()) {
         ResourceGroup resourceGroup = (ResourceGroup)this.globalRGVector.get(i++);
         if (resourceGroup != null && resourceGroup.isTargeted()) {
            hasNext = true;
            break;
         }
      }

      return hasNext;
   }

   public ResourceGroup nextGlobalResourceGroup() {
      ResourceGroup next = null;

      while(this.globalRGIndex < this.globalRGVector.size()) {
         ResourceGroup peek = (ResourceGroup)this.globalRGVector.get(this.globalRGIndex++);
         if (peek != null && peek.isTargeted()) {
            next = peek;
            break;
         }
      }

      return next;
   }

   public boolean hasNextPartitionApps() {
      boolean hasNext = false;
      int i = this.partitionAppsIndex;

      while(i < this.partitionAppsVector.size()) {
         PartitionApps partitionApps = (PartitionApps)this.partitionAppsVector.get(i++);
         if (partitionApps != null && partitionApps.isTargeted()) {
            hasNext = true;
            break;
         }
      }

      return hasNext;
   }

   public PartitionApps nextPartitionApps() {
      PartitionApps next = null;

      while(this.partitionAppsIndex < this.partitionAppsVector.size()) {
         PartitionApps peek = (PartitionApps)this.partitionAppsVector.get(this.partitionAppsIndex++);
         if (peek != null && peek.isTargeted()) {
            next = peek;
            break;
         }
      }

      this.partitionRGVector = new Vector(next.getResourceGroups());
      this.sortResourceGroupVector(this.partitionRGVector);
      this.partitionRGIndex = 0;
      return next;
   }

   public boolean hasNextPartitionResourceGroup() {
      boolean hasNext = false;
      int i = this.partitionRGIndex;

      while(i < this.partitionRGVector.size()) {
         ResourceGroup partitionRG = (ResourceGroup)this.partitionRGVector.get(i++);
         if (partitionRG != null && partitionRG.isTargeted()) {
            hasNext = true;
            break;
         }
      }

      return hasNext;
   }

   public ResourceGroup nextPartitionResourceGroup() {
      ResourceGroup next = null;

      while(this.partitionRGIndex < this.partitionRGVector.size()) {
         ResourceGroup peek = (ResourceGroup)this.partitionRGVector.get(this.partitionRGIndex++);
         if (peek != null && peek.isTargeted()) {
            next = peek;
            break;
         }
      }

      return next;
   }

   public ServerGroup nextServerGroup() {
      ServerGroup next = null;

      while(this.serverGroupIndex < this.serverGroupVector.size()) {
         ServerGroup peek = (ServerGroup)this.serverGroupVector.get(this.serverGroupIndex++);
         if (peek != null && peek.isTargeted()) {
            next = peek;
            break;
         }
      }

      this.serverVector = new Vector(next.getServers().values());
      this.sortServerVector(this.serverVector);
      this.serverIndex = 0;
      return next;
   }

   public Node nextNode() {
      Node next = null;

      while(this.nodeIndex < this.nodeVector.size()) {
         Node peek = (Node)this.nodeVector.get(this.nodeIndex++);
         if (peek != null && peek.isTargeted()) {
            next = peek;
            break;
         }
      }

      this.serverGroupVector = new Vector(next.getServerGroups().values());
      this.sortServerGroupVector(this.serverGroupVector);
      this.serverGroupIndex = 0;
      this.serverVector = null;
      this.serverIndex = 0;
      return next;
   }

   private void sortServerGroupVector(Vector vector) {
      Collections.sort(vector, new Comparator() {
         public int compare(ServerGroup g1, ServerGroup g2) {
            int valuex = false;
            String n1 = g1.getGroupName();
            String n2 = g2.getGroupName();
            int value;
            if ("DEFAULT_GROUP_NAME".equals(n1)) {
               value = -1;
            } else {
               value = n1.compareTo(n2);
            }

            return value;
         }
      });
   }

   private void sortServerVector(Vector vector) {
      Collections.sort(vector, new Comparator() {
         public int compare(Server g1, Server g2) {
            int valuex = false;
            String n1 = g1.getServerName();
            String n2 = g2.getServerName();
            int value = n1.compareTo(n2);
            return value;
         }
      });
   }

   private void sortResourceGroupVector(Vector vector) {
      Collections.sort(vector, new Comparator() {
         public int compare(ResourceGroup g1, ResourceGroup g2) {
            int valuex = false;
            String n1 = g1.getResourceGroupName();
            String n2 = g2.getResourceGroupName();
            int value = n1.compareTo(n2);
            return value;
         }
      });
   }

   private void sortPartitionAppsVector(Vector vector) {
      Collections.sort(vector, new Comparator() {
         public int compare(PartitionApps g1, PartitionApps g2) {
            int valuex = false;
            String n1 = g1.getPartitionName();
            String n2 = g2.getPartitionName();
            int value = n1.compareTo(n2);
            return value;
         }
      });
   }

   public Node skipToNode(String nodeName) {
      Node match = null;

      while(this.hasNextNode()) {
         Node n = this.nextNode();
         if (nodeName.equals(n.getNodeName())) {
            match = n;
            break;
         }
      }

      return match;
   }

   public static String printTargets(DomainModel domainModel) {
      return printTargets(domainModel, RolloutUpdateSettings.RolloutTargetType.DOMAIN);
   }

   public static String printTargets(DomainModel domainModel, RolloutUpdateSettings.RolloutTargetType rolloutTargetType) {
      StringBuilder sb1 = new StringBuilder();
      DomainModelIterator dmi = new DomainModelIterator(domainModel, rolloutTargetType, (String)null);
      sb1.append("\nTargets for domain " + domainModel.getDomainName() + "\n");

      while(dmi.hasNextNode()) {
         Node node = dmi.nextNode();
         sb1.append("\tNode " + node.getNodeName() + " \n");

         while(dmi.hasNextServerGroup()) {
            ServerGroup serverGroup = dmi.nextServerGroup();
            sb1.append("\t\tServerGroup " + serverGroup.getGroupName() + " \n");

            while(dmi.hasNextServer()) {
               Server server = dmi.nextServer();
               sb1.append("\t\t\tServer " + server.getServerName() + " \n");
            }
         }
      }

      return sb1.toString();
   }
}
