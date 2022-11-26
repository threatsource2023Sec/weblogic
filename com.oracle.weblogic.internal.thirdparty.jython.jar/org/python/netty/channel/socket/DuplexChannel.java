package org.python.netty.channel.socket;

import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelPromise;

public interface DuplexChannel extends Channel {
   boolean isInputShutdown();

   ChannelFuture shutdownInput();

   ChannelFuture shutdownInput(ChannelPromise var1);

   boolean isOutputShutdown();

   ChannelFuture shutdownOutput();

   ChannelFuture shutdownOutput(ChannelPromise var1);

   boolean isShutdown();

   ChannelFuture shutdown();

   ChannelFuture shutdown(ChannelPromise var1);
}
