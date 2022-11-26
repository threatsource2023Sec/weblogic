package org.python.apache.commons.compress.archivers.sevenz;

import java.util.LinkedList;

class Folder {
   Coder[] coders;
   long totalInputStreams;
   long totalOutputStreams;
   BindPair[] bindPairs;
   long[] packedStreams;
   long[] unpackSizes;
   boolean hasCrc;
   long crc;
   int numUnpackSubStreams;

   Iterable getOrderedCoders() {
      LinkedList l = new LinkedList();

      int pair;
      for(int current = (int)this.packedStreams[0]; current != -1; current = pair != -1 ? (int)this.bindPairs[pair].inIndex : -1) {
         l.addLast(this.coders[current]);
         pair = this.findBindPairForOutStream(current);
      }

      return l;
   }

   int findBindPairForInStream(int index) {
      for(int i = 0; i < this.bindPairs.length; ++i) {
         if (this.bindPairs[i].inIndex == (long)index) {
            return i;
         }
      }

      return -1;
   }

   int findBindPairForOutStream(int index) {
      for(int i = 0; i < this.bindPairs.length; ++i) {
         if (this.bindPairs[i].outIndex == (long)index) {
            return i;
         }
      }

      return -1;
   }

   long getUnpackSize() {
      if (this.totalOutputStreams == 0L) {
         return 0L;
      } else {
         for(int i = (int)this.totalOutputStreams - 1; i >= 0; --i) {
            if (this.findBindPairForOutStream(i) < 0) {
               return this.unpackSizes[i];
            }
         }

         return 0L;
      }
   }

   long getUnpackSizeForCoder(Coder coder) {
      if (this.coders != null) {
         for(int i = 0; i < this.coders.length; ++i) {
            if (this.coders[i] == coder) {
               return this.unpackSizes[i];
            }
         }
      }

      return 0L;
   }

   public String toString() {
      return "Folder with " + this.coders.length + " coders, " + this.totalInputStreams + " input streams, " + this.totalOutputStreams + " output streams, " + this.bindPairs.length + " bind pairs, " + this.packedStreams.length + " packed streams, " + this.unpackSizes.length + " unpack sizes, " + (this.hasCrc ? "with CRC " + this.crc : "without CRC") + " and " + this.numUnpackSubStreams + " unpack streams";
   }
}
