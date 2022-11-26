package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantInteger extends Constant implements SimpleConstant {
   private int intValue;

   public ConstantInteger(int intValue) {
      super((byte)3);
      this.intValue = intValue;
   }

   ConstantInteger(DataInputStream file) throws IOException {
      this(file.readInt());
   }

   public void accept(ClassVisitor v) {
      v.visitConstantInteger(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeInt(this.intValue);
   }

   public final String toString() {
      return super.toString() + "(bytes = " + this.intValue + ")";
   }

   public Integer getValue() {
      return this.intValue;
   }

   public int getIntValue() {
      return this.intValue;
   }

   public String getStringValue() {
      return Integer.toString(this.intValue);
   }
}
