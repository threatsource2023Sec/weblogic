package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;

public class FieldInstruction extends FieldOrMethod {
   public FieldInstruction(short opcode, int index) {
      super(opcode, index);
   }

   public String toString(ConstantPool cp) {
      return Constants.OPCODE_NAMES[this.opcode] + " " + cp.constantToString(this.index, (byte)9);
   }

   protected int getFieldSize(ConstantPool cpg) {
      return Type.getTypeSize(this.getSignature(cpg));
   }

   public Type getType(ConstantPool cpg) {
      return this.getFieldType(cpg);
   }

   public Type getFieldType(ConstantPool cpg) {
      return Type.getType(this.getSignature(cpg));
   }

   public String getFieldName(ConstantPool cpg) {
      return this.getName(cpg);
   }

   public int produceStack(ConstantPool cpg) {
      return !this.isStackProducer() ? 0 : this.getFieldSize(cpg);
   }

   public int consumeStack(ConstantPool cpg) {
      if (!this.isStackConsumer()) {
         return 0;
      } else {
         return this.opcode == 180 ? 1 : this.getFieldSize(cpg) + (this.opcode == 181 ? 1 : 0);
      }
   }
}
