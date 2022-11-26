package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Cluster implements Serializable, TargetGroup {
   private ClusterSessionHandlingContext clusterSessionHandlingContext;
   DomainModel domainModel;
   HashMap servers;
   HashMap nodes;
   String clusterName;
   private boolean isTargeted = false;

   public Cluster(String clusterName) {
      this.clusterName = clusterName;
      this.nodes = new HashMap();
      this.servers = new HashMap();
   }

   public Cluster(ClusterSessionHandlingContext clusterSessionHandlingContext) {
      clusterSessionHandlingContext.getClusterName();
      this.clusterSessionHandlingContext = clusterSessionHandlingContext;
      this.servers = new HashMap();
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public ClusterSessionHandlingContext getClusterSessionHandlingContext() {
      return this.clusterSessionHandlingContext;
   }

   public void setDomainModel(DomainModel domainModel) {
      this.domainModel = domainModel;
   }

   public DomainModel getDomainModel() {
      return this.domainModel;
   }

   public void addServer(Server server) {
      if (this.servers.containsKey(server.getServerName())) {
         throw new IllegalArgumentException("ServerGroup already contains this server: " + server.getServerName());
      } else {
         this.servers.put(server.getServerName(), server);
         server.setCluster(this);
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

   public int countNodes() {
      HashSet nodeNames = new HashSet();
      if (this.servers != null && this.servers.size() > 0) {
         Iterator var2 = this.servers.values().iterator();

         while(var2.hasNext()) {
            Server s = (Server)var2.next();
            String nodeName = s.getServerGroup().getNode().getNodeName();
            if (!nodeNames.contains(nodeName)) {
               nodeNames.add(nodeName);
            }
         }
      }

      return nodeNames.size();
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
   }

   public void addNode(Node node) {
      if (this.nodes.containsKey(node.getNodeName())) {
         throw new IllegalArgumentException("Cluster already contains this node: " + node.getNodeName());
      } else {
         this.nodes.put(node.getNodeName(), node);
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
}
