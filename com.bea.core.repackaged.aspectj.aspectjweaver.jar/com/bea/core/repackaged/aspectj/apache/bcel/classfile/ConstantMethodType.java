package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantMethodType extends Constant {
   private int descriptorIndex;

   ConstantMethodType(DataInputStream file) throws IOException {
      this(file.readUnsignedShort());
   }

   public ConstantMethodType(int descriptorIndex) {
      super((byte)16);
      this.descriptorIndex = descriptorIndex;
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeShort(this.descriptorIndex);
   }

   public final int getDescriptorIndex() {
      return this.descriptorIndex;
   }

   public final String toString() {
      return super.toString() + "(descriptorIndex=" + this.descriptorIndex + ")";
   }

   public String getValue() {
      return this.toString();
   }

   public void accept(ClassVisitor v) {
      v.visitConstantMethodType(this);
   }
}
