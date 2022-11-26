package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.weaver.ConstantPoolWriter;

class BcelConstantPoolWriter implements ConstantPoolWriter {
   ConstantPool pool;

   public BcelConstantPoolWriter(ConstantPool pool) {
      this.pool = pool;
   }

   public int writeUtf8(String name) {
      return this.pool.addUtf8(name);
   }
}
