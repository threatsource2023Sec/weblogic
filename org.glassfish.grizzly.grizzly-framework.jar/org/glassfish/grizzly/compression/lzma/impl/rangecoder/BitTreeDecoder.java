package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

import java.io.IOException;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;

public class BitTreeDecoder {
   final short[] Models;
   final int NumBitLevels;
   int decodeMethodState;
   int m;
   int bitIndex;
   int reverseDecodeMethodState;
   int symbol;

   public BitTreeDecoder(int numBitLevels) {
      this.NumBitLevels = numBitLevels;
      this.Models = new short[1 << numBitLevels];
   }

   public void init() {
      this.decodeMethodState = 0;
      this.reverseDecodeMethodState = 0;
      RangeDecoder.initBitModels(this.Models);
   }

   public boolean decode(LZMADecoder.LZMAInputState decodeState, RangeDecoder rangeDecoder) throws IOException {
      while(true) {
         switch (this.decodeMethodState) {
            case 0:
               this.m = 1;
               this.bitIndex = this.NumBitLevels;
               this.decodeMethodState = 1;
            case 1:
               if (this.bitIndex == 0) {
                  this.decodeMethodState = 3;
                  break;
               } else {
                  this.decodeMethodState = 2;
               }
            case 2:
               if (!rangeDecoder.decodeBit(decodeState, this.Models, this.m)) {
                  return false;
               }

               this.m = (this.m << 1) + decodeState.lastMethodResult;
               --this.bitIndex;
               this.decodeMethodState = 1;
               break;
            case 3:
               decodeState.lastMethodResult = this.m - (1 << this.NumBitLevels);
               this.decodeMethodState = 0;
               return true;
         }
      }
   }

   public boolean reverseDecode(LZMADecoder.LZMAInputState decodeState, RangeDecoder rangeDecoder) throws IOException {
      while(true) {
         switch (this.reverseDecodeMethodState) {
            case 0:
               this.m = 1;
               this.symbol = 0;
               this.bitIndex = 0;
               this.reverseDecodeMethodState = 1;
            case 1:
               if (this.bitIndex >= this.NumBitLevels) {
                  this.reverseDecodeMethodState = 3;
                  break;
               } else {
                  this.reverseDecodeMethodState = 2;
               }
            case 2:
               if (!rangeDecoder.decodeBit(decodeState, this.Models, this.m)) {
                  return false;
               }

               int bit = decodeState.lastMethodResult;
               this.m <<= 1;
               this.m += bit;
               this.symbol |= bit << this.bitIndex;
               ++this.bitIndex;
               this.reverseDecodeMethodState = 1;
               break;
            case 3:
               decodeState.lastMethodResult = this.symbol;
               this.reverseDecodeMethodState = 0;
               return true;
         }
      }
   }

   public static boolean reverseDecode(LZMADecoder.LZMAInputState decodeState, short[] Models, int startIndex, RangeDecoder rangeDecoder, int NumBitLevels) throws IOException {
      while(true) {
         switch (decodeState.staticReverseDecodeMethodState) {
            case 0:
               decodeState.staticM = 1;
               decodeState.staticSymbol = 0;
               decodeState.staticBitIndex = 0;
               decodeState.staticReverseDecodeMethodState = 1;
            case 1:
               if (decodeState.staticBitIndex >= NumBitLevels) {
                  decodeState.staticReverseDecodeMethodState = 3;
                  break;
               } else {
                  decodeState.staticReverseDecodeMethodState = 2;
               }
            case 2:
               if (!rangeDecoder.decodeBit(decodeState, Models, startIndex + decodeState.staticM)) {
                  return false;
               }

               int bit = decodeState.lastMethodResult;
               decodeState.staticM <<= 1;
               decodeState.staticM += bit;
               decodeState.staticSymbol |= bit << decodeState.staticBitIndex;
               ++decodeState.staticBitIndex;
               decodeState.staticReverseDecodeMethodState = 1;
               break;
            case 3:
               decodeState.lastMethodResult = decodeState.staticSymbol;
               decodeState.staticReverseDecodeMethodState = 0;
               return true;
         }
      }
   }
}
