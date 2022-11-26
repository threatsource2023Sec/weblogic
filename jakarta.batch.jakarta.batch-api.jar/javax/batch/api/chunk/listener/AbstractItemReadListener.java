package javax.batch.api.chunk.listener;

public abstract class AbstractItemReadListener implements ItemReadListener {
   public void beforeRead() throws Exception {
   }

   public void afterRead(Object item) throws Exception {
   }

   public void onReadError(Exception ex) throws Exception {
   }
}
