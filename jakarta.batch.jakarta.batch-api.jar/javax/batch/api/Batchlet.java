package javax.batch.api;

public interface Batchlet {
   String process() throws Exception;

   void stop() throws Exception;
}
