package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MulticastSessionsStatus implements Externalizable {
   private int statusChangeNumber;
   private Set activeMulticastSessionIds;
   private Set inactiveMulticastSessionIds;
   private ClusterMemberInfo clusterMemberInfo;

   public MulticastSessionsStatus(int statusChangeNumber) {
      this.statusChangeNumber = statusChangeNumber;
      this.activeMulticastSessionIds = new HashSet();
      this.inactiveMulticastSessionIds = new HashSet();
      this.clusterMemberInfo = ClusterService.getClusterServiceInternal().getLocalMember();
   }

   protected MulticastSessionsStatus(MulticastSessionsStatus sessionsStatus) {
      this.statusChangeNumber = sessionsStatus.getStatusChangeNumber();
      this.activeMulticastSessionIds = new HashSet(sessionsStatus.getActiveMulticastSessionIds());
      this.inactiveMulticastSessionIds = new HashSet(sessionsStatus.getInactiveMulticastSessionIds());
      this.clusterMemberInfo = sessionsStatus.getClusterMemberInfo();
   }

   public void markAsActive(MulticastSessionId multicastSessionId) {
      if (!this.activeMulticastSessionIds.contains(multicastSessionId)) {
         this.activeMulticastSessionIds.add(multicastSessionId);
      }

      if (this.inactiveMulticastSessionIds.contains(multicastSessionId)) {
         this.inactiveMulticastSessionIds.remove(multicastSessionId);
      }

   }

   public void markAsInactive(MulticastSessionId multicastSessionId) {
      if (this.activeMulticastSessionIds.contains(multicastSessionId)) {
         this.activeMulticastSessionIds.remove(multicastSessionId);
      }

      if (!this.inactiveMulticastSessionIds.contains(multicastSessionId)) {
         this.inactiveMulticastSessionIds.add(multicastSessionId);
      }

   }

   public int getStatusChangeNumber() {
      return this.statusChangeNumber;
   }

   public Set getActiveMulticastSessionIds() {
      return this.activeMulticastSessionIds;
   }

   public Set getInactiveMulticastSessionIds() {
      return this.inactiveMulticastSessionIds;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(this.statusChangeNumber);
      out.writeObject(this.activeMulticastSessionIds);
      out.writeObject(this.inactiveMulticastSessionIds);
      out.writeObject(this.clusterMemberInfo);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.statusChangeNumber = in.readInt();
      this.activeMulticastSessionIds = (HashSet)in.readObject();
      this.inactiveMulticastSessionIds = (HashSet)in.readObject();
      this.clusterMemberInfo = (ClusterMemberInfo)in.readObject();
   }

   public boolean equals(Object o) {
      return this == o || o != null && this.getClass() == o.getClass();
   }

   public int hashCode() {
      return this.getClass().hashCode();
   }

   public String toString() {
      return "MulticastSessionsStatus {changeNum=" + this.statusChangeNumber + ", activeMulticastSessionIds=" + Arrays.asList(this.activeMulticastSessionIds) + ", inactiveMulticastSessionIds=" + Arrays.asList(this.inactiveMulticastSessionIds) + '}';
   }

   public MulticastSessionsStatus() {
   }

   public ClusterMemberInfo getClusterMemberInfo() {
      return this.clusterMemberInfo;
   }
}
