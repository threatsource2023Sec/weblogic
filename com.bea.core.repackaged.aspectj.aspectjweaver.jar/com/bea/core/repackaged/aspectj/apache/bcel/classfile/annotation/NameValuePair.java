package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataOutputStream;
import java.io.IOException;

public class NameValuePair {
   private int nameIdx;
   private ElementValue value;
   private ConstantPool cpool;

   public NameValuePair(NameValuePair pair, ConstantPool cpool, boolean copyPoolEntries) {
      this.cpool = cpool;
      if (copyPoolEntries) {
         this.nameIdx = cpool.addUtf8(pair.getNameString());
      } else {
         this.nameIdx = pair.getNameIndex();
      }

      this.value = ElementValue.copy(pair.getValue(), cpool, copyPoolEntries);
   }

   protected NameValuePair(int idx, ElementValue value, ConstantPool cpool) {
      this.nameIdx = idx;
      this.value = value;
      this.cpool = cpool;
   }

   public NameValuePair(String name, ElementValue value, ConstantPool cpool) {
      this.nameIdx = cpool.addUtf8(name);
      this.value = value;
      this.cpool = cpool;
   }

   protected void dump(DataOutputStream dos) throws IOException {
      dos.writeShort(this.nameIdx);
      this.value.dump(dos);
   }

   public int getNameIndex() {
      return this.nameIdx;
   }

   public final String getNameString() {
      return this.cpool.getConstantUtf8(this.nameIdx).getValue();
   }

   public final ElementValue getValue() {
      return this.value;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getNameString()).append("=").append(this.value.stringifyValue());
      return sb.toString();
   }
}
