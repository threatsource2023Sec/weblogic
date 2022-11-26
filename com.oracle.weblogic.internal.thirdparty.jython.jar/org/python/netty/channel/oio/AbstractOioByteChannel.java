package org.python.netty.channel.oio;

import java.io.IOException;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelMetadata;
import org.python.netty.channel.ChannelOption;
import org.python.netty.channel.ChannelOutboundBuffer;
import org.python.netty.channel.ChannelPipeline;
import org.python.netty.channel.FileRegion;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.socket.ChannelInputShutdownEvent;
import org.python.netty.util.internal.StringUtil;

public abstract class AbstractOioByteChannel extends AbstractOioChannel {
   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';

   protected AbstractOioByteChannel(Channel parent) {
      super(parent);
   }

   public ChannelMetadata metadata() {
      return METADATA;
   }

   protected abstract boolean isInputShutdown();

   protected abstract ChannelFuture shutdownInput();

   private void closeOnRead(ChannelPipeline pipeline) {
      if (this.isOpen()) {
         if (Boolean.TRUE.equals(this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
            this.shutdownInput();
            pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
         } else {
            this.unsafe().close(this.unsafe().voidPromise());
         }
      }

   }

   private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, RecvByteBufAllocator.Handle allocHandle) {
      if (byteBuf != null) {
         if (byteBuf.isReadable()) {
            this.readPending = false;
            pipeline.fireChannelRead(byteBuf);
         } else {
            byteBuf.release();
         }
      }

      allocHandle.readComplete();
      pipeline.fireChannelReadComplete();
      pipeline.fireExceptionCaught(cause);
      if (close || cause instanceof IOException) {
         this.closeOnRead(pipeline);
      }

   }

   protected void doRead() {
      ChannelConfig config = this.config();
      if (!this.isInputShutdown() && this.readPending) {
         this.readPending = false;
         ChannelPipeline pipeline = this.pipeline();
         ByteBufAllocator allocator = config.getAllocator();
         RecvByteBufAllocator.Handle allocHandle = this.unsafe().recvBufAllocHandle();
         allocHandle.reset(config);
         ByteBuf byteBuf = null;
         boolean close = false;
         boolean readData = false;

         try {
            byteBuf = allocHandle.allocate(allocator);

            do {
               allocHandle.lastBytesRead(this.doReadBytes(byteBuf));
               if (allocHandle.lastBytesRead() <= 0) {
                  if (!byteBuf.isReadable()) {
                     byteBuf.release();
                     byteBuf = null;
                     close = allocHandle.lastBytesRead() < 0;
                  }
                  break;
               }

               readData = true;
               int available = this.available();
               if (available <= 0) {
                  break;
               }

               if (!byteBuf.isWritable()) {
                  int capacity = byteBuf.capacity();
                  int maxCapacity = byteBuf.maxCapacity();
                  if (capacity == maxCapacity) {
                     allocHandle.incMessagesRead(1);
                     this.readPending = false;
                     pipeline.fireChannelRead(byteBuf);
                     byteBuf = allocHandle.allocate(allocator);
                  } else {
                     int writerIndex = byteBuf.writerIndex();
                     if (writerIndex + available > maxCapacity) {
                        byteBuf.capacity(maxCapacity);
                     } else {
                        byteBuf.ensureWritable(available);
                     }
                  }
               }
            } while(allocHandle.continueReading());

            if (byteBuf != null) {
               if (byteBuf.isReadable()) {
                  this.readPending = false;
                  pipeline.fireChannelRead(byteBuf);
               } else {
                  byteBuf.release();
               }

               byteBuf = null;
            }

            if (readData) {
               allocHandle.readComplete();
               pipeline.fireChannelReadComplete();
            }

            if (close) {
               this.closeOnRead(pipeline);
            }
         } catch (Throwable var15) {
            this.handleReadException(pipeline, byteBuf, var15, close, allocHandle);
         } finally {
            if (this.readPending || config.isAutoRead() || !readData && this.isActive()) {
               this.read();
            }

         }

      }
   }

   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      while(true) {
         Object msg = in.current();
         if (msg == null) {
            return;
         }

         if (!(msg instanceof ByteBuf)) {
            if (msg instanceof FileRegion) {
               FileRegion region = (FileRegion)msg;
               long transferred = region.transferred();
               this.doWriteFileRegion(region);
               in.progress(region.transferred() - transferred);
               in.remove();
            } else {
               in.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg)));
            }
         } else {
            ByteBuf buf = (ByteBuf)msg;

            int newReadableBytes;
            for(int readableBytes = buf.readableBytes(); readableBytes > 0; readableBytes = newReadableBytes) {
               this.doWriteBytes(buf);
               newReadableBytes = buf.readableBytes();
               in.progress((long)(readableBytes - newReadableBytes));
            }

            in.remove();
         }
      }
   }

   protected final Object filterOutboundMessage(Object msg) throws Exception {
      if (!(msg instanceof ByteBuf) && !(msg instanceof FileRegion)) {
         throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
      } else {
         return msg;
      }
   }

   protected abstract int available();

   protected abstract int doReadBytes(ByteBuf var1) throws Exception;

   protected abstract void doWriteBytes(ByteBuf var1) throws Exception;

   protected abstract void doWriteFileRegion(FileRegion var1) throws Exception;
}
