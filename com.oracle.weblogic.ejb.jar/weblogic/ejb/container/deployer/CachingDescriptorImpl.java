package weblogic.ejb.container.deployer;

import weblogic.ejb.container.interfaces.CachingDescriptor;

class CachingDescriptorImpl implements CachingDescriptor {
   private final int maxBeansInCache;
   private final int maxQueriesInCache;
   private final int maxBeansInFreePool;
   private final int initialBeansInFreePool;
   private int idleTimeoutSecondsCache;
   private final int idleTimeoutSecondsPool;
   private final String cacheType;
   private final String concurrencyStrategy;
   private int readTimeoutSeconds;

   CachingDescriptorImpl(int maxBeansInCache, int maxQueriesInCache, int maxBeansInFreePool, int initialBeansInFreePool, int idleTimeoutSecondsCache, int idleTimeoutSecondsPool, String cacheType, int readTimeoutSeconds, String concurrencyStrategy) {
      this.maxBeansInCache = maxBeansInCache;
      this.maxQueriesInCache = maxQueriesInCache;
      this.maxBeansInFreePool = maxBeansInFreePool;
      this.initialBeansInFreePool = initialBeansInFreePool;
      this.idleTimeoutSecondsCache = idleTimeoutSecondsCache;
      this.idleTimeoutSecondsPool = idleTimeoutSecondsPool;
      this.cacheType = cacheType;
      this.readTimeoutSeconds = readTimeoutSeconds;
      this.concurrencyStrategy = concurrencyStrategy;
   }

   public int getMaxBeansInCache() {
      return this.maxBeansInCache;
   }

   public int getMaxQueriesInCache() {
      return this.maxQueriesInCache;
   }

   public int getMaxBeansInFreePool() {
      return this.maxBeansInFreePool;
   }

   public int getInitialBeansInFreePool() {
      return this.initialBeansInFreePool;
   }

   public void setIdleTimeoutSecondsCache(int val) {
      this.idleTimeoutSecondsCache = val;
   }

   public int getIdleTimeoutSecondsCache() {
      return this.idleTimeoutSecondsCache;
   }

   public int getIdleTimeoutSecondsPool() {
      return this.idleTimeoutSecondsPool;
   }

   public String getCacheType() {
      return this.cacheType;
   }

   public void setReadTimeoutSeconds(int val) {
      this.readTimeoutSeconds = val;
   }

   public int getReadTimeoutSeconds() {
      return this.readTimeoutSeconds;
   }

   public String getConcurrencyStrategy() {
      return this.concurrencyStrategy;
   }
}
