package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantUtf8;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import java.io.DataOutputStream;
import java.io.IOException;

public class EnumElementValue extends ElementValue {
   private int typeIdx;
   private int valueIdx;

   protected EnumElementValue(int typeIdx, int valueIdx, ConstantPool cpool) {
      super(101, cpool);
      if (this.type != 101) {
         throw new RuntimeException("Only element values of type enum can be built with this ctor");
      } else {
         this.typeIdx = typeIdx;
         this.valueIdx = valueIdx;
      }
   }

   public EnumElementValue(ObjectType t, String value, ConstantPool cpool) {
      super(101, cpool);
      this.typeIdx = cpool.addUtf8(t.getSignature());
      this.valueIdx = cpool.addUtf8(value);
   }

   public EnumElementValue(EnumElementValue value, ConstantPool cpool, boolean copyPoolEntries) {
      super(101, cpool);
      if (copyPoolEntries) {
         this.typeIdx = cpool.addUtf8(value.getEnumTypeString());
         this.valueIdx = cpool.addUtf8(value.getEnumValueString());
      } else {
         this.typeIdx = value.getTypeIndex();
         this.valueIdx = value.getValueIndex();
      }

   }

   public void dump(DataOutputStream dos) throws IOException {
      dos.writeByte(this.type);
      dos.writeShort(this.typeIdx);
      dos.writeShort(this.valueIdx);
   }

   public String stringifyValue() {
      StringBuffer sb = new StringBuffer();
      ConstantUtf8 cu8 = (ConstantUtf8)this.cpool.getConstant(this.typeIdx, (byte)1);
      sb.append(cu8.getValue());
      cu8 = (ConstantUtf8)this.cpool.getConstant(this.valueIdx, (byte)1);
      sb.append(cu8.getValue());
      return sb.toString();
   }

   public String toString() {
      StringBuilder s = new StringBuilder("E(");
      s.append(this.getEnumTypeString()).append(" ").append(this.getEnumValueString()).append(")");
      return s.toString();
   }

   public String getEnumTypeString() {
      return ((ConstantUtf8)this.getConstantPool().getConstant(this.typeIdx)).getValue();
   }

   public String getEnumValueString() {
      return ((ConstantUtf8)this.getConstantPool().getConstant(this.valueIdx)).getValue();
   }

   public int getValueIndex() {
      return this.valueIdx;
   }

   public int getTypeIndex() {
      return this.typeIdx;
   }
}
