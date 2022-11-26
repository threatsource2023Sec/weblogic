package com.bea.core.repackaged.aspectj.weaver.tools.cache;

public interface CacheBacking {
   String[] getKeys(String var1);

   void remove(CachedClassReference var1);

   void clear();

   CachedClassEntry get(CachedClassReference var1, byte[] var2);

   void put(CachedClassEntry var1, byte[] var2);
}
