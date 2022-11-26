package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Constant;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.util.StringTokenizer;

public class InvokeInstruction extends FieldOrMethod {
   public InvokeInstruction(short opcode, int index) {
      super(opcode, index);
   }

   public String toString(ConstantPool cp) {
      Constant c = cp.getConstant(this.index);
      StringTokenizer tok = new StringTokenizer(cp.constantToString(c));
      return Constants.OPCODE_NAMES[this.opcode] + " " + tok.nextToken().replace('.', '/') + tok.nextToken();
   }

   public int consumeStack(ConstantPool cpg) {
      String signature = this.getSignature(cpg);
      int sum = Type.getArgumentSizes(signature);
      if (this.opcode != 184) {
         ++sum;
      }

      return sum;
   }

   public int produceStack(ConstantPool cpg) {
      return this.getReturnType(cpg).getSize();
   }

   public Type getType(ConstantPool cpg) {
      return this.getReturnType(cpg);
   }

   public String getMethodName(ConstantPool cpg) {
      return this.getName(cpg);
   }

   public Type getReturnType(ConstantPool cpg) {
      return Type.getReturnType(this.getSignature(cpg));
   }

   public Type[] getArgumentTypes(ConstantPool cpg) {
      return Type.getArgumentTypes(this.getSignature(cpg));
   }
}
