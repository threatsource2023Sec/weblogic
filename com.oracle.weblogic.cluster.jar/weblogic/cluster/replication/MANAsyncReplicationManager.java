package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.work.WorkManagerFactory;

@Service
public final class MANAsyncReplicationManager extends AsyncReplicationManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServiceHandle secondarySelectorServiceHandle;

   protected MANAsyncReplicationManager() {
   }

   @PostConstruct
   public void postConstruct() {
      this.secondarySelectorServiceHandle = GlobalServiceLocator.getServiceLocator().getServiceHandle(MANRemoteClusterSecondarySelector.class, new Annotation[0]);
   }

   public void startService() throws ServiceFailureException {
      this.exportRemoteAdapter();
      this.secondarySelectorServiceHandle.getService();
   }

   public void stopService() {
      this.unExportRemoteAdapter();
      this.secondarySelectorServiceHandle.destroy();
   }

   protected void initializeRuntime() {
      try {
         new MANAsyncReplicationRuntime(ManagementService.getRuntimeAccess(kernelId).getServerName(), this);
      } catch (ManagementException var2) {
         throw new AssertionError(var2);
      }
   }

   protected SecondarySelector getSecondarySelector(ResourceGroupKey key) {
      SecondarySelector selector = null;
      synchronized(this.secondarySelectorHashMap) {
         selector = (SecondarySelector)this.secondarySelectorHashMap.get(key);
         if (selector == null) {
            selector = (SecondarySelector)this.secondarySelectorServiceHandle.getService();
            this.setSecondarySelector(key, selector);
         }

         return selector;
      }
   }

   protected ReplicationServicesInternal lookupRemoteReplicationServiceOnHost(ServerIdentity hostID) throws RemoteException {
      try {
         return this.svcLocator.replicationServicesLookup(hostID, ((MANRemoteClusterSecondarySelector)this.secondarySelectorServiceHandle.getService()).getReplicationChannelFor(hostID), RemoteReplicationServicesInternalImpl.class);
      } catch (NamingException var3) {
         throw new RemoteException(var3.getMessage(), var3);
      }
   }

   public void changeSecondary(HostID[] hostids) {
      Iterator maps = this.getWroManager().resourceGroupMap.values().iterator();

      while(maps.hasNext()) {
         ReplicationMap map = (ReplicationMap)maps.next();
         Iterator iter = map.primaries.values().iterator();
         if (iter.hasNext()) {
            WorkManagerFactory.getInstance().getSystem().schedule(new MANReplicationManager.ChangeSecondaryInfo(iter, hostids, this));
         }
      }

   }
}
