package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.management.patching.commands.ServerUtils;

public class ClusterSessionHandlingContext implements Serializable {
   private String clusterName;
   private boolean sessionCompatibility = false;
   private boolean waitForAllSessions = false;
   private boolean waitForAllSessionsOnRevert = true;
   private ArrayList failoverGroups;
   private ArrayList origFailoverGroups;

   public String getClusterName() {
      return this.clusterName;
   }

   public void setClusterName(String clusterName) {
      this.clusterName = clusterName;
   }

   public void setSessionCompatibility(boolean compatiblity) {
      this.sessionCompatibility = compatiblity;
      if (compatiblity) {
         this.waitForAllSessionsOnRevert = false;
      }

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

   private void copyGroups(ArrayList groupsToCopy, ArrayList copy) {
      copy.clear();
      Iterator var3 = groupsToCopy.iterator();

      while(var3.hasNext()) {
         ArrayList group = (ArrayList)var3.next();
         ArrayList copiedGroup = new ArrayList();
         copiedGroup.addAll(group);
         copy.add(group);
      }

   }

   public void updatePatchedServersInContext(List servers) {
      if (this.failoverGroups == null) {
         throw new IllegalStateException("FailoverGroups is null, must be established first");
      } else {
         if (this.origFailoverGroups == null) {
            this.origFailoverGroups = new ArrayList(2);
         }

         this.copyGroups(this.failoverGroups, this.origFailoverGroups);
         ArrayList group1 = (ArrayList)this.failoverGroups.get(0);
         ArrayList group2 = (ArrayList)this.failoverGroups.get(1);
         if (!group1.isEmpty() && !group2.isEmpty() && this.waitForAllSessionsOnRevert) {
            this.waitForAllSessionsOnRevert = false;
         }

         Iterator var4 = servers.iterator();

         while(var4.hasNext()) {
            String serverName = (String)var4.next();
            if (group1.remove(serverName)) {
               group2.add(serverName);
            } else {
               if (!group2.remove(serverName)) {
                  throw new IllegalStateException(serverName + " was not found in any group: " + (new ServerUtils()).failoverGroupsToString(this.failoverGroups));
               }

               group1.add(serverName);
            }
         }

         if (group1.isEmpty() || group2.isEmpty()) {
            this.waitForAllSessions = !this.sessionCompatibility;
         }

      }
   }

   public ClusterInfo createClusterInfo() {
      ClusterInfo clusterInfo = new ClusterInfo();
      clusterInfo.setClusterName(this.clusterName);
      clusterInfo.setWaitForAllSessions(this.waitForAllSessions);
      clusterInfo.setWaitForAllSessionsOnRevert(this.waitForAllSessionsOnRevert);
      if (this.failoverGroups != null) {
         clusterInfo.setPendingServers((ArrayList)this.failoverGroups.get(0));
         clusterInfo.setUpdatingServers((ArrayList)this.failoverGroups.get(1));
      }

      if (this.origFailoverGroups != null) {
         clusterInfo.setOrigPendingServers((ArrayList)this.origFailoverGroups.get(0));
         clusterInfo.setOrigUpdatingServers((ArrayList)this.origFailoverGroups.get(1));
      }

      return clusterInfo;
   }

   private String collectionToString(Collection c) {
      StringBuffer sb = new StringBuffer();
      sb.append("[");
      if (c == null) {
         sb.append("null");
      } else {
         Iterator var3 = c.iterator();

         while(var3.hasNext()) {
            String s = (String)var3.next();
            sb.append(s);
            sb.append(", ");
         }
      }

      sb.append("]");
      return sb.toString();
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
