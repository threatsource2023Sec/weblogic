package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantFloat extends Constant implements SimpleConstant {
   private float floatValue;

   public ConstantFloat(float floatValue) {
      super((byte)4);
      this.floatValue = floatValue;
   }

   ConstantFloat(DataInputStream file) throws IOException {
      this(file.readFloat());
   }

   public void accept(ClassVisitor v) {
      v.visitConstantFloat(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeFloat(this.floatValue);
   }

   public final Float getValue() {
      return this.floatValue;
   }

   public final String getStringValue() {
      return Float.toString(this.floatValue);
   }

   public final String toString() {
      return super.toString() + "(bytes = " + this.floatValue + ")";
   }
}
