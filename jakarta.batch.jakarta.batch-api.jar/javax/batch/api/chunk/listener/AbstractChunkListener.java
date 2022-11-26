package javax.batch.api.chunk.listener;

public abstract class AbstractChunkListener implements ChunkListener {
   public void beforeChunk() throws Exception {
   }

   public void onError(Exception ex) throws Exception {
   }

   public void afterChunk() throws Exception {
   }
}
