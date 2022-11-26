package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassVisitor;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RuntimeVisAnnos extends RuntimeAnnos {
   public RuntimeVisAnnos(int nameIdx, int len, ConstantPool cpool) {
      super((byte)12, true, nameIdx, len, cpool);
   }

   public RuntimeVisAnnos(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIdx, len, cpool);
      this.readAnnotations(dis, cpool);
   }

   public RuntimeVisAnnos(int nameIndex, int len, byte[] rvaData, ConstantPool cpool) {
      super((byte)12, true, nameIndex, len, rvaData, cpool);
   }

   public void accept(ClassVisitor v) {
      v.visitRuntimeVisibleAnnotations(this);
   }

   public final void dump(DataOutputStream dos) throws IOException {
      super.dump(dos);
      this.writeAnnotations(dos);
   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }
}
