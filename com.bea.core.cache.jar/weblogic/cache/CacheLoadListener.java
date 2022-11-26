package weblogic.cache;

import java.util.Collection;
import java.util.Map;

public interface CacheLoadListener {
   void onLoad(Map var1);

   void onLoadError(Collection var1, Throwable var2);
}
