package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataOutputStream;
import java.io.IOException;

public class RET extends Instruction {
   private boolean wide;
   private int index;

   public RET(int index, boolean wide) {
      super((short)169);
      this.index = index;
      this.wide = wide;
   }

   public void dump(DataOutputStream out) throws IOException {
      if (this.wide) {
         out.writeByte(196);
      }

      out.writeByte(this.opcode);
      if (this.wide) {
         out.writeShort(this.index);
      } else {
         out.writeByte(this.index);
      }

   }

   public int getLength() {
      return this.wide ? 4 : 2;
   }

   public final int getIndex() {
      return this.index;
   }

   public final void setIndex(int index) {
      this.index = index;
      this.wide = index > 255;
   }

   public String toString(boolean verbose) {
      return super.toString(verbose) + " " + this.index;
   }

   public Type getType(ConstantPool cp) {
      return ReturnaddressType.NO_TARGET;
   }

   public boolean equals(Object other) {
      if (!(other instanceof RET)) {
         return false;
      } else {
         RET o = (RET)other;
         return o.opcode == this.opcode && o.index == this.index;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.index;
   }
}
