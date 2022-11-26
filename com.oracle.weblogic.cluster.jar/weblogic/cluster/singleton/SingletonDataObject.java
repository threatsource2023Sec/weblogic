package weblogic.cluster.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.cluster.ClusterHelper;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterService;
import weblogic.management.configuration.ServerMBean;

class SingletonDataObject {
   String name;
   String appName;
   HashMap targets;
   boolean jta;
   String versionId;

   public SingletonDataObject(String name) {
      this.name = name;
      this.jta = false;
   }

   public SingletonDataObject(String name, boolean jta) {
      this.name = name;
      this.jta = jta;
   }

   public SingletonDataObject(String name, String appName, String verId, List targets) {
      this.name = name;
      this.appName = appName;
      this.versionId = verId;
      this.setTargets(targets);
      this.jta = false;
   }

   public boolean isJTA() {
      return this.jta;
   }

   public String getName() {
      return this.name;
   }

   public String getAppName() {
      return this.appName;
   }

   public boolean isAppScopedSingleton() {
      return this.appName != null;
   }

   public List getApplicationScopedTargets() {
      HashMap copyOfTargets = this.copyTargets();
      int idxFirstDot = this.name.indexOf(".");
      String serviceName = this.name;
      if (this.versionId != null) {
         int idxVersionStart = serviceName.lastIndexOf(this.versionId);
         serviceName = idxVersionStart < 1 ? serviceName : serviceName.substring(0, idxVersionStart - 1);
      }

      String partitionName = ClusterHelper.extractPartitionName(serviceName);
      if (partitionName != null) {
         String partitionId = ClusterHelper.getPartitionIdFromName(partitionName);
         Collection activeServers = ClusterService.getClusterServiceInternal().getClusterMemberInfoWithActivePartition(partitionId);
         List servers = this.getServerMBeansFromActiveServerList(copyOfTargets, activeServers);
         return servers;
      } else {
         return this.getTargets(copyOfTargets);
      }
   }

   private List getTargets(HashMap copyOfTargets) {
      Collection activeServers = ClusterService.getClusterServiceInternal().getRemoteMembers();
      activeServers.add(ClusterService.getClusterServiceInternal().getLocalMember());
      List servers = this.getServerMBeansFromActiveServerList(copyOfTargets, activeServers);
      return servers;
   }

   private HashMap copyTargets() {
      synchronized(this) {
         HashMap copyOfTargets = this.targets != null ? new HashMap(this.targets) : new HashMap();
         return copyOfTargets;
      }
   }

   protected List getServerMBeansFromActiveServerList(HashMap targets, Collection activeServers) {
      List servers = new ArrayList();
      Iterator var4 = activeServers.iterator();

      while(var4.hasNext()) {
         ClusterMemberInfo member = (ClusterMemberInfo)var4.next();
         ServerMBean server = (ServerMBean)targets.get(member.serverName());
         if (server != null) {
            servers.add(server);
         }
      }

      return servers;
   }

   public synchronized void setTargets(List newTargets) {
      if (newTargets != null) {
         this.targets = new HashMap();
         Iterator var2 = newTargets.iterator();

         while(var2.hasNext()) {
            ServerMBean server = (ServerMBean)var2.next();
            this.targets.put(server.getName(), server);
         }
      }

   }

   public String toString() {
      return "SingletonDataObject[name:" + this.name + "; appName:" + this.appName + "; jta:" + this.jta + "; targets:" + (this.isAppScopedSingleton() ? this.getApplicationScopedTargets() : this.getTargets(this.copyTargets())) + "]";
   }
}
