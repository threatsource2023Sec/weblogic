package org.apache.openjpa.persistence;

public enum ConnectionRetainMode {
   ON_DEMAND(0),
   TRANSACTION(1),
   ALWAYS(2);

   private final int connectionRetainConstant;

   private ConnectionRetainMode(int value) {
      this.connectionRetainConstant = value;
   }

   int toKernelConstant() {
      return this.connectionRetainConstant;
   }

   static ConnectionRetainMode fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 0:
            return ON_DEMAND;
         case 1:
            return TRANSACTION;
         case 2:
            return ALWAYS;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
