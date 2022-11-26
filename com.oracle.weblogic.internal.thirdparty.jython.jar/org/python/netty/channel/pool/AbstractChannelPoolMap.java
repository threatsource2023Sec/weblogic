package org.python.netty.channel.pool;

import java.io.Closeable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.ReadOnlyIterator;

public abstract class AbstractChannelPoolMap implements ChannelPoolMap, Iterable, Closeable {
   private final ConcurrentMap map = PlatformDependent.newConcurrentHashMap();

   public final ChannelPool get(Object key) {
      ChannelPool pool = (ChannelPool)this.map.get(ObjectUtil.checkNotNull(key, "key"));
      if (pool == null) {
         pool = this.newPool(key);
         ChannelPool old = (ChannelPool)this.map.putIfAbsent(key, pool);
         if (old != null) {
            pool.close();
            pool = old;
         }
      }

      return pool;
   }

   public final boolean remove(Object key) {
      ChannelPool pool = (ChannelPool)this.map.remove(ObjectUtil.checkNotNull(key, "key"));
      if (pool != null) {
         pool.close();
         return true;
      } else {
         return false;
      }
   }

   public final Iterator iterator() {
      return new ReadOnlyIterator(this.map.entrySet().iterator());
   }

   public final int size() {
      return this.map.size();
   }

   public final boolean isEmpty() {
      return this.map.isEmpty();
   }

   public final boolean contains(Object key) {
      return this.map.containsKey(ObjectUtil.checkNotNull(key, "key"));
   }

   protected abstract ChannelPool newPool(Object var1);

   public final void close() {
      Iterator var1 = this.map.keySet().iterator();

      while(var1.hasNext()) {
         Object key = var1.next();
         this.remove(key);
      }

   }
}
