package weblogic.cluster.migration;

public class PartitionAwareDynamicLoadbalancer extends PartitionAwareObject {
   public PartitionAwareDynamicLoadbalancer(String partitionName, DynamicLoadbalancer delegate) {
      super(partitionName, delegate);
   }
}
