package org.apache.openjpa.persistence.jdbc;

public enum JoinSyntax {
   SQL92(0),
   TRADITIONAL(1),
   DATABASE(2);

   private final int joinSyntaxConstant;

   private JoinSyntax(int value) {
      this.joinSyntaxConstant = value;
   }

   int toKernelConstant() {
      return this.joinSyntaxConstant;
   }

   static JoinSyntax fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 0:
            return SQL92;
         case 1:
            return TRADITIONAL;
         case 2:
            return DATABASE;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
