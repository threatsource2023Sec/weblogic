package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.weaver.tools.GeneratedClassHandler;
import java.util.LinkedList;
import java.util.List;

public class WeavedClassCache {
   public static final String WEAVED_CLASS_CACHE_ENABLED = "aj.weaving.cache.enabled";
   public static final String CACHE_IMPL = "aj.weaving.cache.impl";
   private static CacheFactory DEFAULT_FACTORY = new DefaultCacheFactory();
   public static final byte[] ZERO_BYTES = new byte[0];
   private final IMessageHandler messageHandler;
   private final GeneratedCachedClassHandler cachingClassHandler;
   private final CacheBacking backing;
   private final CacheStatistics stats;
   private final CacheKeyResolver resolver;
   private final String name;
   private static final List cacheRegistry = new LinkedList();

   protected WeavedClassCache(GeneratedClassHandler existingClassHandler, IMessageHandler messageHandler, String name, CacheBacking backing, CacheKeyResolver resolver) {
      this.resolver = resolver;
      this.name = name;
      this.backing = backing;
      this.messageHandler = messageHandler;
      this.cachingClassHandler = new GeneratedCachedClassHandler(this, existingClassHandler);
      this.stats = new CacheStatistics();
      synchronized(cacheRegistry) {
         cacheRegistry.add(this);
      }
   }

   public static WeavedClassCache createCache(ClassLoader loader, List aspects, GeneratedClassHandler existingClassHandler, IMessageHandler messageHandler) {
      CacheKeyResolver resolver = DEFAULT_FACTORY.createResolver();
      String name = resolver.createClassLoaderScope(loader, aspects);
      if (name == null) {
         return null;
      } else {
         CacheBacking backing = DEFAULT_FACTORY.createBacking(name);
         return backing != null ? new WeavedClassCache(existingClassHandler, messageHandler, name, backing, resolver) : null;
      }
   }

   public String getName() {
      return this.name;
   }

   public static void setDefaultCacheFactory(CacheFactory factory) {
      DEFAULT_FACTORY = factory;
   }

   public CachedClassReference createGeneratedCacheKey(String className) {
      return this.resolver.generatedKey(className);
   }

   public CachedClassReference createCacheKey(String className, byte[] originalBytes) {
      return this.resolver.weavedKey(className, originalBytes);
   }

   public GeneratedClassHandler getCachingClassHandler() {
      return this.cachingClassHandler;
   }

   public static boolean isEnabled() {
      String enabled = System.getProperty("aj.weaving.cache.enabled");
      String impl = System.getProperty("aj.weaving.cache.impl");
      return enabled != null && (impl == null || !"shared".equalsIgnoreCase(impl));
   }

   public void put(CachedClassReference ref, byte[] classBytes, byte[] weavedBytes) {
      CachedClassEntry.EntryType type = CachedClassEntry.EntryType.WEAVED;
      if (ref.getKey().matches(this.resolver.getGeneratedRegex())) {
         type = CachedClassEntry.EntryType.GENERATED;
      }

      this.backing.put(new CachedClassEntry(ref, weavedBytes, type), classBytes);
      this.stats.put();
   }

   public CachedClassEntry get(CachedClassReference ref, byte[] classBytes) {
      CachedClassEntry entry = this.backing.get(ref, classBytes);
      if (entry == null) {
         this.stats.miss();
      } else {
         this.stats.hit();
         if (entry.isGenerated()) {
            this.stats.generated();
         }

         if (entry.isWeaved()) {
            this.stats.weaved();
         }

         if (entry.isIgnored()) {
            this.stats.ignored();
         }
      }

      return entry;
   }

   public void ignore(CachedClassReference ref, byte[] classBytes) {
      this.stats.putIgnored();
      this.backing.put(new CachedClassEntry(ref, ZERO_BYTES, CachedClassEntry.EntryType.IGNORED), classBytes);
   }

   public void remove(CachedClassReference ref) {
      this.backing.remove(ref);
   }

   public void clear() {
      this.backing.clear();
   }

   public CacheStatistics getStats() {
      return this.stats;
   }

   public static List getCaches() {
      synchronized(cacheRegistry) {
         return new LinkedList(cacheRegistry);
      }
   }

   protected void error(String message, Throwable th) {
      this.messageHandler.handleMessage(new Message(message, IMessage.ERROR, th, (ISourceLocation)null));
   }

   protected void error(String message) {
      MessageUtil.error(this.messageHandler, message);
   }

   protected void info(String message) {
      MessageUtil.info(message);
   }
}
