package org.python.netty.util.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import org.python.netty.util.concurrent.FastThreadLocalThread;

public final class InternalThreadLocalMap extends UnpaddedInternalThreadLocalMap {
   private static final int STRING_BUILDER_MAX_CAPACITY = 65536;
   private static final int DEFAULT_ARRAY_LIST_INITIAL_CAPACITY = 8;
   public static final Object UNSET = new Object();
   public long rp1;
   public long rp2;
   public long rp3;
   public long rp4;
   public long rp5;
   public long rp6;
   public long rp7;
   public long rp8;
   public long rp9;

   public static InternalThreadLocalMap getIfSet() {
      Thread thread = Thread.currentThread();
      return thread instanceof FastThreadLocalThread ? ((FastThreadLocalThread)thread).threadLocalMap() : (InternalThreadLocalMap)slowThreadLocalMap.get();
   }

   public static InternalThreadLocalMap get() {
      Thread thread = Thread.currentThread();
      return thread instanceof FastThreadLocalThread ? fastGet((FastThreadLocalThread)thread) : slowGet();
   }

   private static InternalThreadLocalMap fastGet(FastThreadLocalThread thread) {
      InternalThreadLocalMap threadLocalMap = thread.threadLocalMap();
      if (threadLocalMap == null) {
         thread.setThreadLocalMap(threadLocalMap = new InternalThreadLocalMap());
      }

      return threadLocalMap;
   }

   private static InternalThreadLocalMap slowGet() {
      ThreadLocal slowThreadLocalMap = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
      InternalThreadLocalMap ret = (InternalThreadLocalMap)slowThreadLocalMap.get();
      if (ret == null) {
         ret = new InternalThreadLocalMap();
         slowThreadLocalMap.set(ret);
      }

      return ret;
   }

   public static void remove() {
      Thread thread = Thread.currentThread();
      if (thread instanceof FastThreadLocalThread) {
         ((FastThreadLocalThread)thread).setThreadLocalMap((InternalThreadLocalMap)null);
      } else {
         slowThreadLocalMap.remove();
      }

   }

   public static void destroy() {
      slowThreadLocalMap.remove();
   }

   public static int nextVariableIndex() {
      int index = nextIndex.getAndIncrement();
      if (index < 0) {
         nextIndex.decrementAndGet();
         throw new IllegalStateException("too many thread-local indexed variables");
      } else {
         return index;
      }
   }

   public static int lastVariableIndex() {
      return nextIndex.get() - 1;
   }

   private InternalThreadLocalMap() {
      super(newIndexedVariableTable());
   }

   private static Object[] newIndexedVariableTable() {
      Object[] array = new Object[32];
      Arrays.fill(array, UNSET);
      return array;
   }

   public int size() {
      int count = 0;
      if (this.futureListenerStackDepth != 0) {
         ++count;
      }

      if (this.localChannelReaderStackDepth != 0) {
         ++count;
      }

      if (this.handlerSharableCache != null) {
         ++count;
      }

      if (this.counterHashCode != null) {
         ++count;
      }

      if (this.random != null) {
         ++count;
      }

      if (this.typeParameterMatcherGetCache != null) {
         ++count;
      }

      if (this.typeParameterMatcherFindCache != null) {
         ++count;
      }

      if (this.stringBuilder != null) {
         ++count;
      }

      if (this.charsetEncoderCache != null) {
         ++count;
      }

      if (this.charsetDecoderCache != null) {
         ++count;
      }

      if (this.arrayList != null) {
         ++count;
      }

      Object[] var2 = this.indexedVariables;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object o = var2[var4];
         if (o != UNSET) {
            ++count;
         }
      }

      return count - 1;
   }

   public StringBuilder stringBuilder() {
      StringBuilder builder = this.stringBuilder;
      if (builder != null && builder.capacity() <= 65536) {
         builder.setLength(0);
      } else {
         this.stringBuilder = builder = new StringBuilder(512);
      }

      return builder;
   }

   public Map charsetEncoderCache() {
      Map cache = this.charsetEncoderCache;
      if (cache == null) {
         this.charsetEncoderCache = (Map)(cache = new IdentityHashMap());
      }

      return (Map)cache;
   }

   public Map charsetDecoderCache() {
      Map cache = this.charsetDecoderCache;
      if (cache == null) {
         this.charsetDecoderCache = (Map)(cache = new IdentityHashMap());
      }

      return (Map)cache;
   }

   public ArrayList arrayList() {
      return this.arrayList(8);
   }

   public ArrayList arrayList(int minCapacity) {
      ArrayList list = this.arrayList;
      if (list == null) {
         this.arrayList = new ArrayList(minCapacity);
         return this.arrayList;
      } else {
         list.clear();
         list.ensureCapacity(minCapacity);
         return list;
      }
   }

   public int futureListenerStackDepth() {
      return this.futureListenerStackDepth;
   }

   public void setFutureListenerStackDepth(int futureListenerStackDepth) {
      this.futureListenerStackDepth = futureListenerStackDepth;
   }

   public ThreadLocalRandom random() {
      ThreadLocalRandom r = this.random;
      if (r == null) {
         this.random = r = new ThreadLocalRandom();
      }

      return r;
   }

   public Map typeParameterMatcherGetCache() {
      Map cache = this.typeParameterMatcherGetCache;
      if (cache == null) {
         this.typeParameterMatcherGetCache = (Map)(cache = new IdentityHashMap());
      }

      return (Map)cache;
   }

   public Map typeParameterMatcherFindCache() {
      Map cache = this.typeParameterMatcherFindCache;
      if (cache == null) {
         this.typeParameterMatcherFindCache = (Map)(cache = new IdentityHashMap());
      }

      return (Map)cache;
   }

   public IntegerHolder counterHashCode() {
      return this.counterHashCode;
   }

   public void setCounterHashCode(IntegerHolder counterHashCode) {
      this.counterHashCode = counterHashCode;
   }

   public Map handlerSharableCache() {
      Map cache = this.handlerSharableCache;
      if (cache == null) {
         this.handlerSharableCache = (Map)(cache = new WeakHashMap(4));
      }

      return (Map)cache;
   }

   public int localChannelReaderStackDepth() {
      return this.localChannelReaderStackDepth;
   }

   public void setLocalChannelReaderStackDepth(int localChannelReaderStackDepth) {
      this.localChannelReaderStackDepth = localChannelReaderStackDepth;
   }

   public Object indexedVariable(int index) {
      Object[] lookup = this.indexedVariables;
      return index < lookup.length ? lookup[index] : UNSET;
   }

   public boolean setIndexedVariable(int index, Object value) {
      Object[] lookup = this.indexedVariables;
      if (index < lookup.length) {
         Object oldValue = lookup[index];
         lookup[index] = value;
         return oldValue == UNSET;
      } else {
         this.expandIndexedVariableTableAndSet(index, value);
         return true;
      }
   }

   private void expandIndexedVariableTableAndSet(int index, Object value) {
      Object[] oldArray = this.indexedVariables;
      int oldCapacity = oldArray.length;
      int newCapacity = index | index >>> 1;
      newCapacity |= newCapacity >>> 2;
      newCapacity |= newCapacity >>> 4;
      newCapacity |= newCapacity >>> 8;
      newCapacity |= newCapacity >>> 16;
      ++newCapacity;
      Object[] newArray = Arrays.copyOf(oldArray, newCapacity);
      Arrays.fill(newArray, oldCapacity, newArray.length, UNSET);
      newArray[index] = value;
      this.indexedVariables = newArray;
   }

   public Object removeIndexedVariable(int index) {
      Object[] lookup = this.indexedVariables;
      if (index < lookup.length) {
         Object v = lookup[index];
         lookup[index] = UNSET;
         return v;
      } else {
         return UNSET;
      }
   }

   public boolean isIndexedVariableSet(int index) {
      Object[] lookup = this.indexedVariables;
      return index < lookup.length && lookup[index] != UNSET;
   }
}
