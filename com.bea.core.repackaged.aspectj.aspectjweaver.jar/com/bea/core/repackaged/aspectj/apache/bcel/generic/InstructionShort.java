package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class InstructionShort extends Instruction {
   private final short value;

   public InstructionShort(short opcode, short value) {
      super(opcode);
      this.value = value;
   }

   public void dump(DataOutputStream out) throws IOException {
      out.writeByte(this.opcode);
      out.writeShort(this.value);
   }

   public String toString(boolean verbose) {
      return super.toString(verbose) + " " + this.value;
   }

   public boolean equals(Object other) {
      if (!(other instanceof InstructionShort)) {
         return false;
      } else {
         InstructionShort o = (InstructionShort)other;
         return o.opcode == this.opcode && o.value == this.value;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.value;
   }
}
