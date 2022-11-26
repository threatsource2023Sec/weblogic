package org.python.google.common.util.concurrent;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.Maps;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public final class AtomicLongMap implements Serializable {
   private final ConcurrentHashMap map;
   private transient Map asMap;

   private AtomicLongMap(ConcurrentHashMap map) {
      this.map = (ConcurrentHashMap)Preconditions.checkNotNull(map);
   }

   public static AtomicLongMap create() {
      return new AtomicLongMap(new ConcurrentHashMap());
   }

   public static AtomicLongMap create(Map m) {
      AtomicLongMap result = create();
      result.putAll(m);
      return result;
   }

   public long get(Object key) {
      AtomicLong atomic = (AtomicLong)this.map.get(key);
      return atomic == null ? 0L : atomic.get();
   }

   @CanIgnoreReturnValue
   public long incrementAndGet(Object key) {
      return this.addAndGet(key, 1L);
   }

   @CanIgnoreReturnValue
   public long decrementAndGet(Object key) {
      return this.addAndGet(key, -1L);
   }

   @CanIgnoreReturnValue
   public long addAndGet(Object key, long delta) {
      AtomicLong atomic;
      label23:
      do {
         atomic = (AtomicLong)this.map.get(key);
         if (atomic == null) {
            atomic = (AtomicLong)this.map.putIfAbsent(key, new AtomicLong(delta));
            if (atomic == null) {
               return delta;
            }
         }

         long oldValue;
         long newValue;
         do {
            oldValue = atomic.get();
            if (oldValue == 0L) {
               continue label23;
            }

            newValue = oldValue + delta;
         } while(!atomic.compareAndSet(oldValue, newValue));

         return newValue;
      } while(!this.map.replace(key, atomic, new AtomicLong(delta)));

      return delta;
   }

   @CanIgnoreReturnValue
   public long getAndIncrement(Object key) {
      return this.getAndAdd(key, 1L);
   }

   @CanIgnoreReturnValue
   public long getAndDecrement(Object key) {
      return this.getAndAdd(key, -1L);
   }

   @CanIgnoreReturnValue
   public long getAndAdd(Object key, long delta) {
      AtomicLong atomic;
      label23:
      do {
         atomic = (AtomicLong)this.map.get(key);
         if (atomic == null) {
            atomic = (AtomicLong)this.map.putIfAbsent(key, new AtomicLong(delta));
            if (atomic == null) {
               return 0L;
            }
         }

         long oldValue;
         long newValue;
         do {
            oldValue = atomic.get();
            if (oldValue == 0L) {
               continue label23;
            }

            newValue = oldValue + delta;
         } while(!atomic.compareAndSet(oldValue, newValue));

         return oldValue;
      } while(!this.map.replace(key, atomic, new AtomicLong(delta)));

      return 0L;
   }

   @CanIgnoreReturnValue
   public long put(Object key, long newValue) {
      AtomicLong atomic;
      label23:
      do {
         atomic = (AtomicLong)this.map.get(key);
         if (atomic == null) {
            atomic = (AtomicLong)this.map.putIfAbsent(key, new AtomicLong(newValue));
            if (atomic == null) {
               return 0L;
            }
         }

         long oldValue;
         do {
            oldValue = atomic.get();
            if (oldValue == 0L) {
               continue label23;
            }
         } while(!atomic.compareAndSet(oldValue, newValue));

         return oldValue;
      } while(!this.map.replace(key, atomic, new AtomicLong(newValue)));

      return 0L;
   }

   public void putAll(Map m) {
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put(entry.getKey(), (Long)entry.getValue());
      }

   }

   @CanIgnoreReturnValue
   public long remove(Object key) {
      AtomicLong atomic = (AtomicLong)this.map.get(key);
      if (atomic == null) {
         return 0L;
      } else {
         long oldValue;
         do {
            oldValue = atomic.get();
         } while(oldValue != 0L && !atomic.compareAndSet(oldValue, 0L));

         this.map.remove(key, atomic);
         return oldValue;
      }
   }

   @Beta
   @CanIgnoreReturnValue
   public boolean removeIfZero(Object key) {
      return this.remove(key, 0L);
   }

   public void removeAllZeros() {
      Iterator entryIterator = this.map.entrySet().iterator();

      while(entryIterator.hasNext()) {
         Map.Entry entry = (Map.Entry)entryIterator.next();
         AtomicLong atomic = (AtomicLong)entry.getValue();
         if (atomic != null && atomic.get() == 0L) {
            entryIterator.remove();
         }
      }

   }

   public long sum() {
      long sum = 0L;

      AtomicLong value;
      for(Iterator var3 = this.map.values().iterator(); var3.hasNext(); sum += value.get()) {
         value = (AtomicLong)var3.next();
      }

      return sum;
   }

   public Map asMap() {
      Map result = this.asMap;
      return result == null ? (this.asMap = this.createAsMap()) : result;
   }

   private Map createAsMap() {
      return Collections.unmodifiableMap(Maps.transformValues((Map)this.map, new Function() {
         public Long apply(AtomicLong atomic) {
            return atomic.get();
         }
      }));
   }

   public boolean containsKey(Object key) {
      return this.map.containsKey(key);
   }

   public int size() {
      return this.map.size();
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public void clear() {
      this.map.clear();
   }

   public String toString() {
      return this.map.toString();
   }

   long putIfAbsent(Object key, long newValue) {
      while(true) {
         AtomicLong atomic = (AtomicLong)this.map.get(key);
         if (atomic == null) {
            atomic = (AtomicLong)this.map.putIfAbsent(key, new AtomicLong(newValue));
            if (atomic == null) {
               return 0L;
            }
         }

         long oldValue = atomic.get();
         if (oldValue == 0L) {
            if (!this.map.replace(key, atomic, new AtomicLong(newValue))) {
               continue;
            }

            return 0L;
         }

         return oldValue;
      }
   }

   boolean replace(Object key, long expectedOldValue, long newValue) {
      if (expectedOldValue == 0L) {
         return this.putIfAbsent(key, newValue) == 0L;
      } else {
         AtomicLong atomic = (AtomicLong)this.map.get(key);
         return atomic == null ? false : atomic.compareAndSet(expectedOldValue, newValue);
      }
   }

   boolean remove(Object key, long value) {
      AtomicLong atomic = (AtomicLong)this.map.get(key);
      if (atomic == null) {
         return false;
      } else {
         long oldValue = atomic.get();
         if (oldValue != value) {
            return false;
         } else if (oldValue != 0L && !atomic.compareAndSet(oldValue, 0L)) {
            return false;
         } else {
            this.map.remove(key, atomic);
            return true;
         }
      }
   }
}
