package com.bea.core.repackaged.springframework.cache;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;

public interface CacheManager {
   @Nullable
   Cache getCache(String var1);

   Collection getCacheNames();
}
