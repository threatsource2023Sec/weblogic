package weblogic.cache;

import java.util.Map;

public interface CacheStoreListener {
   void onStore(Map var1);

   void onStoreError(Map var1, Throwable var2);
}
