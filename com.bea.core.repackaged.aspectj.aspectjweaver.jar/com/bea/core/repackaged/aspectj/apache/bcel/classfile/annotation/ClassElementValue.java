package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantUtf8;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClassElementValue extends ElementValue {
   private int idx;

   protected ClassElementValue(int typeIdx, ConstantPool cpool) {
      super(99, cpool);
      this.idx = typeIdx;
   }

   public ClassElementValue(ObjectType t, ConstantPool cpool) {
      super(99, cpool);
      this.idx = cpool.addUtf8(t.getSignature());
   }

   public ClassElementValue(ClassElementValue value, ConstantPool cpool, boolean copyPoolEntries) {
      super(99, cpool);
      if (copyPoolEntries) {
         this.idx = cpool.addUtf8(value.getClassString());
      } else {
         this.idx = value.getIndex();
      }

   }

   public int getIndex() {
      return this.idx;
   }

   public String getClassString() {
      ConstantUtf8 cu8 = (ConstantUtf8)this.getConstantPool().getConstant(this.idx);
      return cu8.getValue();
   }

   public String stringifyValue() {
      return this.getClassString();
   }

   public void dump(DataOutputStream dos) throws IOException {
      dos.writeByte(this.type);
      dos.writeShort(this.idx);
   }
}
