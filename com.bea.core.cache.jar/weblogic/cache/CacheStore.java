package weblogic.cache;

import java.util.Map;

public interface CacheStore {
   void store(Object var1, Object var2);

   void storeAll(Map var1);
}
