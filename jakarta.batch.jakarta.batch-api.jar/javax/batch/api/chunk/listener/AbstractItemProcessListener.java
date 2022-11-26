package javax.batch.api.chunk.listener;

public abstract class AbstractItemProcessListener implements ItemProcessListener {
   public void beforeProcess(Object item) throws Exception {
   }

   public void afterProcess(Object item, Object result) throws Exception {
   }

   public void onProcessError(Object item, Exception ex) throws Exception {
   }
}
