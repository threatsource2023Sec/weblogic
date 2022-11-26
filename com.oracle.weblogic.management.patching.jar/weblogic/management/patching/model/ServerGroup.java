package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerGroup implements TargetGroup, AdminAware, Serializable {
   protected Node node;
   protected Cluster cluster;
   protected String groupName;
   public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP_NAME";
   HashMap servers;
   private boolean isTargeted;

   public ServerGroup() {
      this("DEFAULT_GROUP_NAME");
   }

   public ServerGroup(String groupName) {
      this.isTargeted = false;
      this.groupName = groupName;
      this.servers = new HashMap();
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

   public void addServer(Server server) {
      if (this.servers.containsKey(server.getServerName())) {
         throw new IllegalArgumentException("ServerGroup already contains this server: " + server.getServerName());
      } else {
         this.servers.put(server.getServerName(), server);
         server.setServerGroup(this);
      }
   }

   public Map getServers() {
      return this.servers;
   }

   public Server getServer(String serverName) {
      return (Server)this.servers.get(serverName);
   }

   public boolean hasServer(String serverName) {
      return this.servers.containsKey(serverName);
   }

   public boolean isAdmin() {
      boolean isAdmin = false;
      Iterator var2 = this.getServers().values().iterator();

      while(var2.hasNext()) {
         Server server = (Server)var2.next();
         if (server.isAdmin()) {
            isAdmin = true;
         }
      }

      return isAdmin;
   }

   public Server getAdminServer() {
      Server adminServer = null;
      Iterator var2 = this.servers.values().iterator();

      while(var2.hasNext()) {
         Server server = (Server)var2.next();
         if (server.isAdmin()) {
            adminServer = server;
         }
      }

      return adminServer;
   }

   public void setTargeted(boolean isTargeted) {
      this.isTargeted = isTargeted;
   }

   public boolean isTargeted() {
      return this.isTargeted;
   }

   public int getNumTargetedMembers() {
      int count = 0;
      Iterator var2 = this.servers.values().iterator();

      while(var2.hasNext()) {
         Server s = (Server)var2.next();
         if (s.isTargeted()) {
            ++count;
         }
      }

      return count;
   }

   public int getNumUntargetedMembers() {
      int count = 0;
      Iterator var2 = this.servers.values().iterator();

      while(var2.hasNext()) {
         Server s = (Server)var2.next();
         if (!s.isTargeted()) {
            ++count;
         }
      }

      return count;
   }

   public void applyTargetedAndPropagateValue() {
      this.setTargeted(true);
      this.node.applyTargetedAndPropagateValue();
      if (this.cluster != null) {
         this.cluster.applyTargetedAndPropagateValue();
      }

   }

   public void setNode(Node node) {
      this.node = node;
   }

   public Node getNode() {
      return this.node;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public String getGroupName() {
      return this.groupName;
   }
}
