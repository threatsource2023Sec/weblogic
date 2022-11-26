package org.glassfish.grizzly.http.server.filecache;

import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;

public class FileCacheKey implements Cacheable {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(FileCacheKey.class, 16);
   protected String host;
   protected String uri;

   protected FileCacheKey() {
   }

   protected FileCacheKey(String host, String uri) {
      this.host = host;
      this.uri = uri;
   }

   public void recycle() {
      this.host = null;
      this.uri = null;
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public static FileCacheKey create(String host, String uri) {
      FileCacheKey key = (FileCacheKey)ThreadCache.takeFromCache(CACHE_IDX);
      if (key != null) {
         key.host = host;
         key.uri = uri;
         return key;
      } else {
         return new FileCacheKey(host, uri);
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         FileCacheKey other = (FileCacheKey)obj;
         String otherHost = other.host;
         if (this.host == null) {
            if (otherHost != null) {
               return false;
            }
         } else if (!this.host.equals(otherHost)) {
            return false;
         }

         String otherUri = other.uri;
         if (this.uri == null) {
            if (otherUri != null) {
               return false;
            }
         } else if (!this.uri.equals(otherUri)) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int hash = 3;
      hash = 23 * hash + (this.host != null ? this.host.hashCode() : 0);
      hash = 23 * hash + (this.uri != null ? this.uri.hashCode() : 0);
      return hash;
   }

   protected String getHost() {
      return this.host;
   }

   protected String getUri() {
      return this.uri;
   }
}
