package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;

public class RangeDecoder {
   static final int kTopMask = -16777216;
   static final int kNumBitModelTotalBits = 11;
   static final int kBitModelTotal = 2048;
   static final int kNumMoveBits = 5;
   int Range;
   int Code;
   Buffer inputBuffer;
   int newBound;
   int decodeBitState;
   int decodeDirectBitsState;
   int decodeDirectBitsResult;
   int decodeDirectBitsI;

   public final void initFromState(LZMADecoder.LZMAInputState decoderState) {
      this.inputBuffer = decoderState.getSrc();
   }

   public final void releaseBuffer() {
      this.inputBuffer = null;
   }

   public final void init() throws IOException {
      this.Code = 0;
      this.Range = -1;
      this.decodeBitState = 0;
      this.decodeDirectBitsState = 0;

      for(int i = 0; i < 5; ++i) {
         this.Code = this.Code << 8 | this.inputBuffer.get() & 255;
      }

   }

   public final boolean decodeDirectBits(LZMADecoder.LZMAInputState decodeState, int numTotalBits) throws IOException {
      while(true) {
         switch (this.decodeDirectBitsState) {
            case 0:
               this.decodeDirectBitsResult = 0;
               this.decodeDirectBitsI = numTotalBits;
               this.decodeDirectBitsState = 1;
            case 1:
               if (this.decodeDirectBitsI == 0) {
                  this.decodeDirectBitsState = 4;
                  break;
               }

               this.Range >>>= 1;
               int t = this.Code - this.Range >>> 31;
               this.Code -= this.Range & t - 1;
               this.decodeDirectBitsResult = this.decodeDirectBitsResult << 1 | 1 - t;
               boolean condition = (this.Range & -16777216) == 0;
               this.decodeDirectBitsState = condition ? 2 : 3;
               break;
            case 2:
               if (!this.inputBuffer.hasRemaining()) {
                  return false;
               }

               this.Code = this.Code << 8 | this.inputBuffer.get() & 255;
               this.Range <<= 8;
            case 3:
               --this.decodeDirectBitsI;
               this.decodeDirectBitsState = 1;
               break;
            case 4:
               decodeState.lastMethodResult = this.decodeDirectBitsResult;
               this.decodeDirectBitsState = 0;
               return true;
         }
      }
   }

   public boolean decodeBit(LZMADecoder.LZMAInputState decodeState, short[] probs, int index) throws IOException {
      while(true) {
         short prob;
         boolean condition;
         switch (this.decodeBitState) {
            case 0:
               prob = probs[index];
               this.newBound = (this.Range >>> 11) * prob;
               condition = (this.Code ^ Integer.MIN_VALUE) < (this.newBound ^ Integer.MIN_VALUE);
               this.decodeBitState = condition ? 1 : 4;
               break;
            case 1:
               prob = probs[index];
               this.Range = this.newBound;
               probs[index] = (short)(prob + (2048 - prob >>> 5));
               condition = (this.Range & -16777216) == 0;
               this.decodeBitState = condition ? 2 : 3;
               break;
            case 2:
               if (!this.inputBuffer.hasRemaining()) {
                  return false;
               }

               this.Code = this.Code << 8 | this.inputBuffer.get() & 255;
               this.Range <<= 8;
            case 3:
               decodeState.lastMethodResult = 0;
               this.decodeBitState = 0;
               return true;
            case 4:
               prob = probs[index];
               this.Range -= this.newBound;
               this.Code -= this.newBound;
               probs[index] = (short)(prob - (prob >>> 5));
               condition = (this.Range & -16777216) == 0;
               this.decodeBitState = condition ? 5 : 6;
               break;
            case 5:
               if (!this.inputBuffer.hasRemaining()) {
                  return false;
               }

               this.Code = this.Code << 8 | this.inputBuffer.get() & 255;
               this.Range <<= 8;
            case 6:
               decodeState.lastMethodResult = 1;
               this.decodeBitState = 0;
               return true;
         }
      }
   }

   public static void initBitModels(short[] probs) {
      for(int i = 0; i < probs.length; ++i) {
         probs[i] = 1024;
      }

   }
}
