package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EnclosingMethod extends Attribute {
   private int classIndex;
   private int methodIndex;

   public EnclosingMethod(int nameIndex, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIndex, len, dis.readUnsignedShort(), dis.readUnsignedShort(), cpool);
   }

   private EnclosingMethod(int nameIndex, int len, int classIdx, int methodIdx, ConstantPool cpool) {
      super((byte)17, nameIndex, len, cpool);
      this.classIndex = classIdx;
      this.methodIndex = methodIdx;
   }

   public void accept(ClassVisitor v) {
      v.visitEnclosingMethod(this);
   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }

   public final int getEnclosingClassIndex() {
      return this.classIndex;
   }

   public final int getEnclosingMethodIndex() {
      return this.methodIndex;
   }

   public final void setEnclosingClassIndex(int idx) {
      this.classIndex = idx;
   }

   public final void setEnclosingMethodIndex(int idx) {
      this.methodIndex = idx;
   }

   public final ConstantClass getEnclosingClass() {
      ConstantClass c = (ConstantClass)this.cpool.getConstant(this.classIndex, (byte)7);
      return c;
   }

   public final ConstantNameAndType getEnclosingMethod() {
      if (this.methodIndex == 0) {
         return null;
      } else {
         ConstantNameAndType nat = (ConstantNameAndType)this.cpool.getConstant(this.methodIndex, (byte)12);
         return nat;
      }
   }

   public final void dump(DataOutputStream file) throws IOException {
      super.dump(file);
      file.writeShort(this.classIndex);
      file.writeShort(this.methodIndex);
   }
}
