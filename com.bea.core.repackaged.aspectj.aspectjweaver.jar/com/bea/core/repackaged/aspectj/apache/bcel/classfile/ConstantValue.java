package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantValue extends Attribute {
   private int constantvalue_index;

   ConstantValue(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
      this(name_index, length, file.readUnsignedShort(), constant_pool);
   }

   public ConstantValue(int name_index, int length, int constantvalue_index, ConstantPool constant_pool) {
      super((byte)1, name_index, length, constant_pool);
      this.constantvalue_index = constantvalue_index;
   }

   public void accept(ClassVisitor v) {
      v.visitConstantValue(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      super.dump(file);
      file.writeShort(this.constantvalue_index);
   }

   public final int getConstantValueIndex() {
      return this.constantvalue_index;
   }

   public final String toString() {
      Constant c = this.cpool.getConstant(this.constantvalue_index);
      String buf;
      switch (c.getTag()) {
         case 3:
            buf = "" + ((ConstantInteger)c).getValue();
            break;
         case 4:
            buf = "" + ((ConstantFloat)c).getValue();
            break;
         case 5:
            buf = "" + ((ConstantLong)c).getValue();
            break;
         case 6:
            buf = "" + ((ConstantDouble)c).getValue();
            break;
         case 7:
         default:
            throw new IllegalStateException("Type of ConstValue invalid: " + c);
         case 8:
            int i = ((ConstantString)c).getStringIndex();
            c = this.cpool.getConstant(i, (byte)1);
            buf = "\"" + Utility.convertString(((ConstantUtf8)c).getValue()) + "\"";
      }

      return buf;
   }
}
