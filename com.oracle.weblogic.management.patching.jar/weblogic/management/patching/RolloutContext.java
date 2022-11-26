package weblogic.management.patching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RolloutContext {
   Set updatedServers = new HashSet();
   Set pendingServers = new HashSet();
   Set remainingServers = new HashSet();
   Set clustersToFinalize = new HashSet();
   Set clustersToInitialize = new HashSet();
   Set buildTimeUpdatedNodes = new HashSet();
   Set buildTimeRemainingNodes = new HashSet();
   Map serverSessionInfoMap = new HashMap();
   Map clusterSessionInfoMap = new HashMap();
   Map clustersByMachine = new HashMap();
   Map migrationInfo = new HashMap();
   RolloutUpdateSettings rolloutUpdateSettings;

   public Map getMigrationInfo() {
      return this.migrationInfo;
   }

   public void setMigrationInfo(Map migrationInfo) {
      this.migrationInfo = migrationInfo;
   }

   public RolloutContext(RolloutUpdateSettings rolloutUpdateSettings) {
      this.rolloutUpdateSettings = rolloutUpdateSettings;
   }

   public ClusterSessionContext getSessionInfoForServer(String serverName) {
      return (ClusterSessionContext)this.serverSessionInfoMap.get(serverName);
   }

   public ClusterSessionContext getSessionInfoForCluster(String clusterName) {
      return (ClusterSessionContext)this.clusterSessionInfoMap.get(clusterName);
   }

   public RolloutUpdateSettings getRolloutUpdateSettings() {
      return this.rolloutUpdateSettings;
   }

   public void addTargetedServer(String serverName) {
      this.remainingServers.add(serverName);
   }

   public void addTargetedMachine(String machineName) {
      this.clustersByMachine.put(machineName, new HashSet());
      this.buildTimeRemainingNodes.add(machineName);
   }

   public void addClusterForMachine(String machineName, String clusterName) {
      Set clusterSet = (Set)this.clustersByMachine.get(machineName);
      if (clusterSet == null) {
         clusterSet = new HashSet();
         this.clustersByMachine.put(machineName, new HashSet());
      }

      ((Set)clusterSet).add(clusterName);
   }

   public Set getClustersForMachine(String machineName) {
      return (Set)this.clustersByMachine.get(machineName);
   }

   public void addClusterSessionContext(String clusterName, String serverName, ClusterSessionContext clusterSessionContext) {
      if (clusterName != null) {
         this.clusterSessionInfoMap.put(clusterName, clusterSessionContext);
      }

      this.serverSessionInfoMap.put(serverName, clusterSessionContext);
   }

   public void addServerSessionContext(String serverName, ClusterSessionContext clusterSessionContext) {
      this.serverSessionInfoMap.put(serverName, clusterSessionContext);
   }

   public Set getTargetedClusters() {
      return this.clusterSessionInfoMap.keySet();
   }

   public Set getTargetedClusterServers() {
      return this.serverSessionInfoMap.keySet();
   }

   public Set getUpdatedServers() {
      return this.updatedServers;
   }

   public Set getPendingServers() {
      return this.pendingServers;
   }

   public Set getRemainingServers() {
      return this.remainingServers;
   }

   public Set getClustersToFinalize() {
      return this.clustersToFinalize;
   }

   public Set getClustersToInitialize() {
      return this.clustersToInitialize;
   }

   public Set getBuildTimeUpdatedNodes() {
      return this.buildTimeUpdatedNodes;
   }

   public void setBuildTimeUpdatedNodes(Set buildTimeUpdatedNodes) {
      this.buildTimeUpdatedNodes = buildTimeUpdatedNodes;
   }

   public Set getBuildTimeRemainingNodes() {
      return this.buildTimeRemainingNodes;
   }

   public void setBuildTimeRemainingNodes(Set buildTimeRemainingNodes) {
      this.buildTimeRemainingNodes = buildTimeRemainingNodes;
   }
}
