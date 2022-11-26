package com.googlecode.cqengine.quantizer;

import java.math.BigInteger;

public class BigIntegerQuantizer {
   BigIntegerQuantizer() {
   }

   public static Quantizer withCompressionFactor(int compressionFactor) {
      return new CompressingQuantizer(compressionFactor);
   }

   static class CompressingQuantizer implements Quantizer {
      private final BigInteger compressionFactor;

      public CompressingQuantizer(int compressionFactor) {
         if (compressionFactor < 2) {
            throw new IllegalArgumentException("Invalid compression factor, must be >= 2: " + compressionFactor);
         } else {
            this.compressionFactor = BigInteger.valueOf((long)compressionFactor);
         }
      }

      public BigInteger getQuantizedValue(BigInteger attributeValue) {
         return attributeValue.divide(this.compressionFactor).multiply(this.compressionFactor);
      }
   }
}
