package javax.batch.api.partition;

public interface PartitionReducer {
   void beginPartitionedStep() throws Exception;

   void beforePartitionedStepCompletion() throws Exception;

   void rollbackPartitionedStep() throws Exception;

   void afterPartitionedStepCompletion(PartitionStatus var1) throws Exception;

   public static enum PartitionStatus {
      COMMIT,
      ROLLBACK;
   }
}
