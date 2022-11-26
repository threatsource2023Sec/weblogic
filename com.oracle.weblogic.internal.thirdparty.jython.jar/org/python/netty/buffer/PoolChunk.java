package org.python.netty.buffer;

final class PoolChunk implements PoolChunkMetric {
   private static final int INTEGER_SIZE_MINUS_ONE = 31;
   final PoolArena arena;
   final Object memory;
   final boolean unpooled;
   final int offset;
   private final byte[] memoryMap;
   private final byte[] depthMap;
   private final PoolSubpage[] subpages;
   private final int subpageOverflowMask;
   private final int pageSize;
   private final int pageShifts;
   private final int maxOrder;
   private final int chunkSize;
   private final int log2ChunkSize;
   private final int maxSubpageAllocs;
   private final byte unusable;
   private int freeBytes;
   PoolChunkList parent;
   PoolChunk prev;
   PoolChunk next;

   PoolChunk(PoolArena arena, Object memory, int pageSize, int maxOrder, int pageShifts, int chunkSize, int offset) {
      this.unpooled = false;
      this.arena = arena;
      this.memory = memory;
      this.pageSize = pageSize;
      this.pageShifts = pageShifts;
      this.maxOrder = maxOrder;
      this.chunkSize = chunkSize;
      this.offset = offset;
      this.unusable = (byte)(maxOrder + 1);
      this.log2ChunkSize = log2(chunkSize);
      this.subpageOverflowMask = ~(pageSize - 1);
      this.freeBytes = chunkSize;

      assert maxOrder < 30 : "maxOrder should be < 30, but is: " + maxOrder;

      this.maxSubpageAllocs = 1 << maxOrder;
      this.memoryMap = new byte[this.maxSubpageAllocs << 1];
      this.depthMap = new byte[this.memoryMap.length];
      int memoryMapIndex = 1;

      for(int d = 0; d <= maxOrder; ++d) {
         int depth = 1 << d;

         for(int p = 0; p < depth; ++p) {
            this.memoryMap[memoryMapIndex] = (byte)d;
            this.depthMap[memoryMapIndex] = (byte)d;
            ++memoryMapIndex;
         }
      }

      this.subpages = this.newSubpageArray(this.maxSubpageAllocs);
   }

   PoolChunk(PoolArena arena, Object memory, int size, int offset) {
      this.unpooled = true;
      this.arena = arena;
      this.memory = memory;
      this.offset = offset;
      this.memoryMap = null;
      this.depthMap = null;
      this.subpages = null;
      this.subpageOverflowMask = 0;
      this.pageSize = 0;
      this.pageShifts = 0;
      this.maxOrder = 0;
      this.unusable = (byte)(this.maxOrder + 1);
      this.chunkSize = size;
      this.log2ChunkSize = log2(this.chunkSize);
      this.maxSubpageAllocs = 0;
   }

   private PoolSubpage[] newSubpageArray(int size) {
      return new PoolSubpage[size];
   }

   public int usage() {
      int freeBytes;
      synchronized(this.arena) {
         freeBytes = this.freeBytes;
      }

      return this.usage(freeBytes);
   }

   private int usage(int freeBytes) {
      if (freeBytes == 0) {
         return 100;
      } else {
         int freePercentage = (int)((long)freeBytes * 100L / (long)this.chunkSize);
         return freePercentage == 0 ? 99 : 100 - freePercentage;
      }
   }

   long allocate(int normCapacity) {
      return (normCapacity & this.subpageOverflowMask) != 0 ? this.allocateRun(normCapacity) : this.allocateSubpage(normCapacity);
   }

   private void updateParentsAlloc(int id) {
      while(id > 1) {
         int parentId = id >>> 1;
         byte val1 = this.value(id);
         byte val2 = this.value(id ^ 1);
         byte val = val1 < val2 ? val1 : val2;
         this.setValue(parentId, val);
         id = parentId;
      }

   }

   private void updateParentsFree(int id) {
      int parentId;
      for(int logChild = this.depth(id) + 1; id > 1; id = parentId) {
         parentId = id >>> 1;
         byte val1 = this.value(id);
         byte val2 = this.value(id ^ 1);
         --logChild;
         if (val1 == logChild && val2 == logChild) {
            this.setValue(parentId, (byte)(logChild - 1));
         } else {
            byte val = val1 < val2 ? val1 : val2;
            this.setValue(parentId, val);
         }
      }

   }

   private int allocateNode(int d) {
      int id = 1;
      int initial = -(1 << d);
      byte val = this.value(id);
      if (val > d) {
         return -1;
      } else {
         while(val < d || (id & initial) == 0) {
            id <<= 1;
            val = this.value(id);
            if (val > d) {
               id ^= 1;
               val = this.value(id);
            }
         }

         byte value = this.value(id);

         assert value == d && (id & initial) == 1 << d : String.format("val = %d, id & initial = %d, d = %d", value, id & initial, d);

         this.setValue(id, this.unusable);
         this.updateParentsAlloc(id);
         return id;
      }
   }

   private long allocateRun(int normCapacity) {
      int d = this.maxOrder - (log2(normCapacity) - this.pageShifts);
      int id = this.allocateNode(d);
      if (id < 0) {
         return (long)id;
      } else {
         this.freeBytes -= this.runLength(id);
         return (long)id;
      }
   }

   private long allocateSubpage(int normCapacity) {
      PoolSubpage head = this.arena.findSubpagePoolHead(normCapacity);
      synchronized(head) {
         int d = this.maxOrder;
         int id = this.allocateNode(d);
         if (id < 0) {
            return (long)id;
         } else {
            PoolSubpage[] subpages = this.subpages;
            int pageSize = this.pageSize;
            this.freeBytes -= pageSize;
            int subpageIdx = this.subpageIdx(id);
            PoolSubpage subpage = subpages[subpageIdx];
            if (subpage == null) {
               subpage = new PoolSubpage(head, this, id, this.runOffset(id), pageSize, normCapacity);
               subpages[subpageIdx] = subpage;
            } else {
               subpage.init(head, normCapacity);
            }

            return subpage.allocate();
         }
      }
   }

   void free(long handle) {
      int memoryMapIdx = memoryMapIdx(handle);
      int bitmapIdx = bitmapIdx(handle);
      if (bitmapIdx != 0) {
         PoolSubpage subpage = this.subpages[this.subpageIdx(memoryMapIdx)];

         assert subpage != null && subpage.doNotDestroy;

         PoolSubpage head = this.arena.findSubpagePoolHead(subpage.elemSize);
         synchronized(head) {
            if (subpage.free(head, bitmapIdx & 1073741823)) {
               return;
            }
         }
      }

      this.freeBytes += this.runLength(memoryMapIdx);
      this.setValue(memoryMapIdx, this.depth(memoryMapIdx));
      this.updateParentsFree(memoryMapIdx);
   }

   void initBuf(PooledByteBuf buf, long handle, int reqCapacity) {
      int memoryMapIdx = memoryMapIdx(handle);
      int bitmapIdx = bitmapIdx(handle);
      if (bitmapIdx == 0) {
         byte val = this.value(memoryMapIdx);

         assert val == this.unusable : String.valueOf(val);

         buf.init(this, handle, this.runOffset(memoryMapIdx) + this.offset, reqCapacity, this.runLength(memoryMapIdx), this.arena.parent.threadCache());
      } else {
         this.initBufWithSubpage(buf, handle, bitmapIdx, reqCapacity);
      }

   }

   void initBufWithSubpage(PooledByteBuf buf, long handle, int reqCapacity) {
      this.initBufWithSubpage(buf, handle, bitmapIdx(handle), reqCapacity);
   }

   private void initBufWithSubpage(PooledByteBuf buf, long handle, int bitmapIdx, int reqCapacity) {
      assert bitmapIdx != 0;

      int memoryMapIdx = memoryMapIdx(handle);
      PoolSubpage subpage = this.subpages[this.subpageIdx(memoryMapIdx)];

      assert subpage.doNotDestroy;

      assert reqCapacity <= subpage.elemSize;

      buf.init(this, handle, this.runOffset(memoryMapIdx) + (bitmapIdx & 1073741823) * subpage.elemSize + this.offset, reqCapacity, subpage.elemSize, this.arena.parent.threadCache());
   }

   private byte value(int id) {
      return this.memoryMap[id];
   }

   private void setValue(int id, byte val) {
      this.memoryMap[id] = val;
   }

   private byte depth(int id) {
      return this.depthMap[id];
   }

   private static int log2(int val) {
      return 31 - Integer.numberOfLeadingZeros(val);
   }

   private int runLength(int id) {
      return 1 << this.log2ChunkSize - this.depth(id);
   }

   private int runOffset(int id) {
      int shift = id ^ 1 << this.depth(id);
      return shift * this.runLength(id);
   }

   private int subpageIdx(int memoryMapIdx) {
      return memoryMapIdx ^ this.maxSubpageAllocs;
   }

   private static int memoryMapIdx(long handle) {
      return (int)handle;
   }

   private static int bitmapIdx(long handle) {
      return (int)(handle >>> 32);
   }

   public int chunkSize() {
      return this.chunkSize;
   }

   public int freeBytes() {
      synchronized(this.arena) {
         return this.freeBytes;
      }
   }

   public String toString() {
      int freeBytes;
      synchronized(this.arena) {
         freeBytes = this.freeBytes;
      }

      return "Chunk(" + Integer.toHexString(System.identityHashCode(this)) + ": " + this.usage(freeBytes) + "%, " + (this.chunkSize - freeBytes) + '/' + this.chunkSize + ')';
   }

   void destroy() {
      this.arena.destroyChunk(this);
   }
}
