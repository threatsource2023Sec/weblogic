package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.RemoteClusterHealthCheckerImpl;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public class ReplicationService extends AbstractServerService {
   @Inject
   @Named("JobSchedulerService")
   private ServerService dependencyOnJobSchedulerService;
   @Inject
   @Named("StartupClassAfterAppsRunningService")
   private ServerService dependencyOnStartupClassAfterAppsRunningService;
   @Inject
   private ReplicationServicesFactoryImpl replicationServicesFactoryImpl;
   @Inject
   private RuntimeAccess runtimeAccess;
   @Inject
   private RemoteClusterHealthCheckerImpl remoteClusterHealthCheckerImpl;

   public void start() throws ServiceFailureException {
      ClusterMBean cluster = this.runtimeAccess.getServer().getCluster();
      if (cluster != null) {
         ReplicationManager syncReplicationManager = this.replicationServicesFactoryImpl.getSyncReplicationManager();
         syncReplicationManager.startService();
         ClusterLogger.logStartingReplicationService("async", cluster.getClusterAddress());
         this.replicationServicesFactoryImpl.getAsyncReplicationManager().startService();
         if (cluster.getClusterType().equals("man")) {
            ClusterLogger.logStartingReplicationService("man", cluster.getRemoteClusterAddress());
            this.replicationServicesFactoryImpl.getMANSyncReplicationManager().startService();
            ClusterLogger.logStartingReplicationService("man-async", cluster.getRemoteClusterAddress());
            this.replicationServicesFactoryImpl.getMANAsyncReplicationManager().startService();
         } else if (cluster.getClusterType().equals("wan")) {
            ClusterLogger.logStartingReplicationService("wan", cluster.getRemoteClusterAddress());
            ((WANPersistenceServiceControl)GlobalServiceLocator.getServiceLocator().getService(WANPersistenceServiceControl.class, new Annotation[0])).startService();
         }

         this.remoteClusterHealthCheckerImpl.start();
         ResourceGroupMigrationManagerImpl.theOne().start();
      }

   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      ClusterMBean cluster = this.runtimeAccess.getServer().getCluster();
      if (cluster != null) {
         ReplicationManager syncReplicationManager = this.replicationServicesFactoryImpl.getSyncReplicationManager();
         ClusterServices clusterServices = Locator.locateClusterServices();
         if (clusterServices != null && clusterServices.isSessionReplicationOnShutdownEnabled()) {
            syncReplicationManager.replicateOnShutdown();
         }

         syncReplicationManager.stopService();
         ClusterLogger.logStoppingReplicationService("async");
         AsyncReplicationManager asyncRepManager = this.replicationServicesFactoryImpl.getAsyncReplicationManager();
         if (clusterServices != null && clusterServices.isSessionReplicationOnShutdownEnabled()) {
            asyncRepManager.replicateOnShutdown();
         }

         asyncRepManager.stopService();
         if (cluster.getClusterType().equals("man")) {
            ClusterLogger.logStoppingReplicationService("man");
            this.replicationServicesFactoryImpl.getMANSyncReplicationManager().stopService();
            ClusterLogger.logStoppingReplicationService("man-async");
            this.replicationServicesFactoryImpl.getMANAsyncReplicationManager().stopService();
         } else if (cluster.getClusterType().equals("wan")) {
            ClusterLogger.logStoppingReplicationService("wan");
            ((WANPersistenceServiceControl)GlobalServiceLocator.getServiceLocator().getService(WANPersistenceServiceControl.class, new Annotation[0])).stopService();
         }

         this.remoteClusterHealthCheckerImpl.stop();
         ResourceGroupMigrationManagerImpl.theOne().stop();
      }

   }
}
