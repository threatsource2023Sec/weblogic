package weblogic.management.patching;

import java.util.ArrayList;
import java.util.Set;
import weblogic.management.patching.commands.ServerUtils;

class ClusterSessionContext {
   private String clusterName;
   private ArrayList origPendingServers;
   private ArrayList origUpdatingServers;
   private ArrayList pendingServers;
   private ArrayList updatingServers;
   private boolean waitForAllSessions;
   private boolean waitForAllSessionsOnRevert;
   private ArrayList failoverGroups;
   private ArrayList origFailoverGroups;
   private Set partitions;

   public String getClusterName() {
      return this.clusterName;
   }

   public void setClusterName(String clusterName) {
      this.clusterName = clusterName;
   }

   public boolean getWaitForAllSessions() {
      return this.waitForAllSessions;
   }

   public void setWaitForAllSessions(boolean waitForAllSessions) {
      this.waitForAllSessions = waitForAllSessions;
   }

   public boolean getWaitForAllSessionsOnRevert() {
      return this.waitForAllSessionsOnRevert;
   }

   public void setWaitForAllSessionsOnRevert(boolean waitForAllSessionsOnRevert) {
      this.waitForAllSessionsOnRevert = waitForAllSessionsOnRevert;
   }

   public ArrayList getFailoverGroups() {
      return this.failoverGroups;
   }

   public void setFailoverGroups(ArrayList failoverGroups) {
      this.failoverGroups = failoverGroups;
   }

   public ArrayList getOrigFailoverGroups() {
      return this.origFailoverGroups;
   }

   public void setOrigFailoverGroups(ArrayList origFailoverGroups) {
      this.origFailoverGroups = origFailoverGroups;
   }

   public Set getPartitions() {
      return this.partitions;
   }

   public void setPartitions(Set partitions) {
      this.partitions = partitions;
   }

   public String toString() {
      ServerUtils serverUtils = new ServerUtils();
      StringBuffer sb = new StringBuffer();
      sb.append("waitForAllSessions ");
      sb.append(this.waitForAllSessions);
      sb.append(", waitForAllSessionsOnRevert ");
      sb.append(this.waitForAllSessionsOnRevert);
      sb.append(", failoverGroups: ");
      sb.append(serverUtils.failoverGroupsToString(this.failoverGroups));
      sb.append(", origFailoverGroups: ");
      sb.append(serverUtils.failoverGroupsToString(this.origFailoverGroups));
      return sb.toString();
   }
}
