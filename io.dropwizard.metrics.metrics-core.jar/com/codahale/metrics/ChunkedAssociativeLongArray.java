package com.codahale.metrics;

import java.lang.ref.SoftReference;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;

class ChunkedAssociativeLongArray {
   private static final long[] EMPTY = new long[0];
   private static final int DEFAULT_CHUNK_SIZE = 512;
   private static final int MAX_CACHE_SIZE = 128;
   private final int defaultChunkSize;
   private final ArrayDeque chunksCache;
   private final Deque chunks;

   ChunkedAssociativeLongArray() {
      this(512);
   }

   ChunkedAssociativeLongArray(int chunkSize) {
      this.chunksCache = new ArrayDeque();
      this.chunks = new ArrayDeque();
      this.defaultChunkSize = chunkSize;
   }

   private Chunk allocateChunk() {
      Chunk chunk;
      do {
         SoftReference chunkRef = (SoftReference)this.chunksCache.pollLast();
         if (chunkRef == null) {
            return new Chunk(this.defaultChunkSize);
         }

         chunk = (Chunk)chunkRef.get();
      } while(chunk == null);

      chunk.cursor = 0;
      chunk.startIndex = 0;
      chunk.chunkSize = chunk.keys.length;
      return chunk;
   }

   private void freeChunk(Chunk chunk) {
      if (this.chunksCache.size() < 128) {
         this.chunksCache.add(new SoftReference(chunk));
      }

   }

   synchronized boolean put(long key, long value) {
      Chunk activeChunk = (Chunk)this.chunks.peekLast();
      if (activeChunk != null && activeChunk.cursor != 0 && activeChunk.keys[activeChunk.cursor - 1] > key) {
         return false;
      } else {
         if (activeChunk == null || activeChunk.cursor - activeChunk.startIndex == activeChunk.chunkSize) {
            activeChunk = this.allocateChunk();
            this.chunks.add(activeChunk);
         }

         activeChunk.append(key, value);
         return true;
      }
   }

   synchronized long[] values() {
      int valuesSize = this.size();
      if (valuesSize == 0) {
         return EMPTY;
      } else {
         long[] values = new long[valuesSize];
         int valuesIndex = 0;

         int length;
         for(Iterator var4 = this.chunks.iterator(); var4.hasNext(); valuesIndex += length) {
            Chunk chunk = (Chunk)var4.next();
            length = chunk.cursor - chunk.startIndex;
            int itemsToCopy = Math.min(valuesSize - valuesIndex, length);
            System.arraycopy(chunk.values, chunk.startIndex, values, valuesIndex, itemsToCopy);
         }

         return values;
      }
   }

   synchronized int size() {
      int result = 0;

      Chunk chunk;
      for(Iterator var2 = this.chunks.iterator(); var2.hasNext(); result += chunk.cursor - chunk.startIndex) {
         chunk = (Chunk)var2.next();
      }

      return result;
   }

   synchronized String out() {
      StringBuilder builder = new StringBuilder();
      Iterator iterator = this.chunks.iterator();

      while(iterator.hasNext()) {
         Chunk chunk = (Chunk)iterator.next();
         builder.append('[');

         for(int i = chunk.startIndex; i < chunk.cursor; ++i) {
            builder.append('(').append(chunk.keys[i]).append(": ").append(chunk.values[i]).append(')').append(' ');
         }

         builder.append(']');
         if (iterator.hasNext()) {
            builder.append("->");
         }
      }

      return builder.toString();
   }

   synchronized void trim(long startKey, long endKey) {
      Iterator descendingIterator = this.chunks.descendingIterator();

      while(descendingIterator.hasNext()) {
         Chunk currentTail = (Chunk)descendingIterator.next();
         if (!this.isFirstElementIsEmptyOrGreaterEqualThanKey(currentTail, endKey)) {
            currentTail.cursor = this.findFirstIndexOfGreaterEqualElements(currentTail.keys, currentTail.startIndex, currentTail.cursor, endKey);
            break;
         }

         this.freeChunk(currentTail);
         descendingIterator.remove();
      }

      Iterator iterator = this.chunks.iterator();

      while(iterator.hasNext()) {
         Chunk currentHead = (Chunk)iterator.next();
         if (!this.isLastElementIsLessThanKey(currentHead, startKey)) {
            int newStartIndex = this.findFirstIndexOfGreaterEqualElements(currentHead.keys, currentHead.startIndex, currentHead.cursor, startKey);
            if (currentHead.startIndex != newStartIndex) {
               currentHead.startIndex = newStartIndex;
               currentHead.chunkSize = currentHead.cursor - currentHead.startIndex;
            }
            break;
         }

         this.freeChunk(currentHead);
         iterator.remove();
      }

   }

   synchronized void clear() {
      this.chunks.clear();
   }

   private boolean isFirstElementIsEmptyOrGreaterEqualThanKey(Chunk chunk, long key) {
      return chunk.cursor == chunk.startIndex || chunk.keys[chunk.startIndex] >= key;
   }

   private boolean isLastElementIsLessThanKey(Chunk chunk, long key) {
      return chunk.cursor == chunk.startIndex || chunk.keys[chunk.cursor - 1] < key;
   }

   private int findFirstIndexOfGreaterEqualElements(long[] array, int startIndex, int endIndex, long minKey) {
      if (endIndex != startIndex && array[startIndex] < minKey) {
         int keyIndex = Arrays.binarySearch(array, startIndex, endIndex, minKey);
         return keyIndex < 0 ? -(keyIndex + 1) : keyIndex;
      } else {
         return startIndex;
      }
   }

   private static class Chunk {
      private final long[] keys;
      private final long[] values;
      private int chunkSize;
      private int startIndex;
      private int cursor;

      private Chunk(int chunkSize) {
         this.startIndex = 0;
         this.cursor = 0;
         this.chunkSize = chunkSize;
         this.keys = new long[chunkSize];
         this.values = new long[chunkSize];
      }

      private void append(long key, long value) {
         this.keys[this.cursor] = key;
         this.values[this.cursor] = value;
         ++this.cursor;
      }

      // $FF: synthetic method
      Chunk(int x0, Object x1) {
         this(x0);
      }
   }
}
