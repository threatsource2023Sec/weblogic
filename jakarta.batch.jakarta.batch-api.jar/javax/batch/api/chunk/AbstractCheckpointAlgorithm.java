package javax.batch.api.chunk;

public abstract class AbstractCheckpointAlgorithm implements CheckpointAlgorithm {
   public int checkpointTimeout() throws Exception {
      return 0;
   }

   public void beginCheckpoint() throws Exception {
   }

   public abstract boolean isReadyToCheckpoint() throws Exception;

   public void endCheckpoint() throws Exception {
   }
}
