package org.python.netty.channel;

import org.python.netty.util.concurrent.OrderedEventExecutor;

public interface EventLoop extends OrderedEventExecutor, EventLoopGroup {
   EventLoopGroup parent();
}
