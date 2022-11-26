package javax.batch.api.listener;

public interface StepListener {
   void beforeStep() throws Exception;

   void afterStep() throws Exception;
}
