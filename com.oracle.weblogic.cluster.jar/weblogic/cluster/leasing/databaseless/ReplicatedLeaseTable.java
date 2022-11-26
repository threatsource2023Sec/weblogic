package weblogic.cluster.leasing.databaseless;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ReplicatedLeaseTable implements Map {
   private ClusterLeader leader;
   private LeaseView leaseView;
   private Map localMap;

   ReplicatedLeaseTable(ClusterLeader leader, LeaseView leaseView) {
      this.leader = leader;
      this.leaseView = leaseView;
      this.localMap = leaseView.getLeaseTableReplica();
   }

   public Object put(Object key, Object value) {
      assert this.leader != null;

      if (value != null && value.equals(this.localMap.get(key))) {
         return this.localMap.put(key, value);
      } else {
         LeaseTableUpdateMessage message = LeaseTableUpdateMessage.createPutMessage(this.leader.getLeaderInformation(), this.leaseView.getVersionNumber() + 1L, (Serializable)key, (Serializable)value);
         if (this.leader.sendGroupMessage(message)) {
            this.leaseView.incrementVersionNumber();
            return this.localMap.put(key, value);
         } else {
            throw new LeaseTableUpdateException("Unable to send lease table PUT to remote servers");
         }
      }
   }

   public Object remove(Object key) {
      assert this.leader != null;

      LeaseTableUpdateMessage message = LeaseTableUpdateMessage.createRemoveMessage(this.leader.getLeaderInformation(), this.leaseView.getVersionNumber() + 1L, (Serializable)key);
      if (this.leader.sendGroupMessage(message)) {
         this.leaseView.incrementVersionNumber();
         return this.localMap.remove(key);
      } else {
         throw new LeaseTableUpdateException("Unable to send lease table REMOVE to remote servers");
      }
   }

   public void putAll(Map map) {
      this.localMap.putAll(map);
   }

   public void clear() {
      this.localMap.clear();
   }

   public Set keySet() {
      return this.localMap.keySet();
   }

   public Collection values() {
      return this.localMap.values();
   }

   public Set entrySet() {
      return this.localMap.entrySet();
   }

   public int size() {
      return this.localMap.size();
   }

   public boolean isEmpty() {
      return this.localMap.isEmpty();
   }

   public boolean containsKey(Object key) {
      return this.localMap.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.localMap.containsValue(value);
   }

   public Object get(Object key) {
      return this.localMap.get(key);
   }
}
