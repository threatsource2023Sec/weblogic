package weblogic.messaging.dispatcher;

public interface PartitionAware {
   String getPartitionId();

   String getPartitionName();

   String getConnectionPartitionName();
}
