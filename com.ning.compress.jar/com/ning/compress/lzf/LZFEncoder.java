package com.ning.compress.lzf;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.util.ChunkEncoderFactory;

public class LZFEncoder {
   public static final int MAX_CHUNK_RESULT_SIZE = 67657;
   private static final int MAX_CHUNK_WORKSPACE_SIZE = 67657;
   private static final int FULL_UNCOMP_ENCODED_CHUNK = 65542;

   private LZFEncoder() {
   }

   public static int estimateMaxWorkspaceSize(int inputSize) {
      if (inputSize <= 65535) {
         return 9 + inputSize + (inputSize >> 5) + (inputSize >> 6);
      } else {
         inputSize -= 65535;
         if (inputSize <= 65535) {
            return 67657 + 7 + inputSize;
         } else {
            int chunkCount = inputSize / '\uffff';
            inputSize -= chunkCount * '\uffff';
            return 67657 + chunkCount * 65542 + 7 + inputSize;
         }
      }
   }

   public static byte[] encode(byte[] data) {
      return encode(data, 0, data.length);
   }

   public static byte[] safeEncode(byte[] data) {
      return safeEncode(data, 0, data.length);
   }

   public static byte[] encode(byte[] data, int offset, int length) {
      ChunkEncoder enc = ChunkEncoderFactory.optimalInstance(length);
      byte[] result = encode(enc, data, offset, length);
      enc.close();
      return result;
   }

   public static byte[] safeEncode(byte[] data, int offset, int length) {
      ChunkEncoder enc = ChunkEncoderFactory.safeInstance(length);
      byte[] result = encode(enc, data, offset, length);
      enc.close();
      return result;
   }

   public static byte[] encode(byte[] data, int offset, int length, BufferRecycler bufferRecycler) {
      ChunkEncoder enc = ChunkEncoderFactory.optimalInstance(length, bufferRecycler);
      byte[] result = encode(enc, data, offset, length);
      enc.close();
      return result;
   }

   public static byte[] safeEncode(byte[] data, int offset, int length, BufferRecycler bufferRecycler) {
      ChunkEncoder enc = ChunkEncoderFactory.safeInstance(length, bufferRecycler);
      byte[] result = encode(enc, data, offset, length);
      enc.close();
      return result;
   }

   public static byte[] encode(ChunkEncoder enc, byte[] data, int length) {
      return encode(enc, data, 0, length);
   }

   public static byte[] encode(ChunkEncoder enc, byte[] data, int offset, int length) {
      int chunkLen = Math.min(65535, length);
      LZFChunk first = enc.encodeChunk(data, offset, chunkLen);
      int left = length - chunkLen;
      if (left < 1) {
         return first.getData();
      } else {
         int resultBytes = first.length();
         offset += chunkLen;
         LZFChunk last = first;

         do {
            chunkLen = Math.min(left, 65535);
            LZFChunk chunk = enc.encodeChunk(data, offset, chunkLen);
            offset += chunkLen;
            left -= chunkLen;
            resultBytes += chunk.length();
            last.setNext(chunk);
            last = chunk;
         } while(left > 0);

         byte[] result = new byte[resultBytes];

         for(int ptr = 0; first != null; first = first.next()) {
            ptr = first.copyTo(result, ptr);
         }

         return result;
      }
   }

   public static int appendEncoded(byte[] input, int inputPtr, int inputLength, byte[] outputBuffer, int outputPtr) {
      ChunkEncoder enc = ChunkEncoderFactory.optimalNonAllocatingInstance(inputLength);
      int len = appendEncoded(enc, input, inputPtr, inputLength, outputBuffer, outputPtr);
      enc.close();
      return len;
   }

   public static int safeAppendEncoded(byte[] input, int inputPtr, int inputLength, byte[] outputBuffer, int outputPtr) {
      ChunkEncoder enc = ChunkEncoderFactory.safeNonAllocatingInstance(inputLength);
      int len = appendEncoded(enc, input, inputPtr, inputLength, outputBuffer, outputPtr);
      enc.close();
      return len;
   }

   public static int appendEncoded(byte[] input, int inputPtr, int inputLength, byte[] outputBuffer, int outputPtr, BufferRecycler bufferRecycler) {
      ChunkEncoder enc = ChunkEncoderFactory.optimalNonAllocatingInstance(inputLength, bufferRecycler);
      int len = appendEncoded(enc, input, inputPtr, inputLength, outputBuffer, outputPtr);
      enc.close();
      return len;
   }

   public static int safeAppendEncoded(byte[] input, int inputPtr, int inputLength, byte[] outputBuffer, int outputPtr, BufferRecycler bufferRecycler) {
      ChunkEncoder enc = ChunkEncoderFactory.safeNonAllocatingInstance(inputLength, bufferRecycler);
      int len = appendEncoded(enc, input, inputPtr, inputLength, outputBuffer, outputPtr);
      enc.close();
      return len;
   }

   public static int appendEncoded(ChunkEncoder enc, byte[] input, int inputPtr, int inputLength, byte[] outputBuffer, int outputPtr) {
      int chunkLen = Math.min(65535, inputLength);
      outputPtr = enc.appendEncodedChunk(input, inputPtr, chunkLen, outputBuffer, outputPtr);
      int left = inputLength - chunkLen;
      if (left < 1) {
         return outputPtr;
      } else {
         inputPtr += chunkLen;

         do {
            chunkLen = Math.min(left, 65535);
            outputPtr = enc.appendEncodedChunk(input, inputPtr, chunkLen, outputBuffer, outputPtr);
            inputPtr += chunkLen;
            left -= chunkLen;
         } while(left > 0);

         return outputPtr;
      }
   }
}
