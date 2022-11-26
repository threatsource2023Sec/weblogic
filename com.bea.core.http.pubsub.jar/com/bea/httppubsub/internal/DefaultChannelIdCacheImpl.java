package com.bea.httppubsub.internal;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultChannelIdCacheImpl implements ChannelIdCache {
   private final ConcurrentHashMap cache = new ConcurrentHashMap();

   public ChannelId get(String url) {
      return (ChannelId)this.cache.get(url);
   }

   public void put(String url, ChannelId cid) {
      this.cache.putIfAbsent(url, cid);
   }
}
