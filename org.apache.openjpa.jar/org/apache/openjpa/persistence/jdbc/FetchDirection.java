package org.apache.openjpa.persistence.jdbc;

public enum FetchDirection {
   FORWARD(1000),
   REVERSE(1001),
   UNKNOWN(1002);

   private final int resultSetConstant;

   private FetchDirection(int value) {
      this.resultSetConstant = value;
   }

   int toKernelConstant() {
      return this.resultSetConstant;
   }

   static FetchDirection fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 1000:
            return FORWARD;
         case 1001:
            return REVERSE;
         case 1002:
            return UNKNOWN;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
