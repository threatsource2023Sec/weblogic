package weblogic.cluster.replication;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.management.ManagementException;
import weblogic.rmi.ServerShuttingDownException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.spi.HostID;

@Service
public class AsyncReplicationManager extends ReplicationServicesImplBase implements AsyncFlush {
   private static HashMap queues;
   private static final Object queueItrLock = new Object();
   @Inject
   private QueueManagerFactory queueManagerFactory;

   protected AsyncReplicationManager() {
   }

   @PostConstruct
   private void postConstruct() {
      queues = new HashMap();
      this.initializeRuntime();
   }

   protected void initializeRuntime() {
      try {
         new AsyncReplicationRuntime();
      } catch (ManagementException var2) {
         throw new AssertionError(var2);
      }
   }

   public long getTimeAtLastUpdateFlush() {
      return this.getQueue().getTimeAtLastUpdateFlush();
   }

   public int getSessionsWaitingForFlushCount() {
      return this.getQueue().getQueueSize();
   }

   public void replicateOnShutdown() {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("AsyncReplicationManager.replicateOnShutdown");
      }

      this.blockingFlush();
      super.replicateOnShutdown();
   }

   public void ensureFullStateReplicated(List roids) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("AsyncReplicationManager.ensureFullStateReplicated");
      }

      this.blockingFlush();
      super.ensureFullStateReplicated(roids);
   }

   public void localCleanupOnPartitionShutdown(List roids, Object key) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("AsyncReplicationManager.localCleanupOnPartitionShutdown");
      }

   }

   public void stopService() {
      super.stopService();
      this.blockingFlush();
   }

   public void unregister(ROID[] ids, Object key) {
      this.removeFromQueue(ids);
      super.unregister(ids, key);
   }

   public void removeFromQueue(ROID[] ids) {
      synchronized(queueItrLock) {
         Iterator var3 = queues.values().iterator();

         while(var3.hasNext()) {
            AsyncQueueManager queue = (AsyncQueueManager)var3.next();

            for(int i = 0; i < ids.length; ++i) {
               Iterator theUpdates = queue.iterator();

               while(theUpdates.hasNext()) {
                  AsyncUpdate tmpUpdate = (AsyncUpdate)theUpdates.next();
                  if (tmpUpdate.getId().equals(ids[i])) {
                     queue.remove(tmpUpdate);
                  }
               }
            }
         }

      }
   }

   public Object updateSecondary(ROID id, Serializable change, Object key) throws NotFoundException {
      ResourceGroupKey resourceGroupKey = getResourceGroupKey();
      WrappedRO wro = this.getPrimary(id, false, key, resourceGroupKey);
      AsyncReplicatable aro = (AsyncReplicatable)change;
      wro.incrementVersion(key);
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug("Adding a new update: " + aro.getBatchedChanges() + " to the queue for " + id + " with version: " + wro.getVersion(key) + " and key: " + key);
      }

      this.getQueue(resourceGroupKey).addToUpdates(new AsyncUpdate(id, wro.getVersion(key), aro, key, resourceGroupKey));
      return wro.getSecondaryROInfo();
   }

   public void blockingFlush() {
      Iterator var1 = queues.values().iterator();

      while(var1.hasNext()) {
         AsyncQueueManager queue = (AsyncQueueManager)var1.next();
         queue.flushOnce();
      }

   }

   public void sync() {
      this.blockingFlush();
   }

   protected void createSecondary(WrappedRO wro, Object key) {
      if (key == null) {
         Iterator iter = wro.getMap().keySet().iterator();

         while(iter.hasNext()) {
            Object iterKey = iter.next();
            if (iterKey != null) {
               this.createSecondary(wro, iterKey);
            }
         }

      } else {
         Replicatable repObj = wro.getRO(key);
         HostID currentSecondary = this.getSecondarySelector(wro.getResourceGroupKey()).getSecondarySrvr();
         wro.setOtherHost(currentSecondary);
         wro.setOtherHostInfo(currentSecondary);
         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug(wro.getID(), "Adding a new create to the queue with version: " + wro.getVersion(key) + " and key: " + key + " target for currentSecondary: " + wro.getSecondaryROInfo());
         }

         this.getQueue(wro.getResourceGroupKey()).addToUpdates(new AsyncUpdate(LOCAL_HOSTID, wro.getID(), wro.getVersion(key), key, repObj, wro.getResourceGroupKey()));
      }
   }

   public synchronized void flushQueue(BlockingQueue updateSet, ResourceGroupKey resourceGroupKey) {
      Set set = new LinkedHashSet();
      synchronized(queueItrLock) {
         updateSet.drainTo(set);
      }

      this.flush(set, resourceGroupKey);
   }

   private void prepareFlush(Set set, HostID currentSecondary) {
      WroManager wroManager = this.getWroManager();
      Iterator updates = set.iterator();

      while(true) {
         while(updates.hasNext()) {
            AsyncUpdate update = (AsyncUpdate)updates.next();
            WrappedRO wro = wroManager.find(update.getId());
            if (wro == null) {
               if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                  ReplicationDetailsDebugLogger.debug("Problem setting new secondary for " + update.getId());
               }
            } else if (wro.getOtherHost() == null || !wro.getOtherHost().equals(currentSecondary)) {
               update.recreate(LOCAL_HOSTID, update.getVersion());
               wro.setOtherHost(currentSecondary);
               wro.setOtherHostInfo(currentSecondary);
            }
         }

         return;
      }
   }

   public void flush(Set set, ResourceGroupKey resourceGroupKey) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("FLUSH");
      }

      HostID currentSecondary = this.getSecondarySelector(resourceGroupKey).getSecondarySrvr();

      while(currentSecondary != null) {
         try {
            this.prepareFlush(set, currentSecondary);
            ReplicationServicesInternal secondaryRepMan = this.getRepMan(currentSecondary);
            int size = set.size();
            AsyncUpdate[] updates = new AsyncUpdate[size];
            set.toArray(updates);
            AsyncBatch state = new AsyncBatch(updates);
            secondaryRepMan.update(state);
            resetTimeOut(currentSecondary);
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug("AsyncBatch flushed to " + currentSecondary);
            }

            return;
         } catch (ConnectException var8) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)("Failed to reach secondary server " + currentSecondary + " trying to create/update secondary for batch"), (Throwable)var8);
            }

            this.cleanupDeadServer(currentSecondary);
         } catch (ServerShuttingDownException var9) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)("Secondary server " + currentSecondary + " is shutting down. Failed to create/update secondary '" + currentSecondary + "' for batch"), (Throwable)var9);
            }

            this.cleanupDeadServer(currentSecondary);
         } catch (ConnectIOException var10) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)("ConnectIOException while trying to connect to secondary server " + currentSecondary + " for creating/updating secondary '" + currentSecondary + "' for batch"), (Throwable)var10);
            }

            this.cleanupDeadServer(currentSecondary);
         } catch (MarshalException var11) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)"Marshalling error: ", (Throwable)var11);
            }

            ClusterLogger.logUnableToUpdateNonSerializableObject(var11);
            if (var11.detail instanceof NotSerializableException) {
               this.decrementUpdates(set);
            }

            return;
         } catch (UnmarshalException var12) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)"Unmarshalling error: ", (Throwable)var12);
            }

            ClusterLogger.logUnableToUpdateNonSerializableObject(var12);
            if (var12.detail instanceof NotSerializableException) {
               this.decrementUpdates(set);
            }

            return;
         } catch (AsyncBatchFailedException var13) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("AsyncBatchFailed updating secondary for batch on " + currentSecondary + ". Re-creating secondaries.");
            }

            set = this.recreateList(var13.getIDs(), set);
            this.flush(set, resourceGroupKey);
            return;
         } catch (RemoteRuntimeException var14) {
            ClusterExtensionLogger.logUnexpectedExceptionDuringReplication(var14.getCause());
            return;
         } catch (RequestTimeoutException var15) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)("Error sending batched sessions on " + currentSecondary), (Throwable)var15);
            }

            this.cleanupDeadServer(currentSecondary);
         } catch (RemoteException var16) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)("Error creating secondary for batched sessions on " + currentSecondary), (Throwable)var16);
            }

            if (var16 instanceof ApplicationUnavailableRemoteException) {
               return;
            }
         }

         currentSecondary = this.getSecondarySelector().getSecondarySrvr();
         if (currentSecondary != null && ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("Recreating secondaries on " + currentSecondary);
         }
      }

      WroManager wroManager = this.getWroManager();
      Iterator updates = set.iterator();

      while(updates.hasNext()) {
         AsyncUpdate update = (AsyncUpdate)updates.next();
         WrappedRO wro = wroManager.find(update.getId());
         if (wro != null) {
            wro.setOtherHost((HostID)null);
            wro.setOtherHostInfo((Object)null);
         }
      }

      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("Unable to create secondarys for async batch " + set);
      }

   }

   private Set recreateList(ROID[] ids, Set updates) {
      Set createSet = new HashSet();
      AsyncUpdate[] asyncUpdates = new AsyncUpdate[updates.size()];
      asyncUpdates = (AsyncUpdate[])((AsyncUpdate[])updates.toArray(asyncUpdates));

      for(int i = 0; i < ids.length; ++i) {
         for(int j = asyncUpdates.length; j > 0; --j) {
            AsyncUpdate tmpUpdate = asyncUpdates[j - 1];
            if (tmpUpdate.getId().equals(ids[i])) {
               createSet.add(tmpUpdate);
               tmpUpdate.recreate(LOCAL_HOSTID, tmpUpdate.getVersion());
               break;
            }
         }
      }

      return createSet;
   }

   private void decrementUpdates(Set updates) {
      WroManager wroManager = this.getWroManager();
      Iterator theUpdates = updates.iterator();

      while(theUpdates.hasNext()) {
         AsyncUpdate tmpUpdate = (AsyncUpdate)theUpdates.next();
         if (tmpUpdate.isUpdate()) {
            WrappedRO wro = wroManager.find(tmpUpdate.getId());
            Serializable s = tmpUpdate.getRO();
            Replicatable ro;
            if (s instanceof WrappedSerializable) {
               ro = (Replicatable)((WrappedSerializable)s).serializable;
            } else {
               ro = (Replicatable)s;
            }

            if (wro != null) {
               wro.decrementVersion(ro.getKey());
            }
         }
      }

   }

   protected AsyncQueueManager getQueue() {
      return this.getQueue(getResourceGroupKey());
   }

   protected AsyncQueueManager getQueue(ResourceGroupKey key) {
      synchronized(queues) {
         AsyncQueueManager queue = (AsyncQueueManager)queues.get(key);
         if (queue == null) {
            queue = (AsyncQueueManager)this.queueManagerFactory.newAsyncQueue(this, true);
            queue.setResourceGroupKey(key);
            queues.put(key, queue);
         }

         return queue;
      }
   }

   protected boolean supportsAsyncBatchUpdates() {
      return true;
   }
}
