package weblogic.cluster.replication;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.MANAsyncReplicationRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class MANAsyncReplicationRuntime extends MANReplicationRuntime implements MANAsyncReplicationRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MANAsyncReplicationRuntime(String name, ReplicationServicesImplBase mngr) throws ManagementException {
      super(name, mngr);
   }

   protected void registerRuntime() {
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setMANAsyncReplicationRuntime(this);
   }

   public int getSessionsWaitingForFlushCount() {
      return ((MANAsyncReplicationManager)this.manager).getSessionsWaitingForFlushCount();
   }

   public long getLastSessionsFlushTime() {
      return ((MANAsyncReplicationManager)this.manager).getTimeAtLastUpdateFlush();
   }
}
