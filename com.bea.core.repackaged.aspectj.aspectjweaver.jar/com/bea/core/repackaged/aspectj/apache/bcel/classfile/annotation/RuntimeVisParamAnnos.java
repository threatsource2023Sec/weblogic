package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassVisitor;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeVisParamAnnos extends RuntimeParamAnnos {
   public RuntimeVisParamAnnos(int nameIdx, int len, ConstantPool cpool) {
      super((byte)14, true, nameIdx, len, cpool);
   }

   public RuntimeVisParamAnnos(int nameIndex, int len, byte[] rvaData, ConstantPool cpool) {
      super((byte)14, true, nameIndex, len, rvaData, cpool);
   }

   public RuntimeVisParamAnnos(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIdx, len, cpool);
      this.readParameterAnnotations(dis, cpool);
   }

   public void accept(ClassVisitor v) {
      v.visitRuntimeVisibleParameterAnnotations(this);
   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }
}
