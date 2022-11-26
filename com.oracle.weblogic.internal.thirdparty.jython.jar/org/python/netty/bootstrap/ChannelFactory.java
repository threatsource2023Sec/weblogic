package org.python.netty.bootstrap;

import org.python.netty.channel.Channel;

/** @deprecated */
@Deprecated
public interface ChannelFactory {
   Channel newChannel();
}
