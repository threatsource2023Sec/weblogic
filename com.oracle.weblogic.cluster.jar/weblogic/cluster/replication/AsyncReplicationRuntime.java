package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.AsyncReplicationRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class AsyncReplicationRuntime extends RuntimeMBeanDelegate implements AsyncReplicationRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public AsyncReplicationRuntime() throws ManagementException {
      super("AsyncReplication");
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setAsyncReplicationRuntime(this);
   }

   public String[] getDetailedSecondariesDistribution() {
      return this.getAsyncReplicationManager().getSecondaryDistributionNames();
   }

   public long getPrimaryCount() {
      return this.getAsyncReplicationManager().getPrimaryCount();
   }

   public long getSecondaryCount() {
      return this.getAsyncReplicationManager().getSecondaryCount();
   }

   public String getSecondaryServerDetails() {
      HostID srvr = this.getAsyncReplicationManager().getSecondarySelector().getSecondarySrvr();
      return srvr != null ? srvr.toString() : "";
   }

   public int getSessionsWaitingForFlushCount() {
      return this.getAsyncReplicationManager().getSessionsWaitingForFlushCount();
   }

   public long getLastSessionsFlushTime() {
      return this.getAsyncReplicationManager().getTimeAtLastUpdateFlush();
   }

   private AsyncReplicationManager getAsyncReplicationManager() {
      return (AsyncReplicationManager)GlobalServiceLocator.getServiceLocator().getService(AsyncReplicationManager.class, new Annotation[0]);
   }
}
