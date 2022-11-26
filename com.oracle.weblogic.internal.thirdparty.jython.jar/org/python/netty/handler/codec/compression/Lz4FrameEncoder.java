package org.python.netty.handler.codec.compression;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.xxhash.XXHashFactory;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.ChannelPromiseNotifier;
import org.python.netty.handler.codec.EncoderException;
import org.python.netty.handler.codec.MessageToByteEncoder;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.internal.ObjectUtil;

public class Lz4FrameEncoder extends MessageToByteEncoder {
   static final int DEFAULT_MAX_ENCODE_SIZE = Integer.MAX_VALUE;
   private final int blockSize;
   private LZ4Compressor compressor;
   private ByteBufChecksum checksum;
   private final int compressionLevel;
   private ByteBuf buffer;
   private final int maxEncodeSize;
   private volatile boolean finished;
   private volatile ChannelHandlerContext ctx;

   public Lz4FrameEncoder() {
      this(false);
   }

   public Lz4FrameEncoder(boolean highCompressor) {
      this(LZ4Factory.fastestInstance(), highCompressor, 65536, XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum());
   }

   public Lz4FrameEncoder(LZ4Factory factory, boolean highCompressor, int blockSize, Checksum checksum) {
      this(factory, highCompressor, blockSize, checksum, Integer.MAX_VALUE);
   }

   public Lz4FrameEncoder(LZ4Factory factory, boolean highCompressor, int blockSize, Checksum checksum, int maxEncodeSize) {
      if (factory == null) {
         throw new NullPointerException("factory");
      } else if (checksum == null) {
         throw new NullPointerException("checksum");
      } else {
         this.compressor = highCompressor ? factory.highCompressor() : factory.fastCompressor();
         this.checksum = ByteBufChecksum.wrapChecksum(checksum);
         this.compressionLevel = compressionLevel(blockSize);
         this.blockSize = blockSize;
         this.maxEncodeSize = ObjectUtil.checkPositive(maxEncodeSize, "maxEncodeSize");
         this.finished = false;
      }
   }

   private static int compressionLevel(int blockSize) {
      if (blockSize >= 64 && blockSize <= 33554432) {
         int compressionLevel = 32 - Integer.numberOfLeadingZeros(blockSize - 1);
         compressionLevel = Math.max(0, compressionLevel - 10);
         return compressionLevel;
      } else {
         throw new IllegalArgumentException(String.format("blockSize: %d (expected: %d-%d)", blockSize, 64, 33554432));
      }
   }

   protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) {
      return this.allocateBuffer(ctx, msg, preferDirect, true);
   }

   private ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect, boolean allowEmptyReturn) {
      int targetBufSize = 0;
      int remaining = msg.readableBytes() + this.buffer.readableBytes();
      if (remaining < 0) {
         throw new EncoderException("too much data to allocate a buffer for compression");
      } else {
         while(remaining > 0) {
            int curSize = Math.min(this.blockSize, remaining);
            remaining -= curSize;
            targetBufSize += this.compressor.maxCompressedLength(curSize) + 21;
         }

         if (targetBufSize <= this.maxEncodeSize && 0 <= targetBufSize) {
            if (allowEmptyReturn && targetBufSize < this.blockSize) {
               return Unpooled.EMPTY_BUFFER;
            } else {
               return preferDirect ? ctx.alloc().ioBuffer(targetBufSize, targetBufSize) : ctx.alloc().heapBuffer(targetBufSize, targetBufSize);
            }
         } else {
            throw new EncoderException(String.format("requested encode buffer size (%d bytes) exceeds the maximum allowable size (%d bytes)", targetBufSize, this.maxEncodeSize));
         }
      }
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
      if (this.finished) {
         out.writeBytes(in);
      } else {
         ByteBuf buffer = this.buffer;

         int length;
         while((length = in.readableBytes()) > 0) {
            int nextChunkSize = Math.min(length, buffer.writableBytes());
            in.readBytes(buffer, nextChunkSize);
            if (!buffer.isWritable()) {
               this.flushBufferedData(out);
            }
         }

      }
   }

   private void flushBufferedData(ByteBuf out) {
      int flushableBytes = this.buffer.readableBytes();
      if (flushableBytes != 0) {
         this.checksum.reset();
         this.checksum.update(this.buffer, this.buffer.readerIndex(), flushableBytes);
         int check = (int)this.checksum.getValue();
         int bufSize = this.compressor.maxCompressedLength(flushableBytes) + 21;
         out.ensureWritable(bufSize);
         int idx = out.writerIndex();

         int compressedLength;
         try {
            ByteBuffer outNioBuffer = out.internalNioBuffer(idx + 21, out.writableBytes() - 21);
            int pos = outNioBuffer.position();
            this.compressor.compress(this.buffer.internalNioBuffer(this.buffer.readerIndex(), flushableBytes), outNioBuffer);
            compressedLength = outNioBuffer.position() - pos;
         } catch (LZ4Exception var9) {
            throw new CompressionException(var9);
         }

         byte blockType;
         if (compressedLength >= flushableBytes) {
            blockType = 16;
            compressedLength = flushableBytes;
            out.setBytes(idx + 21, (ByteBuf)this.buffer, 0, flushableBytes);
         } else {
            blockType = 32;
         }

         out.setLong(idx, 5501767354678207339L);
         out.setByte(idx + 8, (byte)(blockType | this.compressionLevel));
         out.setIntLE(idx + 9, compressedLength);
         out.setIntLE(idx + 13, flushableBytes);
         out.setIntLE(idx + 17, check);
         out.writerIndex(idx + 21 + compressedLength);
         this.buffer.clear();
      }
   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      if (this.buffer != null && this.buffer.isReadable()) {
         ByteBuf buf = this.allocateBuffer(ctx, Unpooled.EMPTY_BUFFER, this.isPreferDirect(), false);
         this.flushBufferedData(buf);
         ctx.write(buf);
      }

      ctx.flush();
   }

   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
      if (this.finished) {
         promise.setSuccess();
         return promise;
      } else {
         this.finished = true;

         ChannelFuture var5;
         try {
            ByteBuf footer = ctx.alloc().heapBuffer(this.compressor.maxCompressedLength(this.buffer.readableBytes()) + 21);
            this.flushBufferedData(footer);
            int idx = footer.writerIndex();
            footer.setLong(idx, 5501767354678207339L);
            footer.setByte(idx + 8, (byte)(16 | this.compressionLevel));
            footer.setInt(idx + 9, 0);
            footer.setInt(idx + 13, 0);
            footer.setInt(idx + 17, 0);
            footer.writerIndex(idx + 21);
            var5 = ctx.writeAndFlush(footer, promise);
         } finally {
            this.cleanup();
         }

         return var5;
      }
   }

   private void cleanup() {
      this.compressor = null;
      this.checksum = null;
      if (this.buffer != null) {
         this.buffer.release();
         this.buffer = null;
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
               ChannelFuture f = Lz4FrameEncoder.this.finishEncode(Lz4FrameEncoder.this.ctx(), promise);
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

   private ChannelHandlerContext ctx() {
      ChannelHandlerContext ctx = this.ctx;
      if (ctx == null) {
         throw new IllegalStateException("not added to a pipeline");
      } else {
         return ctx;
      }
   }

   public void handlerAdded(ChannelHandlerContext ctx) {
      this.ctx = ctx;
      this.buffer = Unpooled.wrappedBuffer(new byte[this.blockSize]);
      this.buffer.clear();
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      super.handlerRemoved(ctx);
      this.cleanup();
   }

   final ByteBuf getBackingBuffer() {
      return this.buffer;
   }
}
