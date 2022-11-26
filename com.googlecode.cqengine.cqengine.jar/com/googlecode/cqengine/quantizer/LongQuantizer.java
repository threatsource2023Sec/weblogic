package com.googlecode.cqengine.quantizer;

public class LongQuantizer {
   public static Quantizer withCompressionFactor(int compressionFactor) {
      return new CompressingQuantizer(compressionFactor);
   }

   LongQuantizer() {
   }

   static class CompressingQuantizer implements Quantizer {
      private final int compressionFactor;

      public CompressingQuantizer(int compressionFactor) {
         if (compressionFactor < 2) {
            throw new IllegalArgumentException("Invalid compression factor, must be >= 2: " + compressionFactor);
         } else {
            this.compressionFactor = compressionFactor;
         }
      }

      public Long getQuantizedValue(Long attributeValue) {
         return attributeValue / (long)this.compressionFactor * (long)this.compressionFactor;
      }
   }
}
