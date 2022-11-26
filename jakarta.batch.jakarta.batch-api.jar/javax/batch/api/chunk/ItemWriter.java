package javax.batch.api.chunk;

import java.io.Serializable;
import java.util.List;

public interface ItemWriter {
   void open(Serializable var1) throws Exception;

   void close() throws Exception;

   void writeItems(List var1) throws Exception;

   Serializable checkpointInfo() throws Exception;
}
