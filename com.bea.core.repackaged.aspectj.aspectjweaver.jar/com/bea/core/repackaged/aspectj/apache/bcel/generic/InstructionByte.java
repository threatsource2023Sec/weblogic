package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class InstructionByte extends Instruction {
   private final byte theByte;

   public InstructionByte(short opcode, byte b) {
      super(opcode);
      this.theByte = b;
   }

   public void dump(DataOutputStream out) throws IOException {
      out.writeByte(this.opcode);
      out.writeByte(this.theByte);
   }

   public String toString(boolean verbose) {
      return super.toString(verbose) + " " + this.theByte;
   }

   public final byte getTypecode() {
      return this.theByte;
   }

   public final Type getType() {
      return new ArrayType(BasicType.getType(this.theByte), 1);
   }

   public boolean equals(Object other) {
      if (!(other instanceof InstructionByte)) {
         return false;
      } else {
         InstructionByte o = (InstructionByte)other;
         return o.opcode == this.opcode && o.theByte == this.theByte;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.theByte;
   }
}
