package weblogic.management.patching.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ClusterInfo {
   private String clusterName;
   private ArrayList origPendingServers;
   private ArrayList origUpdatingServers;
   private ArrayList pendingServers;
   private ArrayList updatingServers;
   private boolean waitForAllSessions;
   private boolean waitForAllSessionsOnRevert;

   public String getClusterName() {
      return this.clusterName;
   }

   public void setClusterName(String clusterName) {
      this.clusterName = clusterName;
   }

   public ArrayList getPendingServers() {
      return this.pendingServers;
   }

   public void setPendingServers(ArrayList pendingServers) {
      this.pendingServers = pendingServers;
   }

   public ArrayList getUpdatingServers() {
      return this.updatingServers;
   }

   public void setUpdatingServers(ArrayList updatingServers) {
      this.updatingServers = updatingServers;
   }

   public ArrayList getOrigPendingServers() {
      return this.origPendingServers;
   }

   public void setOrigPendingServers(ArrayList origPendingServers) {
      this.origPendingServers = origPendingServers;
   }

   public ArrayList getOrigUpdatingServers() {
      return this.origUpdatingServers;
   }

   public void setOrigUpdatingServers(ArrayList origUpdatingServers) {
      this.origUpdatingServers = origUpdatingServers;
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
      StringBuffer sb = new StringBuffer();
      sb.append("waitForAllSessions ");
      sb.append(this.waitForAllSessions);
      sb.append(", waitForAllSessionsOnRevert ");
      sb.append(this.waitForAllSessionsOnRevert);
      sb.append(", origPendingServers ");
      sb.append(this.collectionToString(this.origPendingServers));
      sb.append(", origUpdatingServers ");
      sb.append(this.collectionToString(this.origUpdatingServers));
      sb.append(", pendingServers ");
      sb.append(this.collectionToString(this.pendingServers));
      sb.append(", updatingServers ");
      sb.append(this.collectionToString(this.updatingServers));
      return sb.toString();
   }
}
