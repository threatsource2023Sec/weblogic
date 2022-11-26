package javax.batch.api.chunk.listener;

import java.util.List;

public abstract class AbstractItemWriteListener implements ItemWriteListener {
   public void beforeWrite(List items) throws Exception {
   }

   public void afterWrite(List items) throws Exception {
   }

   public void onWriteError(List items, Exception ex) throws Exception {
   }
}
