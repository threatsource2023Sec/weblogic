package com.bea.httppubsub.internal;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.security.ChannelAuthorizationManager;

public interface InternalChannel extends Channel {
   String RESERVED_CHANNEL = "/__WL_CHANNEL";
   String RECURSIVE_RESERVED_CHANNEL = "/__WL_CHANNEL/__WL_CHANNEL";

   void setAuthorizationManager(ChannelAuthorizationManager var1);

   ChannelAuthorizationManager getChannelAuthorizationManager();

   void setChannelPersistenceManager(ChannelPersistenceManager var1);

   ChannelPersistenceManager getChannelPersistenceManager();

   void setChannelPersistManBuilder(ChannelPersistenceManagerBuilder var1);

   ChannelPersistenceManagerBuilder getChannelPersistManBuilder();

   boolean hasPermission(Client var1, Channel.ChannelPattern var2, ChannelAuthorizationManager.Action var3);
}
