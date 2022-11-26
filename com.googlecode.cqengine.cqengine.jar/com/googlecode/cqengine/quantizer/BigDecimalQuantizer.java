package com.googlecode.cqengine.quantizer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalQuantizer {
   BigDecimalQuantizer() {
   }

   public static Quantizer withCompressionFactor(int compressionFactor) {
      return (Quantizer)(compressionFactor < 2 ? new TruncatingQuantizer() : new TruncatingAndCompressingQuantizer(compressionFactor));
   }

   static class TruncatingQuantizer implements Quantizer {
      public BigDecimal getQuantizedValue(BigDecimal attributeValue) {
         return attributeValue.setScale(0, RoundingMode.DOWN);
      }
   }

   static class TruncatingAndCompressingQuantizer implements Quantizer {
      private final BigDecimal compressionFactor;

      public TruncatingAndCompressingQuantizer(int compressionFactor) {
         if (compressionFactor < 2) {
            throw new IllegalArgumentException("Invalid compression factor, must be >= 2: " + compressionFactor);
         } else {
            this.compressionFactor = BigDecimal.valueOf((long)compressionFactor);
         }
      }

      public BigDecimal getQuantizedValue(BigDecimal attributeValue) {
         return attributeValue.divideToIntegralValue(this.compressionFactor).multiply(this.compressionFactor).setScale(0, RoundingMode.DOWN);
      }
   }
}
