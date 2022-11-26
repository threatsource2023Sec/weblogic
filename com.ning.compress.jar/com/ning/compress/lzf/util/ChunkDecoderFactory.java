package com.ning.compress.lzf.util;

import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.impl.UnsafeChunkDecoder;
import com.ning.compress.lzf.impl.VanillaChunkDecoder;

public class ChunkDecoderFactory {
   private static final ChunkDecoderFactory _instance;
   private final Class _implClass;

   private ChunkDecoderFactory(Class imp) {
      this._implClass = imp;
   }

   public static ChunkDecoder optimalInstance() {
      try {
         return (ChunkDecoder)_instance._implClass.newInstance();
      } catch (Exception var1) {
         throw new IllegalStateException("Failed to load a ChunkDecoder instance (" + var1.getClass().getName() + "): " + var1.getMessage(), var1);
      }
   }

   public static ChunkDecoder safeInstance() {
      return new VanillaChunkDecoder();
   }

   static {
      Class impl = null;

      try {
         impl = Class.forName(UnsafeChunkDecoder.class.getName());
      } catch (Throwable var2) {
      }

      if (impl == null) {
         impl = VanillaChunkDecoder.class;
      }

      _instance = new ChunkDecoderFactory(impl);
   }
}
