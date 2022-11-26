package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import weblogic.management.patching.ApplicationProperties;

public class Server implements AdminAware, Serializable {
   public static final String orphanedMachineName = "?ORPHANED?";
   private String serverName;
   private boolean isTargeted;
   private ServerInfo serverInfo;
   private ServerGroup serverGroup;
   private Cluster cluster;
   private List partitionAppsList;
   private List resourceGroupList;
   private List applicationPropertyList;
   private NodeManagerRelation nmRelation;

   public NodeManagerRelation getNmRelation() {
      return this.nmRelation;
   }

   public void setNmRelation(NodeManagerRelation nmRelation) {
      this.nmRelation = nmRelation;
   }

   public Server(String serverName) {
      this.isTargeted = false;
      this.nmRelation = NodeManagerRelation.REGISTERED;
      this.serverName = serverName;
      this.init();
   }

   public Server(ServerInfo serverInfo) {
      this(serverInfo.getServerName());
      this.serverInfo = serverInfo;
      this.init();
   }

   private void init() {
      this.partitionAppsList = new ArrayList();
      this.resourceGroupList = new ArrayList();
      this.applicationPropertyList = new ArrayList();
   }

   public String getServerName() {
      return this.serverName;
   }

   public ServerInfo getServerInfo() {
      return this.serverInfo;
   }

   public boolean isAdmin() {
      boolean isAdmin = false;
      if (this.serverInfo != null) {
         isAdmin = this.serverInfo.isAdminServer;
      }

      return isAdmin;
   }

   public void setTargeted(boolean isTargeted) {
      this.isTargeted = isTargeted;
   }

   public boolean isTargeted() {
      return this.isTargeted;
   }

   public void applyTargetedAndPropagateValue() {
      this.setTargeted(true);
      this.serverGroup.applyTargetedAndPropagateValue();
   }

   public void setServerGroup(ServerGroup serverGroup) {
      this.serverGroup = serverGroup;
   }

   public ServerGroup getServerGroup() {
      return this.serverGroup;
   }

   public boolean isMemberOfCluster(String clusterName) {
      boolean isMember = false;
      if (this.cluster != null && clusterName != null) {
         isMember = clusterName.equals(this.cluster.getClusterName());
      }

      return isMember;
   }

   public void setCluster(Cluster cluster) {
      this.cluster = cluster;
   }

   public Cluster getCluster() {
      return this.cluster;
   }

   public List getPartitionApps() {
      return this.partitionAppsList;
   }

   public void addPartitionApps(PartitionApps partitionApps) {
      this.partitionAppsList.add(partitionApps);
   }

   public List getResourceGroups() {
      return this.resourceGroupList;
   }

   public void addResourceGroup(ResourceGroup resourceGroup) {
      this.resourceGroupList.add(resourceGroup);
   }

   public List getApplicationPropertyList() {
      return this.applicationPropertyList;
   }

   public void addApplicationProperties(ApplicationProperties app) {
      this.applicationPropertyList.add(app);
   }

   public boolean hasPartitionApps(String partitionName) {
      Optional partitionApps = this.partitionAppsList.stream().filter((p) -> {
         return p.getPartitionName().equals(partitionName);
      }).findFirst();
      return partitionApps.isPresent();
   }

   public PartitionApps getPartitionApps(String partitionName) {
      Optional partitionApps = this.partitionAppsList.stream().filter((p) -> {
         return p.getPartitionName().equals(partitionName);
      }).findFirst();
      return (PartitionApps)partitionApps.get();
   }
}
