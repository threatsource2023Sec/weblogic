package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassVisitor;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RuntimeInvisAnnos extends RuntimeAnnos {
   public RuntimeInvisAnnos(int nameIdx, int len, ConstantPool cpool) {
      super((byte)13, false, nameIdx, len, cpool);
   }

   public RuntimeInvisAnnos(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIdx, len, cpool);
      this.readAnnotations(dis, cpool);
   }

   public RuntimeInvisAnnos(int nameIndex, int len, byte[] rvaData, ConstantPool cpool) {
      super((byte)13, false, nameIndex, len, rvaData, cpool);
   }

   public void accept(ClassVisitor v) {
      v.visitRuntimeInvisibleAnnotations(this);
   }

   public final void dump(DataOutputStream dos) throws IOException {
      super.dump(dos);
      this.writeAnnotations(dos);
   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }
}
