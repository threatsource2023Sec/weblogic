package org.jboss.classfilewriter.constpool;

public enum ConstPoolEntryType {
   CLASS(7, 1),
   FIELDREF(9, 1),
   METHODREF(10, 1),
   INTERFACE_METHODREF(11, 1),
   STRING(8, 1),
   INTEGER(3, 1),
   FLOAT(4, 1),
   LONG(5, 2),
   DOUBLE(6, 2),
   NAME_AND_TYPE(12, 1),
   UTF8(1, 1);

   private final int tag;
   private final int slots;

   private ConstPoolEntryType(int tag, int slots) {
      this.tag = tag;
      this.slots = slots;
   }

   public int getTag() {
      return this.tag;
   }

   public int getSlots() {
      return this.slots;
   }
}
