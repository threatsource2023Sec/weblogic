package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public final class ConstantMethodref extends ConstantCP {
   ConstantMethodref(DataInputStream file) throws IOException {
      super((byte)10, file);
   }

   public ConstantMethodref(int class_index, int name_and_type_index) {
      super((byte)10, class_index, name_and_type_index);
   }

   public void accept(ClassVisitor v) {
      v.visitConstantMethodref(this);
   }

   public String getValue() {
      return this.toString();
   }
}
