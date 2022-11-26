package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantInvokeDynamic;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantNameAndType;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataOutputStream;
import java.io.IOException;

public final class InvokeDynamic extends InvokeInstruction {
   public InvokeDynamic(int index, int zeroes) {
      super((short)186, index);
   }

   public void dump(DataOutputStream out) throws IOException {
      out.writeByte(this.opcode);
      out.writeShort(this.index);
      out.writeShort(0);
   }

   public String toString(ConstantPool cp) {
      return super.toString(cp) + " " + this.index;
   }

   public boolean equals(Object other) {
      if (!(other instanceof InvokeDynamic)) {
         return false;
      } else {
         InvokeDynamic o = (InvokeDynamic)other;
         return o.opcode == this.opcode && o.index == this.index;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.index;
   }

   public Type getReturnType(ConstantPool cp) {
      return Type.getReturnType(this.getSignature(cp));
   }

   public Type[] getArgumentTypes(ConstantPool cp) {
      return Type.getArgumentTypes(this.getSignature(cp));
   }

   public String getSignature(ConstantPool cp) {
      if (this.signature == null) {
         ConstantInvokeDynamic cid = (ConstantInvokeDynamic)cp.getConstant(this.index);
         ConstantNameAndType cnat = (ConstantNameAndType)cp.getConstant(cid.getNameAndTypeIndex());
         this.signature = cp.getConstantUtf8(cnat.getSignatureIndex()).getValue();
      }

      return this.signature;
   }

   public String getName(ConstantPool cp) {
      if (this.name == null) {
         ConstantInvokeDynamic cid = (ConstantInvokeDynamic)cp.getConstant(this.index);
         ConstantNameAndType cnat = (ConstantNameAndType)cp.getConstant(cid.getNameAndTypeIndex());
         this.name = cp.getConstantUtf8(cnat.getNameIndex()).getValue();
      }

      return this.name;
   }

   public String getClassName(ConstantPool cp) {
      throw new IllegalStateException("there is no classname for invokedynamic");
   }
}
