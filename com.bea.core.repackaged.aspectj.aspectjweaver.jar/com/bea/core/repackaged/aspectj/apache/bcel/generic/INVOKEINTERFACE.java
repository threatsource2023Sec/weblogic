package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataOutputStream;
import java.io.IOException;

public final class INVOKEINTERFACE extends InvokeInstruction {
   private int nargs;

   public INVOKEINTERFACE(int index, int nargs, int zerobyte) {
      super((short)185, index);
      if (nargs < 1) {
         throw new ClassGenException("Number of arguments must be > 0 " + nargs);
      } else {
         this.nargs = nargs;
      }
   }

   public void dump(DataOutputStream out) throws IOException {
      out.writeByte(this.opcode);
      out.writeShort(this.index);
      out.writeByte(this.nargs);
      out.writeByte(0);
   }

   public int getCount() {
      return this.nargs;
   }

   public String toString(ConstantPool cp) {
      return super.toString(cp) + " " + this.nargs;
   }

   public int consumeStack(ConstantPool cpg) {
      return this.nargs;
   }

   public boolean equals(Object other) {
      if (!(other instanceof INVOKEINTERFACE)) {
         return false;
      } else {
         INVOKEINTERFACE o = (INVOKEINTERFACE)other;
         return o.opcode == this.opcode && o.index == this.index && o.nargs == this.nargs;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.index * (this.nargs + 17);
   }
}
