package org.apache.openjpa.persistence.jdbc;

public enum LRSSizeAlgorithm {
   UNKNOWN(0),
   LAST(1),
   QUERY(2);

   private final int lrsConstant;

   private LRSSizeAlgorithm(int value) {
      this.lrsConstant = value;
   }

   int toKernelConstant() {
      return this.lrsConstant;
   }

   static LRSSizeAlgorithm fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 0:
            return UNKNOWN;
         case 1:
            return LAST;
         case 2:
            return QUERY;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
