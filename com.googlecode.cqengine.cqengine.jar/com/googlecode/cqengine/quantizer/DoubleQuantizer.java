package com.googlecode.cqengine.quantizer;

public class DoubleQuantizer {
   DoubleQuantizer() {
   }

   public static Quantizer withCompressionFactor(int compressionFactor) {
      return (Quantizer)(compressionFactor < 2 ? new TruncatingQuantizer() : new TruncatingAndCompressingQuantizer(compressionFactor));
   }

   static class TruncatingQuantizer implements Quantizer {
      public Double getQuantizedValue(Double attributeValue) {
         return (double)((long)attributeValue);
      }
   }

   static class TruncatingAndCompressingQuantizer implements Quantizer {
      private final int compressionFactor;

      public TruncatingAndCompressingQuantizer(int compressionFactor) {
         if (compressionFactor < 2) {
            throw new IllegalArgumentException("Invalid compression factor, must be >= 2: " + compressionFactor);
         } else {
            this.compressionFactor = compressionFactor;
         }
      }

      public Double getQuantizedValue(Double attributeValue) {
         return (double)(attributeValue.longValue() / (long)this.compressionFactor * (long)this.compressionFactor);
      }
   }
}
