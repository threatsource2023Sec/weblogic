package org.apache.openjpa.persistence;

public enum AutoClearType {
   DATASTORE(0),
   ALL(1);

   private final int autoClearConstant;

   private AutoClearType(int value) {
      this.autoClearConstant = value;
   }

   int toKernelConstant() {
      return this.autoClearConstant;
   }

   static AutoClearType fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 0:
            return DATASTORE;
         case 1:
            return ALL;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
