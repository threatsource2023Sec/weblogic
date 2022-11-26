package weblogic.cluster.replication;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterServicesInvocationContext;
import weblogic.invocation.ManagedInvocationContext;

@Service
@Singleton
final class WroManager {
   final Map resourceGroupMap = new ConcurrentHashMap(128);
   @Inject
   private ClusterServicesInvocationContext clusterServicesInvocationContext;

   WrappedRO create(Replicatable ro, ROID id, byte status, int version, ResourceGroupKey resourceGroupKey) {
      WrappedRO wro;
      synchronized(id) {
         ReplicationMap replicationMaps = this.findOrCreateReplicationMap(resourceGroupKey);
         wro = replicationMaps.find(id);
         if (wro == null) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(id, "Creating " + (status == 0 ? "primary " : "secondary ") + " for application key " + ro.getKey() + " and resourceGroupKey: " + resourceGroupKey);
            }

            wro = new WrappedRO(ro, id, status, version, ReplicationServicesImplBase.getChannelIndex(id), resourceGroupKey);
            if (status == 0) {
               replicationMaps.primaries.put(id, wro);
            } else {
               replicationMaps.secondaries.put(id, wro);
            }

            return wro;
         }
      }

      this.ensureStatus(status, wro);
      int wroVersion = wro.getVersion(ro.getKey());
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(id, "Found existing " + (status == 0 ? "primary " : "secondary ") + " for application key " + ro.getKey() + " with wroVersion: " + wroVersion + ", version: " + version + ", ro: " + ro + ", resourceGroupKey: " + wro.getResourceGroupKey());
      }

      if (wroVersion != -1 && version <= wroVersion) {
         wro.addRO(ro, wroVersion);
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, "Keep existing " + (status == 0 ? "primary " : "secondary ") + " for application key " + ro.getKey() + " version: " + version + ", wroVersion: " + wroVersion + ", resourceGroupKey: " + wro.getResourceGroupKey());
         }
      } else {
         wro.addRO(ro, version);
      }

      return wro;
   }

   Map getPrimaryResourceGroupMap(ResourceGroupKey resourceGroup) {
      ReplicationMap map = this.findOrCreateReplicationMap(resourceGroup);
      return map.primaries;
   }

   private Map getSecondaryResourceGroupMap(ResourceGroupKey resourceGroup) {
      ReplicationMap map = this.findOrCreateReplicationMap(resourceGroup);
      return map.secondaries;
   }

   private ReplicationMap findOrCreateReplicationMap(ResourceGroupKey resourceGroup) {
      ReplicationMap map = this.findReplicationMap(resourceGroup);
      if (map == null) {
         ReplicationMap newMap = new ReplicationMap();
         map = (ReplicationMap)((ConcurrentHashMap)this.resourceGroupMap).putIfAbsent(resourceGroup, newMap);
         if (map == null) {
            map = newMap;
         }
      }

      return map;
   }

   private ReplicationMap findReplicationMap(ResourceGroupKey resourceGroup) {
      return (ReplicationMap)this.resourceGroupMap.get(resourceGroup);
   }

   WrappedRO remove(ROID id, Object key, ResourceGroupKey resourceGroupKey, int version) {
      Replicatable replicatable = null;
      WrappedRO wro;
      synchronized(id) {
         ReplicationMap map = this.findReplicationMap(resourceGroupKey);
         if (map == null) {
            return null;
         }

         wro = map.find(id);
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, wro == null ? "Didn't find wro to remove for version " + version : "Found wro to remove: " + wro + " with version: " + wro.getVersion() + " need to remove v: " + version);
         }

         if (wro != null && wro.getVersion() == version) {
            replicatable = wro.getRO(key);
            map.remove(id, key);
         }
      }

      if (replicatable != null) {
         replicatable.becomeUnregistered(id);
      }

      return wro;
   }

   WrappedRO remove(ROID id, Object key, ResourceGroupKey resourceGroupKey) {
      Replicatable replicatable = null;
      WrappedRO wro;
      synchronized(id) {
         ReplicationMap map = this.findReplicationMap(resourceGroupKey);
         if (map == null) {
            return null;
         }

         wro = map.find(id);
         if (wro != null) {
            replicatable = wro.getRO(key);
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(wro.getID(), "Removing " + (wro.getStatus() == 0 ? "primary " : "secondary "));
            }

            map.remove(id, key);
         }
      }

      if (replicatable != null && wro.getStatus() == 1) {
         replicatable.becomeUnregistered(id);
      }

      return wro;
   }

   WrappedRO removeAll(ROID id, ResourceGroupKey resourceGroupKey) {
      synchronized(id) {
         ReplicationMap map = this.findReplicationMap(resourceGroupKey);
         if (map == null) {
            return null;
         } else {
            WrappedRO wro = map.remove(id);
            if (wro == null) {
               return null;
            } else {
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug(wro.getID(), "Removing " + (wro.getStatus() == 0 ? "primary " : "secondary "));
               }

               wro.removeAll();
               return wro;
            }
         }
      }
   }

   WrappedRO findPrimary(ROID id, ResourceGroupKey resourceGroupKey) {
      synchronized(id) {
         ReplicationMap repmap = this.findReplicationMap(resourceGroupKey);
         return repmap != null ? repmap.findPrimary(id) : null;
      }
   }

   WrappedRO findSecondary(ROID id, ResourceGroupKey resourceGroupKey) {
      synchronized(id) {
         ReplicationMap repmap = this.findReplicationMap(resourceGroupKey);
         return repmap != null ? repmap.findSecondary(id) : null;
      }
   }

   WrappedRO find(ROID id, ResourceGroupKey resourceGroupKey) {
      synchronized(id) {
         return this.findOrCreateReplicationMap(resourceGroupKey).find(id);
      }
   }

   WrappedRO find(ROID id) {
      WrappedRO wro = null;
      synchronized(id) {
         Set resourceGroupKeys = this.resourceGroupMap.keySet();
         Iterator var5 = resourceGroupKeys.iterator();

         while(var5.hasNext()) {
            ResourceGroupKey resourceGroupKey = (ResourceGroupKey)var5.next();
            wro = this.find(id, resourceGroupKey);
            if (wro != null) {
               break;
            }
         }

         return wro;
      }
   }

   void ensureStatus(byte status, WrappedRO wro) {
      String partitionName = wro.getResourceGroupKey().getPartitionName();
      ManagedInvocationContext mic = this.clusterServicesInvocationContext.setInvocationContext(partitionName);
      Throwable var5 = null;

      try {
         wro.ensureStatus(status);
      } catch (Throwable var14) {
         var5 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var13) {
                  var5.addSuppressed(var13);
               }
            } else {
               mic.close();
            }
         }

      }

      Map primaries = this.getPrimaryResourceGroupMap(wro.getResourceGroupKey());
      Map secondaries = this.getSecondaryResourceGroupMap(wro.getResourceGroupKey());
      WrappedRO secondary;
      if (status == 0) {
         if (primaries.containsKey(wro.getID())) {
            return;
         }

         secondary = (WrappedRO)secondaries.remove(wro.getID());
         if (secondary != null) {
            primaries.put(wro.getID(), wro);
         }
      } else {
         if (secondaries.containsKey(wro.getID())) {
            return;
         }

         secondary = (WrappedRO)primaries.remove(wro.getID());
         if (secondary != null) {
            secondaries.put(wro.getID(), wro);
         }
      }

   }
}
