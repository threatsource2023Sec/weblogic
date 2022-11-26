package javax.batch.api.chunk.listener;

import java.util.List;

public interface ItemWriteListener {
   void beforeWrite(List var1) throws Exception;

   void afterWrite(List var1) throws Exception;

   void onWriteError(List var1, Exception var2) throws Exception;
}
