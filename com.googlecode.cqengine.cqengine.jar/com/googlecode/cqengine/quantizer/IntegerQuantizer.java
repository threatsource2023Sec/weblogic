package com.googlecode.cqengine.quantizer;

public class IntegerQuantizer {
   public static Quantizer withCompressionFactor(int compressionFactor) {
      return new CompressingQuantizer(compressionFactor);
   }

   IntegerQuantizer() {
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

      public Integer getQuantizedValue(Integer attributeValue) {
         return attributeValue / this.compressionFactor * this.compressionFactor;
      }
   }
}
