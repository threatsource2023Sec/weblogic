package javax.batch.api.chunk.listener;

public interface ChunkListener {
   void beforeChunk() throws Exception;

   void onError(Exception var1) throws Exception;

   void afterChunk() throws Exception;
}
