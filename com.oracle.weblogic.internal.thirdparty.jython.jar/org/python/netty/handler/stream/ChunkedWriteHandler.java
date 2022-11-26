package org.python.netty.handler.stream;

import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelDuplexHandler;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelProgressivePromise;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class ChunkedWriteHandler extends ChannelDuplexHandler {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
   private final Queue queue = new ArrayDeque();
   private volatile ChannelHandlerContext ctx;
   private PendingWrite currentWrite;

   public ChunkedWriteHandler() {
   }

   /** @deprecated */
   @Deprecated
   public ChunkedWriteHandler(int maxPendingWrites) {
      if (maxPendingWrites <= 0) {
         throw new IllegalArgumentException("maxPendingWrites: " + maxPendingWrites + " (expected: > 0)");
      }
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.ctx = ctx;
   }

   public void resumeTransfer() {
      final ChannelHandlerContext ctx = this.ctx;
      if (ctx != null) {
         if (ctx.executor().inEventLoop()) {
            try {
               this.doFlush(ctx);
            } catch (Exception var3) {
               if (logger.isWarnEnabled()) {
                  logger.warn("Unexpected exception while sending chunks.", (Throwable)var3);
               }
            }
         } else {
            ctx.executor().execute(new Runnable() {
               public void run() {
                  try {
                     ChunkedWriteHandler.this.doFlush(ctx);
                  } catch (Exception var2) {
                     if (ChunkedWriteHandler.logger.isWarnEnabled()) {
                        ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", (Throwable)var2);
                     }
                  }

               }
            });
         }

      }
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      this.queue.add(new PendingWrite(msg, promise));
   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      this.doFlush(ctx);
   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      this.doFlush(ctx);
      ctx.fireChannelInactive();
   }

   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
      if (ctx.channel().isWritable()) {
         this.doFlush(ctx);
      }

      ctx.fireChannelWritabilityChanged();
   }

   private void discard(Throwable cause) {
      while(true) {
         PendingWrite currentWrite = this.currentWrite;
         if (this.currentWrite == null) {
            currentWrite = (PendingWrite)this.queue.poll();
         } else {
            this.currentWrite = null;
         }

         if (currentWrite == null) {
            return;
         }

         Object message = currentWrite.msg;
         if (message instanceof ChunkedInput) {
            ChunkedInput in = (ChunkedInput)message;

            try {
               if (!in.isEndOfInput()) {
                  if (cause == null) {
                     cause = new ClosedChannelException();
                  }

                  currentWrite.fail((Throwable)cause);
               } else {
                  currentWrite.success(in.length());
               }

               closeInput(in);
            } catch (Exception var6) {
               currentWrite.fail(var6);
               logger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", (Throwable)var6);
               closeInput(in);
            }
         } else {
            if (cause == null) {
               cause = new ClosedChannelException();
            }

            currentWrite.fail((Throwable)cause);
         }
      }
   }

   private void doFlush(ChannelHandlerContext ctx) throws Exception {
      final Channel channel = ctx.channel();
      if (!channel.isActive()) {
         this.discard((Throwable)null);
      } else {
         boolean requiresFlush = true;
         ByteBufAllocator allocator = ctx.alloc();

         while(channel.isWritable()) {
            if (this.currentWrite == null) {
               this.currentWrite = (PendingWrite)this.queue.poll();
            }

            if (this.currentWrite == null) {
               break;
            }

            final PendingWrite currentWrite = this.currentWrite;
            final Object pendingMessage = currentWrite.msg;
            if (pendingMessage instanceof ChunkedInput) {
               final ChunkedInput chunks = (ChunkedInput)pendingMessage;
               Object message = null;

               boolean endOfInput;
               boolean suspend;
               try {
                  message = chunks.readChunk(allocator);
                  endOfInput = chunks.isEndOfInput();
                  if (message == null) {
                     suspend = !endOfInput;
                  } else {
                     suspend = false;
                  }
               } catch (Throwable var12) {
                  this.currentWrite = null;
                  if (message != null) {
                     ReferenceCountUtil.release(message);
                  }

                  currentWrite.fail(var12);
                  closeInput(chunks);
                  break;
               }

               if (suspend) {
                  break;
               }

               if (message == null) {
                  message = Unpooled.EMPTY_BUFFER;
               }

               ChannelFuture f = ctx.write(message);
               if (endOfInput) {
                  this.currentWrite = null;
                  f.addListener(new ChannelFutureListener() {
                     public void operationComplete(ChannelFuture future) throws Exception {
                        currentWrite.progress(chunks.progress(), chunks.length());
                        currentWrite.success(chunks.length());
                        ChunkedWriteHandler.closeInput(chunks);
                     }
                  });
               } else if (channel.isWritable()) {
                  f.addListener(new ChannelFutureListener() {
                     public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                           ChunkedWriteHandler.closeInput((ChunkedInput)pendingMessage);
                           currentWrite.fail(future.cause());
                        } else {
                           currentWrite.progress(chunks.progress(), chunks.length());
                        }

                     }
                  });
               } else {
                  f.addListener(new ChannelFutureListener() {
                     public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                           ChunkedWriteHandler.closeInput((ChunkedInput)pendingMessage);
                           currentWrite.fail(future.cause());
                        } else {
                           currentWrite.progress(chunks.progress(), chunks.length());
                           if (channel.isWritable()) {
                              ChunkedWriteHandler.this.resumeTransfer();
                           }
                        }

                     }
                  });
               }

               ctx.flush();
               requiresFlush = false;
            } else {
               ctx.write(pendingMessage, currentWrite.promise);
               this.currentWrite = null;
               requiresFlush = true;
            }

            if (!channel.isActive()) {
               this.discard(new ClosedChannelException());
               break;
            }
         }

         if (requiresFlush) {
            ctx.flush();
         }

      }
   }

   static void closeInput(ChunkedInput chunks) {
      try {
         chunks.close();
      } catch (Throwable var2) {
         if (logger.isWarnEnabled()) {
            logger.warn("Failed to close a chunked input.", var2);
         }
      }

   }

   private static final class PendingWrite {
      final Object msg;
      final ChannelPromise promise;

      PendingWrite(Object msg, ChannelPromise promise) {
         this.msg = msg;
         this.promise = promise;
      }

      void fail(Throwable cause) {
         ReferenceCountUtil.release(this.msg);
         this.promise.tryFailure(cause);
      }

      void success(long total) {
         if (!this.promise.isDone()) {
            if (this.promise instanceof ChannelProgressivePromise) {
               ((ChannelProgressivePromise)this.promise).tryProgress(total, total);
            }

            this.promise.trySuccess();
         }
      }

      void progress(long progress, long total) {
         if (this.promise instanceof ChannelProgressivePromise) {
            ((ChannelProgressivePromise)this.promise).tryProgress(progress, total);
         }

      }
   }
}
