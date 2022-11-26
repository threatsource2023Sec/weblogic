package org.apache.openjpa.persistence;

public enum RestoreStateType {
   NONE(0),
   IMMUTABLE(1),
   ALL(2);

   private final int restoreStateConstant;

   private RestoreStateType(int value) {
      this.restoreStateConstant = value;
   }

   int toKernelConstant() {
      return this.restoreStateConstant;
   }

   static RestoreStateType fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 0:
            return NONE;
         case 1:
            return IMMUTABLE;
         case 2:
            return ALL;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
