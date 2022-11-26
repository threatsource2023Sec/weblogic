package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantClass extends Constant {
   private int nameIndex;

   ConstantClass(DataInputStream file) throws IOException {
      super((byte)7);
      this.nameIndex = file.readUnsignedShort();
   }

   public ConstantClass(int nameIndex) {
      super((byte)7);
      this.nameIndex = nameIndex;
   }

   public void accept(ClassVisitor v) {
      v.visitConstantClass(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeShort(this.nameIndex);
   }

   public final int getNameIndex() {
      return this.nameIndex;
   }

   public Integer getValue() {
      return this.nameIndex;
   }

   public String getClassname(ConstantPool cpool) {
      return cpool.getConstantUtf8(this.nameIndex).getValue();
   }

   public final String toString() {
      return super.toString() + "(name_index = " + this.nameIndex + ")";
   }
}
