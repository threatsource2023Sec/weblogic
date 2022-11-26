package javax.batch.api.chunk;

public interface CheckpointAlgorithm {
   int checkpointTimeout() throws Exception;

   void beginCheckpoint() throws Exception;

   boolean isReadyToCheckpoint() throws Exception;

   void endCheckpoint() throws Exception;
}
