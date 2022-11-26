package weblogic.rmi.internal;

public interface PartitionAwareRef {
   String INVALID_PARTITION_NAME = "~";

   String getPartitionName();
}
