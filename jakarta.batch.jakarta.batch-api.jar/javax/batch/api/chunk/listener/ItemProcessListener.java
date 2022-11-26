package javax.batch.api.chunk.listener;

public interface ItemProcessListener {
   void beforeProcess(Object var1) throws Exception;

   void afterProcess(Object var1, Object var2) throws Exception;

   void onProcessError(Object var1, Exception var2) throws Exception;
}
