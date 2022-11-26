package org.python.netty.channel;

import org.python.netty.util.concurrent.AbstractEventExecutorGroup;

public abstract class AbstractEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
   public abstract EventLoop next();
}
