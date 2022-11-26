package javax.batch.api.chunk;

import java.io.Serializable;

public interface ItemReader {
   void open(Serializable var1) throws Exception;

   void close() throws Exception;

   Object readItem() throws Exception;

   Serializable checkpointInfo() throws Exception;
}
