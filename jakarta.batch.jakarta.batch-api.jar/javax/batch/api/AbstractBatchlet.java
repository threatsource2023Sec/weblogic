package javax.batch.api;

public abstract class AbstractBatchlet implements Batchlet {
   public abstract String process() throws Exception;

   public void stop() throws Exception {
   }
}
