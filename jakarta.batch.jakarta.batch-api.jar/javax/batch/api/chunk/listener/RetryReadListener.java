package javax.batch.api.chunk.listener;

public interface RetryReadListener {
   void onRetryReadException(Exception var1) throws Exception;
}
