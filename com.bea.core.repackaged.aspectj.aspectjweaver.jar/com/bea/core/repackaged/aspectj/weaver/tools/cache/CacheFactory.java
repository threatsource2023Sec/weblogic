package com.bea.core.repackaged.aspectj.weaver.tools.cache;

public interface CacheFactory {
   CacheKeyResolver createResolver();

   CacheBacking createBacking(String var1);
}
