package org.apache.openjpa.persistence.jdbc;

public enum FetchMode {
   NONE(0),
   JOIN(1),
   PARALLEL(2);

   private final int eagerFetchConstant;

   private FetchMode(int value) {
      this.eagerFetchConstant = value;
   }

   int toKernelConstant() {
      return this.eagerFetchConstant;
   }

   static FetchMode fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 0:
            return NONE;
         case 1:
            return JOIN;
         case 2:
            return PARALLEL;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
