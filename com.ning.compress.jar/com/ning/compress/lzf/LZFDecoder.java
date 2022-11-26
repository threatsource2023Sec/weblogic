package com.ning.compress.lzf;

import com.ning.compress.lzf.util.ChunkDecoderFactory;
import java.util.concurrent.atomic.AtomicReference;

public class LZFDecoder {
   protected static final AtomicReference _fastDecoderRef = new AtomicReference();
   protected static final AtomicReference _safeDecoderRef = new AtomicReference();

   public static ChunkDecoder fastDecoder() {
      ChunkDecoder dec = (ChunkDecoder)_fastDecoderRef.get();
      if (dec == null) {
         dec = ChunkDecoderFactory.optimalInstance();
         _fastDecoderRef.compareAndSet((Object)null, dec);
      }

      return dec;
   }

   public static ChunkDecoder safeDecoder() {
      ChunkDecoder dec = (ChunkDecoder)_safeDecoderRef.get();
      if (dec == null) {
         dec = ChunkDecoderFactory.safeInstance();
         _safeDecoderRef.compareAndSet((Object)null, dec);
      }

      return dec;
   }

   public static int calculateUncompressedSize(byte[] data, int offset, int length) throws LZFException {
      return ChunkDecoder.calculateUncompressedSize(data, length, length);
   }

   public static byte[] decode(byte[] inputBuffer) throws LZFException {
      return fastDecoder().decode(inputBuffer, 0, inputBuffer.length);
   }

   public static byte[] decode(byte[] inputBuffer, int offset, int length) throws LZFException {
      return fastDecoder().decode(inputBuffer, offset, length);
   }

   public static int decode(byte[] inputBuffer, byte[] targetBuffer) throws LZFException {
      return fastDecoder().decode(inputBuffer, 0, inputBuffer.length, targetBuffer);
   }

   public static int decode(byte[] sourceBuffer, int offset, int length, byte[] targetBuffer) throws LZFException {
      return fastDecoder().decode(sourceBuffer, offset, length, targetBuffer);
   }

   public static byte[] safeDecode(byte[] inputBuffer) throws LZFException {
      return safeDecoder().decode(inputBuffer, 0, inputBuffer.length);
   }

   public static byte[] safeDecode(byte[] inputBuffer, int offset, int length) throws LZFException {
      return safeDecoder().decode(inputBuffer, offset, length);
   }

   public static int safeDecode(byte[] inputBuffer, byte[] targetBuffer) throws LZFException {
      return safeDecoder().decode(inputBuffer, 0, inputBuffer.length, targetBuffer);
   }

   public static int safeDecode(byte[] sourceBuffer, int offset, int length, byte[] targetBuffer) throws LZFException {
      return safeDecoder().decode(sourceBuffer, offset, length, targetBuffer);
   }
}
