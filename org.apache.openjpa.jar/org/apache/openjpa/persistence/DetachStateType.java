package org.apache.openjpa.persistence;

public enum DetachStateType {
   FETCH_GROUPS(0),
   LOADED(1),
   ALL(2);

   private final int detachStateConstant;

   private DetachStateType(int value) {
      this.detachStateConstant = value;
   }

   int toKernelConstant() {
      return this.detachStateConstant;
   }

   static DetachStateType fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 0:
            return FETCH_GROUPS;
         case 1:
            return LOADED;
         case 2:
            return ALL;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
