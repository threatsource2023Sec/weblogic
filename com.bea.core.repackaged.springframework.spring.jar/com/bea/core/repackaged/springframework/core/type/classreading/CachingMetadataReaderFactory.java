package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.core.io.DefaultResourceLoader;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class CachingMetadataReaderFactory extends SimpleMetadataReaderFactory {
   public static final int DEFAULT_CACHE_LIMIT = 256;
   @Nullable
   private Map metadataReaderCache;

   public CachingMetadataReaderFactory() {
      this.setCacheLimit(256);
   }

   public CachingMetadataReaderFactory(@Nullable ClassLoader classLoader) {
      super(classLoader);
      this.setCacheLimit(256);
   }

   public CachingMetadataReaderFactory(@Nullable ResourceLoader resourceLoader) {
      super(resourceLoader);
      if (resourceLoader instanceof DefaultResourceLoader) {
         this.metadataReaderCache = ((DefaultResourceLoader)resourceLoader).getResourceCache(MetadataReader.class);
      } else {
         this.setCacheLimit(256);
      }

   }

   public void setCacheLimit(int cacheLimit) {
      if (cacheLimit <= 0) {
         this.metadataReaderCache = null;
      } else if (this.metadataReaderCache instanceof LocalResourceCache) {
         ((LocalResourceCache)this.metadataReaderCache).setCacheLimit(cacheLimit);
      } else {
         this.metadataReaderCache = new LocalResourceCache(cacheLimit);
      }

   }

   public int getCacheLimit() {
      if (this.metadataReaderCache instanceof LocalResourceCache) {
         return ((LocalResourceCache)this.metadataReaderCache).getCacheLimit();
      } else {
         return this.metadataReaderCache != null ? Integer.MAX_VALUE : 0;
      }
   }

   public MetadataReader getMetadataReader(Resource resource) throws IOException {
      if (this.metadataReaderCache instanceof ConcurrentMap) {
         MetadataReader metadataReader = (MetadataReader)this.metadataReaderCache.get(resource);
         if (metadataReader == null) {
            metadataReader = super.getMetadataReader(resource);
            this.metadataReaderCache.put(resource, metadataReader);
         }

         return metadataReader;
      } else if (this.metadataReaderCache != null) {
         synchronized(this.metadataReaderCache) {
            MetadataReader metadataReader = (MetadataReader)this.metadataReaderCache.get(resource);
            if (metadataReader == null) {
               metadataReader = super.getMetadataReader(resource);
               this.metadataReaderCache.put(resource, metadataReader);
            }

            return metadataReader;
         }
      } else {
         return super.getMetadataReader(resource);
      }
   }

   public void clearCache() {
      if (this.metadataReaderCache instanceof LocalResourceCache) {
         synchronized(this.metadataReaderCache) {
            this.metadataReaderCache.clear();
         }
      } else if (this.metadataReaderCache != null) {
         this.setCacheLimit(256);
      }

   }

   private static class LocalResourceCache extends LinkedHashMap {
      private volatile int cacheLimit;

      public LocalResourceCache(int cacheLimit) {
         super(cacheLimit, 0.75F, true);
         this.cacheLimit = cacheLimit;
      }

      public void setCacheLimit(int cacheLimit) {
         this.cacheLimit = cacheLimit;
      }

      public int getCacheLimit() {
         return this.cacheLimit;
      }

      protected boolean removeEldestEntry(Map.Entry eldest) {
         return this.size() > this.cacheLimit;
      }
   }
}
