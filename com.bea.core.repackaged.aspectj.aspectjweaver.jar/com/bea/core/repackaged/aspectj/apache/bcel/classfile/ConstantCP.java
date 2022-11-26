package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ConstantCP extends Constant {
   protected int classIndex;
   protected int nameAndTypeIndex;

   ConstantCP(byte tag, DataInputStream file) throws IOException {
      this(tag, file.readUnsignedShort(), file.readUnsignedShort());
   }

   protected ConstantCP(byte tag, int classIndex, int nameAndTypeIndex) {
      super(tag);
      this.classIndex = classIndex;
      this.nameAndTypeIndex = nameAndTypeIndex;
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeShort(this.classIndex);
      file.writeShort(this.nameAndTypeIndex);
   }

   public final int getClassIndex() {
      return this.classIndex;
   }

   public final int getNameAndTypeIndex() {
      return this.nameAndTypeIndex;
   }

   public String getClass(ConstantPool cp) {
      return cp.constantToString(this.classIndex, (byte)7);
   }

   public final String toString() {
      return super.toString() + "(classIndex = " + this.classIndex + ", nameAndTypeIndex = " + this.nameAndTypeIndex + ")";
   }
}
