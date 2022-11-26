package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class IINC extends InstructionLV {
   private int c;

   public IINC(int n, int c, boolean w) {
      super((short)132, n);
      this.c = c;
   }

   private boolean wide() {
      return this.lvar > 255 || Math.abs(this.c) > 127;
   }

   public void dump(DataOutputStream out) throws IOException {
      if (this.wide()) {
         out.writeByte(196);
         out.writeByte(this.opcode);
         out.writeShort(this.lvar);
         out.writeShort(this.c);
      } else {
         out.writeByte(this.opcode);
         out.writeByte(this.lvar);
         out.writeByte(this.c);
      }

   }

   public int getLength() {
      return this.wide() ? 6 : 3;
   }

   public String toString(boolean verbose) {
      return super.toString(verbose) + " " + this.c;
   }

   public final int getIncrement() {
      return this.c;
   }

   public boolean equals(Object other) {
      if (!(other instanceof IINC)) {
         return false;
      } else {
         IINC o = (IINC)other;
         return o.lvar == this.lvar && o.c == this.c;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.lvar * (this.c + 17);
   }
}
