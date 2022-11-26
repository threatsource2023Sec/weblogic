package net.shibboleth.utilities.java.support.httpclient;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.http.impl.client.cache.BasicHttpCacheStorage;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
import org.apache.http.impl.client.cache.HeapResourceFactory;

public class InMemoryCachingHttpClientBuilder extends HttpClientBuilder {
   private int maxCacheEntries;
   private long maxCacheEntrySize;

   public InMemoryCachingHttpClientBuilder() {
      this(CachingHttpClientBuilder.create());
   }

   public InMemoryCachingHttpClientBuilder(@Nonnull CachingHttpClientBuilder builder) {
      super(builder);
      this.maxCacheEntries = 50;
      this.maxCacheEntrySize = 1048576L;
   }

   public int getMaxCacheEntries() {
      return this.maxCacheEntries;
   }

   public void setMaxCacheEntries(int maxEntries) {
      this.maxCacheEntries = (int)Constraint.isGreaterThan(0L, (long)maxEntries, "Maximum number of cache entries must be greater than 0");
   }

   public long getMaxCacheEntrySize() {
      return this.maxCacheEntrySize;
   }

   public void setMaxCacheEntrySize(long size) {
      this.maxCacheEntrySize = Constraint.isGreaterThan(0L, size, "Maximum cache entry size must be greater than 0");
   }

   protected void decorateApacheBuilder() throws Exception {
      super.decorateApacheBuilder();
      CachingHttpClientBuilder cachingBuilder = (CachingHttpClientBuilder)this.getApacheBuilder();
      CacheConfig.Builder cacheConfigBuilder = CacheConfig.custom();
      cacheConfigBuilder.setMaxCacheEntries(this.maxCacheEntries);
      cacheConfigBuilder.setMaxObjectSize(this.maxCacheEntrySize);
      cacheConfigBuilder.setHeuristicCachingEnabled(false);
      cacheConfigBuilder.setSharedCache(false);
      CacheConfig cacheConfig = cacheConfigBuilder.build();
      cachingBuilder.setCacheConfig(cacheConfig);
      cachingBuilder.setResourceFactory(new HeapResourceFactory());
      cachingBuilder.setHttpCacheStorage(new BasicHttpCacheStorage(cacheConfig));
   }
}
