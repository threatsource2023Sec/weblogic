package weblogic.management.patching.model;

import java.io.Serializable;

public class ServerInfo implements Serializable, Comparable {
   String serverName;
   String wlsHome;
   String serverStatus;
   String clusterName;
   String machineName;
   String stagingDirectory;
   boolean isAdminServer;

   public ServerInfo(String serverName, String wlsHome, String serverStatus, String machineName) {
      this(serverName, wlsHome, serverStatus, machineName, "");
   }

   public ServerInfo(String serverName, String wlsHome, String serverStatus, String machineName, String stagingDirectory) {
      this.serverName = serverName;
      this.wlsHome = wlsHome;
      this.serverStatus = serverStatus;
      this.machineName = machineName;
      this.isAdminServer = false;
      this.stagingDirectory = stagingDirectory;
   }

   public String getServerName() {
      return this.serverName;
   }

   public void setServerName(String serverName) {
      this.serverName = serverName;
   }

   public String getWlsHome() {
      return this.wlsHome;
   }

   public void setWlsHome(String wlsHome) {
      this.wlsHome = wlsHome;
   }

   public String getServerStatus() {
      return this.serverStatus;
   }

   public void setServerStatus(String serverStatus) {
      this.serverStatus = serverStatus;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public void setClusterName(String clusterName) {
      this.clusterName = clusterName;
   }

   public String getMachineName() {
      return this.machineName;
   }

   public void setMachineName(String machineName) {
      this.machineName = machineName;
   }

   public boolean isAdminServer() {
      return this.isAdminServer;
   }

   public void setAdminServer(boolean isAdminServer) {
      this.isAdminServer = isAdminServer;
   }

   public String getStagingDirectory() {
      return this.stagingDirectory;
   }

   public void setStagingDirectory(String stagingDirectory) {
      this.stagingDirectory = stagingDirectory;
   }

   public int compareTo(ServerInfo other) {
      int result;
      if (other == null) {
         result = 1;
      } else if (this.serverName == null) {
         if (other.serverName == null) {
            result = 0;
         } else {
            result = -1;
         }
      } else {
         result = this.serverName.compareTo(other.serverName);
      }

      return result;
   }

   public String toString() {
      return "ServerInfo: " + super.toString() + " with\n serverName " + this.serverName + "\n wlsHome " + this.wlsHome + "\n serverStatus " + this.serverStatus + "\n clusterName " + this.clusterName + "\n machineName " + this.machineName + "\n stagingDirectory " + this.stagingDirectory + "\n isAdminServer " + this.isAdminServer;
   }
}
