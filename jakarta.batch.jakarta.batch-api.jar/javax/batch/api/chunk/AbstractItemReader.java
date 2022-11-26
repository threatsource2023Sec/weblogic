package javax.batch.api.chunk;

import java.io.Serializable;

public abstract class AbstractItemReader implements ItemReader {
   public void open(Serializable checkpoint) throws Exception {
   }

   public void close() throws Exception {
   }

   public abstract Object readItem() throws Exception;

   public Serializable checkpointInfo() throws Exception {
      return null;
   }
}
