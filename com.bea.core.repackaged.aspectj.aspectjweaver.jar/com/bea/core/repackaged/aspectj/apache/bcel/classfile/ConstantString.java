package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantString extends Constant {
   private int stringIndex;

   ConstantString(DataInputStream file) throws IOException {
      this(file.readUnsignedShort());
   }

   public ConstantString(int stringIndex) {
      super((byte)8);
      this.stringIndex = stringIndex;
   }

   public void accept(ClassVisitor v) {
      v.visitConstantString(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeShort(this.stringIndex);
   }

   public Integer getValue() {
      return this.stringIndex;
   }

   public final int getStringIndex() {
      return this.stringIndex;
   }

   public final String toString() {
      return super.toString() + "(string_index = " + this.stringIndex + ")";
   }

   public String getString(ConstantPool cpool) {
      Constant c = cpool.getConstant(this.stringIndex, (byte)1);
      return ((ConstantUtf8)c).getValue();
   }
}
