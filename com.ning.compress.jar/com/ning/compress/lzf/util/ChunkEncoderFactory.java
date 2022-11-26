package com.ning.compress.lzf.util;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import com.ning.compress.lzf.impl.UnsafeChunkEncoders;
import com.ning.compress.lzf.impl.VanillaChunkEncoder;

public class ChunkEncoderFactory {
   public static ChunkEncoder optimalInstance() {
      return optimalInstance(65535);
   }

   public static ChunkEncoder optimalInstance(int totalLength) {
      try {
         return UnsafeChunkEncoders.createEncoder(totalLength);
      } catch (Exception var2) {
         return safeInstance(totalLength);
      }
   }

   public static ChunkEncoder optimalNonAllocatingInstance(int totalLength) {
      try {
         return UnsafeChunkEncoders.createNonAllocatingEncoder(totalLength);
      } catch (Exception var2) {
         return safeNonAllocatingInstance(totalLength);
      }
   }

   public static ChunkEncoder safeInstance() {
      return safeInstance(65535);
   }

   public static ChunkEncoder safeInstance(int totalLength) {
      return new VanillaChunkEncoder(totalLength);
   }

   public static ChunkEncoder safeNonAllocatingInstance(int totalLength) {
      return VanillaChunkEncoder.nonAllocatingEncoder(totalLength);
   }

   public static ChunkEncoder optimalInstance(BufferRecycler bufferRecycler) {
      return optimalInstance(65535, bufferRecycler);
   }

   public static ChunkEncoder optimalInstance(int totalLength, BufferRecycler bufferRecycler) {
      try {
         return UnsafeChunkEncoders.createEncoder(totalLength, bufferRecycler);
      } catch (Exception var3) {
         return safeInstance(totalLength, bufferRecycler);
      }
   }

   public static ChunkEncoder optimalNonAllocatingInstance(int totalLength, BufferRecycler bufferRecycler) {
      try {
         return UnsafeChunkEncoders.createNonAllocatingEncoder(totalLength, bufferRecycler);
      } catch (Exception var3) {
         return safeNonAllocatingInstance(totalLength, bufferRecycler);
      }
   }

   public static ChunkEncoder safeInstance(BufferRecycler bufferRecycler) {
      return safeInstance(65535, bufferRecycler);
   }

   public static ChunkEncoder safeInstance(int totalLength, BufferRecycler bufferRecycler) {
      return new VanillaChunkEncoder(totalLength, bufferRecycler);
   }

   public static ChunkEncoder safeNonAllocatingInstance(int totalLength, BufferRecycler bufferRecycler) {
      return VanillaChunkEncoder.nonAllocatingEncoder(totalLength, bufferRecycler);
   }
}
