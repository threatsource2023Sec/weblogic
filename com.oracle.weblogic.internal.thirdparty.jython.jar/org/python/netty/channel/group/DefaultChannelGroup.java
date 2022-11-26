package org.python.netty.channel.group;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufHolder;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelId;
import org.python.netty.channel.ServerChannel;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;

public class DefaultChannelGroup extends AbstractSet implements ChannelGroup {
   private static final AtomicInteger nextId = new AtomicInteger();
   private final String name;
   private final EventExecutor executor;
   private final ConcurrentMap serverChannels;
   private final ConcurrentMap nonServerChannels;
   private final ChannelFutureListener remover;
   private final VoidChannelGroupFuture voidFuture;
   private final boolean stayClosed;
   private volatile boolean closed;

   public DefaultChannelGroup(EventExecutor executor) {
      this(executor, false);
   }

   public DefaultChannelGroup(String name, EventExecutor executor) {
      this(name, executor, false);
   }

   public DefaultChannelGroup(EventExecutor executor, boolean stayClosed) {
      this("group-0x" + Integer.toHexString(nextId.incrementAndGet()), executor, stayClosed);
   }

   public DefaultChannelGroup(String name, EventExecutor executor, boolean stayClosed) {
      this.serverChannels = PlatformDependent.newConcurrentHashMap();
      this.nonServerChannels = PlatformDependent.newConcurrentHashMap();
      this.remover = new ChannelFutureListener() {
         public void operationComplete(ChannelFuture future) throws Exception {
            DefaultChannelGroup.this.remove(future.channel());
         }
      };
      this.voidFuture = new VoidChannelGroupFuture(this);
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         this.name = name;
         this.executor = executor;
         this.stayClosed = stayClosed;
      }
   }

   public String name() {
      return this.name;
   }

   public Channel find(ChannelId id) {
      Channel c = (Channel)this.nonServerChannels.get(id);
      return c != null ? c : (Channel)this.serverChannels.get(id);
   }

   public boolean isEmpty() {
      return this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty();
   }

   public int size() {
      return this.nonServerChannels.size() + this.serverChannels.size();
   }

   public boolean contains(Object o) {
      if (o instanceof Channel) {
         Channel c = (Channel)o;
         return o instanceof ServerChannel ? this.serverChannels.containsValue(c) : this.nonServerChannels.containsValue(c);
      } else {
         return false;
      }
   }

   public boolean add(Channel channel) {
      ConcurrentMap map = channel instanceof ServerChannel ? this.serverChannels : this.nonServerChannels;
      boolean added = map.putIfAbsent(channel.id(), channel) == null;
      if (added) {
         channel.closeFuture().addListener(this.remover);
      }

      if (this.stayClosed && this.closed) {
         channel.close();
      }

      return added;
   }

   public boolean remove(Object o) {
      Channel c = null;
      if (o instanceof ChannelId) {
         c = (Channel)this.nonServerChannels.remove(o);
         if (c == null) {
            c = (Channel)this.serverChannels.remove(o);
         }
      } else if (o instanceof Channel) {
         c = (Channel)o;
         if (c instanceof ServerChannel) {
            c = (Channel)this.serverChannels.remove(c.id());
         } else {
            c = (Channel)this.nonServerChannels.remove(c.id());
         }
      }

      if (c == null) {
         return false;
      } else {
         c.closeFuture().removeListener(this.remover);
         return true;
      }
   }

   public void clear() {
      this.nonServerChannels.clear();
      this.serverChannels.clear();
   }

   public Iterator iterator() {
      return new CombinedIterator(this.serverChannels.values().iterator(), this.nonServerChannels.values().iterator());
   }

   public Object[] toArray() {
      Collection channels = new ArrayList(this.size());
      channels.addAll(this.serverChannels.values());
      channels.addAll(this.nonServerChannels.values());
      return channels.toArray();
   }

   public Object[] toArray(Object[] a) {
      Collection channels = new ArrayList(this.size());
      channels.addAll(this.serverChannels.values());
      channels.addAll(this.nonServerChannels.values());
      return channels.toArray(a);
   }

   public ChannelGroupFuture close() {
      return this.close(ChannelMatchers.all());
   }

   public ChannelGroupFuture disconnect() {
      return this.disconnect(ChannelMatchers.all());
   }

   public ChannelGroupFuture deregister() {
      return this.deregister(ChannelMatchers.all());
   }

   public ChannelGroupFuture write(Object message) {
      return this.write(message, ChannelMatchers.all());
   }

   private static Object safeDuplicate(Object message) {
      if (message instanceof ByteBuf) {
         return ((ByteBuf)message).retainedDuplicate();
      } else {
         return message instanceof ByteBufHolder ? ((ByteBufHolder)message).retainedDuplicate() : ReferenceCountUtil.retain(message);
      }
   }

   public ChannelGroupFuture write(Object message, ChannelMatcher matcher) {
      return this.write(message, matcher, false);
   }

   public ChannelGroupFuture write(Object message, ChannelMatcher matcher, boolean voidPromise) {
      if (message == null) {
         throw new NullPointerException("message");
      } else if (matcher == null) {
         throw new NullPointerException("matcher");
      } else {
         Object future;
         if (voidPromise) {
            Iterator var4 = this.nonServerChannels.values().iterator();

            while(var4.hasNext()) {
               Channel c = (Channel)var4.next();
               if (matcher.matches(c)) {
                  c.write(safeDuplicate(message), c.voidPromise());
               }
            }

            future = this.voidFuture;
         } else {
            Map futures = new LinkedHashMap(this.size());
            Iterator var9 = this.nonServerChannels.values().iterator();

            while(var9.hasNext()) {
               Channel c = (Channel)var9.next();
               if (matcher.matches(c)) {
                  futures.put(c, c.write(safeDuplicate(message)));
               }
            }

            future = new DefaultChannelGroupFuture(this, futures, this.executor);
         }

         ReferenceCountUtil.release(message);
         return (ChannelGroupFuture)future;
      }
   }

   public ChannelGroup flush() {
      return this.flush(ChannelMatchers.all());
   }

   public ChannelGroupFuture flushAndWrite(Object message) {
      return this.writeAndFlush(message);
   }

   public ChannelGroupFuture writeAndFlush(Object message) {
      return this.writeAndFlush(message, ChannelMatchers.all());
   }

   public ChannelGroupFuture disconnect(ChannelMatcher matcher) {
      if (matcher == null) {
         throw new NullPointerException("matcher");
      } else {
         Map futures = new LinkedHashMap(this.size());
         Iterator var3 = this.serverChannels.values().iterator();

         Channel c;
         while(var3.hasNext()) {
            c = (Channel)var3.next();
            if (matcher.matches(c)) {
               futures.put(c, c.disconnect());
            }
         }

         var3 = this.nonServerChannels.values().iterator();

         while(var3.hasNext()) {
            c = (Channel)var3.next();
            if (matcher.matches(c)) {
               futures.put(c, c.disconnect());
            }
         }

         return new DefaultChannelGroupFuture(this, futures, this.executor);
      }
   }

   public ChannelGroupFuture close(ChannelMatcher matcher) {
      if (matcher == null) {
         throw new NullPointerException("matcher");
      } else {
         Map futures = new LinkedHashMap(this.size());
         if (this.stayClosed) {
            this.closed = true;
         }

         Iterator var3 = this.serverChannels.values().iterator();

         Channel c;
         while(var3.hasNext()) {
            c = (Channel)var3.next();
            if (matcher.matches(c)) {
               futures.put(c, c.close());
            }
         }

         var3 = this.nonServerChannels.values().iterator();

         while(var3.hasNext()) {
            c = (Channel)var3.next();
            if (matcher.matches(c)) {
               futures.put(c, c.close());
            }
         }

         return new DefaultChannelGroupFuture(this, futures, this.executor);
      }
   }

   public ChannelGroupFuture deregister(ChannelMatcher matcher) {
      if (matcher == null) {
         throw new NullPointerException("matcher");
      } else {
         Map futures = new LinkedHashMap(this.size());
         Iterator var3 = this.serverChannels.values().iterator();

         Channel c;
         while(var3.hasNext()) {
            c = (Channel)var3.next();
            if (matcher.matches(c)) {
               futures.put(c, c.deregister());
            }
         }

         var3 = this.nonServerChannels.values().iterator();

         while(var3.hasNext()) {
            c = (Channel)var3.next();
            if (matcher.matches(c)) {
               futures.put(c, c.deregister());
            }
         }

         return new DefaultChannelGroupFuture(this, futures, this.executor);
      }
   }

   public ChannelGroup flush(ChannelMatcher matcher) {
      Iterator var2 = this.nonServerChannels.values().iterator();

      while(var2.hasNext()) {
         Channel c = (Channel)var2.next();
         if (matcher.matches(c)) {
            c.flush();
         }
      }

      return this;
   }

   public ChannelGroupFuture flushAndWrite(Object message, ChannelMatcher matcher) {
      return this.writeAndFlush(message, matcher);
   }

   public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher) {
      return this.writeAndFlush(message, matcher, false);
   }

   public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher, boolean voidPromise) {
      if (message == null) {
         throw new NullPointerException("message");
      } else {
         Object future;
         if (voidPromise) {
            Iterator var4 = this.nonServerChannels.values().iterator();

            while(var4.hasNext()) {
               Channel c = (Channel)var4.next();
               if (matcher.matches(c)) {
                  c.writeAndFlush(safeDuplicate(message), c.voidPromise());
               }
            }

            future = this.voidFuture;
         } else {
            Map futures = new LinkedHashMap(this.size());
            Iterator var9 = this.nonServerChannels.values().iterator();

            while(var9.hasNext()) {
               Channel c = (Channel)var9.next();
               if (matcher.matches(c)) {
                  futures.put(c, c.writeAndFlush(safeDuplicate(message)));
               }
            }

            future = new DefaultChannelGroupFuture(this, futures, this.executor);
         }

         ReferenceCountUtil.release(message);
         return (ChannelGroupFuture)future;
      }
   }

   public ChannelGroupFuture newCloseFuture() {
      return this.newCloseFuture(ChannelMatchers.all());
   }

   public ChannelGroupFuture newCloseFuture(ChannelMatcher matcher) {
      Map futures = new LinkedHashMap(this.size());
      Iterator var3 = this.serverChannels.values().iterator();

      Channel c;
      while(var3.hasNext()) {
         c = (Channel)var3.next();
         if (matcher.matches(c)) {
            futures.put(c, c.closeFuture());
         }
      }

      var3 = this.nonServerChannels.values().iterator();

      while(var3.hasNext()) {
         c = (Channel)var3.next();
         if (matcher.matches(c)) {
            futures.put(c, c.closeFuture());
         }
      }

      return new DefaultChannelGroupFuture(this, futures, this.executor);
   }

   public int hashCode() {
      return System.identityHashCode(this);
   }

   public boolean equals(Object o) {
      return this == o;
   }

   public int compareTo(ChannelGroup o) {
      int v = this.name().compareTo(o.name());
      return v != 0 ? v : System.identityHashCode(this) - System.identityHashCode(o);
   }

   public String toString() {
      return StringUtil.simpleClassName((Object)this) + "(name: " + this.name() + ", size: " + this.size() + ')';
   }
}
