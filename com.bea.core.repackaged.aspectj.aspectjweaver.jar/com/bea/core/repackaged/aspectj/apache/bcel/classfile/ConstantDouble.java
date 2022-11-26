package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantDouble extends Constant implements SimpleConstant {
   private double value;

   public ConstantDouble(double value) {
      super((byte)6);
      this.value = value;
   }

   ConstantDouble(DataInputStream file) throws IOException {
      this(file.readDouble());
   }

   public void accept(ClassVisitor v) {
      v.visitConstantDouble(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeDouble(this.value);
   }

   public final String toString() {
      return super.toString() + "(bytes = " + this.value + ")";
   }

   public Double getValue() {
      return this.value;
   }

   public String getStringValue() {
      return Double.toString(this.value);
   }
}
