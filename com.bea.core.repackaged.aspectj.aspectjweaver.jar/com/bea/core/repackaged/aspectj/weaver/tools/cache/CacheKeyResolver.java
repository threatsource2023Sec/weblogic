package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import java.util.List;

public interface CacheKeyResolver {
   CachedClassReference generatedKey(String var1);

   CachedClassReference weavedKey(String var1, byte[] var2);

   String keyToClass(String var1);

   String createClassLoaderScope(ClassLoader var1, List var2);

   String getGeneratedRegex();

   String getWeavedRegex();
}
