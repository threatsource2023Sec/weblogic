package org.apache.openjpa.persistence;

public enum QueryOperationType {
   SELECT(1),
   DELETE(2),
   UPDATE(3);

   private final int queryOperationConstant;

   private QueryOperationType(int value) {
      this.queryOperationConstant = value;
   }

   int toKernelConstant() {
      return this.queryOperationConstant;
   }

   static QueryOperationType fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 1:
            return SELECT;
         case 2:
            return DELETE;
         case 3:
            return UPDATE;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
