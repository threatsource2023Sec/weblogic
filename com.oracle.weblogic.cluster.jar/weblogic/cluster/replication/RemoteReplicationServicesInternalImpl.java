package weblogic.cluster.replication;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterService;
import weblogic.cluster.ClusterServicesInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.HostID;

class RemoteReplicationServicesInternalImpl implements ReplicationServicesInternal {
   private ReplicationServicesImplBase localReplicationManager;
   private static final int MAX_LOG_MESSAGES = 1000;
   private static int counter;

   public RemoteReplicationServicesInternalImpl(ReplicationServicesImplBase localReplicationManager) {
      this.localReplicationManager = localReplicationManager;
   }

   void export() throws RemoteException {
      if (ClusterService.getClusterServiceInternal().isReplicationTimeoutEnabled()) {
         ServerHelper.exportObject(this, ClusterService.getClusterServiceInternal().getHeartbeatTimeoutMillis());
      } else {
         ServerHelper.exportObject(this);
      }

   }

   void unExport() throws RemoteException {
      ServerHelper.unexportObject(this, false);
   }

   public final Object create(HostID primaryHostID, int version, ROID id, Serializable ro) throws RemoteException {
      if (ro == null) {
         throw new RemoteException("Got a Null session object for id " + id + " version " + version + " from primary " + primaryHostID.toString());
      } else {
         ResourceGroupKeyImpl resourceGroupKey = null;
         Replicatable replicatable;
         if (ro instanceof WrappedSerializable) {
            resourceGroupKey = (ResourceGroupKeyImpl)((WrappedSerializable)ro).resourceGroupKey;
            replicatable = (Replicatable)((WrappedSerializable)ro).serializable;
         } else {
            resourceGroupKey = (ResourceGroupKeyImpl)ReplicationServicesImplBase.getResourceGroupKey();
            replicatable = (Replicatable)ro;
         }

         if (replicatable == null) {
            throw new RemoteException("Got a Null replicatable object for id " + id + " version " + version + " from primary " + primaryHostID.toString());
         } else {
            ReplicationServicesImplBase.resetTimeOut(primaryHostID);
            WrappedRO wro = this.localReplicationManager.getWroManager().find(id, resourceGroupKey);
            Object obj;
            if (wro != null) {
               int currentVersion = -1;
               if (replicatable != null) {
                  obj = replicatable.getKey();
                  if (obj == null) {
                     throw new RemoteException("Got a Null key for id " + id + " version " + version + " from primary " + primaryHostID.toString());
                  }

                  currentVersion = wro.getVersion(obj);
               }

               if (currentVersion > version) {
                  if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                     ReplicationDetailsDebugLogger.debug(id, "Received stale update request with new version " + version + " from primary: " + primaryHostID + " Existing Object info\n\totherhost " + (wro.getOtherHost() != null ? wro.getOtherHost() : "null") + "\n\tcurrent version " + currentVersion);
                  }

                  ClusterLogger.logStaleReplicationRequest(id.toString());
                  return wro.getSecondaryROInfo();
               }
            }

            ClusterServicesInvocationContext clusterSvcContext = this.localReplicationManager.getClusterServicesInvocationContext();

            try {
               ManagedInvocationContext mic = clusterSvcContext.setInvocationContext(resourceGroupKey.getPartitionName());
               Throwable var27 = null;

               try {
                  wro = this.localReplicationManager.getWroManager().create(replicatable, id, (byte)1, version, resourceGroupKey);
               } catch (Throwable var21) {
                  var27 = var21;
                  throw var21;
               } finally {
                  if (mic != null) {
                     if (var27 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var20) {
                           var27.addSuppressed(var20);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }
            } catch (ApplicationUnavailableException var23) {
               throw new ApplicationUnavailableRemoteException(var23.getMessage(), var23);
            } catch (AssertionError var24) {
               if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                  ReplicationDetailsDebugLogger.debug(id, var24.getMessage(), var24);
               }

               ApplicationUnavailableException aue = new ApplicationUnavailableException(var24.getMessage());
               throw new ApplicationUnavailableRemoteException(aue.getMessage(), aue);
            }

            wro.setOtherHost(primaryHostID);
            obj = wro.getSecondaryROInfo();
            return obj;
         }
      }
   }

   public void update(ROID id, int version, Serializable change, Object key) throws NotFoundException, RemoteException {
      this.updateInternal("update", id, version, change, key, true);
   }

   public void copyUpdate(ROID id, int version, Serializable change, Object key) throws NotFoundException, RemoteException {
      this.updateInternal("copyUpdate", id, version, change, key, false);
   }

   public void updateOneWay(ROID id, int version, Serializable change, Object key) throws RemoteException {
      try {
         this.updateInternal("updateOneWay", id, version, change, key, true);
      } catch (NotFoundException var6) {
         throw new RemoteException(var6.getMessage(), var6);
      }
   }

   public void copyUpdateOneWay(ROID id, int version, Serializable change, Object key) throws RemoteException {
      try {
         this.updateInternal("copyUpdateOneWay", id, version, change, key, false);
      } catch (NotFoundException var6) {
         throw new RemoteException(var6.getMessage(), var6);
      }
   }

   public void update(AsyncBatch state) throws RemoteException {
      if (!this.localReplicationManager.supportsAsyncBatchUpdates()) {
         throw new UnsupportedOperationException("ReplicationManager should not take batched updates");
      } else {
         AsyncUpdate[] updates = state.getUpdates();
         ArrayList recreateList = new ArrayList();

         for(int i = 0; i < updates.length; ++i) {
            if (!recreateList.contains(updates[i].getId())) {
               if (updates[i].isUpdate()) {
                  try {
                     this.update(updates[i].getId(), updates[i].getVersion(), updates[i].getChange(), updates[i].getKey());
                  } catch (Exception var6) {
                     recreateList.add(updates[i].getId());
                     if (ReplicationDebugLogger.isDebugEnabled()) {
                        ReplicationDebugLogger.debug(updates[i].getId(), "Error updating secondary with version " + updates[i].getVersion() + " from " + updates[i].getPrimaryHost() + " on this server:  " + ReplicationServicesImplBase.LOCAL_HOSTID + ". Re-creating secondary.", var6);
                     }
                  }
               } else {
                  this.create(updates[i].getPrimaryHost(), updates[i].getVersion(), updates[i].getId(), updates[i].getRO());
               }
            }
         }

         if (recreateList.size() > 0) {
            ROID[] idList = new ROID[recreateList.size()];
            recreateList.toArray(idList);
            throw new AsyncBatchFailedException(idList);
         }
      }
   }

   public final void remove(ROID[] ids, Object key) throws RemoteException {
      this.removeInternal("remove", ids, key);
   }

   public final void removeOneWay(ROID[] ids, Object key) throws RemoteException {
      this.removeInternal("removeOneWay", ids, key);
   }

   public final void remove(ROID[] ids) throws RemoteException {
      WroManager wroManager = this.localReplicationManager.getWroManager();

      for(int i = 0; i < ids.length; ++i) {
         WrappedRO wro = wroManager.find(ids[i]);
         if (wro != null) {
            wroManager.ensureStatus((byte)1, wro);
            wroManager.removeAll(ids[i], wro.getResourceGroupKey());
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("Removed migrated secondary for roids: " + ids[i]);
            }
         } else if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("Attempt to remove non-existent object for roids: " + ids[i]);
         }
      }

   }

   public final ROObject fetch(ROID id) throws RemoteException, NotFoundException {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug("Fetching " + id);
      }

      WrappedRO wro = this.localReplicationManager.getWroManager().find(id);
      if (wro != null) {
         return new ROObject(wro.getMap(), wro.getVersionMap(), wro.getResourceGroupKey());
      } else {
         throw new NotFoundException("Failed to locate ROID " + id);
      }
   }

   public void removeOrphanedSessionOnCondition(ROID id, int version, Object key) throws RemoteException {
      this.localReplicationManager.removeOrphanedSessionOnCondition(id, version, key);
   }

   private void updateInternal(String method, ROID id, int version, Serializable change, Object key, boolean incrementVersion) throws NotFoundException {
      ResourceGroupKeyImpl resourceGroupKey = null;
      ReplicationServicesImplBase var10000;
      if (change instanceof WrappedSerializable) {
         resourceGroupKey = (ResourceGroupKeyImpl)((WrappedSerializable)change).resourceGroupKey;
         change = ((WrappedSerializable)change).serializable;
      } else {
         var10000 = this.localReplicationManager;
         resourceGroupKey = (ResourceGroupKeyImpl)ReplicationServicesImplBase.getResourceGroupKey();
      }

      WrappedRO wro = this.localReplicationManager.getWroManager().find(id, resourceGroupKey);
      if (wro == null) {
         throw new NotFoundException("Unable to find " + id + " with resourceGroupKey: " + resourceGroupKey);
      } else {
         HostID otherHost = wro.getOtherHost();
         if (otherHost != null) {
            var10000 = this.localReplicationManager;
            ReplicationServicesImplBase.resetTimeOut(otherHost);
         }

         int currentVersion = wro.getVersion(key);
         if (currentVersion > version) {
            ClusterLogger.logStaleReplicationRequest(id.toString());
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug(wro.getID(), "Got stale replication request for UPDATE with version " + version + " from " + wro.getOtherHost());
            }
         } else if (currentVersion == -1) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug(id, "RO not found for key " + key);
            }
         } else {
            int versionDifference = version - currentVersion;
            if (versionDifference != 1) {
               if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                  ReplicationDetailsDebugLogger.debug("Missed " + versionDifference + " updates for " + id.toString() + ", key " + key + ", update version: " + version + ", current version: " + currentVersion + " from: " + otherHost);
               }

               if (versionDifference != 0) {
                  if (method.equals("update")) {
                     ClusterLogger.logReplicationVersionMismatch(versionDifference, id + "; update version: " + version + ", current version: " + currentVersion);
                     throw new NotFoundException("Lost " + versionDifference + " updates of " + id);
                  }

                  if (counter < 1000) {
                     ++counter;
                     ClusterExtensionLogger.logOutOfOrderUpdateOneWayRequest();
                  }
               }
            } else {
               ClusterServicesInvocationContext clusterSvcContext = this.localReplicationManager.getClusterServicesInvocationContext();
               ManagedInvocationContext mic = clusterSvcContext.setInvocationContext(resourceGroupKey.getPartitionName());
               Throwable var14 = null;

               try {
                  this.localReplicationManager.getWroManager().ensureStatus((byte)1, wro);
                  if (incrementVersion) {
                     wro.incrementVersion(key);
                  }

                  Replicatable ro = wro.getRO(key);
                  if (ro == null) {
                     if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                        ReplicationDetailsDebugLogger.debug("Remote object was null for key " + key + ", for version update " + version + " from " + wro.getOtherHost() + "for replication object " + id.toString());
                     }

                     throw new AssertionError("Found the session for " + id + " but not the application for " + key + ". Double-check that proxy/loadbalancers are respecting session stickiness.");
                  }

                  ro.update(id, change);
                  if (ReplicationDebugLogger.isDebugEnabled()) {
                     ReplicationDebugLogger.debug("Updated local secondary with version " + version + " from " + wro.getOtherHost() + "for replication object " + id.toString() + ", key " + key + ", ro = " + ro.toString() + ", resourceGroupKey: " + wro.getResourceGroupKey());
                  }
               } catch (Throwable var23) {
                  var14 = var23;
                  throw var23;
               } finally {
                  if (mic != null) {
                     if (var14 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var22) {
                           var14.addSuppressed(var22);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }
            }
         }

      }
   }

   private void removeInternal(String method, ROID[] ids, Object key) throws RemoteException {
      WroManager wroManager = this.localReplicationManager.getWroManager();

      for(int i = 0; i < ids.length; ++i) {
         WrappedRO wro = wroManager.find(ids[i]);
         if (wro != null && wro.getStatus() != 0) {
            HostID otherHost = wro.getOtherHost();
            if (otherHost != null) {
               ReplicationServicesImplBase var10000 = this.localReplicationManager;
               ReplicationServicesImplBase.resetTimeOut(otherHost);
            }

            wroManager.remove(ids[i], key, wro.getResourceGroupKey());
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("Removed secondary for roids: " + ids[i]);
            }
         } else if (ReplicationDebugLogger.isDebugEnabled()) {
            if (wro != null && wro.getStatus() == 0) {
               ReplicationDebugLogger.debug("Attempt to remove current primary which is old secondary: " + ids[i]);
            } else {
               ReplicationDebugLogger.debug("Attempt to remove non-existent object for roids: " + ids[i]);
            }
         }
      }

   }
}
