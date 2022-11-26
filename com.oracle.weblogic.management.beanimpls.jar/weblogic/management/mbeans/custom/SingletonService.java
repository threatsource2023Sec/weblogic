package weblogic.management.mbeans.custom;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import weblogic.cluster.singleton.SingletonMonitorRemote;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.utils.Debug;

public class SingletonService extends ConfigurationMBeanCustomizer {
   private transient ServerMBean userPreferredServer;
   private static final ServerMBean[] NO_SERVERS = new ServerMBean[0];

   public SingletonService(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public ServerMBean getHostingServer() {
      SingletonServiceMBean mt = (SingletonServiceMBean)this.getMbean();
      String owner = null;
      ConfigurationMBean c = (ConfigurationMBean)this.getMbean().getParent();
      if (c instanceof ServerMBean) {
         return (ServerMBean)c;
      } else {
         try {
            SingletonMonitorRemote smr = ClusterSingletonMonitorFinder.findRemoteMonitor(mt.getCluster());
            if (smr == null) {
               return this.getUserPreferredServer();
            }

            owner = smr.findServiceLocation(mt.getName());
         } catch (RemoteException var5) {
            return this.getUserPreferredServer();
         }

         if (c instanceof DomainMBean && owner != null) {
            DomainMBean d = (DomainMBean)c;
            return d.lookupServer(owner);
         } else {
            return this.getUserPreferredServer();
         }
      }
   }

   public ServerMBean[] getAllCandidateServers() {
      SingletonServiceMBean mt = (SingletonServiceMBean)this.getMbean();
      if (mt == null) {
         return NO_SERVERS;
      } else if (mt.getConstrainedCandidateServers() != null && mt.getConstrainedCandidateServers().length > 0) {
         return this.moveUserPreferredServerToHead(mt.getConstrainedCandidateServers());
      } else {
         return mt.getCluster() != null && mt.getCluster().getServers().length > 0 ? this.moveUserPreferredServerToHead(mt.getCluster().getServers()) : NO_SERVERS;
      }
   }

   public boolean isManualActiveOn(ServerMBean server) {
      return this.getUserPreferredServer().getName().equals(server.getName());
   }

   public boolean isCandidate(ServerMBean server) {
      ServerMBean[] servers = ((SingletonServiceMBean)this.getMbean()).getAllCandidateServers();

      for(int i = 0; i < servers.length; ++i) {
         if (server.getName().equals(servers[i].getName())) {
            return true;
         }
      }

      return false;
   }

   public ServerMBean getUserPreferredServer() {
      return this.userPreferredServer;
   }

   public void setUserPreferredServer(ServerMBean server) {
      this.userPreferredServer = server;
      if (server != null) {
         ClusterMBean c = server.getCluster();
         SingletonServiceMBean myself = (SingletonServiceMBean)this.getMbean();
         if (myself.getCluster() == null && c != null) {
            myself.setCluster(c);
            ClusterMBean cc = myself.getCluster();
            Debug.assertion(cc != null, "Migratable Target Cluster is null");
            Debug.assertion(c.getName() != null, "Cluster name is null");
            Debug.assertion(c.getName().equals(cc.getName()));
         }
      }

   }

   public Set getServerNames() {
      ServerMBean[] cServers = this.getAllCandidateServers();
      Set serverNames = new HashSet(cServers.length);

      for(int j = 0; j < cServers.length; ++j) {
         serverNames.add(cServers[j].getName());
      }

      return serverNames;
   }

   private ServerMBean[] moveUserPreferredServerToHead(ServerMBean[] servers) {
      if (this.getUserPreferredServer() == null) {
         return servers;
      } else {
         ServerMBean preferredServer = this.getUserPreferredServer();
         String preferredName = preferredServer.getName();
         if (servers[0].getName().equals(preferredName)) {
            return servers;
         } else {
            for(int i = 1; i < servers.length; ++i) {
               ServerMBean server = servers[i];
               if (preferredName.equals(server.getName())) {
                  ServerMBean temp = servers[0];
                  servers[0] = servers[i];
                  servers[i] = temp;
                  return servers;
               }
            }

            ServerMBean[] result = new ServerMBean[servers.length + 1];
            System.arraycopy(servers, 0, result, 1, servers.length);
            result[0] = preferredServer;
            return result;
         }
      }
   }
}
