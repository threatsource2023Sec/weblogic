package org.python.netty.channel.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.util.concurrent.BlockingOperationException;
import org.python.netty.util.concurrent.DefaultPromise;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.GenericFutureListener;
import org.python.netty.util.concurrent.ImmediateEventExecutor;

final class DefaultChannelGroupFuture extends DefaultPromise implements ChannelGroupFuture {
   private final ChannelGroup group;
   private final Map futures;
   private int successCount;
   private int failureCount;
   private final ChannelFutureListener childListener = new ChannelFutureListener() {
      public void operationComplete(ChannelFuture future) throws Exception {
         boolean success = future.isSuccess();
         boolean callSetDone;
         synchronized(DefaultChannelGroupFuture.this) {
            if (success) {
               DefaultChannelGroupFuture.this.successCount++;
            } else {
               DefaultChannelGroupFuture.this.failureCount++;
            }

            callSetDone = DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount == DefaultChannelGroupFuture.this.futures.size();

            assert DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount <= DefaultChannelGroupFuture.this.futures.size();
         }

         if (callSetDone) {
            if (DefaultChannelGroupFuture.this.failureCount > 0) {
               List failed = new ArrayList(DefaultChannelGroupFuture.this.failureCount);
               Iterator var5 = DefaultChannelGroupFuture.this.futures.values().iterator();

               while(var5.hasNext()) {
                  ChannelFuture f = (ChannelFuture)var5.next();
                  if (!f.isSuccess()) {
                     failed.add(new DefaultEntry(f.channel(), f.cause()));
                  }
               }

               DefaultChannelGroupFuture.this.setFailure0(new ChannelGroupException(failed));
            } else {
               DefaultChannelGroupFuture.this.setSuccess0();
            }
         }

      }
   };

   DefaultChannelGroupFuture(ChannelGroup group, Collection futures, EventExecutor executor) {
      super(executor);
      if (group == null) {
         throw new NullPointerException("group");
      } else if (futures == null) {
         throw new NullPointerException("futures");
      } else {
         this.group = group;
         Map futureMap = new LinkedHashMap();
         Iterator var5 = futures.iterator();

         ChannelFuture f;
         while(var5.hasNext()) {
            f = (ChannelFuture)var5.next();
            futureMap.put(f.channel(), f);
         }

         this.futures = Collections.unmodifiableMap(futureMap);
         var5 = this.futures.values().iterator();

         while(var5.hasNext()) {
            f = (ChannelFuture)var5.next();
            f.addListener(this.childListener);
         }

         if (this.futures.isEmpty()) {
            this.setSuccess0();
         }

      }
   }

   DefaultChannelGroupFuture(ChannelGroup group, Map futures, EventExecutor executor) {
      super(executor);
      this.group = group;
      this.futures = Collections.unmodifiableMap(futures);
      Iterator var4 = this.futures.values().iterator();

      while(var4.hasNext()) {
         ChannelFuture f = (ChannelFuture)var4.next();
         f.addListener(this.childListener);
      }

      if (this.futures.isEmpty()) {
         this.setSuccess0();
      }

   }

   public ChannelGroup group() {
      return this.group;
   }

   public ChannelFuture find(Channel channel) {
      return (ChannelFuture)this.futures.get(channel);
   }

   public Iterator iterator() {
      return this.futures.values().iterator();
   }

   public synchronized boolean isPartialSuccess() {
      return this.successCount != 0 && this.successCount != this.futures.size();
   }

   public synchronized boolean isPartialFailure() {
      return this.failureCount != 0 && this.failureCount != this.futures.size();
   }

   public DefaultChannelGroupFuture addListener(GenericFutureListener listener) {
      super.addListener(listener);
      return this;
   }

   public DefaultChannelGroupFuture addListeners(GenericFutureListener... listeners) {
      super.addListeners(listeners);
      return this;
   }

   public DefaultChannelGroupFuture removeListener(GenericFutureListener listener) {
      super.removeListener(listener);
      return this;
   }

   public DefaultChannelGroupFuture removeListeners(GenericFutureListener... listeners) {
      super.removeListeners(listeners);
      return this;
   }

   public DefaultChannelGroupFuture await() throws InterruptedException {
      super.await();
      return this;
   }

   public DefaultChannelGroupFuture awaitUninterruptibly() {
      super.awaitUninterruptibly();
      return this;
   }

   public DefaultChannelGroupFuture syncUninterruptibly() {
      super.syncUninterruptibly();
      return this;
   }

   public DefaultChannelGroupFuture sync() throws InterruptedException {
      super.sync();
      return this;
   }

   public ChannelGroupException cause() {
      return (ChannelGroupException)super.cause();
   }

   private void setSuccess0() {
      super.setSuccess((Object)null);
   }

   private void setFailure0(ChannelGroupException cause) {
      super.setFailure(cause);
   }

   public DefaultChannelGroupFuture setSuccess(Void result) {
      throw new IllegalStateException();
   }

   public boolean trySuccess(Void result) {
      throw new IllegalStateException();
   }

   public DefaultChannelGroupFuture setFailure(Throwable cause) {
      throw new IllegalStateException();
   }

   public boolean tryFailure(Throwable cause) {
      throw new IllegalStateException();
   }

   protected void checkDeadLock() {
      EventExecutor e = this.executor();
      if (e != null && e != ImmediateEventExecutor.INSTANCE && e.inEventLoop()) {
         throw new BlockingOperationException();
      }
   }

   private static final class DefaultEntry implements Map.Entry {
      private final Object key;
      private final Object value;

      DefaultEntry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException("read-only");
      }
   }
}
