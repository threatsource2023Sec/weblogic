package com.bea.cache.jcache;

import java.util.Map;

public interface CacheEntry extends Map.Entry {
   long getCreationTime();

   long getLastAccessTime();

   long getLastUpdateTime();

   long getExpirationTime();
}
