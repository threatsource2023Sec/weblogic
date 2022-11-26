package org.python.netty.channel.group;

import java.util.Set;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelId;

public interface ChannelGroup extends Set, Comparable {
   String name();

   Channel find(ChannelId var1);

   ChannelGroupFuture write(Object var1);

   ChannelGroupFuture write(Object var1, ChannelMatcher var2);

   ChannelGroupFuture write(Object var1, ChannelMatcher var2, boolean var3);

   ChannelGroup flush();

   ChannelGroup flush(ChannelMatcher var1);

   ChannelGroupFuture writeAndFlush(Object var1);

   /** @deprecated */
   @Deprecated
   ChannelGroupFuture flushAndWrite(Object var1);

   ChannelGroupFuture writeAndFlush(Object var1, ChannelMatcher var2);

   ChannelGroupFuture writeAndFlush(Object var1, ChannelMatcher var2, boolean var3);

   /** @deprecated */
   @Deprecated
   ChannelGroupFuture flushAndWrite(Object var1, ChannelMatcher var2);

   ChannelGroupFuture disconnect();

   ChannelGroupFuture disconnect(ChannelMatcher var1);

   ChannelGroupFuture close();

   ChannelGroupFuture close(ChannelMatcher var1);

   /** @deprecated */
   @Deprecated
   ChannelGroupFuture deregister();

   /** @deprecated */
   @Deprecated
   ChannelGroupFuture deregister(ChannelMatcher var1);

   ChannelGroupFuture newCloseFuture();

   ChannelGroupFuture newCloseFuture(ChannelMatcher var1);
}
