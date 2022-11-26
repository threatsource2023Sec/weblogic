package com.bea.httppubsub.internal;

public class ChannelIdFactory {
   private static final ChannelIdFactory instance = new ChannelIdFactory();
   private final ChannelIdCache channelIdCache = new DefaultChannelIdCacheImpl();

   private ChannelIdFactory() {
   }

   public static ChannelIdFactory getInstance() {
      return instance;
   }

   public ChannelId build(String url) {
      ChannelId cid = this.channelIdCache.get(url);
      if (cid == null) {
         cid = new ChannelId(url);
         this.channelIdCache.put(url, cid);
      }

      return cid;
   }
}
