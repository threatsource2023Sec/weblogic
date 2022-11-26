package org.python.netty.channel.oio;

import java.net.SocketAddress;
import org.python.netty.channel.AbstractChannel;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.EventLoop;
import org.python.netty.channel.ThreadPerChannelEventLoop;

public abstract class AbstractOioChannel extends AbstractChannel {
   protected static final int SO_TIMEOUT = 1000;
   boolean readPending;
   private final Runnable readTask = new Runnable() {
      public void run() {
         AbstractOioChannel.this.doRead();
      }
   };
   private final Runnable clearReadPendingRunnable = new Runnable() {
      public void run() {
         AbstractOioChannel.this.readPending = false;
      }
   };

   protected AbstractOioChannel(Channel parent) {
      super(parent);
   }

   protected AbstractChannel.AbstractUnsafe newUnsafe() {
      return new DefaultOioUnsafe();
   }

   protected boolean isCompatible(EventLoop loop) {
      return loop instanceof ThreadPerChannelEventLoop;
   }

   protected abstract void doConnect(SocketAddress var1, SocketAddress var2) throws Exception;

   protected void doBeginRead() throws Exception {
      if (!this.readPending) {
         this.readPending = true;
         this.eventLoop().execute(this.readTask);
      }
   }

   protected abstract void doRead();

   /** @deprecated */
   @Deprecated
   protected boolean isReadPending() {
      return this.readPending;
   }

   /** @deprecated */
   @Deprecated
   protected void setReadPending(final boolean readPending) {
      if (this.isRegistered()) {
         EventLoop eventLoop = this.eventLoop();
         if (eventLoop.inEventLoop()) {
            this.readPending = readPending;
         } else {
            eventLoop.execute(new Runnable() {
               public void run() {
                  AbstractOioChannel.this.readPending = readPending;
               }
            });
         }
      } else {
         this.readPending = readPending;
      }

   }

   protected final void clearReadPending() {
      if (this.isRegistered()) {
         EventLoop eventLoop = this.eventLoop();
         if (eventLoop.inEventLoop()) {
            this.readPending = false;
         } else {
            eventLoop.execute(this.clearReadPendingRunnable);
         }
      } else {
         this.readPending = false;
      }

   }

   private final class DefaultOioUnsafe extends AbstractChannel.AbstractUnsafe {
      private DefaultOioUnsafe() {
         super();
      }

      public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
         if (promise.setUncancellable() && this.ensureOpen(promise)) {
            try {
               boolean wasActive = AbstractOioChannel.this.isActive();
               AbstractOioChannel.this.doConnect(remoteAddress, localAddress);
               boolean active = AbstractOioChannel.this.isActive();
               this.safeSetSuccess(promise);
               if (!wasActive && active) {
                  AbstractOioChannel.this.pipeline().fireChannelActive();
               }
            } catch (Throwable var6) {
               this.safeSetFailure(promise, this.annotateConnectException(var6, remoteAddress));
               this.closeIfClosed();
            }

         }
      }

      // $FF: synthetic method
      DefaultOioUnsafe(Object x1) {
         this();
      }
   }
}
