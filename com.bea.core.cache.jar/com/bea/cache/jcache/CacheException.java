package com.bea.cache.jcache;

public class CacheException extends RuntimeException {
   public CacheException() {
   }

   public CacheException(String s) {
      super(s);
   }

   public CacheException(String s, Throwable ex) {
      super(s, ex);
   }
}
