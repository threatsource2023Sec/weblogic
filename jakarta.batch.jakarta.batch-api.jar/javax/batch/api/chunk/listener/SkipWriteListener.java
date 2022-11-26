package javax.batch.api.chunk.listener;

import java.util.List;

public interface SkipWriteListener {
   void onSkipWriteItem(List var1, Exception var2) throws Exception;
}
