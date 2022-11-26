package org.python.netty.channel;

public interface ChannelFactory extends org.python.netty.bootstrap.ChannelFactory {
   Channel newChannel();
}
