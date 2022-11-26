package javax.batch.api.listener;

public interface JobListener {
   void beforeJob() throws Exception;

   void afterJob() throws Exception;
}
