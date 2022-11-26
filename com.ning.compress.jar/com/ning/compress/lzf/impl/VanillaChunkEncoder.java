package com.ning.compress.lzf.impl;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;

public class VanillaChunkEncoder extends ChunkEncoder {
   public VanillaChunkEncoder(int totalLength) {
      super(totalLength);
   }

   protected VanillaChunkEncoder(int totalLength, boolean bogus) {
      super(totalLength, bogus);
   }

   public VanillaChunkEncoder(int totalLength, BufferRecycler bufferRecycler) {
      super(totalLength, bufferRecycler);
   }

   protected VanillaChunkEncoder(int totalLength, BufferRecycler bufferRecycler, boolean bogus) {
      super(totalLength, bufferRecycler, bogus);
   }

   public static VanillaChunkEncoder nonAllocatingEncoder(int totalLength) {
      return new VanillaChunkEncoder(totalLength, true);
   }

   public static VanillaChunkEncoder nonAllocatingEncoder(int totalLength, BufferRecycler bufferRecycler) {
      return new VanillaChunkEncoder(totalLength, bufferRecycler, true);
   }

   protected int tryCompress(byte[] in, int inPos, int inEnd, byte[] out, int outPos) {
      int[] hashTable = this._hashTable;
      ++outPos;
      int seen = this.first(in, inPos);
      int literals = 0;
      inEnd -= 4;
      int firstPos = inPos;

      while(true) {
         while(inPos < inEnd) {
            byte p2 = in[inPos + 2];
            seen = (seen << 8) + (p2 & 255);
            int off = this.hash(seen);
            int ref = hashTable[off];
            hashTable[off] = inPos;
            if (ref < inPos && ref >= firstPos && (off = inPos - ref) <= 8192 && in[ref + 2] == p2 && in[ref + 1] == (byte)(seen >> 8) && in[ref] == (byte)(seen >> 16)) {
               int maxLen = inEnd - inPos + 2;
               if (maxLen > 264) {
                  maxLen = 264;
               }

               if (literals == 0) {
                  --outPos;
               } else {
                  out[outPos - literals - 1] = (byte)(literals - 1);
                  literals = 0;
               }

               int len;
               for(len = 3; len < maxLen && in[ref + len] == in[inPos + len]; ++len) {
               }

               len -= 2;
               --off;
               if (len < 7) {
                  out[outPos++] = (byte)((off >> 8) + (len << 5));
               } else {
                  out[outPos++] = (byte)((off >> 8) + 224);
                  out[outPos++] = (byte)(len - 7);
               }

               out[outPos++] = (byte)off;
               ++outPos;
               inPos += len;
               seen = this.first(in, inPos);
               seen = (seen << 8) + (in[inPos + 2] & 255);
               hashTable[this.hash(seen)] = inPos++;
               seen = (seen << 8) + (in[inPos + 2] & 255);
               hashTable[this.hash(seen)] = inPos++;
            } else {
               out[outPos++] = in[inPos++];
               ++literals;
               if (literals == 32) {
                  out[outPos - 33] = 31;
                  literals = 0;
                  ++outPos;
               }
            }
         }

         return this._handleTail(in, inPos, inEnd + 4, out, outPos, literals);
      }
   }

   private final int _handleTail(byte[] in, int inPos, int inEnd, byte[] out, int outPos, int literals) {
      while(inPos < inEnd) {
         out[outPos++] = in[inPos++];
         ++literals;
         if (literals == 32) {
            out[outPos - literals - 1] = (byte)(literals - 1);
            literals = 0;
            ++outPos;
         }
      }

      out[outPos - literals - 1] = (byte)(literals - 1);
      if (literals == 0) {
         --outPos;
      }

      return outPos;
   }

   private final int first(byte[] in, int inPos) {
      return (in[inPos] << 8) + (in[inPos + 1] & 255);
   }
}
