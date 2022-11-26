package weblogic.cluster.replication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ReplicationMap {
   Map primaries = new ConcurrentHashMap(65536);
   Map secondaries = new ConcurrentHashMap(65536);

   WrappedRO find(ROID roid) {
      WrappedRO wro = (WrappedRO)this.primaries.get(roid);
      if (wro != null) {
         return wro;
      } else {
         wro = (WrappedRO)this.secondaries.get(roid);
         return wro;
      }
   }

   WrappedRO findPrimary(ROID roid) {
      return (WrappedRO)this.primaries.get(roid);
   }

   WrappedRO findSecondary(ROID roid) {
      return (WrappedRO)this.secondaries.get(roid);
   }

   WrappedRO remove(ROID id) {
      return this.remove(id, (Object)null);
   }

   WrappedRO remove(ROID roid, Object key) {
      WrappedRO wro = this.find(roid);
      if (wro == null) {
         return null;
      } else {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(wro.getID(), "Removing " + (wro.getStatus() == 0 ? "primary " : "secondary ") + " for key " + key);
         }

         if (key == null || wro.removeRO(key)) {
            if (wro.getStatus() == 0) {
               this.primaries.remove(wro.getID());
            } else {
               this.secondaries.remove(wro.getID());
            }
         }

         return wro;
      }
   }

   Map getCombinedMap() {
      Map map = new ConcurrentHashMap(this.primaries);
      map.putAll(this.secondaries);
      return map;
   }
}
