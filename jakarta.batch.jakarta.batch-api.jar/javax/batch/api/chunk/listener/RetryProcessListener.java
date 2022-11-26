package javax.batch.api.chunk.listener;

public interface RetryProcessListener {
   void onRetryProcessException(Object var1, Exception var2) throws Exception;
}
