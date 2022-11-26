package weblogic.cluster;

public final class ClusterMembersPartitionChangeEvent extends ClusterMembersChangeEvent {
   private String partitionId;

   public ClusterMembersPartitionChangeEvent(Object source, int action, ClusterMemberInfo member, String partitionId) {
      super(source, action, member);
      this.partitionId = partitionId;
   }

   public String getPartitionId() {
      return this.partitionId;
   }
}
