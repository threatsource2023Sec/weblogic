package com.bea.cache.jcache;

import java.util.Collection;
import java.util.Map;

public interface CacheLoader {
   Object load(Object var1);

   Map loadAll(Collection var1);

   Map loadAll();
}
