package org.python.netty.channel.nio;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.ChannelOutboundBuffer;
import org.python.netty.channel.ChannelPipeline;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.ServerChannel;

public abstract class AbstractNioMessageChannel extends AbstractNioChannel {
   boolean inputShutdown;

   protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
      super(parent, ch, readInterestOp);
   }

   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
      return new NioMessageUnsafe();
   }

   protected void doBeginRead() throws Exception {
      if (!this.inputShutdown) {
         super.doBeginRead();
      }
   }

   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
      SelectionKey key = this.selectionKey();
      int interestOps = key.interestOps();

      while(true) {
         Object msg = in.current();
         if (msg == null) {
            if ((interestOps & 4) != 0) {
               key.interestOps(interestOps & -5);
            }
            break;
         }

         try {
            boolean done = false;

            for(int i = this.config().getWriteSpinCount() - 1; i >= 0; --i) {
               if (this.doWriteMessage(msg, in)) {
                  done = true;
                  break;
               }
            }

            if (!done) {
               if ((interestOps & 4) == 0) {
                  key.interestOps(interestOps | 4);
               }
               break;
            }

            in.remove();
         } catch (IOException var7) {
            if (!this.continueOnWriteError()) {
               throw var7;
            }

            in.remove(var7);
         }
      }

   }

   protected boolean continueOnWriteError() {
      return false;
   }

   protected boolean closeOnReadError(Throwable cause) {
      return cause instanceof IOException && !(cause instanceof PortUnreachableException) && !(this instanceof ServerChannel);
   }

   protected abstract int doReadMessages(List var1) throws Exception;

   protected abstract boolean doWriteMessage(Object var1, ChannelOutboundBuffer var2) throws Exception;

   private final class NioMessageUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
      private final List readBuf;

      private NioMessageUnsafe() {
         super();
         this.readBuf = new ArrayList();
      }

      public void read() {
         assert AbstractNioMessageChannel.this.eventLoop().inEventLoop();

         ChannelConfig config = AbstractNioMessageChannel.this.config();
         ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
         RecvByteBufAllocator.Handle allocHandle = AbstractNioMessageChannel.this.unsafe().recvBufAllocHandle();
         allocHandle.reset(config);
         boolean closed = false;
         Throwable exception = null;

         try {
            int localRead;
            try {
               do {
                  localRead = AbstractNioMessageChannel.this.doReadMessages(this.readBuf);
                  if (localRead == 0) {
                     break;
                  }

                  if (localRead < 0) {
                     closed = true;
                     break;
                  }

                  allocHandle.incMessagesRead(localRead);
               } while(allocHandle.continueReading());
            } catch (Throwable var11) {
               exception = var11;
            }

            localRead = this.readBuf.size();

            for(int i = 0; i < localRead; ++i) {
               AbstractNioMessageChannel.this.readPending = false;
               pipeline.fireChannelRead(this.readBuf.get(i));
            }

            this.readBuf.clear();
            allocHandle.readComplete();
            pipeline.fireChannelReadComplete();
            if (exception != null) {
               closed = AbstractNioMessageChannel.this.closeOnReadError(exception);
               pipeline.fireExceptionCaught(exception);
            }

            if (closed) {
               AbstractNioMessageChannel.this.inputShutdown = true;
               if (AbstractNioMessageChannel.this.isOpen()) {
                  this.close(this.voidPromise());
               }
            }
         } finally {
            if (!AbstractNioMessageChannel.this.readPending && !config.isAutoRead()) {
               this.removeReadOp();
            }

         }

      }

      // $FF: synthetic method
      NioMessageUnsafe(Object x1) {
         this();
      }
   }
}
