package com.ning.compress.lzf.parallel;

import com.ning.compress.lzf.ChunkEncoder;
import com.ning.compress.lzf.LZFChunk;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import java.util.concurrent.Callable;

class CompressTask implements Callable {
   private static final ThreadLocal ENCODER = new ThreadLocal() {
      protected ChunkEncoder initialValue() {
         return ChunkEncoderFactory.optimalInstance();
      }
   };
   protected byte[] data;
   protected int offset;
   protected int length;
   protected BlockManager blockManager;

   public CompressTask(byte[] input, int offset, int length, BlockManager blockManager) {
      this.data = input;
      this.offset = offset;
      this.length = length;
      this.blockManager = blockManager;
   }

   public CompressTask(byte[] input, BlockManager blockManager) {
      this(input, 0, input.length, blockManager);
   }

   public LZFChunk call() {
      if (this.data != null) {
         LZFChunk lzfChunk = ((ChunkEncoder)ENCODER.get()).encodeChunk(this.data, this.offset, this.length);
         this.blockManager.releaseBlockToPool(this.data);
         return lzfChunk;
      } else {
         ENCODER.remove();
         return null;
      }
   }
}
