package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public final class ConstantInterfaceMethodref extends ConstantCP {
   ConstantInterfaceMethodref(DataInputStream file) throws IOException {
      super((byte)11, file);
   }

   public ConstantInterfaceMethodref(int classIndex, int nameAndTypeIndex) {
      super((byte)11, classIndex, nameAndTypeIndex);
   }

   public void accept(ClassVisitor v) {
      v.visitConstantInterfaceMethodref(this);
   }

   public String getValue() {
      return this.toString();
   }
}
