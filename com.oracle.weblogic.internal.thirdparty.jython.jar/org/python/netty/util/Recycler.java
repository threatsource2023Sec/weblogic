package org.python.netty.util;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.python.netty.util.concurrent.FastThreadLocal;
import org.python.netty.util.internal.MathUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public abstract class Recycler {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
   private static final Handle NOOP_HANDLE = new Handle() {
      public void recycle(Object object) {
      }
   };
   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
   private static final int OWN_THREAD_ID;
   private static final int DEFAULT_INITIAL_MAX_CAPACITY_PER_THREAD = 32768;
   private static final int DEFAULT_MAX_CAPACITY_PER_THREAD;
   private static final int INITIAL_CAPACITY;
   private static final int MAX_SHARED_CAPACITY_FACTOR;
   private static final int MAX_DELAYED_QUEUES_PER_THREAD;
   private static final int LINK_CAPACITY;
   private static final int RATIO;
   private final int maxCapacityPerThread;
   private final int maxSharedCapacityFactor;
   private final int ratioMask;
   private final int maxDelayedQueuesPerThread;
   private final FastThreadLocal threadLocal;
   private static final FastThreadLocal DELAYED_RECYCLED;

   protected Recycler() {
      this(DEFAULT_MAX_CAPACITY_PER_THREAD);
   }

   protected Recycler(int maxCapacityPerThread) {
      this(maxCapacityPerThread, MAX_SHARED_CAPACITY_FACTOR);
   }

   protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor) {
      this(maxCapacityPerThread, maxSharedCapacityFactor, RATIO, MAX_DELAYED_QUEUES_PER_THREAD);
   }

   protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor, int ratio, int maxDelayedQueuesPerThread) {
      this.threadLocal = new FastThreadLocal() {
         protected Stack initialValue() {
            return new Stack(Recycler.this, Thread.currentThread(), Recycler.this.maxCapacityPerThread, Recycler.this.maxSharedCapacityFactor, Recycler.this.ratioMask, Recycler.this.maxDelayedQueuesPerThread);
         }
      };
      this.ratioMask = MathUtil.safeFindNextPositivePowerOfTwo(ratio) - 1;
      if (maxCapacityPerThread <= 0) {
         this.maxCapacityPerThread = 0;
         this.maxSharedCapacityFactor = 1;
         this.maxDelayedQueuesPerThread = 0;
      } else {
         this.maxCapacityPerThread = maxCapacityPerThread;
         this.maxSharedCapacityFactor = Math.max(1, maxSharedCapacityFactor);
         this.maxDelayedQueuesPerThread = Math.max(0, maxDelayedQueuesPerThread);
      }

   }

   public final Object get() {
      if (this.maxCapacityPerThread == 0) {
         return this.newObject(NOOP_HANDLE);
      } else {
         Stack stack = (Stack)this.threadLocal.get();
         DefaultHandle handle = stack.pop();
         if (handle == null) {
            handle = stack.newHandle();
            handle.value = this.newObject(handle);
         }

         return handle.value;
      }
   }

   /** @deprecated */
   @Deprecated
   public final boolean recycle(Object o, Handle handle) {
      if (handle == NOOP_HANDLE) {
         return false;
      } else {
         DefaultHandle h = (DefaultHandle)handle;
         if (h.stack.parent != this) {
            return false;
         } else {
            h.recycle(o);
            return true;
         }
      }
   }

   final int threadLocalCapacity() {
      return ((Stack)this.threadLocal.get()).elements.length;
   }

   final int threadLocalSize() {
      return ((Stack)this.threadLocal.get()).size;
   }

   protected abstract Object newObject(Handle var1);

   static {
      OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();
      int maxCapacityPerThread = SystemPropertyUtil.getInt("org.python.netty.recycler.maxCapacityPerThread", SystemPropertyUtil.getInt("org.python.netty.recycler.maxCapacity", 32768));
      if (maxCapacityPerThread < 0) {
         maxCapacityPerThread = 32768;
      }

      DEFAULT_MAX_CAPACITY_PER_THREAD = maxCapacityPerThread;
      MAX_SHARED_CAPACITY_FACTOR = Math.max(2, SystemPropertyUtil.getInt("org.python.netty.recycler.maxSharedCapacityFactor", 2));
      MAX_DELAYED_QUEUES_PER_THREAD = Math.max(0, SystemPropertyUtil.getInt("org.python.netty.recycler.maxDelayedQueuesPerThread", NettyRuntime.availableProcessors() * 2));
      LINK_CAPACITY = MathUtil.safeFindNextPositivePowerOfTwo(Math.max(SystemPropertyUtil.getInt("org.python.netty.recycler.linkCapacity", 16), 16));
      RATIO = MathUtil.safeFindNextPositivePowerOfTwo(SystemPropertyUtil.getInt("org.python.netty.recycler.ratio", 8));
      if (logger.isDebugEnabled()) {
         if (DEFAULT_MAX_CAPACITY_PER_THREAD == 0) {
            logger.debug("-Dio.netty.recycler.maxCapacityPerThread: disabled");
            logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: disabled");
            logger.debug("-Dio.netty.recycler.linkCapacity: disabled");
            logger.debug("-Dio.netty.recycler.ratio: disabled");
         } else {
            logger.debug("-Dio.netty.recycler.maxCapacityPerThread: {}", (Object)DEFAULT_MAX_CAPACITY_PER_THREAD);
            logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: {}", (Object)MAX_SHARED_CAPACITY_FACTOR);
            logger.debug("-Dio.netty.recycler.linkCapacity: {}", (Object)LINK_CAPACITY);
            logger.debug("-Dio.netty.recycler.ratio: {}", (Object)RATIO);
         }
      }

      INITIAL_CAPACITY = Math.min(DEFAULT_MAX_CAPACITY_PER_THREAD, 256);
      DELAYED_RECYCLED = new FastThreadLocal() {
         protected Map initialValue() {
            return new WeakHashMap();
         }
      };
   }

   static final class Stack {
      final Recycler parent;
      final Thread thread;
      final AtomicInteger availableSharedCapacity;
      final int maxDelayedQueues;
      private final int maxCapacity;
      private final int ratioMask;
      private DefaultHandle[] elements;
      private int size;
      private int handleRecycleCount = -1;
      private WeakOrderQueue cursor;
      private WeakOrderQueue prev;
      private volatile WeakOrderQueue head;

      Stack(Recycler parent, Thread thread, int maxCapacity, int maxSharedCapacityFactor, int ratioMask, int maxDelayedQueues) {
         this.parent = parent;
         this.thread = thread;
         this.maxCapacity = maxCapacity;
         this.availableSharedCapacity = new AtomicInteger(Math.max(maxCapacity / maxSharedCapacityFactor, Recycler.LINK_CAPACITY));
         this.elements = new DefaultHandle[Math.min(Recycler.INITIAL_CAPACITY, maxCapacity)];
         this.ratioMask = ratioMask;
         this.maxDelayedQueues = maxDelayedQueues;
      }

      synchronized void setHead(WeakOrderQueue queue) {
         queue.setNext(this.head);
         this.head = queue;
      }

      int increaseCapacity(int expectedCapacity) {
         int newCapacity = this.elements.length;
         int maxCapacity = this.maxCapacity;

         do {
            newCapacity <<= 1;
         } while(newCapacity < expectedCapacity && newCapacity < maxCapacity);

         newCapacity = Math.min(newCapacity, maxCapacity);
         if (newCapacity != this.elements.length) {
            this.elements = (DefaultHandle[])Arrays.copyOf(this.elements, newCapacity);
         }

         return newCapacity;
      }

      DefaultHandle pop() {
         int size = this.size;
         if (size == 0) {
            if (!this.scavenge()) {
               return null;
            }

            size = this.size;
         }

         --size;
         DefaultHandle ret = this.elements[size];
         this.elements[size] = null;
         if (ret.lastRecycledId != ret.recycleId) {
            throw new IllegalStateException("recycled multiple times");
         } else {
            ret.recycleId = 0;
            ret.lastRecycledId = 0;
            this.size = size;
            return ret;
         }
      }

      boolean scavenge() {
         if (this.scavengeSome()) {
            return true;
         } else {
            this.prev = null;
            this.cursor = this.head;
            return false;
         }
      }

      boolean scavengeSome() {
         WeakOrderQueue cursor = this.cursor;
         WeakOrderQueue prev;
         if (cursor == null) {
            prev = null;
            cursor = this.head;
            if (cursor == null) {
               return false;
            }
         } else {
            prev = this.prev;
         }

         boolean success = false;

         WeakOrderQueue next;
         do {
            if (cursor.transfer(this)) {
               success = true;
               break;
            }

            next = cursor.next;
            if (cursor.owner.get() == null) {
               if (cursor.hasFinalData()) {
                  while(cursor.transfer(this)) {
                     success = true;
                  }
               }

               if (prev != null) {
                  prev.setNext(next);
               }
            } else {
               prev = cursor;
            }

            cursor = next;
         } while(next != null && !success);

         this.prev = prev;
         this.cursor = cursor;
         return success;
      }

      void push(DefaultHandle item) {
         Thread currentThread = Thread.currentThread();
         if (this.thread == currentThread) {
            this.pushNow(item);
         } else {
            this.pushLater(item, currentThread);
         }

      }

      private void pushNow(DefaultHandle item) {
         if ((item.recycleId | item.lastRecycledId) != 0) {
            throw new IllegalStateException("recycled already");
         } else {
            item.recycleId = item.lastRecycledId = Recycler.OWN_THREAD_ID;
            int size = this.size;
            if (size < this.maxCapacity && !this.dropHandle(item)) {
               if (size == this.elements.length) {
                  this.elements = (DefaultHandle[])Arrays.copyOf(this.elements, Math.min(size << 1, this.maxCapacity));
               }

               this.elements[size] = item;
               this.size = size + 1;
            }
         }
      }

      private void pushLater(DefaultHandle item, Thread thread) {
         Map delayedRecycled = (Map)Recycler.DELAYED_RECYCLED.get();
         WeakOrderQueue queue = (WeakOrderQueue)delayedRecycled.get(this);
         if (queue == null) {
            if (delayedRecycled.size() >= this.maxDelayedQueues) {
               delayedRecycled.put(this, Recycler.WeakOrderQueue.DUMMY);
               return;
            }

            if ((queue = Recycler.WeakOrderQueue.allocate(this, thread)) == null) {
               return;
            }

            delayedRecycled.put(this, queue);
         } else if (queue == Recycler.WeakOrderQueue.DUMMY) {
            return;
         }

         queue.add(item);
      }

      boolean dropHandle(DefaultHandle handle) {
         if (!handle.hasBeenRecycled) {
            if ((++this.handleRecycleCount & this.ratioMask) != 0) {
               return true;
            }

            handle.hasBeenRecycled = true;
         }

         return false;
      }

      DefaultHandle newHandle() {
         return new DefaultHandle(this);
      }
   }

   private static final class WeakOrderQueue {
      static final WeakOrderQueue DUMMY = new WeakOrderQueue();
      private Link head;
      private Link tail;
      private WeakOrderQueue next;
      private final WeakReference owner;
      private final int id;
      private final AtomicInteger availableSharedCapacity;

      private WeakOrderQueue() {
         this.id = Recycler.ID_GENERATOR.getAndIncrement();
         this.owner = null;
         this.availableSharedCapacity = null;
      }

      private WeakOrderQueue(Stack stack, Thread thread) {
         this.id = Recycler.ID_GENERATOR.getAndIncrement();
         this.head = this.tail = new Link();
         this.owner = new WeakReference(thread);
         this.availableSharedCapacity = stack.availableSharedCapacity;
      }

      static WeakOrderQueue newQueue(Stack stack, Thread thread) {
         WeakOrderQueue queue = new WeakOrderQueue(stack, thread);
         stack.setHead(queue);
         return queue;
      }

      private void setNext(WeakOrderQueue next) {
         assert next != this;

         this.next = next;
      }

      static WeakOrderQueue allocate(Stack stack, Thread thread) {
         return reserveSpace(stack.availableSharedCapacity, Recycler.LINK_CAPACITY) ? newQueue(stack, thread) : null;
      }

      private static boolean reserveSpace(AtomicInteger availableSharedCapacity, int space) {
         assert space >= 0;

         int available;
         do {
            available = availableSharedCapacity.get();
            if (available < space) {
               return false;
            }
         } while(!availableSharedCapacity.compareAndSet(available, available - space));

         return true;
      }

      private void reclaimSpace(int space) {
         assert space >= 0;

         this.availableSharedCapacity.addAndGet(space);
      }

      void add(DefaultHandle handle) {
         handle.lastRecycledId = this.id;
         Link tail = this.tail;
         int writeIndex;
         if ((writeIndex = tail.get()) == Recycler.LINK_CAPACITY) {
            if (!reserveSpace(this.availableSharedCapacity, Recycler.LINK_CAPACITY)) {
               return;
            }

            this.tail = tail = tail.next = new Link();
            writeIndex = tail.get();
         }

         tail.elements[writeIndex] = handle;
         handle.stack = null;
         tail.lazySet(writeIndex + 1);
      }

      boolean hasFinalData() {
         return this.tail.readIndex != this.tail.get();
      }

      boolean transfer(Stack dst) {
         Link head = this.head;
         if (head == null) {
            return false;
         } else {
            if (head.readIndex == Recycler.LINK_CAPACITY) {
               if (head.next == null) {
                  return false;
               }

               this.head = head = head.next;
            }

            int srcStart = head.readIndex;
            int srcEnd = head.get();
            int srcSize = srcEnd - srcStart;
            if (srcSize == 0) {
               return false;
            } else {
               int dstSize = dst.size;
               int expectedCapacity = dstSize + srcSize;
               if (expectedCapacity > dst.elements.length) {
                  int actualCapacity = dst.increaseCapacity(expectedCapacity);
                  srcEnd = Math.min(srcStart + actualCapacity - dstSize, srcEnd);
               }

               if (srcStart != srcEnd) {
                  DefaultHandle[] srcElems = head.elements;
                  DefaultHandle[] dstElems = dst.elements;
                  int newDstSize = dstSize;

                  for(int i = srcStart; i < srcEnd; ++i) {
                     DefaultHandle element = srcElems[i];
                     if (element.recycleId == 0) {
                        element.recycleId = element.lastRecycledId;
                     } else if (element.recycleId != element.lastRecycledId) {
                        throw new IllegalStateException("recycled already");
                     }

                     srcElems[i] = null;
                     if (!dst.dropHandle(element)) {
                        element.stack = dst;
                        dstElems[newDstSize++] = element;
                     }
                  }

                  if (srcEnd == Recycler.LINK_CAPACITY && head.next != null) {
                     this.reclaimSpace(Recycler.LINK_CAPACITY);
                     this.head = head.next;
                  }

                  head.readIndex = srcEnd;
                  if (dst.size == newDstSize) {
                     return false;
                  } else {
                     dst.size = newDstSize;
                     return true;
                  }
               } else {
                  return false;
               }
            }
         }
      }

      protected void finalize() throws Throwable {
         boolean var5 = false;

         try {
            var5 = true;
            super.finalize();
            var5 = false;
         } finally {
            if (var5) {
               for(Link link = this.head; link != null; link = link.next) {
                  this.reclaimSpace(Recycler.LINK_CAPACITY);
               }

            }
         }

         for(Link link = this.head; link != null; link = link.next) {
            this.reclaimSpace(Recycler.LINK_CAPACITY);
         }

      }

      private static final class Link extends AtomicInteger {
         private final DefaultHandle[] elements;
         private int readIndex;
         private Link next;

         private Link() {
            this.elements = new DefaultHandle[Recycler.LINK_CAPACITY];
         }

         // $FF: synthetic method
         Link(Object x0) {
            this();
         }
      }
   }

   static final class DefaultHandle implements Handle {
      private int lastRecycledId;
      private int recycleId;
      boolean hasBeenRecycled;
      private Stack stack;
      private Object value;

      DefaultHandle(Stack stack) {
         this.stack = stack;
      }

      public void recycle(Object object) {
         if (object != this.value) {
            throw new IllegalArgumentException("object does not belong to handle");
         } else {
            this.stack.push(this);
         }
      }
   }

   public interface Handle {
      void recycle(Object var1);
   }
}
