package weblogic.management.mbeans.custom;

import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public final class JTAMigratableTarget extends MigratableTarget {
   private transient ServerMBean userPreferredServer;
   private transient ServerMBean[] candidateServers;
   private static final ServerMBean[] NO_SERVERS = new ServerMBean[0];

   public JTAMigratableTarget(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setConstrainedCandidateServers(ServerMBean[] servers) {
      if (!(this.getMbean().getParent() instanceof ServerMBean)) {
         this.candidateServers = NO_SERVERS;
      } else {
         if (servers != null && servers.length > 0) {
            this.candidateServers = servers;
         } else {
            this.candidateServers = NO_SERVERS;
         }

      }
   }

   public ServerMBean[] getConstrainedCandidateServers() {
      if (!(this.getMbean().getParent() instanceof ServerMBean)) {
         return NO_SERVERS;
      } else if (this.candidateServers != null) {
         return this.candidateServers;
      } else {
         ServerMBean server = (ServerMBean)((ServerMBean)this.getMbean().getParent());
         ClusterMBean cluster = null;
         if (server != null) {
            cluster = server.getCluster();
         }

         return cluster != null ? cluster.getServers() : NO_SERVERS;
      }
   }

   public ClusterMBean getCluster() {
      if (this.getMbean().getParent() != null) {
         if (!(this.getMbean().getParent() instanceof ServerMBean)) {
            ServerTemplateMBean serverTemplateMBean = (ServerTemplateMBean)((ServerTemplateMBean)this.getMbean().getParent());
            return serverTemplateMBean.getCluster();
         } else {
            ServerMBean server = (ServerMBean)((ServerMBean)this.getMbean().getParent());
            return server.getCluster();
         }
      } else {
         return null;
      }
   }

   public void setUserPreferredServer(ServerMBean server) {
      if (!(this.getMbean().getParent() instanceof ServerMBean)) {
         this.userPreferredServer = null;
      } else {
         this.userPreferredServer = server;
      }
   }

   public ServerMBean getUserPreferredServer() {
      if (!(this.getMbean().getParent() instanceof ServerMBean)) {
         return null;
      } else {
         return this.userPreferredServer != null ? this.userPreferredServer : (ServerMBean)this.getMbean().getParent();
      }
   }
}
