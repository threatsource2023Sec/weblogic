package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.weaver.tools.GeneratedClassHandler;

public class GeneratedCachedClassHandler implements GeneratedClassHandler {
   private final WeavedClassCache cache;
   private final GeneratedClassHandler nextGeneratedClassHandler;

   public GeneratedCachedClassHandler(WeavedClassCache cache, GeneratedClassHandler nextHandler) {
      this.cache = cache;
      this.nextGeneratedClassHandler = nextHandler;
   }

   public void acceptClass(String name, byte[] originalBytes, byte[] wovenBytes) {
      CachedClassReference ref = this.cache.createGeneratedCacheKey(name.replace('/', '.'));
      this.cache.put(ref, originalBytes, wovenBytes);
      if (this.nextGeneratedClassHandler != null) {
         this.nextGeneratedClassHandler.acceptClass(name, originalBytes, wovenBytes);
      }

   }
}
