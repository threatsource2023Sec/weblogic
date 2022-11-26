package javax.batch.api.chunk;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractItemWriter implements ItemWriter {
   public void open(Serializable checkpoint) throws Exception {
   }

   public void close() throws Exception {
   }

   public abstract void writeItems(List var1) throws Exception;

   public Serializable checkpointInfo() throws Exception {
      return null;
   }
}
