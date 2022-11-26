package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public final class ConstantFieldref extends ConstantCP {
   ConstantFieldref(DataInputStream file) throws IOException {
      super((byte)9, file);
   }

   public ConstantFieldref(int class_index, int name_and_type_index) {
      super((byte)9, class_index, name_and_type_index);
   }

   public void accept(ClassVisitor v) {
      v.visitConstantFieldref(this);
   }

   public String getValue() {
      return this.toString();
   }
}
