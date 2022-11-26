package org.python.netty.channel;

import org.python.netty.util.concurrent.AbstractEventExecutor;

public abstract class AbstractEventLoop extends AbstractEventExecutor implements EventLoop {
   protected AbstractEventLoop() {
   }

   protected AbstractEventLoop(EventLoopGroup parent) {
      super(parent);
   }

   public EventLoopGroup parent() {
      return (EventLoopGroup)super.parent();
   }

   public EventLoop next() {
      return (EventLoop)super.next();
   }
}
