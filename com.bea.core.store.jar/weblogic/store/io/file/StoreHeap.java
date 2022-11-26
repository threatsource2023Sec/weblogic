package weblogic.store.io.file;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import weblogic.store.internal.StoreStatisticsImpl;
import weblogic.utils.collections.AbstractEmbeddedListElement;
import weblogic.utils.collections.EmbeddedList;
import weblogic.utils.collections.TreeMap;

final class StoreHeap {
   private final TreeMap chunksByPos;
   private final TreeMap chunksBySize;
   private int allocatedBlocks;
   private int capacity;
   private boolean scheduled;
   private static final ChunkComparator COMPARATOR = new ChunkComparator();
   static final boolean DEBUG_SPACE_UPDATES = Boolean.getBoolean("weblogic.store.DebugSpaceUpdates");

   StoreHeap(boolean scheduled) {
      this.scheduled = scheduled;
      this.chunksByPos = new TreeMap(COMPARATOR);
      if (scheduled) {
         this.chunksBySize = null;
      } else {
         this.chunksBySize = new TreeMap();
      }

   }

   synchronized long allocForce(short fileHint, int posHint, int numBlocks) {
      Chunk chunk = this.allocChunk(fileHint, posHint, numBlocks, true);
      if (chunk == null) {
         return -1L;
      } else {
         this.allocatedBlocks += numBlocks;
         return makeHandle(chunk.fileNum, chunk.pos, chunk.size);
      }
   }

   synchronized long[] alloc(short fileHint, int posHint, int[] allocVec) {
      int numBlocks = 0;

      for(int i = 0; i < allocVec.length; ++i) {
         numBlocks += allocVec[i];
      }

      Chunk chunk = this.allocChunk(fileHint, posHint, numBlocks, false);
      if (chunk == null) {
         return null;
      } else {
         this.allocatedBlocks += numBlocks;
         long[] ret = new long[allocVec.length];
         int pos = chunk.pos;

         for(int i = 0; i < ret.length; ++i) {
            int size = allocVec[i];
            ret[i] = makeHandle(chunk.fileNum, pos, size);
            pos += size;
         }

         return ret;
      }
   }

   private Chunk allocChunk(short fileHint, int posHint, int numBlocks, boolean forceHint) {
      Chunk chunk = null;
      if (!this.scheduled && !forceHint) {
         chunk = this.allocIgnoringHint(numBlocks);
      } else {
         SearchChunk sc = new SearchChunk(fileHint, posHint, numBlocks);
         chunk = this.allocUsingHint(this.chunksByPos.tailMap(sc), sc);
         if (chunk == null) {
            chunk = this.allocUsingHint(this.chunksByPos.headMap(sc), sc);
         }
      }

      return chunk;
   }

   synchronized void free(long handle) {
      short fileNum = handleToFileNum(handle);
      int pos = handleToFileBlock(handle);
      int size = handleToNumBlocks(handle);
      if (DEBUG_SPACE_UPDATES) {
         System.out.println("RS: " + (new Date(System.currentTimeMillis())).toString() + " free handle: fileNum = " + fileNum + " pos = " + pos + " size = " + size);
      }

      this.allocatedBlocks -= size;
      Chunk sc = new Chunk(fileNum, pos, size);
      Chunk prev = null;
      Chunk next = null;
      Iterator nextIterator = null;
      if (!this.chunksByPos.isEmpty()) {
         Map.Entry prevEntry = this.chunksByPos.lowerEntry(sc);
         if (prevEntry != null) {
            prev = (Chunk)prevEntry.getKey();
         }

         SortedMap tailMap = this.chunksByPos.tailMap(sc);
         nextIterator = tailMap.values().iterator();
         if (nextIterator.hasNext()) {
            next = (Chunk)nextIterator.next();
         }
      }

      assert prev == null || COMPARATOR.compare(sc, prev) > 0;

      assert next == null || COMPARATOR.compare(sc, next) < 0;

      if (prev != null && prev.fileNum == fileNum && prev.pos + prev.size == pos) {
         this.removeChunkAllocatedBySize(prev);
         prev.size += size;
         if (next != null && prev.fileNum == next.fileNum && prev.pos + prev.size == next.pos) {
            this.removeChunkAllocatedBySize(next);
            prev.size += next.size;
            nextIterator.remove();
         }

         if (!this.scheduled) {
            this.freeChunkBySize(prev);
         }

      } else if (next != null && next.fileNum == fileNum && next.pos == pos + size) {
         nextIterator.remove();
         this.removeChunkAllocatedBySize(next);
         next.pos -= size;
         next.size += size;
         this.chunksByPos.put(next, next);
         this.freeChunkBySize(next);
      } else {
         Chunk newChunk = new Chunk(fileNum, pos, size);
         this.chunksByPos.put(newChunk, newChunk);
         this.freeChunkBySize(newChunk);
      }
   }

   synchronized void expand(short fileNum, int fileBlock, int numBlocks) {
      this.capacity += numBlocks;
      long handle = makeHandle(fileNum, fileBlock, numBlocks);
      this.allocatedBlocks += numBlocks;
      this.free(handle);
   }

   private Chunk allocUsingHint(Map m, SearchChunk sc) {
      Iterator i = m.values().iterator();

      Chunk next;
      do {
         if (!i.hasNext()) {
            return null;
         }

         next = (Chunk)i.next();
      } while(sc.size > next.size);

      int startLen;
      if (next.contains(sc)) {
         startLen = sc.pos - next.pos;

         assert startLen >= 0;

         int endLen = next.pos + next.size - (sc.pos + sc.size);

         assert endLen >= 0;

         this.removeChunkAllocatedBySize(next);
         if (startLen > 0) {
            next.size = startLen;
            this.freeChunkBySize(next);
         } else {
            i.remove();
         }

         if (endLen > 0) {
            Chunk newChunk = new Chunk(sc.fileNum, sc.pos + sc.size, endLen);
            this.chunksByPos.put(newChunk, newChunk);
            this.freeChunkBySize(newChunk);
         }

         return new Chunk(sc.fileNum, sc.pos, sc.size);
      } else {
         this.removeChunkAllocatedBySize(next);
         startLen = next.pos;
         next.pos += sc.size;
         next.size -= sc.size;
         i.remove();
         if (next.size > 0) {
            this.chunksByPos.put(next, next);
            this.freeChunkBySize(next);
         }

         return new Chunk(next.fileNum, startLen, sc.size);
      }
   }

   private Chunk allocateChunkBySize(int numBlocks) {
      assert !this.scheduled;

      SortedMap sizeMap = this.chunksBySize.tailMap(numBlocks);
      Iterator mi = sizeMap.values().iterator();
      if (mi.hasNext()) {
         EmbeddedList chunkList = (EmbeddedList)mi.next();
         Iterator li = chunkList.iterator();
         Chunk next = (Chunk)li.next();
         li.remove();
         if (chunkList.isEmpty()) {
            mi.remove();
         }

         return next;
      } else {
         if (DEBUG_SPACE_UPDATES) {
            System.out.println("RS: allocateChunkBysize failed for " + numBlocks + " blocks.\nsizeMap = " + sizeMap);
            this.dump();
         }

         return null;
      }
   }

   void dump() {
      System.out.println("RS: The avaiable chunks are:");
      System.out.println("RS: " + (new Date(System.currentTimeMillis())).toString());
      Iterator itr;
      if (!this.chunksByPos.isEmpty()) {
         System.out.println("RS: by pos ---------------------");
         itr = this.chunksByPos.values().iterator();

         while(itr.hasNext()) {
            Chunk chunk = (Chunk)itr.next();
            System.out.println("RS: chunk= " + chunk.toString());
         }
      }

      System.out.println("RS: by size ---------------------");
      itr = this.chunksBySize.values().iterator();

      while(itr.hasNext()) {
         EmbeddedList chunkList = (EmbeddedList)itr.next();
         Iterator li = chunkList.iterator();
         System.out.println("RS: size --");

         while(li.hasNext()) {
            Chunk chunk = (Chunk)li.next();
            System.out.println("RS: chunk= " + chunk.toString());
         }
      }

   }

   private void freeChunkBySize(Chunk chunk) {
      if (!this.scheduled) {
         EmbeddedList chunkList = (EmbeddedList)this.chunksBySize.get(chunk.size);
         if (DEBUG_SPACE_UPDATES) {
            System.out.println("RS: freeChunkBySize: chunk= " + chunk + " chunkList = " + chunkList);
         }

         if (chunkList == null) {
            chunkList = new EmbeddedList();
            chunkList.add(chunk);
            this.chunksBySize.put(chunk.size, chunkList);
         } else {
            chunkList.add(chunk);
         }

      }
   }

   private void removeChunkAllocatedBySize(Chunk chunk) {
      if (!this.scheduled) {
         EmbeddedList chunkList = (EmbeddedList)this.chunksBySize.get(chunk.size);
         if (DEBUG_SPACE_UPDATES) {
            System.out.println("RS: removeChunkAllocatedBysize: chunk= " + chunk + " chunkList = " + chunkList);
         }

         assert chunkList != null;

         chunkList.remove(chunk);
         if (chunkList.isEmpty()) {
            this.chunksBySize.remove(chunk.size);
         }

      }
   }

   private Chunk allocIgnoringHint(int numBlocks) {
      assert !this.scheduled;

      Chunk next = this.allocateChunkBySize(numBlocks);
      if (next != null) {
         assert next.size >= numBlocks;

         int pos = next.pos + next.size - numBlocks;
         next.size -= numBlocks;
         if (next.size == 0) {
            this.chunksByPos.remove(next);
         } else {
            this.freeChunkBySize(next);
         }

         if (DEBUG_SPACE_UPDATES) {
            System.out.println("RS: allocateChunkBySize: numBlocks = " + numBlocks + " new chunk = [f=" + next.fileNum + ",pos=" + pos + " removing from chunk structure: next = " + next);
         }

         return new Chunk(next.fileNum, pos, numBlocks);
      } else {
         return null;
      }
   }

   synchronized void clear() {
      this.chunksByPos.clear();
   }

   synchronized int getAllocatedBlocks() {
      return this.allocatedBlocks;
   }

   synchronized int getCapacity() {
      return this.capacity;
   }

   void updateStats(StoreStatisticsImpl stats, long extraBlocks, long deleteRecordOnlyBlocks, int blockSize) {
      long percent;
      synchronized(this) {
         if (extraBlocks < 0L) {
            throw new AssertionError();
         }

         if (this.capacity <= 0 || (long)this.allocatedBlocks - deleteRecordOnlyBlocks < 0L) {
            stats.setLocalUsedPercent(0);
            return;
         }

         percent = ((long)this.allocatedBlocks - deleteRecordOnlyBlocks) * 100L / ((long)this.capacity + extraBlocks);
         if (percent > 100L) {
            percent = 100L;
         }

         stats.setLocalUsedPercent((int)percent);
         if (!this.chunksBySize.isEmpty()) {
            stats.setLargestFreeChunkBlocks((Integer)this.chunksBySize.lastKey());
         } else {
            stats.setLargestFreeChunkBlocks(0);
         }

         if (this.allocatedBlocks != 0) {
            stats.setDeleteRecordOnlyPercent((int)(100L * deleteRecordOnlyBlocks / (long)this.allocatedBlocks));
         } else {
            stats.setDeleteRecordOnlyPercent(0);
         }
      }

      if (DEBUG_SPACE_UPDATES) {
         System.out.println("RS: before setting largest free chunk bytes: chunksBySize isEmpty = " + this.chunksBySize.isEmpty() + " largestFreeChunkBlocks = " + stats.getLargestFreeChunkBlocks());
         System.out.println("*** STORE DEBUG SPACE allocator cap/used/deleteOnly/extra/total/percent " + this.capacity + "/" + this.allocatedBlocks + "/" + deleteRecordOnlyBlocks + "/" + extraBlocks + "/" + (extraBlocks + (long)this.capacity) + "/" + percent);
      }

   }

   private static long makeHandle(short fileNum, int fileBlock, int numBlocks) {
      long h = (long)fileNum;
      h <<= 24;
      h |= (long)fileBlock;
      h <<= 24;
      h |= (long)numBlocks;
      return h;
   }

   static final short handleToFileNum(long handle) {
      return (short)((int)(handle >> 48));
   }

   static int handleToFileBlock(long handle) {
      return (int)(handle >> 24) & 16777215;
   }

   static int handleToNumBlocks(long handle) {
      return (int)handle & 16777215;
   }

   private static final class ChunkComparator implements Comparator {
      private ChunkComparator() {
      }

      public int compare(Object o1, Object o2) {
         Chunk c1 = (Chunk)o1;
         Chunk c2 = (Chunk)o2;
         if (c1 instanceof SearchChunk && c2.contains((SearchChunk)c1)) {
            return 0;
         } else if (c2 instanceof SearchChunk && c1.contains((SearchChunk)c2)) {
            return 0;
         } else if (c1.fileNum < c2.fileNum) {
            return -1;
         } else if (c1.fileNum > c2.fileNum) {
            return 1;
         } else if (c1.pos < c2.pos) {
            return -1;
         } else {
            return c1.pos > c2.pos ? 1 : 0;
         }
      }

      // $FF: synthetic method
      ChunkComparator(Object x0) {
         this();
      }
   }

   private static final class SearchChunk extends Chunk {
      SearchChunk(short fileNum, int pos, int size) {
         super(fileNum, pos, size);
      }
   }

   private static class Chunk extends AbstractEmbeddedListElement {
      short fileNum;
      int pos;
      int size;

      Chunk(short fileNum, int pos, int size) {
         this.fileNum = fileNum;
         this.pos = pos;
         this.size = size;
      }

      final boolean contains(SearchChunk sc) {
         return this.fileNum == sc.fileNum && this.pos <= sc.pos && sc.pos + sc.size <= this.pos + this.size;
      }

      public String toString() {
         return this.getClass().getName() + "[f=" + this.fileNum + " p=" + this.pos + " s=" + this.size + "]";
      }
   }
}
