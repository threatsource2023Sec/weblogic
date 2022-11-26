package org.python.netty.handler.flush;

import java.util.concurrent.Future;
import org.python.netty.channel.ChannelDuplexHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;

public class FlushConsolidationHandler extends ChannelDuplexHandler {
   private final int explicitFlushAfterFlushes;
   private final boolean consolidateWhenNoReadInProgress;
   private final Runnable flushTask;
   private int flushPendingCount;
   private boolean readInProgress;
   private ChannelHandlerContext ctx;
   private Future nextScheduledFlush;

   public FlushConsolidationHandler() {
      this(256, false);
   }

   public FlushConsolidationHandler(int explicitFlushAfterFlushes) {
      this(explicitFlushAfterFlushes, false);
   }

   public FlushConsolidationHandler(int explicitFlushAfterFlushes, boolean consolidateWhenNoReadInProgress) {
      if (explicitFlushAfterFlushes <= 0) {
         throw new IllegalArgumentException("explicitFlushAfterFlushes: " + explicitFlushAfterFlushes + " (expected: > 0)");
      } else {
         this.explicitFlushAfterFlushes = explicitFlushAfterFlushes;
         this.consolidateWhenNoReadInProgress = consolidateWhenNoReadInProgress;
         this.flushTask = consolidateWhenNoReadInProgress ? new Runnable() {
            public void run() {
               if (FlushConsolidationHandler.this.flushPendingCount > 0 && !FlushConsolidationHandler.this.readInProgress) {
                  FlushConsolidationHandler.this.flushPendingCount = 0;
                  FlushConsolidationHandler.this.ctx.flush();
                  FlushConsolidationHandler.this.nextScheduledFlush = null;
               }

            }
         } : null;
      }
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.ctx = ctx;
   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      if (this.readInProgress) {
         if (++this.flushPendingCount == this.explicitFlushAfterFlushes) {
            this.flushNow(ctx);
         }
      } else if (this.consolidateWhenNoReadInProgress) {
         if (++this.flushPendingCount == this.explicitFlushAfterFlushes) {
            this.flushNow(ctx);
         } else {
            this.scheduleFlush(ctx);
         }
      } else {
         this.flushNow(ctx);
      }

   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      this.resetReadAndFlushIfNeeded(ctx);
      ctx.fireChannelReadComplete();
   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      this.readInProgress = true;
      ctx.fireChannelRead(msg);
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      this.resetReadAndFlushIfNeeded(ctx);
      ctx.fireExceptionCaught(cause);
   }

   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.resetReadAndFlushIfNeeded(ctx);
      ctx.disconnect(promise);
   }

   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.resetReadAndFlushIfNeeded(ctx);
      ctx.close(promise);
   }

   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
      if (!ctx.channel().isWritable()) {
         this.flushIfNeeded(ctx);
      }

      ctx.fireChannelWritabilityChanged();
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      this.flushIfNeeded(ctx);
   }

   private void resetReadAndFlushIfNeeded(ChannelHandlerContext ctx) {
      this.readInProgress = false;
      this.flushIfNeeded(ctx);
   }

   private void flushIfNeeded(ChannelHandlerContext ctx) {
      if (this.flushPendingCount > 0) {
         this.flushNow(ctx);
      }

   }

   private void flushNow(ChannelHandlerContext ctx) {
      this.cancelScheduledFlush();
      this.flushPendingCount = 0;
      ctx.flush();
   }

   private void scheduleFlush(ChannelHandlerContext ctx) {
      if (this.nextScheduledFlush == null) {
         this.nextScheduledFlush = ctx.channel().eventLoop().submit(this.flushTask);
      }

   }

   private void cancelScheduledFlush() {
      if (this.nextScheduledFlush != null) {
         this.nextScheduledFlush.cancel(false);
         this.nextScheduledFlush = null;
      }

   }
}
