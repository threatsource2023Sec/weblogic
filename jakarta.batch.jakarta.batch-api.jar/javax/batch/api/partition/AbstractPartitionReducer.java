package javax.batch.api.partition;

public abstract class AbstractPartitionReducer implements PartitionReducer {
   public void beginPartitionedStep() throws Exception {
   }

   public void beforePartitionedStepCompletion() throws Exception {
   }

   public void rollbackPartitionedStep() throws Exception {
   }

   public void afterPartitionedStepCompletion(PartitionReducer.PartitionStatus status) throws Exception {
   }
}
