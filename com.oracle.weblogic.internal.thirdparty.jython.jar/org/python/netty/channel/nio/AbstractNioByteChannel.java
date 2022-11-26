package org.python.netty.channel.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
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
import org.python.netty.channel.socket.ChannelInputShutdownReadComplete;
import org.python.netty.util.internal.StringUtil;

public abstract class AbstractNioByteChannel extends AbstractNioChannel {
   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
   private Runnable flushTask;

   protected AbstractNioByteChannel(Channel parent, SelectableChannel ch) {
      super(parent, ch, 1);
   }

   protected abstract ChannelFuture shutdownInput();

   protected boolean isInputShutdown0() {
      return false;
   }

   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
      return new NioByteUnsafe();
   }

   public ChannelMetadata metadata() {
      return METADATA;
   }

   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      int writeSpinCount = -1;
      boolean setOpWrite = false;

      while(true) {
         Object msg = in.current();
         if (msg == null) {
            this.clearOpWrite();
            return;
         }

         if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf)msg;
            int readableBytes = buf.readableBytes();
            if (readableBytes == 0) {
               in.remove();
            } else {
               boolean done = false;
               long flushedAmount = 0L;
               if (writeSpinCount == -1) {
                  writeSpinCount = this.config().getWriteSpinCount();
               }

               for(int i = writeSpinCount - 1; i >= 0; --i) {
                  int localFlushedAmount = this.doWriteBytes(buf);
                  if (localFlushedAmount == 0) {
                     setOpWrite = true;
                     break;
                  }

                  flushedAmount += (long)localFlushedAmount;
                  if (!buf.isReadable()) {
                     done = true;
                     break;
                  }
               }

               in.progress(flushedAmount);
               if (!done) {
                  break;
               }

               in.remove();
            }
         } else {
            if (!(msg instanceof FileRegion)) {
               throw new Error();
            }

            FileRegion region = (FileRegion)msg;
            boolean done = region.transferred() >= region.count();
            if (!done) {
               long flushedAmount = 0L;
               if (writeSpinCount == -1) {
                  writeSpinCount = this.config().getWriteSpinCount();
               }

               for(int i = writeSpinCount - 1; i >= 0; --i) {
                  long localFlushedAmount = this.doWriteFileRegion(region);
                  if (localFlushedAmount == 0L) {
                     setOpWrite = true;
                     break;
                  }

                  flushedAmount += localFlushedAmount;
                  if (region.transferred() >= region.count()) {
                     done = true;
                     break;
                  }
               }

               in.progress(flushedAmount);
            }

            if (!done) {
               break;
            }

            in.remove();
         }
      }

      this.incompleteWrite(setOpWrite);
   }

   protected final Object filterOutboundMessage(Object msg) {
      if (msg instanceof ByteBuf) {
         ByteBuf buf = (ByteBuf)msg;
         return buf.isDirect() ? msg : this.newDirectBuffer(buf);
      } else if (msg instanceof FileRegion) {
         return msg;
      } else {
         throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
      }
   }

   protected final void incompleteWrite(boolean setOpWrite) {
      if (setOpWrite) {
         this.setOpWrite();
      } else {
         Runnable flushTask = this.flushTask;
         if (flushTask == null) {
            flushTask = this.flushTask = new Runnable() {
               public void run() {
                  AbstractNioByteChannel.this.flush();
               }
            };
         }

         this.eventLoop().execute(flushTask);
      }

   }

   protected abstract long doWriteFileRegion(FileRegion var1) throws Exception;

   protected abstract int doReadBytes(ByteBuf var1) throws Exception;

   protected abstract int doWriteBytes(ByteBuf var1) throws Exception;

   protected final void setOpWrite() {
      SelectionKey key = this.selectionKey();
      if (key.isValid()) {
         int interestOps = key.interestOps();
         if ((interestOps & 4) == 0) {
            key.interestOps(interestOps | 4);
         }

      }
   }

   protected final void clearOpWrite() {
      SelectionKey key = this.selectionKey();
      if (key.isValid()) {
         int interestOps = key.interestOps();
         if ((interestOps & 4) != 0) {
            key.interestOps(interestOps & -5);
         }

      }
   }

   protected class NioByteUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
      protected NioByteUnsafe() {
         super();
      }

      private void closeOnRead(ChannelPipeline pipeline) {
         if (!AbstractNioByteChannel.this.isInputShutdown0()) {
            if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
               AbstractNioByteChannel.this.shutdownInput();
               pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            } else {
               this.close(this.voidPromise());
            }
         } else {
            pipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
         }

      }

      private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, RecvByteBufAllocator.Handle allocHandle) {
         if (byteBuf != null) {
            if (byteBuf.isReadable()) {
               AbstractNioByteChannel.this.readPending = false;
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

      public final void read() {
         ChannelConfig config = AbstractNioByteChannel.this.config();
         ChannelPipeline pipeline = AbstractNioByteChannel.this.pipeline();
         ByteBufAllocator allocator = config.getAllocator();
         RecvByteBufAllocator.Handle allocHandle = this.recvBufAllocHandle();
         allocHandle.reset(config);
         ByteBuf byteBuf = null;
         boolean close = false;

         try {
            do {
               byteBuf = allocHandle.allocate(allocator);
               allocHandle.lastBytesRead(AbstractNioByteChannel.this.doReadBytes(byteBuf));
               if (allocHandle.lastBytesRead() <= 0) {
                  byteBuf.release();
                  byteBuf = null;
                  close = allocHandle.lastBytesRead() < 0;
                  break;
               }

               allocHandle.incMessagesRead(1);
               AbstractNioByteChannel.this.readPending = false;
               pipeline.fireChannelRead(byteBuf);
               byteBuf = null;
            } while(allocHandle.continueReading());

            allocHandle.readComplete();
            pipeline.fireChannelReadComplete();
            if (close) {
               this.closeOnRead(pipeline);
            }
         } catch (Throwable var11) {
            this.handleReadException(pipeline, byteBuf, var11, close, allocHandle);
         } finally {
            if (!AbstractNioByteChannel.this.readPending && !config.isAutoRead()) {
               this.removeReadOp();
            }

         }

      }
   }
}
