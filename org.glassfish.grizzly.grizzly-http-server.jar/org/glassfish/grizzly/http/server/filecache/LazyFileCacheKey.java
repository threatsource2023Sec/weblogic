package org.glassfish.grizzly.http.server.filecache;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;

public class LazyFileCacheKey extends FileCacheKey {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(LazyFileCacheKey.class, 16);
   private HttpRequestPacket request;
   private boolean isInitialized;
   private int hashCode;

   private LazyFileCacheKey(HttpRequestPacket request) {
      this.request = request;
   }

   protected String getHost() {
      if (!this.isInitialized) {
         this.initialize();
      }

      return super.getHost();
   }

   protected String getUri() {
      if (!this.isInitialized) {
         this.initialize();
      }

      return super.getUri();
   }

   public void recycle() {
      this.host = null;
      this.uri = null;
      this.isInitialized = false;
      this.request = null;
      this.hashCode = 0;
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public static LazyFileCacheKey create(HttpRequestPacket request) {
      LazyFileCacheKey key = (LazyFileCacheKey)ThreadCache.takeFromCache(CACHE_IDX);
      if (key != null) {
         key.request = request;
         return key;
      } else {
         return new LazyFileCacheKey(request);
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass().isAssignableFrom(obj.getClass())) {
         return false;
      } else {
         FileCacheKey other = (FileCacheKey)obj;
         String otherHost = other.host;
         DataChunk hostDC = this.getHostLazy();
         if (hostDC != null && !hostDC.isNull()) {
            if (!hostDC.equals(otherHost)) {
               return false;
            }
         } else if (otherHost != null) {
            return false;
         }

         String otherUri = other.uri;
         DataChunk uriDC = this.getUriLazy();
         if (uriDC != null && !uriDC.isNull()) {
            if (!uriDC.equals(otherUri)) {
               return false;
            }
         } else if (otherUri != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int hash = 3;
         DataChunk hostDC = this.getHostLazy();
         DataChunk uriDC = this.getUriLazy();
         hash = 23 * hash + (hostDC != null ? hostDC.hashCode() : 0);
         hash = 23 * hash + (uriDC != null ? uriDC.hashCode() : 0);
         this.hashCode = hash;
      }

      return this.hashCode;
   }

   private void initialize() {
      this.isInitialized = true;
      this.host = this.request.getHeader(Header.Host);
      this.uri = this.request.getRequestURI();
   }

   private DataChunk getHostLazy() {
      return this.request.getHeaders().getValue(Header.Host);
   }

   private DataChunk getUriLazy() {
      return this.request.getRequestURIRef().getRequestURIBC();
   }
}
