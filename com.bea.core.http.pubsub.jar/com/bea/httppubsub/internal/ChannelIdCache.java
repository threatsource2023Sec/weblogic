package com.bea.httppubsub.internal;

public interface ChannelIdCache {
   ChannelId get(String var1);

   void put(String var1, ChannelId var2);
}
