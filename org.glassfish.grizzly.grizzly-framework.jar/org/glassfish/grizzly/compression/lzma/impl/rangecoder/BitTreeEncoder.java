package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

import java.io.IOException;

public class BitTreeEncoder {
   final short[] Models;
   final int NumBitLevels;

   public BitTreeEncoder(int numBitLevels) {
      this.NumBitLevels = numBitLevels;
      this.Models = new short[1 << numBitLevels];
   }

   public void init() {
      RangeDecoder.initBitModels(this.Models);
   }

   public void encode(RangeEncoder rangeEncoder, int symbol) throws IOException {
      int m = 1;

      int bit;
      for(int bitIndex = this.NumBitLevels; bitIndex != 0; m = m << 1 | bit) {
         --bitIndex;
         bit = symbol >>> bitIndex & 1;
         rangeEncoder.encode(this.Models, m, bit);
      }

   }

   public void reverseEncode(RangeEncoder rangeEncoder, int symbol) throws IOException {
      int m = 1;

      for(int i = 0; i < this.NumBitLevels; ++i) {
         int bit = symbol & 1;
         rangeEncoder.encode(this.Models, m, bit);
         m = m << 1 | bit;
         symbol >>= 1;
      }

   }

   public int getPrice(int symbol) {
      int price = 0;
      int m = 1;

      int bit;
      for(int bitIndex = this.NumBitLevels; bitIndex != 0; m = (m << 1) + bit) {
         --bitIndex;
         bit = symbol >>> bitIndex & 1;
         price += RangeEncoder.getPrice(this.Models[m], bit);
      }

      return price;
   }

   public int reverseGetPrice(int symbol) {
      int price = 0;
      int m = 1;

      for(int i = this.NumBitLevels; i != 0; --i) {
         int bit = symbol & 1;
         symbol >>>= 1;
         price += RangeEncoder.getPrice(this.Models[m], bit);
         m = m << 1 | bit;
      }

      return price;
   }

   public static int reverseGetPrice(short[] Models, int startIndex, int NumBitLevels, int symbol) {
      int price = 0;
      int m = 1;

      for(int i = NumBitLevels; i != 0; --i) {
         int bit = symbol & 1;
         symbol >>>= 1;
         price += RangeEncoder.getPrice(Models[startIndex + m], bit);
         m = m << 1 | bit;
      }

      return price;
   }

   public static void reverseEncode(short[] Models, int startIndex, RangeEncoder rangeEncoder, int NumBitLevels, int symbol) throws IOException {
      int m = 1;

      for(int i = 0; i < NumBitLevels; ++i) {
         int bit = symbol & 1;
         rangeEncoder.encode(Models, startIndex + m, bit);
         m = m << 1 | bit;
         symbol >>= 1;
      }

   }
}
