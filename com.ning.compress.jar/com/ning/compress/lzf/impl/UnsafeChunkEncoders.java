package com.ning.compress.lzf.impl;

import com.ning.compress.BufferRecycler;
import java.nio.ByteOrder;

public final class UnsafeChunkEncoders {
   private static final boolean LITTLE_ENDIAN;

   public static UnsafeChunkEncoder createEncoder(int totalLength) {
      return (UnsafeChunkEncoder)(LITTLE_ENDIAN ? new UnsafeChunkEncoderLE(totalLength) : new UnsafeChunkEncoderBE(totalLength));
   }

   public static UnsafeChunkEncoder createNonAllocatingEncoder(int totalLength) {
      return (UnsafeChunkEncoder)(LITTLE_ENDIAN ? new UnsafeChunkEncoderLE(totalLength, false) : new UnsafeChunkEncoderBE(totalLength, false));
   }

   public static UnsafeChunkEncoder createEncoder(int totalLength, BufferRecycler bufferRecycler) {
      return (UnsafeChunkEncoder)(LITTLE_ENDIAN ? new UnsafeChunkEncoderLE(totalLength, bufferRecycler) : new UnsafeChunkEncoderBE(totalLength, bufferRecycler));
   }

   public static UnsafeChunkEncoder createNonAllocatingEncoder(int totalLength, BufferRecycler bufferRecycler) {
      return (UnsafeChunkEncoder)(LITTLE_ENDIAN ? new UnsafeChunkEncoderLE(totalLength, bufferRecycler, false) : new UnsafeChunkEncoderBE(totalLength, bufferRecycler, false));
   }

   static {
      LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
   }
}
