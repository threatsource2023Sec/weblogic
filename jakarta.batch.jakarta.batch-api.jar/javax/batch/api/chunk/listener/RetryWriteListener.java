package javax.batch.api.chunk.listener;

import java.util.List;

public interface RetryWriteListener {
   void onRetryWriteException(List var1, Exception var2) throws Exception;
}
