package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.MANReplicationRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class MANReplicationRuntime extends RuntimeMBeanDelegate implements MANReplicationRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected ReplicationServicesImplBase manager;

   public MANReplicationRuntime(String name, ReplicationServicesImplBase mgr) throws ManagementException {
      super(name);
      this.manager = mgr;
      this.registerRuntime();
   }

   protected void registerRuntime() {
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setMANReplicationRuntime(this);
   }

   public String[] getActiveServersInRemoteCluster() {
      return ((MANRemoteClusterSecondarySelector)GlobalServiceLocator.getServiceLocator().getService(MANRemoteClusterSecondarySelector.class, new Annotation[0])).getActiveServersInRemoteCluster();
   }

   public String[] getDetailedSecondariesDistribution() {
      return this.manager.getSecondaryDistributionNames();
   }

   public long getPrimaryCount() {
      return this.manager.getPrimaryCount();
   }

   public long getSecondaryCount() {
      return this.manager.getSecondaryCount();
   }

   public boolean getRemoteClusterReachable() {
      return ((MANRemoteClusterSecondarySelector)GlobalServiceLocator.getServiceLocator().getService(MANRemoteClusterSecondarySelector.class, new Annotation[0])).canReplicateToRemoteCluster();
   }

   public String getSecondaryServerDetails() {
      HostID hostID = ((MANRemoteClusterSecondarySelector)GlobalServiceLocator.getServiceLocator().getService(MANRemoteClusterSecondarySelector.class, new Annotation[0])).getSecondarySrvr();
      return hostID != null ? hostID.toString() : "";
   }

   public String getSecondaryServerName() {
      HostID identity = ((MANRemoteClusterSecondarySelector)GlobalServiceLocator.getServiceLocator().getService(MANRemoteClusterSecondarySelector.class, new Annotation[0])).getSecondarySrvr();
      return identity != null ? identity.getServerName() : "";
   }
}
