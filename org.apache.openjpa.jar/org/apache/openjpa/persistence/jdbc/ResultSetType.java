package org.apache.openjpa.persistence.jdbc;

public enum ResultSetType {
   FORWARD_ONLY(1003),
   SCROLL_INSENSITIVE(1004),
   SCROLL_SENSITIVE(1005);

   private final int resultSetConstant;

   private ResultSetType(int value) {
      this.resultSetConstant = value;
   }

   int toKernelConstant() {
      return this.resultSetConstant;
   }

   static ResultSetType fromKernelConstant(int kernelConstant) {
      switch (kernelConstant) {
         case 1003:
            return FORWARD_ONLY;
         case 1004:
            return SCROLL_INSENSITIVE;
         case 1005:
            return SCROLL_SENSITIVE;
         default:
            throw new IllegalArgumentException(kernelConstant + "");
      }
   }
}
