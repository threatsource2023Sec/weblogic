package org.python.netty.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.python.netty.util.internal.StringUtil;

final class PoolChunkList implements PoolChunkListMetric {
   private static final Iterator EMPTY_METRICS = Collections.emptyList().iterator();
   private final PoolArena arena;
   private final PoolChunkList nextList;
   private final int minUsage;
   private final int maxUsage;
   private final int maxCapacity;
   private PoolChunk head;
   private PoolChunkList prevList;

   PoolChunkList(PoolArena arena, PoolChunkList nextList, int minUsage, int maxUsage, int chunkSize) {
      assert minUsage <= maxUsage;

      this.arena = arena;
      this.nextList = nextList;
      this.minUsage = minUsage;
      this.maxUsage = maxUsage;
      this.maxCapacity = calculateMaxCapacity(minUsage, chunkSize);
   }

   private static int calculateMaxCapacity(int minUsage, int chunkSize) {
      minUsage = minUsage0(minUsage);
      return minUsage == 100 ? 0 : (int)((long)chunkSize * (100L - (long)minUsage) / 100L);
   }

   void prevList(PoolChunkList prevList) {
      assert this.prevList == null;

      this.prevList = prevList;
   }

   boolean allocate(PooledByteBuf buf, int reqCapacity, int normCapacity) {
      if (this.head != null && normCapacity <= this.maxCapacity) {
         PoolChunk cur = this.head;

         do {
            long handle = cur.allocate(normCapacity);
            if (handle >= 0L) {
               cur.initBuf(buf, handle, reqCapacity);
               if (cur.usage() >= this.maxUsage) {
                  this.remove(cur);
                  this.nextList.add(cur);
               }

               return true;
            }

            cur = cur.next;
         } while(cur != null);

         return false;
      } else {
         return false;
      }
   }

   boolean free(PoolChunk chunk, long handle) {
      chunk.free(handle);
      if (chunk.usage() < this.minUsage) {
         this.remove(chunk);
         return this.move0(chunk);
      } else {
         return true;
      }
   }

   private boolean move(PoolChunk chunk) {
      assert chunk.usage() < this.maxUsage;

      if (chunk.usage() < this.minUsage) {
         return this.move0(chunk);
      } else {
         this.add0(chunk);
         return true;
      }
   }

   private boolean move0(PoolChunk chunk) {
      if (this.prevList == null) {
         assert chunk.usage() == 0;

         return false;
      } else {
         return this.prevList.move(chunk);
      }
   }

   void add(PoolChunk chunk) {
      if (chunk.usage() >= this.maxUsage) {
         this.nextList.add(chunk);
      } else {
         this.add0(chunk);
      }
   }

   void add0(PoolChunk chunk) {
      chunk.parent = this;
      if (this.head == null) {
         this.head = chunk;
         chunk.prev = null;
         chunk.next = null;
      } else {
         chunk.prev = null;
         chunk.next = this.head;
         this.head.prev = chunk;
         this.head = chunk;
      }

   }

   private void remove(PoolChunk cur) {
      if (cur == this.head) {
         this.head = cur.next;
         if (this.head != null) {
            this.head.prev = null;
         }
      } else {
         PoolChunk next = cur.next;
         cur.prev.next = next;
         if (next != null) {
            next.prev = cur.prev;
         }
      }

   }

   public int minUsage() {
      return minUsage0(this.minUsage);
   }

   public int maxUsage() {
      return Math.min(this.maxUsage, 100);
   }

   private static int minUsage0(int value) {
      return Math.max(1, value);
   }

   public Iterator iterator() {
      synchronized(this.arena) {
         if (this.head == null) {
            return EMPTY_METRICS;
         } else {
            List metrics = new ArrayList();
            PoolChunk cur = this.head;

            do {
               metrics.add(cur);
               cur = cur.next;
            } while(cur != null);

            return metrics.iterator();
         }
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      synchronized(this.arena) {
         if (this.head == null) {
            return "none";
         } else {
            PoolChunk cur = this.head;

            while(true) {
               buf.append(cur);
               cur = cur.next;
               if (cur == null) {
                  return buf.toString();
               }

               buf.append(StringUtil.NEWLINE);
            }
         }
      }
   }

   void destroy(PoolArena arena) {
      for(PoolChunk chunk = this.head; chunk != null; chunk = chunk.next) {
         arena.destroyChunk(chunk);
      }

      this.head = null;
   }
}
