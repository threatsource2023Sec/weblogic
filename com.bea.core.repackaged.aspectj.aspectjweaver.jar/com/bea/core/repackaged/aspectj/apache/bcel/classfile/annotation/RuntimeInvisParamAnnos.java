package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassVisitor;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisParamAnnos extends RuntimeParamAnnos {
   public RuntimeInvisParamAnnos(int nameIdx, int len, ConstantPool cpool) {
      super((byte)15, false, nameIdx, len, cpool);
   }

   public RuntimeInvisParamAnnos(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIdx, len, cpool);
      this.readParameterAnnotations(dis, cpool);
   }

   public RuntimeInvisParamAnnos(int nameIndex, int len, byte[] rvaData, ConstantPool cpool) {
      super((byte)15, false, nameIndex, len, rvaData, cpool);
   }

   public void accept(ClassVisitor v) {
      v.visitRuntimeInvisibleParameterAnnotations(this);
   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }
}
