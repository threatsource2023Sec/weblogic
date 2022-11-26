package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.ExceptionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataOutputStream;
import java.io.IOException;

public class MULTIANEWARRAY extends InstructionCP {
   private short dimensions;

   public MULTIANEWARRAY(int index, short dimensions) {
      super((short)197, index);
      this.dimensions = dimensions;
   }

   public void dump(DataOutputStream out) throws IOException {
      out.writeByte(this.opcode);
      out.writeShort(this.index);
      out.writeByte(this.dimensions);
   }

   public final short getDimensions() {
      return this.dimensions;
   }

   public String toString(boolean verbose) {
      return super.toString(verbose) + " " + this.index + " " + this.dimensions;
   }

   public String toString(ConstantPool cp) {
      return super.toString(cp) + " " + this.dimensions;
   }

   public int consumeStack(ConstantPool cpg) {
      return this.dimensions;
   }

   public Class[] getExceptions() {
      Class[] cs = new Class[2 + ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length];
      System.arraycopy(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length);
      cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length + 1] = ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION;
      cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
      return cs;
   }

   public ObjectType getLoadClassType(ConstantPool cpg) {
      Type t = this.getType(cpg);
      if (t instanceof ArrayType) {
         t = ((ArrayType)t).getBasicType();
      }

      return t instanceof ObjectType ? (ObjectType)t : null;
   }

   public boolean equals(Object other) {
      if (!(other instanceof MULTIANEWARRAY)) {
         return false;
      } else {
         MULTIANEWARRAY o = (MULTIANEWARRAY)other;
         return o.opcode == this.opcode && o.index == this.index && o.dimensions == this.dimensions;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.index * (this.dimensions + 17);
   }
}
