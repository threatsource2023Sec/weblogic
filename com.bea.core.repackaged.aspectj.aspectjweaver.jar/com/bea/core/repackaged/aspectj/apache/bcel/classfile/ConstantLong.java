package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantLong extends Constant implements SimpleConstant {
   private long longValue;

   public ConstantLong(long longValue) {
      super((byte)5);
      this.longValue = longValue;
   }

   ConstantLong(DataInputStream file) throws IOException {
      this(file.readLong());
   }

   public void accept(ClassVisitor v) {
      v.visitConstantLong(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeLong(this.longValue);
   }

   public final Long getValue() {
      return this.longValue;
   }

   public final String getStringValue() {
      return Long.toString(this.longValue);
   }

   public final String toString() {
      return super.toString() + "(longValue = " + this.longValue + ")";
   }
}
