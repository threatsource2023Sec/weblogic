package org.python.netty.channel;

import java.util.ArrayList;
import java.util.List;

public class AdaptiveRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
   static final int DEFAULT_MINIMUM = 64;
   static final int DEFAULT_INITIAL = 1024;
   static final int DEFAULT_MAXIMUM = 65536;
   private static final int INDEX_INCREMENT = 4;
   private static final int INDEX_DECREMENT = 1;
   private static final int[] SIZE_TABLE;
   /** @deprecated */
   @Deprecated
   public static final AdaptiveRecvByteBufAllocator DEFAULT;
   private final int minIndex;
   private final int maxIndex;
   private final int initial;

   private static int getSizeTableIndex(int size) {
      int low = 0;
      int high = SIZE_TABLE.length - 1;

      while(high >= low) {
         if (high == low) {
            return high;
         }

         int mid = low + high >>> 1;
         int a = SIZE_TABLE[mid];
         int b = SIZE_TABLE[mid + 1];
         if (size > b) {
            low = mid + 1;
         } else {
            if (size >= a) {
               if (size == a) {
                  return mid;
               }

               return mid + 1;
            }

            high = mid - 1;
         }
      }

      return low;
   }

   public AdaptiveRecvByteBufAllocator() {
      this(64, 1024, 65536);
   }

   public AdaptiveRecvByteBufAllocator(int minimum, int initial, int maximum) {
      if (minimum <= 0) {
         throw new IllegalArgumentException("minimum: " + minimum);
      } else if (initial < minimum) {
         throw new IllegalArgumentException("initial: " + initial);
      } else if (maximum < initial) {
         throw new IllegalArgumentException("maximum: " + maximum);
      } else {
         int minIndex = getSizeTableIndex(minimum);
         if (SIZE_TABLE[minIndex] < minimum) {
            this.minIndex = minIndex + 1;
         } else {
            this.minIndex = minIndex;
         }

         int maxIndex = getSizeTableIndex(maximum);
         if (SIZE_TABLE[maxIndex] > maximum) {
            this.maxIndex = maxIndex - 1;
         } else {
            this.maxIndex = maxIndex;
         }

         this.initial = initial;
      }
   }

   public RecvByteBufAllocator.Handle newHandle() {
      return new HandleImpl(this.minIndex, this.maxIndex, this.initial);
   }

   static {
      List sizeTable = new ArrayList();

      int i;
      for(i = 16; i < 512; i += 16) {
         sizeTable.add(i);
      }

      for(i = 512; i > 0; i <<= 1) {
         sizeTable.add(i);
      }

      SIZE_TABLE = new int[sizeTable.size()];

      for(i = 0; i < SIZE_TABLE.length; ++i) {
         SIZE_TABLE[i] = (Integer)sizeTable.get(i);
      }

      DEFAULT = new AdaptiveRecvByteBufAllocator();
   }

   private final class HandleImpl extends DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle {
      private final int minIndex;
      private final int maxIndex;
      private int index;
      private int nextReceiveBufferSize;
      private boolean decreaseNow;

      public HandleImpl(int minIndex, int maxIndex, int initial) {
         super();
         this.minIndex = minIndex;
         this.maxIndex = maxIndex;
         this.index = AdaptiveRecvByteBufAllocator.getSizeTableIndex(initial);
         this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
      }

      public int guess() {
         return this.nextReceiveBufferSize;
      }

      private void record(int actualReadBytes) {
         if (actualReadBytes <= AdaptiveRecvByteBufAllocator.SIZE_TABLE[Math.max(0, this.index - 1 - 1)]) {
            if (this.decreaseNow) {
               this.index = Math.max(this.index - 1, this.minIndex);
               this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
               this.decreaseNow = false;
            } else {
               this.decreaseNow = true;
            }
         } else if (actualReadBytes >= this.nextReceiveBufferSize) {
            this.index = Math.min(this.index + 4, this.maxIndex);
            this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
            this.decreaseNow = false;
         }

      }

      public void readComplete() {
         this.record(this.totalBytesRead());
      }
   }
}
