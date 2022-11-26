package org.python.netty.handler.traffic;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;

public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
   private final ArrayDeque messagesQueue = new ArrayDeque();
   private long queueSize;

   public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime) {
      super(writeLimit, readLimit, checkInterval, maxTime);
   }

   public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
      super(writeLimit, readLimit, checkInterval);
   }

   public ChannelTrafficShapingHandler(long writeLimit, long readLimit) {
      super(writeLimit, readLimit);
   }

   public ChannelTrafficShapingHandler(long checkInterval) {
      super(checkInterval);
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      TrafficCounter trafficCounter = new TrafficCounter(this, ctx.executor(), "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
      this.setTrafficCounter(trafficCounter);
      trafficCounter.start();
      super.handlerAdded(ctx);
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      this.trafficCounter.stop();
      synchronized(this) {
         Iterator var3;
         ToSend toSend;
         if (ctx.channel().isActive()) {
            var3 = this.messagesQueue.iterator();

            while(var3.hasNext()) {
               toSend = (ToSend)var3.next();
               long size = this.calculateSize(toSend.toSend);
               this.trafficCounter.bytesRealWriteFlowControl(size);
               this.queueSize -= size;
               ctx.write(toSend.toSend, toSend.promise);
            }
         } else {
            var3 = this.messagesQueue.iterator();

            while(var3.hasNext()) {
               toSend = (ToSend)var3.next();
               if (toSend.toSend instanceof ByteBuf) {
                  ((ByteBuf)toSend.toSend).release();
               }
            }
         }

         this.messagesQueue.clear();
      }

      this.releaseWriteSuspended(ctx);
      this.releaseReadSuspended(ctx);
      super.handlerRemoved(ctx);
   }

   void submitWrite(final ChannelHandlerContext ctx, Object msg, long size, long delay, long now, ChannelPromise promise) {
      ToSend newToSend;
      synchronized(this) {
         if (delay == 0L && this.messagesQueue.isEmpty()) {
            this.trafficCounter.bytesRealWriteFlowControl(size);
            ctx.write(msg, promise);
            return;
         }

         newToSend = new ToSend(delay + now, msg, promise);
         this.messagesQueue.addLast(newToSend);
         this.queueSize += size;
         this.checkWriteSuspend(ctx, delay, this.queueSize);
      }

      final long futureNow = newToSend.relativeTimeAction;
      ctx.executor().schedule(new Runnable() {
         public void run() {
            ChannelTrafficShapingHandler.this.sendAllValid(ctx, futureNow);
         }
      }, delay, TimeUnit.MILLISECONDS);
   }

   private void sendAllValid(ChannelHandlerContext ctx, long now) {
      synchronized(this) {
         ToSend newToSend = (ToSend)this.messagesQueue.pollFirst();

         while(true) {
            if (newToSend != null) {
               if (newToSend.relativeTimeAction <= now) {
                  long size = this.calculateSize(newToSend.toSend);
                  this.trafficCounter.bytesRealWriteFlowControl(size);
                  this.queueSize -= size;
                  ctx.write(newToSend.toSend, newToSend.promise);
                  newToSend = (ToSend)this.messagesQueue.pollFirst();
                  continue;
               }

               this.messagesQueue.addFirst(newToSend);
            }

            if (this.messagesQueue.isEmpty()) {
               this.releaseWriteSuspended(ctx);
            }
            break;
         }
      }

      ctx.flush();
   }

   public long queueSize() {
      return this.queueSize;
   }

   private static final class ToSend {
      final long relativeTimeAction;
      final Object toSend;
      final ChannelPromise promise;

      private ToSend(long delay, Object toSend, ChannelPromise promise) {
         this.relativeTimeAction = delay;
         this.toSend = toSend;
         this.promise = promise;
      }

      // $FF: synthetic method
      ToSend(long x0, Object x1, ChannelPromise x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
