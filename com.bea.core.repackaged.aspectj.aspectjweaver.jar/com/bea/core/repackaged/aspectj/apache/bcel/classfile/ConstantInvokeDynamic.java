package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantInvokeDynamic extends Constant {
   private final int bootstrapMethodAttrIndex;
   private final int nameAndTypeIndex;

   ConstantInvokeDynamic(DataInputStream file) throws IOException {
      this(file.readUnsignedShort(), file.readUnsignedShort());
   }

   public ConstantInvokeDynamic(int readUnsignedShort, int nameAndTypeIndex) {
      super((byte)18);
      this.bootstrapMethodAttrIndex = readUnsignedShort;
      this.nameAndTypeIndex = nameAndTypeIndex;
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeShort(this.bootstrapMethodAttrIndex);
      file.writeShort(this.nameAndTypeIndex);
   }

   public final int getNameAndTypeIndex() {
      return this.nameAndTypeIndex;
   }

   public final int getBootstrapMethodAttrIndex() {
      return this.bootstrapMethodAttrIndex;
   }

   public final String toString() {
      return super.toString() + "(bootstrapMethodAttrIndex=" + this.bootstrapMethodAttrIndex + ",nameAndTypeIndex=" + this.nameAndTypeIndex + ")";
   }

   public String getValue() {
      return this.toString();
   }

   public void accept(ClassVisitor v) {
      v.visitConstantInvokeDynamic(this);
   }
}
