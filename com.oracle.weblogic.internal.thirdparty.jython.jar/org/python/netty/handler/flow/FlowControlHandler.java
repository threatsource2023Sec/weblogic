package org.python.netty.handler.flow;

import java.util.ArrayDeque;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.ChannelDuplexHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.util.Recycler;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class FlowControlHandler extends ChannelDuplexHandler {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(FlowControlHandler.class);
   private final boolean releaseMessages;
   private RecyclableArrayDeque queue;
   private ChannelConfig config;
   private boolean shouldConsume;

   public FlowControlHandler() {
      this(true);
   }

   public FlowControlHandler(boolean releaseMessages) {
      this.releaseMessages = releaseMessages;
   }

   boolean isQueueEmpty() {
      return this.queue.isEmpty();
   }

   private void destroy() {
      if (this.queue != null) {
         if (!this.queue.isEmpty()) {
            logger.trace("Non-empty queue: {}", (Object)this.queue);
            Object msg;
            if (this.releaseMessages) {
               while((msg = this.queue.poll()) != null) {
                  ReferenceCountUtil.safeRelease(msg);
               }
            }
         }

         this.queue.recycle();
         this.queue = null;
      }

   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.config = ctx.channel().config();
   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      this.destroy();
      ctx.fireChannelInactive();
   }

   public void read(ChannelHandlerContext ctx) throws Exception {
      if (this.dequeue(ctx, 1) == 0) {
         this.shouldConsume = true;
         ctx.read();
      }

   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      if (this.queue == null) {
         this.queue = FlowControlHandler.RecyclableArrayDeque.newInstance();
      }

      this.queue.offer(msg);
      int minConsume = this.shouldConsume ? 1 : 0;
      this.shouldConsume = false;
      this.dequeue(ctx, minConsume);
   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
   }

   private int dequeue(ChannelHandlerContext ctx, int minConsume) {
      if (this.queue == null) {
         return 0;
      } else {
         int consumed = 0;

         while(consumed < minConsume || this.config.isAutoRead()) {
            Object msg = this.queue.poll();
            if (msg == null) {
               break;
            }

            ++consumed;
            ctx.fireChannelRead(msg);
         }

         if (this.queue.isEmpty() && consumed > 0) {
            ctx.fireChannelReadComplete();
         }

         return consumed;
      }
   }

   private static final class RecyclableArrayDeque extends ArrayDeque {
      private static final long serialVersionUID = 0L;
      private static final int DEFAULT_NUM_ELEMENTS = 2;
      private static final Recycler RECYCLER = new Recycler() {
         protected RecyclableArrayDeque newObject(Recycler.Handle handle) {
            return new RecyclableArrayDeque(2, handle);
         }
      };
      private final Recycler.Handle handle;

      public static RecyclableArrayDeque newInstance() {
         return (RecyclableArrayDeque)RECYCLER.get();
      }

      private RecyclableArrayDeque(int numElements, Recycler.Handle handle) {
         super(numElements);
         this.handle = handle;
      }

      public void recycle() {
         this.clear();
         this.handle.recycle(this);
      }

      // $FF: synthetic method
      RecyclableArrayDeque(int x0, Recycler.Handle x1, Object x2) {
         this(x0, x1);
      }
   }
}
