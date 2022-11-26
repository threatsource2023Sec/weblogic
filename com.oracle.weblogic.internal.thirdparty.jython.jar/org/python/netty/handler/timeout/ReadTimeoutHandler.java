package org.python.netty.handler.timeout;

import java.util.concurrent.TimeUnit;
import org.python.netty.channel.ChannelHandlerContext;

public class ReadTimeoutHandler extends IdleStateHandler {
   private boolean closed;

   public ReadTimeoutHandler(int timeoutSeconds) {
      this((long)timeoutSeconds, TimeUnit.SECONDS);
   }

   public ReadTimeoutHandler(long timeout, TimeUnit unit) {
      super(timeout, 0L, 0L, unit);
   }

   protected final void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
      assert evt.state() == IdleState.READER_IDLE;

      this.readTimedOut(ctx);
   }

   protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
      if (!this.closed) {
         ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
         ctx.close();
         this.closed = true;
      }

   }
}
