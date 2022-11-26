package org.python.netty.handler.codec.compression;

import java.util.concurrent.TimeUnit;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.ChannelPromiseNotifier;
import org.python.netty.handler.codec.MessageToByteEncoder;
import org.python.netty.util.concurrent.EventExecutor;

public class Bzip2Encoder extends MessageToByteEncoder {
   private State currentState;
   private final Bzip2BitWriter writer;
   private final int streamBlockSize;
   private int streamCRC;
   private Bzip2BlockCompressor blockCompressor;
   private volatile boolean finished;
   private volatile ChannelHandlerContext ctx;

   public Bzip2Encoder() {
      this(9);
   }

   public Bzip2Encoder(int blockSizeMultiplier) {
      this.currentState = Bzip2Encoder.State.INIT;
      this.writer = new Bzip2BitWriter();
      if (blockSizeMultiplier >= 1 && blockSizeMultiplier <= 9) {
         this.streamBlockSize = blockSizeMultiplier * 100000;
      } else {
         throw new IllegalArgumentException("blockSizeMultiplier: " + blockSizeMultiplier + " (expected: 1-9)");
      }
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
      if (this.finished) {
         out.writeBytes(in);
      } else {
         while(true) {
            switch (this.currentState) {
               case INIT:
                  out.ensureWritable(4);
                  out.writeMedium(4348520);
                  out.writeByte(48 + this.streamBlockSize / 100000);
                  this.currentState = Bzip2Encoder.State.INIT_BLOCK;
               case INIT_BLOCK:
                  this.blockCompressor = new Bzip2BlockCompressor(this.writer, this.streamBlockSize);
                  this.currentState = Bzip2Encoder.State.WRITE_DATA;
               case WRITE_DATA:
                  if (!in.isReadable()) {
                     return;
                  }

                  Bzip2BlockCompressor blockCompressor = this.blockCompressor;
                  int length = Math.min(in.readableBytes(), blockCompressor.availableSize());
                  int bytesWritten = blockCompressor.write(in, in.readerIndex(), length);
                  in.skipBytes(bytesWritten);
                  if (!blockCompressor.isFull()) {
                     if (!in.isReadable()) {
                        return;
                     }
                     break;
                  } else {
                     this.currentState = Bzip2Encoder.State.CLOSE_BLOCK;
                  }
               case CLOSE_BLOCK:
                  this.closeBlock(out);
                  this.currentState = Bzip2Encoder.State.INIT_BLOCK;
                  break;
               default:
                  throw new IllegalStateException();
            }
         }
      }
   }

   private void closeBlock(ByteBuf out) {
      Bzip2BlockCompressor blockCompressor = this.blockCompressor;
      if (!blockCompressor.isEmpty()) {
         blockCompressor.close(out);
         int blockCRC = blockCompressor.crc();
         this.streamCRC = (this.streamCRC << 1 | this.streamCRC >>> 31) ^ blockCRC;
      }

   }

   public boolean isClosed() {
      return this.finished;
   }

   public ChannelFuture close() {
      return this.close(this.ctx().newPromise());
   }

   public ChannelFuture close(final ChannelPromise promise) {
      ChannelHandlerContext ctx = this.ctx();
      EventExecutor executor = ctx.executor();
      if (executor.inEventLoop()) {
         return this.finishEncode(ctx, promise);
      } else {
         executor.execute(new Runnable() {
            public void run() {
               ChannelFuture f = Bzip2Encoder.this.finishEncode(Bzip2Encoder.this.ctx(), promise);
               f.addListener(new ChannelPromiseNotifier(new ChannelPromise[]{promise}));
            }
         });
         return promise;
      }
   }

   public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
      ChannelFuture f = this.finishEncode(ctx, ctx.newPromise());
      f.addListener(new ChannelFutureListener() {
         public void operationComplete(ChannelFuture f) throws Exception {
            ctx.close(promise);
         }
      });
      if (!f.isDone()) {
         ctx.executor().schedule(new Runnable() {
            public void run() {
               ctx.close(promise);
            }
         }, 10L, TimeUnit.SECONDS);
      }

   }

   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
      if (this.finished) {
         promise.setSuccess();
         return promise;
      } else {
         this.finished = true;
         ByteBuf footer = ctx.alloc().buffer();
         this.closeBlock(footer);
         int streamCRC = this.streamCRC;
         Bzip2BitWriter writer = this.writer;

         try {
            writer.writeBits(footer, 24, 1536581L);
            writer.writeBits(footer, 24, 3690640L);
            writer.writeInt(footer, streamCRC);
            writer.flush(footer);
         } finally {
            this.blockCompressor = null;
         }

         return ctx.writeAndFlush(footer, promise);
      }
   }

   private ChannelHandlerContext ctx() {
      ChannelHandlerContext ctx = this.ctx;
      if (ctx == null) {
         throw new IllegalStateException("not added to a pipeline");
      } else {
         return ctx;
      }
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.ctx = ctx;
   }

   private static enum State {
      INIT,
      INIT_BLOCK,
      WRITE_DATA,
      CLOSE_BLOCK;
   }
}
