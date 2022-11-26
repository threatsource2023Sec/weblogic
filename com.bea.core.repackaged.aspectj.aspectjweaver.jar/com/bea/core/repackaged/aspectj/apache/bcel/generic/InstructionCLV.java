package com.bea.core.repackaged.aspectj.apache.bcel.generic;

public class InstructionCLV extends InstructionLV {
   public InstructionCLV(short opcode) {
      super(opcode);
   }

   public InstructionCLV(short opcode, int localVariableIndex) {
      super(opcode, localVariableIndex);
   }

   public void setIndex(int localVariableIndex) {
      if (localVariableIndex != this.getIndex()) {
         throw new ClassGenException("Do not attempt to modify the index to '" + localVariableIndex + "' for this constant instruction: " + this);
      }
   }

   public boolean canSetIndex() {
      return false;
   }
}
