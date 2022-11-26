package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.RemoteClusterMemberManager.Locator;
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
public final class MANReplicationManager extends ReplicationServicesImplBase {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServiceHandle secondarySelectorServiceHandle;

   protected MANReplicationManager() {
      try {
         new MANReplicationRuntime(ManagementService.getRuntimeAccess(kernelId).getServerName(), this);
      } catch (ManagementException var2) {
         throw new AssertionError(var2);
      }
   }

   @PostConstruct
   public void postConstruct() {
      this.secondarySelectorServiceHandle = GlobalServiceLocator.getServiceLocator().getServiceHandle(MANRemoteClusterSecondarySelector.class, new Annotation[0]);
   }

   public void startService() throws ServiceFailureException {
      super.startService();
      Locator.locateRemoteSiteManager().start();
      this.secondarySelectorServiceHandle.getService();
   }

   public void stopService() {
      super.stopService();
      this.secondarySelectorServiceHandle.destroy();
      Locator.locateRemoteSiteManager().stop();
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
      } catch (NamingException var4) {
         throw new RemoteException(var4.getMessage(), var4);
      }
   }

   void changeSecondary(HostID[] hostids) {
      Iterator maps = this.getWroManager().resourceGroupMap.values().iterator();

      while(maps.hasNext()) {
         ReplicationMap map = (ReplicationMap)maps.next();
         Iterator iter = map.primaries.values().iterator();
         if (iter.hasNext()) {
            WorkManagerFactory.getInstance().getSystem().schedule(new ChangeSecondaryInfo(iter, hostids, this));
         }
      }

   }

   static class ChangeSecondaryInfo implements Runnable {
      private final Iterator iterator;
      private final HostID[] hostIDs;
      private final HashMap hostIDToROIDMap;
      private final ReplicationServicesImplBase mngr;

      ChangeSecondaryInfo(Iterator iterator, HostID[] hostIDs, ReplicationServicesImplBase manager) {
         this.iterator = iterator;
         this.hostIDs = hostIDs;
         this.hostIDToROIDMap = new HashMap();
         this.mngr = manager;
      }

      public void run() {
         int i = 0;
         long start = System.currentTimeMillis();

         for(int j = 0; j < this.hostIDs.length; ++j) {
            ArrayList list = (ArrayList)this.hostIDToROIDMap.get(this.hostIDs[j]);
            if (list == null) {
               list = new ArrayList();
               this.hostIDToROIDMap.put(this.hostIDs[j], list);
            }

            while(this.iterator.hasNext()) {
               WrappedRO wro = (WrappedRO)this.iterator.next();
               if (this.hostIDs[j].equals(wro.getOtherHost())) {
                  list.add(wro.getROInfo().getROID());
                  wro.setOtherHost((HostID)null);
                  wro.setOtherHostInfo((Object)null);
                  ++i;
               }
            }
         }

         long end = System.currentTimeMillis();

         for(int j = 0; j < this.hostIDs.length; ++j) {
            ArrayList list = (ArrayList)this.hostIDToROIDMap.get(this.hostIDs[j]);
            int size = list.size();
            if (size > 0) {
               try {
                  ReplicationServicesInternal services = this.mngr.getRepMan(this.hostIDs[j]);
                  ROID[] ids = new ROID[size];
                  list.toArray(ids);
                  services.remove(ids);
               } catch (RemoteException var11) {
               }
            }
         }

         if (i > 0 && ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("Changed the status of " + i + " objects and it took " + (end - start) + " ms");
         }

      }
   }
}
