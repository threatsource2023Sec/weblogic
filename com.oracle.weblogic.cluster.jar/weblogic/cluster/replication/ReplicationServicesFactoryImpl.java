package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.server.GlobalServiceLocator;

@Service
public class ReplicationServicesFactoryImpl implements ReplicationServicesFactory {
   private HashMap replicationManagers = new HashMap(4);

   public void postConstruct() {
      this.replicationManagers.put(ServiceType.SYNC, ReplicationManager.class);
      this.replicationManagers.put(ServiceType.ASYNC, AsyncReplicationManager.class);
      this.replicationManagers.put(ServiceType.MAN_SYNC, MANReplicationManager.class);
      this.replicationManagers.put(ServiceType.MAN_ASYNC, MANAsyncReplicationManager.class);
   }

   public ReplicationServices getReplicationService(ReplicationServicesFactory.ServiceType type) {
      return (ReplicationServices)GlobalServiceLocator.getServiceLocator().getService((Class)this.replicationManagers.get(type), new Annotation[0]);
   }

   ReplicationManager getSyncReplicationManager() {
      return (ReplicationManager)GlobalServiceLocator.getServiceLocator().getService(ReplicationManager.class, new Annotation[0]);
   }

   AsyncReplicationManager getAsyncReplicationManager() {
      return (AsyncReplicationManager)GlobalServiceLocator.getServiceLocator().getService(AsyncReplicationManager.class, new Annotation[0]);
   }

   MANReplicationManager getMANSyncReplicationManager() {
      return (MANReplicationManager)GlobalServiceLocator.getServiceLocator().getService(MANReplicationManager.class, new Annotation[0]);
   }

   MANAsyncReplicationManager getMANAsyncReplicationManager() {
      return (MANAsyncReplicationManager)GlobalServiceLocator.getServiceLocator().getService(MANAsyncReplicationManager.class, new Annotation[0]);
   }
}
