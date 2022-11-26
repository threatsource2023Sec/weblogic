package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Constant;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantDouble;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantFloat;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantInteger;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantLong;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantString;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantUtf8;
import java.io.DataOutputStream;
import java.io.IOException;

public class InstructionCP extends Instruction {
   protected int index;

   public InstructionCP(short opcode, int index) {
      super(opcode);
      this.index = index;
   }

   public void dump(DataOutputStream out) throws IOException {
      if (this.opcode == 19 && this.index < 256) {
         out.writeByte(18);
         out.writeByte(this.index);
      } else {
         out.writeByte(this.opcode);
         if (Constants.iLen[this.opcode] == 2) {
            if (this.index > 255) {
               throw new IllegalStateException();
            }

            out.writeByte(this.index);
         } else {
            out.writeShort(this.index);
         }
      }

   }

   public int getLength() {
      return this.opcode == 19 && this.index < 256 ? 2 : super.getLength();
   }

   public String toString(boolean verbose) {
      return super.toString(verbose) + " " + this.index;
   }

   public String toString(ConstantPool cp) {
      Constant c = cp.getConstant(this.index);
      String str = cp.constantToString(c);
      if (c instanceof ConstantClass) {
         str = str.replace('.', '/');
      }

      return Constants.OPCODE_NAMES[this.opcode] + " " + str;
   }

   public final int getIndex() {
      return this.index;
   }

   public void setIndex(int index) {
      this.index = index;
      if (this.index > 255 && this.opcode == 18) {
         this.opcode = 19;
      }

   }

   public Type getType(ConstantPool cpg) {
      switch (cpg.getConstant(this.index).getTag()) {
         case 3:
            return Type.INT;
         case 4:
            return Type.FLOAT;
         case 5:
            return Type.LONG;
         case 6:
            return Type.DOUBLE;
         case 7:
            String name = cpg.getConstantString_CONSTANTClass(this.index);
            if (!name.startsWith("[")) {
               StringBuffer sb = new StringBuffer();
               sb.append("L").append(name).append(";");
               return Type.getType(sb.toString());
            }

            return Type.getType(name);
         case 8:
            return Type.STRING;
         default:
            throw new RuntimeException("Unknown or invalid constant type at " + this.index);
      }
   }

   public Object getValue(ConstantPool constantPool) {
      Constant constant = constantPool.getConstant(this.index);
      switch (constant.getTag()) {
         case 3:
            return ((ConstantInteger)constant).getValue();
         case 4:
            return ((ConstantFloat)constant).getValue();
         case 5:
            return ((ConstantLong)constant).getValue();
         case 6:
            return ((ConstantDouble)constant).getValue();
         case 7:
         default:
            throw new RuntimeException("Unknown or invalid constant type at " + this.index);
         case 8:
            int i = ((ConstantString)constant).getStringIndex();
            constant = constantPool.getConstant(i);
            return ((ConstantUtf8)constant).getValue();
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof InstructionCP)) {
         return false;
      } else {
         InstructionCP o = (InstructionCP)other;
         return o.opcode == this.opcode && o.index == this.index;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.index;
   }
}
