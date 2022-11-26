package org.python.netty.buffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.python.netty.util.internal.LongCounter;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;

abstract class PoolArena implements PoolArenaMetric {
   static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe();
   static final int numTinySubpagePools = 32;
   final PooledByteBufAllocator parent;
   private final int maxOrder;
   final int pageSize;
   final int pageShifts;
   final int chunkSize;
   final int subpageOverflowMask;
   final int numSmallSubpagePools;
   final int directMemoryCacheAlignment;
   final int directMemoryCacheAlignmentMask;
   private final PoolSubpage[] tinySubpagePools;
   private final PoolSubpage[] smallSubpagePools;
   private final PoolChunkList q050;
   private final PoolChunkList q025;
   private final PoolChunkList q000;
   private final PoolChunkList qInit;
   private final PoolChunkList q075;
   private final PoolChunkList q100;
   private final List chunkListMetrics;
   private long allocationsNormal;
   private final LongCounter allocationsTiny = PlatformDependent.newLongCounter();
   private final LongCounter allocationsSmall = PlatformDependent.newLongCounter();
   private final LongCounter allocationsHuge = PlatformDependent.newLongCounter();
   private final LongCounter activeBytesHuge = PlatformDependent.newLongCounter();
   private long deallocationsTiny;
   private long deallocationsSmall;
   private long deallocationsNormal;
   private final LongCounter deallocationsHuge = PlatformDependent.newLongCounter();
   final AtomicInteger numThreadCaches = new AtomicInteger();

   protected PoolArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int cacheAlignment) {
      this.parent = parent;
      this.pageSize = pageSize;
      this.maxOrder = maxOrder;
      this.pageShifts = pageShifts;
      this.chunkSize = chunkSize;
      this.directMemoryCacheAlignment = cacheAlignment;
      this.directMemoryCacheAlignmentMask = cacheAlignment - 1;
      this.subpageOverflowMask = ~(pageSize - 1);
      this.tinySubpagePools = this.newSubpagePoolArray(32);

      int i;
      for(i = 0; i < this.tinySubpagePools.length; ++i) {
         this.tinySubpagePools[i] = this.newSubpagePoolHead(pageSize);
      }

      this.numSmallSubpagePools = pageShifts - 9;
      this.smallSubpagePools = this.newSubpagePoolArray(this.numSmallSubpagePools);

      for(i = 0; i < this.smallSubpagePools.length; ++i) {
         this.smallSubpagePools[i] = this.newSubpagePoolHead(pageSize);
      }

      this.q100 = new PoolChunkList(this, (PoolChunkList)null, 100, Integer.MAX_VALUE, chunkSize);
      this.q075 = new PoolChunkList(this, this.q100, 75, 100, chunkSize);
      this.q050 = new PoolChunkList(this, this.q075, 50, 100, chunkSize);
      this.q025 = new PoolChunkList(this, this.q050, 25, 75, chunkSize);
      this.q000 = new PoolChunkList(this, this.q025, 1, 50, chunkSize);
      this.qInit = new PoolChunkList(this, this.q000, Integer.MIN_VALUE, 25, chunkSize);
      this.q100.prevList(this.q075);
      this.q075.prevList(this.q050);
      this.q050.prevList(this.q025);
      this.q025.prevList(this.q000);
      this.q000.prevList((PoolChunkList)null);
      this.qInit.prevList(this.qInit);
      List metrics = new ArrayList(6);
      metrics.add(this.qInit);
      metrics.add(this.q000);
      metrics.add(this.q025);
      metrics.add(this.q050);
      metrics.add(this.q075);
      metrics.add(this.q100);
      this.chunkListMetrics = Collections.unmodifiableList(metrics);
   }

   private PoolSubpage newSubpagePoolHead(int pageSize) {
      PoolSubpage head = new PoolSubpage(pageSize);
      head.prev = head;
      head.next = head;
      return head;
   }

   private PoolSubpage[] newSubpagePoolArray(int size) {
      return new PoolSubpage[size];
   }

   abstract boolean isDirect();

   PooledByteBuf allocate(PoolThreadCache cache, int reqCapacity, int maxCapacity) {
      PooledByteBuf buf = this.newByteBuf(maxCapacity);
      this.allocate(cache, buf, reqCapacity);
      return buf;
   }

   static int tinyIdx(int normCapacity) {
      return normCapacity >>> 4;
   }

   static int smallIdx(int normCapacity) {
      int tableIdx = 0;

      for(int i = normCapacity >>> 10; i != 0; ++tableIdx) {
         i >>>= 1;
      }

      return tableIdx;
   }

   boolean isTinyOrSmall(int normCapacity) {
      return (normCapacity & this.subpageOverflowMask) == 0;
   }

   static boolean isTiny(int normCapacity) {
      return (normCapacity & -512) == 0;
   }

   private void allocate(PoolThreadCache cache, PooledByteBuf buf, int reqCapacity) {
      int normCapacity = this.normalizeCapacity(reqCapacity);
      if (this.isTinyOrSmall(normCapacity)) {
         boolean tiny = isTiny(normCapacity);
         int tableIdx;
         PoolSubpage[] table;
         if (tiny) {
            if (cache.allocateTiny(this, buf, reqCapacity, normCapacity)) {
               return;
            }

            tableIdx = tinyIdx(normCapacity);
            table = this.tinySubpagePools;
         } else {
            if (cache.allocateSmall(this, buf, reqCapacity, normCapacity)) {
               return;
            }

            tableIdx = smallIdx(normCapacity);
            table = this.smallSubpagePools;
         }

         PoolSubpage head = table[tableIdx];
         synchronized(head) {
            PoolSubpage s = head.next;
            if (s != head) {
               assert s.doNotDestroy && s.elemSize == normCapacity;

               long handle = s.allocate();

               assert handle >= 0L;

               s.chunk.initBufWithSubpage(buf, handle, reqCapacity);
               this.incTinySmallAllocation(tiny);
               return;
            }
         }

         synchronized(this) {
            this.allocateNormal(buf, reqCapacity, normCapacity);
         }

         this.incTinySmallAllocation(tiny);
      } else {
         if (normCapacity <= this.chunkSize) {
            if (cache.allocateNormal(this, buf, reqCapacity, normCapacity)) {
               return;
            }

            synchronized(this) {
               this.allocateNormal(buf, reqCapacity, normCapacity);
               ++this.allocationsNormal;
            }
         } else {
            this.allocateHuge(buf, reqCapacity);
         }

      }
   }

   private void allocateNormal(PooledByteBuf buf, int reqCapacity, int normCapacity) {
      if (!this.q050.allocate(buf, reqCapacity, normCapacity) && !this.q025.allocate(buf, reqCapacity, normCapacity) && !this.q000.allocate(buf, reqCapacity, normCapacity) && !this.qInit.allocate(buf, reqCapacity, normCapacity) && !this.q075.allocate(buf, reqCapacity, normCapacity)) {
         PoolChunk c = this.newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
         long handle = c.allocate(normCapacity);

         assert handle > 0L;

         c.initBuf(buf, handle, reqCapacity);
         this.qInit.add(c);
      }
   }

   private void incTinySmallAllocation(boolean tiny) {
      if (tiny) {
         this.allocationsTiny.increment();
      } else {
         this.allocationsSmall.increment();
      }

   }

   private void allocateHuge(PooledByteBuf buf, int reqCapacity) {
      PoolChunk chunk = this.newUnpooledChunk(reqCapacity);
      this.activeBytesHuge.add((long)chunk.chunkSize());
      buf.initUnpooled(chunk, reqCapacity);
      this.allocationsHuge.increment();
   }

   void free(PoolChunk chunk, long handle, int normCapacity, PoolThreadCache cache) {
      if (chunk.unpooled) {
         int size = chunk.chunkSize();
         this.destroyChunk(chunk);
         this.activeBytesHuge.add((long)(-size));
         this.deallocationsHuge.increment();
      } else {
         SizeClass sizeClass = this.sizeClass(normCapacity);
         if (cache != null && cache.add(this, chunk, handle, normCapacity, sizeClass)) {
            return;
         }

         this.freeChunk(chunk, handle, sizeClass);
      }

   }

   private SizeClass sizeClass(int normCapacity) {
      if (!this.isTinyOrSmall(normCapacity)) {
         return PoolArena.SizeClass.Normal;
      } else {
         return isTiny(normCapacity) ? PoolArena.SizeClass.Tiny : PoolArena.SizeClass.Small;
      }
   }

   void freeChunk(PoolChunk chunk, long handle, SizeClass sizeClass) {
      boolean destroyChunk;
      synchronized(this) {
         switch (sizeClass) {
            case Normal:
               ++this.deallocationsNormal;
               break;
            case Small:
               ++this.deallocationsSmall;
               break;
            case Tiny:
               ++this.deallocationsTiny;
               break;
            default:
               throw new Error();
         }

         destroyChunk = !chunk.parent.free(chunk, handle);
      }

      if (destroyChunk) {
         this.destroyChunk(chunk);
      }

   }

   PoolSubpage findSubpagePoolHead(int elemSize) {
      int tableIdx;
      PoolSubpage[] table;
      if (isTiny(elemSize)) {
         tableIdx = elemSize >>> 4;
         table = this.tinySubpagePools;
      } else {
         tableIdx = 0;

         for(elemSize >>>= 10; elemSize != 0; ++tableIdx) {
            elemSize >>>= 1;
         }

         table = this.smallSubpagePools;
      }

      return table[tableIdx];
   }

   int normalizeCapacity(int reqCapacity) {
      if (reqCapacity < 0) {
         throw new IllegalArgumentException("capacity: " + reqCapacity + " (expected: 0+)");
      } else if (reqCapacity >= this.chunkSize) {
         return this.directMemoryCacheAlignment == 0 ? reqCapacity : this.alignCapacity(reqCapacity);
      } else if (!isTiny(reqCapacity)) {
         int normalizedCapacity = reqCapacity - 1;
         normalizedCapacity |= normalizedCapacity >>> 1;
         normalizedCapacity |= normalizedCapacity >>> 2;
         normalizedCapacity |= normalizedCapacity >>> 4;
         normalizedCapacity |= normalizedCapacity >>> 8;
         normalizedCapacity |= normalizedCapacity >>> 16;
         ++normalizedCapacity;
         if (normalizedCapacity < 0) {
            normalizedCapacity >>>= 1;
         }

         assert this.directMemoryCacheAlignment == 0 || (normalizedCapacity & this.directMemoryCacheAlignmentMask) == 0;

         return normalizedCapacity;
      } else if (this.directMemoryCacheAlignment > 0) {
         return this.alignCapacity(reqCapacity);
      } else {
         return (reqCapacity & 15) == 0 ? reqCapacity : (reqCapacity & -16) + 16;
      }
   }

   int alignCapacity(int reqCapacity) {
      int delta = reqCapacity & this.directMemoryCacheAlignmentMask;
      return delta == 0 ? reqCapacity : reqCapacity + this.directMemoryCacheAlignment - delta;
   }

   void reallocate(PooledByteBuf buf, int newCapacity, boolean freeOldMemory) {
      if (newCapacity >= 0 && newCapacity <= buf.maxCapacity()) {
         int oldCapacity = buf.length;
         if (oldCapacity != newCapacity) {
            PoolChunk oldChunk = buf.chunk;
            long oldHandle = buf.handle;
            Object oldMemory = buf.memory;
            int oldOffset = buf.offset;
            int oldMaxLength = buf.maxLength;
            int readerIndex = buf.readerIndex();
            int writerIndex = buf.writerIndex();
            this.allocate(this.parent.threadCache(), buf, newCapacity);
            if (newCapacity > oldCapacity) {
               this.memoryCopy(oldMemory, oldOffset, buf.memory, buf.offset, oldCapacity);
            } else if (newCapacity < oldCapacity) {
               if (readerIndex < newCapacity) {
                  if (writerIndex > newCapacity) {
                     writerIndex = newCapacity;
                  }

                  this.memoryCopy(oldMemory, oldOffset + readerIndex, buf.memory, buf.offset + readerIndex, writerIndex - readerIndex);
               } else {
                  writerIndex = newCapacity;
                  readerIndex = newCapacity;
               }
            }

            buf.setIndex(readerIndex, writerIndex);
            if (freeOldMemory) {
               this.free(oldChunk, oldHandle, oldMaxLength, buf.cache);
            }

         }
      } else {
         throw new IllegalArgumentException("newCapacity: " + newCapacity);
      }
   }

   public int numThreadCaches() {
      return this.numThreadCaches.get();
   }

   public int numTinySubpages() {
      return this.tinySubpagePools.length;
   }

   public int numSmallSubpages() {
      return this.smallSubpagePools.length;
   }

   public int numChunkLists() {
      return this.chunkListMetrics.size();
   }

   public List tinySubpages() {
      return subPageMetricList(this.tinySubpagePools);
   }

   public List smallSubpages() {
      return subPageMetricList(this.smallSubpagePools);
   }

   public List chunkLists() {
      return this.chunkListMetrics;
   }

   private static List subPageMetricList(PoolSubpage[] pages) {
      List metrics = new ArrayList();
      PoolSubpage[] var2 = pages;
      int var3 = pages.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PoolSubpage head = var2[var4];
         if (head.next != head) {
            PoolSubpage s = head.next;

            do {
               metrics.add(s);
               s = s.next;
            } while(s != head);
         }
      }

      return metrics;
   }

   public long numAllocations() {
      long allocsNormal;
      synchronized(this) {
         allocsNormal = this.allocationsNormal;
      }

      return this.allocationsTiny.value() + this.allocationsSmall.value() + allocsNormal + this.allocationsHuge.value();
   }

   public long numTinyAllocations() {
      return this.allocationsTiny.value();
   }

   public long numSmallAllocations() {
      return this.allocationsSmall.value();
   }

   public synchronized long numNormalAllocations() {
      return this.allocationsNormal;
   }

   public long numDeallocations() {
      long deallocs;
      synchronized(this) {
         deallocs = this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal;
      }

      return deallocs + this.deallocationsHuge.value();
   }

   public synchronized long numTinyDeallocations() {
      return this.deallocationsTiny;
   }

   public synchronized long numSmallDeallocations() {
      return this.deallocationsSmall;
   }

   public synchronized long numNormalDeallocations() {
      return this.deallocationsNormal;
   }

   public long numHugeAllocations() {
      return this.allocationsHuge.value();
   }

   public long numHugeDeallocations() {
      return this.deallocationsHuge.value();
   }

   public long numActiveAllocations() {
      long val = this.allocationsTiny.value() + this.allocationsSmall.value() + this.allocationsHuge.value() - this.deallocationsHuge.value();
      synchronized(this) {
         val += this.allocationsNormal - (this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal);
      }

      return Math.max(val, 0L);
   }

   public long numActiveTinyAllocations() {
      return Math.max(this.numTinyAllocations() - this.numTinyDeallocations(), 0L);
   }

   public long numActiveSmallAllocations() {
      return Math.max(this.numSmallAllocations() - this.numSmallDeallocations(), 0L);
   }

   public long numActiveNormalAllocations() {
      long val;
      synchronized(this) {
         val = this.allocationsNormal - this.deallocationsNormal;
      }

      return Math.max(val, 0L);
   }

   public long numActiveHugeAllocations() {
      return Math.max(this.numHugeAllocations() - this.numHugeDeallocations(), 0L);
   }

   public long numActiveBytes() {
      long val = this.activeBytesHuge.value();
      synchronized(this) {
         PoolChunkMetric m;
         for(int i = 0; i < this.chunkListMetrics.size(); ++i) {
            for(Iterator var5 = ((PoolChunkListMetric)this.chunkListMetrics.get(i)).iterator(); var5.hasNext(); val += (long)m.chunkSize()) {
               m = (PoolChunkMetric)var5.next();
            }
         }

         return Math.max(0L, val);
      }
   }

   protected abstract PoolChunk newChunk(int var1, int var2, int var3, int var4);

   protected abstract PoolChunk newUnpooledChunk(int var1);

   protected abstract PooledByteBuf newByteBuf(int var1);

   protected abstract void memoryCopy(Object var1, int var2, Object var3, int var4, int var5);

   protected abstract void destroyChunk(PoolChunk var1);

   public synchronized String toString() {
      StringBuilder buf = (new StringBuilder()).append("Chunk(s) at 0~25%:").append(StringUtil.NEWLINE).append(this.qInit).append(StringUtil.NEWLINE).append("Chunk(s) at 0~50%:").append(StringUtil.NEWLINE).append(this.q000).append(StringUtil.NEWLINE).append("Chunk(s) at 25~75%:").append(StringUtil.NEWLINE).append(this.q025).append(StringUtil.NEWLINE).append("Chunk(s) at 50~100%:").append(StringUtil.NEWLINE).append(this.q050).append(StringUtil.NEWLINE).append("Chunk(s) at 75~100%:").append(StringUtil.NEWLINE).append(this.q075).append(StringUtil.NEWLINE).append("Chunk(s) at 100%:").append(StringUtil.NEWLINE).append(this.q100).append(StringUtil.NEWLINE).append("tiny subpages:");
      appendPoolSubPages(buf, this.tinySubpagePools);
      buf.append(StringUtil.NEWLINE).append("small subpages:");
      appendPoolSubPages(buf, this.smallSubpagePools);
      buf.append(StringUtil.NEWLINE);
      return buf.toString();
   }

   private static void appendPoolSubPages(StringBuilder buf, PoolSubpage[] subpages) {
      for(int i = 0; i < subpages.length; ++i) {
         PoolSubpage head = subpages[i];
         if (head.next != head) {
            buf.append(StringUtil.NEWLINE).append(i).append(": ");
            PoolSubpage s = head.next;

            do {
               buf.append(s);
               s = s.next;
            } while(s != head);
         }
      }

   }

   protected final void finalize() throws Throwable {
      try {
         super.finalize();
      } finally {
         destroyPoolSubPages(this.smallSubpagePools);
         destroyPoolSubPages(this.tinySubpagePools);
         this.destroyPoolChunkLists(this.qInit, this.q000, this.q025, this.q050, this.q075, this.q100);
      }

   }

   private static void destroyPoolSubPages(PoolSubpage[] pages) {
      PoolSubpage[] var1 = pages;
      int var2 = pages.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PoolSubpage page = var1[var3];
         page.destroy();
      }

   }

   private void destroyPoolChunkLists(PoolChunkList... chunkLists) {
      PoolChunkList[] var2 = chunkLists;
      int var3 = chunkLists.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PoolChunkList chunkList = var2[var4];
         chunkList.destroy(this);
      }

   }

   static final class DirectArena extends PoolArena {
      DirectArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int directMemoryCacheAlignment) {
         super(parent, pageSize, maxOrder, pageShifts, chunkSize, directMemoryCacheAlignment);
      }

      boolean isDirect() {
         return true;
      }

      private int offsetCacheLine(ByteBuffer memory) {
         return HAS_UNSAFE ? (int)(PlatformDependent.directBufferAddress(memory) & (long)this.directMemoryCacheAlignmentMask) : 0;
      }

      protected PoolChunk newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
         if (this.directMemoryCacheAlignment == 0) {
            return new PoolChunk(this, allocateDirect(chunkSize), pageSize, maxOrder, pageShifts, chunkSize, 0);
         } else {
            ByteBuffer memory = allocateDirect(chunkSize + this.directMemoryCacheAlignment);
            return new PoolChunk(this, memory, pageSize, maxOrder, pageShifts, chunkSize, this.offsetCacheLine(memory));
         }
      }

      protected PoolChunk newUnpooledChunk(int capacity) {
         if (this.directMemoryCacheAlignment == 0) {
            return new PoolChunk(this, allocateDirect(capacity), capacity, 0);
         } else {
            ByteBuffer memory = allocateDirect(capacity + this.directMemoryCacheAlignment);
            return new PoolChunk(this, memory, capacity, this.offsetCacheLine(memory));
         }
      }

      private static ByteBuffer allocateDirect(int capacity) {
         return PlatformDependent.useDirectBufferNoCleaner() ? PlatformDependent.allocateDirectNoCleaner(capacity) : ByteBuffer.allocateDirect(capacity);
      }

      protected void destroyChunk(PoolChunk chunk) {
         if (PlatformDependent.useDirectBufferNoCleaner()) {
            PlatformDependent.freeDirectNoCleaner((ByteBuffer)chunk.memory);
         } else {
            PlatformDependent.freeDirectBuffer((ByteBuffer)chunk.memory);
         }

      }

      protected PooledByteBuf newByteBuf(int maxCapacity) {
         return (PooledByteBuf)(HAS_UNSAFE ? PooledUnsafeDirectByteBuf.newInstance(maxCapacity) : PooledDirectByteBuf.newInstance(maxCapacity));
      }

      protected void memoryCopy(ByteBuffer src, int srcOffset, ByteBuffer dst, int dstOffset, int length) {
         if (length != 0) {
            if (HAS_UNSAFE) {
               PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(src) + (long)srcOffset, PlatformDependent.directBufferAddress(dst) + (long)dstOffset, (long)length);
            } else {
               src = src.duplicate();
               dst = dst.duplicate();
               src.position(srcOffset).limit(srcOffset + length);
               dst.position(dstOffset);
               dst.put(src);
            }

         }
      }
   }

   static final class HeapArena extends PoolArena {
      HeapArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int directMemoryCacheAlignment) {
         super(parent, pageSize, maxOrder, pageShifts, chunkSize, directMemoryCacheAlignment);
      }

      private static byte[] newByteArray(int size) {
         return PlatformDependent.allocateUninitializedArray(size);
      }

      boolean isDirect() {
         return false;
      }

      protected PoolChunk newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
         return new PoolChunk(this, newByteArray(chunkSize), pageSize, maxOrder, pageShifts, chunkSize, 0);
      }

      protected PoolChunk newUnpooledChunk(int capacity) {
         return new PoolChunk(this, newByteArray(capacity), capacity, 0);
      }

      protected void destroyChunk(PoolChunk chunk) {
      }

      protected PooledByteBuf newByteBuf(int maxCapacity) {
         return (PooledByteBuf)(HAS_UNSAFE ? PooledUnsafeHeapByteBuf.newUnsafeInstance(maxCapacity) : PooledHeapByteBuf.newInstance(maxCapacity));
      }

      protected void memoryCopy(byte[] src, int srcOffset, byte[] dst, int dstOffset, int length) {
         if (length != 0) {
            System.arraycopy(src, srcOffset, dst, dstOffset, length);
         }
      }
   }

   static enum SizeClass {
      Tiny,
      Small,
      Normal;
   }
}
