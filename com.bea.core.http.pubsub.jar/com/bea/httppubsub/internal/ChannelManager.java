package com.bea.httppubsub.internal;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;

public interface ChannelManager {
   Channel getRootChannel();

   Channel findChannel(String var1);

   Channel findOrCreateChannel(String var1);

   Channel createChannel(String var1);

   void deleteChannel(String var1, Client var2);

   void destroy();
}
