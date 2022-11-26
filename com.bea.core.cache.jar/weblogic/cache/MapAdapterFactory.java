package weblogic.cache;

import java.util.Map;
import weblogic.cache.configuration.CacheBean;

public interface MapAdapterFactory {
   CacheMap adapt(CacheMap var1, CacheBean var2);

   CacheMap unwrap(CacheMap var1);

   void reconfigure(CacheMap var1, CacheBean var2, CacheBean var3, Map var4);
}
