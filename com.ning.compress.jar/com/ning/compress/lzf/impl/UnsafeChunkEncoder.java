package com.ning.compress.lzf.impl;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public abstract class UnsafeChunkEncoder extends ChunkEncoder {
   protected static final Unsafe unsafe;
   protected static final long BYTE_ARRAY_OFFSET;
   protected static final long BYTE_ARRAY_OFFSET_PLUS2;

   public UnsafeChunkEncoder(int totalLength) {
      super(totalLength);
   }

   public UnsafeChunkEncoder(int totalLength, boolean bogus) {
      super(totalLength, bogus);
   }

   public UnsafeChunkEncoder(int totalLength, BufferRecycler bufferRecycler) {
      super(totalLength, bufferRecycler);
   }

   public UnsafeChunkEncoder(int totalLength, BufferRecycler bufferRecycler, boolean bogus) {
      super(totalLength, bufferRecycler, bogus);
   }

   protected static final int _copyPartialLiterals(byte[] in, int inPos, byte[] out, int outPos, int literals) {
      out[outPos++] = (byte)(literals - 1);
      long rawInPtr = BYTE_ARRAY_OFFSET + (long)inPos - (long)literals;
      long rawOutPtr = BYTE_ARRAY_OFFSET + (long)outPos;
      switch (literals >> 3) {
         case 3:
            unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
            rawInPtr += 8L;
            rawOutPtr += 8L;
         case 2:
            unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
            rawInPtr += 8L;
            rawOutPtr += 8L;
         case 1:
            unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
            rawInPtr += 8L;
            rawOutPtr += 8L;
         default:
            int left = literals & 7;
            if (left > 4) {
               unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
            } else {
               unsafe.putInt(out, rawOutPtr, unsafe.getInt(in, rawInPtr));
            }

            return outPos + literals;
      }
   }

   protected static final int _copyLongLiterals(byte[] in, int inPos, byte[] out, int outPos, int literals) {
      inPos -= literals;
      long rawInPtr = BYTE_ARRAY_OFFSET + (long)inPos;

      for(long rawOutPtr = BYTE_ARRAY_OFFSET + (long)outPos; literals >= 32; literals -= 32) {
         out[outPos++] = 31;
         ++rawOutPtr;
         unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
         rawInPtr += 8L;
         rawOutPtr += 8L;
         unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
         rawInPtr += 8L;
         rawOutPtr += 8L;
         unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
         rawInPtr += 8L;
         rawOutPtr += 8L;
         unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
         rawInPtr += 8L;
         rawOutPtr += 8L;
         inPos += 32;
         outPos += 32;
      }

      return literals > 0 ? _copyPartialLiterals(in, inPos + literals, out, outPos, literals) : outPos;
   }

   protected static final int _copyFullLiterals(byte[] in, int inPos, byte[] out, int outPos) {
      out[outPos++] = 31;
      long rawInPtr = BYTE_ARRAY_OFFSET + (long)inPos - 32L;
      long rawOutPtr = BYTE_ARRAY_OFFSET + (long)outPos;
      unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
      rawInPtr += 8L;
      rawOutPtr += 8L;
      unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
      rawInPtr += 8L;
      rawOutPtr += 8L;
      unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
      rawInPtr += 8L;
      rawOutPtr += 8L;
      unsafe.putLong(out, rawOutPtr, unsafe.getLong(in, rawInPtr));
      return outPos + 32;
   }

   protected static final int _handleTail(byte[] in, int inPos, int inEnd, byte[] out, int outPos, int literals) {
      while(inPos < inEnd) {
         ++inPos;
         ++literals;
         if (literals == 32) {
            out[outPos++] = (byte)(literals - 1);
            System.arraycopy(in, inPos - literals, out, outPos, literals);
            outPos += literals;
            literals = 0;
         }
      }

      if (literals > 0) {
         out[outPos++] = (byte)(literals - 1);
         System.arraycopy(in, inPos - literals, out, outPos, literals);
         outPos += literals;
      }

      return outPos;
   }

   protected static final int _findTailMatchLength(byte[] in, int ptr1, int ptr2, int maxPtr1) {
      int start1;
      for(start1 = ptr1; ptr1 < maxPtr1 && in[ptr1] == in[ptr2]; ++ptr2) {
         ++ptr1;
      }

      return ptr1 - start1 + 1;
   }

   static {
      try {
         Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
         theUnsafe.setAccessible(true);
         unsafe = (Unsafe)theUnsafe.get((Object)null);
      } catch (Exception var1) {
         throw new RuntimeException(var1);
      }

      BYTE_ARRAY_OFFSET = (long)unsafe.arrayBaseOffset(byte[].class);
      BYTE_ARRAY_OFFSET_PLUS2 = BYTE_ARRAY_OFFSET + 2L;
   }
}
