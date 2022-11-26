package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.weaver.ConstantPoolReader;

public class BcelConstantPoolReader implements ConstantPoolReader {
   private ConstantPool constantPool;

   public BcelConstantPoolReader(ConstantPool constantPool) {
      this.constantPool = constantPool;
   }

   public String readUtf8(int cpIndex) {
      return this.constantPool.getConstantUtf8(cpIndex).getValue();
   }
}
