package org.python.netty.channel.pool;

import java.util.Deque;
import org.python.netty.bootstrap.Bootstrap;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelInitializer;
import org.python.netty.channel.EventLoop;
import org.python.netty.util.AttributeKey;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.ThrowableUtil;

public class SimpleChannelPool implements ChannelPool {
   private static final AttributeKey POOL_KEY = AttributeKey.newInstance("channelPool");
   private static final IllegalStateException FULL_EXCEPTION = (IllegalStateException)ThrowableUtil.unknownStackTrace(new IllegalStateException("ChannelPool full"), SimpleChannelPool.class, "releaseAndOffer(...)");
   private static final IllegalStateException UNHEALTHY_NON_OFFERED_TO_POOL = (IllegalStateException)ThrowableUtil.unknownStackTrace(new IllegalStateException("Channel is unhealthy not offering it back to pool"), SimpleChannelPool.class, "releaseAndOffer(...)");
   private final Deque deque;
   private final ChannelPoolHandler handler;
   private final ChannelHealthChecker healthCheck;
   private final Bootstrap bootstrap;
   private final boolean releaseHealthCheck;

   public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler) {
      this(bootstrap, handler, ChannelHealthChecker.ACTIVE);
   }

   public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck) {
      this(bootstrap, handler, healthCheck, true);
   }

   public SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler handler, ChannelHealthChecker healthCheck, boolean releaseHealthCheck) {
      this.deque = PlatformDependent.newConcurrentDeque();
      this.handler = (ChannelPoolHandler)ObjectUtil.checkNotNull(handler, "handler");
      this.healthCheck = (ChannelHealthChecker)ObjectUtil.checkNotNull(healthCheck, "healthCheck");
      this.releaseHealthCheck = releaseHealthCheck;
      this.bootstrap = ((Bootstrap)ObjectUtil.checkNotNull(bootstrap, "bootstrap")).clone();
      this.bootstrap.handler(new ChannelInitializer() {
         protected void initChannel(Channel ch) throws Exception {
            assert ch.eventLoop().inEventLoop();

            handler.channelCreated(ch);
         }
      });
   }

   protected Bootstrap bootstrap() {
      return this.bootstrap;
   }

   protected ChannelPoolHandler handler() {
      return this.handler;
   }

   protected ChannelHealthChecker healthChecker() {
      return this.healthCheck;
   }

   protected boolean releaseHealthCheck() {
      return this.releaseHealthCheck;
   }

   public final Future acquire() {
      return this.acquire(this.bootstrap.config().group().next().newPromise());
   }

   public Future acquire(Promise promise) {
      ObjectUtil.checkNotNull(promise, "promise");
      return this.acquireHealthyFromPoolOrNew(promise);
   }

   private Future acquireHealthyFromPoolOrNew(final Promise promise) {
      try {
         final Channel ch = this.pollChannel();
         if (ch == null) {
            Bootstrap bs = this.bootstrap.clone();
            bs.attr(POOL_KEY, this);
            ChannelFuture f = this.connectChannel(bs);
            if (f.isDone()) {
               this.notifyConnect(f, promise);
            } else {
               f.addListener(new ChannelFutureListener() {
                  public void operationComplete(ChannelFuture future) throws Exception {
                     SimpleChannelPool.this.notifyConnect(future, promise);
                  }
               });
            }

            return promise;
         }

         EventLoop loop = ch.eventLoop();
         if (loop.inEventLoop()) {
            this.doHealthCheck(ch, promise);
         } else {
            loop.execute(new Runnable() {
               public void run() {
                  SimpleChannelPool.this.doHealthCheck(ch, promise);
               }
            });
         }
      } catch (Throwable var5) {
         promise.tryFailure(var5);
      }

      return promise;
   }

   private void notifyConnect(ChannelFuture future, Promise promise) {
      if (future.isSuccess()) {
         Channel channel = future.channel();
         if (!promise.trySuccess(channel)) {
            this.release(channel);
         }
      } else {
         promise.tryFailure(future.cause());
      }

   }

   private void doHealthCheck(final Channel ch, final Promise promise) {
      assert ch.eventLoop().inEventLoop();

      Future f = this.healthCheck.isHealthy(ch);
      if (f.isDone()) {
         this.notifyHealthCheck(f, ch, promise);
      } else {
         f.addListener(new FutureListener() {
            public void operationComplete(Future future) throws Exception {
               SimpleChannelPool.this.notifyHealthCheck(future, ch, promise);
            }
         });
      }

   }

   private void notifyHealthCheck(Future future, Channel ch, Promise promise) {
      assert ch.eventLoop().inEventLoop();

      if (future.isSuccess()) {
         if ((Boolean)future.getNow()) {
            try {
               ch.attr(POOL_KEY).set(this);
               this.handler.channelAcquired(ch);
               promise.setSuccess(ch);
            } catch (Throwable var5) {
               closeAndFail(ch, var5, promise);
            }
         } else {
            closeChannel(ch);
            this.acquireHealthyFromPoolOrNew(promise);
         }
      } else {
         closeChannel(ch);
         this.acquireHealthyFromPoolOrNew(promise);
      }

   }

   protected ChannelFuture connectChannel(Bootstrap bs) {
      return bs.connect();
   }

   public final Future release(Channel channel) {
      return this.release(channel, channel.eventLoop().newPromise());
   }

   public Future release(final Channel channel, final Promise promise) {
      ObjectUtil.checkNotNull(channel, "channel");
      ObjectUtil.checkNotNull(promise, "promise");

      try {
         EventLoop loop = channel.eventLoop();
         if (loop.inEventLoop()) {
            this.doReleaseChannel(channel, promise);
         } else {
            loop.execute(new Runnable() {
               public void run() {
                  SimpleChannelPool.this.doReleaseChannel(channel, promise);
               }
            });
         }
      } catch (Throwable var4) {
         closeAndFail(channel, var4, promise);
      }

      return promise;
   }

   private void doReleaseChannel(Channel channel, Promise promise) {
      assert channel.eventLoop().inEventLoop();

      if (channel.attr(POOL_KEY).getAndSet((Object)null) != this) {
         closeAndFail(channel, new IllegalArgumentException("Channel " + channel + " was not acquired from this ChannelPool"), promise);
      } else {
         try {
            if (this.releaseHealthCheck) {
               this.doHealthCheckOnRelease(channel, promise);
            } else {
               this.releaseAndOffer(channel, promise);
            }
         } catch (Throwable var4) {
            closeAndFail(channel, var4, promise);
         }
      }

   }

   private void doHealthCheckOnRelease(final Channel channel, final Promise promise) throws Exception {
      final Future f = this.healthCheck.isHealthy(channel);
      if (f.isDone()) {
         this.releaseAndOfferIfHealthy(channel, promise, f);
      } else {
         f.addListener(new FutureListener() {
            public void operationComplete(Future future) throws Exception {
               SimpleChannelPool.this.releaseAndOfferIfHealthy(channel, promise, f);
            }
         });
      }

   }

   private void releaseAndOfferIfHealthy(Channel channel, Promise promise, Future future) throws Exception {
      if ((Boolean)future.getNow()) {
         this.releaseAndOffer(channel, promise);
      } else {
         this.handler.channelReleased(channel);
         closeAndFail(channel, UNHEALTHY_NON_OFFERED_TO_POOL, promise);
      }

   }

   private void releaseAndOffer(Channel channel, Promise promise) throws Exception {
      if (this.offerChannel(channel)) {
         this.handler.channelReleased(channel);
         promise.setSuccess((Object)null);
      } else {
         closeAndFail(channel, FULL_EXCEPTION, promise);
      }

   }

   private static void closeChannel(Channel channel) {
      channel.attr(POOL_KEY).getAndSet((Object)null);
      channel.close();
   }

   private static void closeAndFail(Channel channel, Throwable cause, Promise promise) {
      closeChannel(channel);
      promise.tryFailure(cause);
   }

   protected Channel pollChannel() {
      return (Channel)this.deque.pollLast();
   }

   protected boolean offerChannel(Channel channel) {
      return this.deque.offer(channel);
   }

   public void close() {
      while(true) {
         Channel channel = this.pollChannel();
         if (channel == null) {
            return;
         }

         channel.close();
      }
   }
}
