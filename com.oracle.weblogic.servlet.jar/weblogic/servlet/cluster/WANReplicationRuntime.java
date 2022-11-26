package weblogic.servlet.cluster;

import java.security.AccessController;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.SecondarySelector;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.cluster.replication.SecondarySelector.Locator;
import weblogic.cluster.replication.SecondarySelector.Locator.SelectorPolicy;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WANReplicationRuntimeMBean;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WANReplicationRuntime extends RuntimeMBeanDelegate implements WANReplicationRuntimeMBean {
   private long numberOfSessionsFlushed = 0L;
   private long numberOfSessionRetrieved = 0L;
   private boolean remoteClusterReachable;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   WANReplicationRuntime(String name) throws ManagementException {
      super(name);
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setWANReplicationRuntime(this);
   }

   public long getNumberOfSessionsFlushedToTheDatabase() {
      return this.numberOfSessionsFlushed;
   }

   public long getNumberOfSessionsRetrievedFromTheDatabase() {
      return this.numberOfSessionRetrieved;
   }

   public void cleanupExpiredSessionsInTheDatabase() {
   }

   public String getSecondaryServerName() {
      SecondarySelector selector = Locator.locate(SelectorPolicy.LOCAL);
      if (selector != null) {
         HostID identity = selector.getSecondarySrvr();
         if (identity != null) {
            return identity.getServerName();
         }
      }

      return "";
   }

   public String getSecondaryServerDetails() {
      return this.getSecondaryServerName();
   }

   public long getPrimaryCount() {
      ReplicationServices services = weblogic.cluster.replication.ReplicationServicesFactory.Locator.locate().getReplicationService(ServiceType.SYNC);
      return services.getPrimaryCount();
   }

   public long getSecondaryCount() {
      ReplicationServices services = weblogic.cluster.replication.ReplicationServicesFactory.Locator.locate().getReplicationService(ServiceType.SYNC);
      return services.getSecondaryCount();
   }

   public String[] getDetailedSecondariesDistribution() {
      ReplicationServices services = weblogic.cluster.replication.ReplicationServicesFactory.Locator.locate().getReplicationService(ServiceType.SYNC);
      return services.getSecondaryDistributionNames();
   }

   public synchronized void incrementNumberOfSessionsFlushedToTheDatabase() {
      ++this.numberOfSessionsFlushed;
   }

   public synchronized void incrementNumberOfSessionsRetrievedFromTheDatabase() {
      ++this.numberOfSessionRetrieved;
   }

   public boolean getRemoteClusterReachable() {
      return this.remoteClusterReachable;
   }

   public void setRemoteClusterReachable(boolean value) {
      this.remoteClusterReachable = value;
   }
}
