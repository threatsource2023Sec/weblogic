package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassVisitor;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisTypeAnnos extends RuntimeTypeAnnos {
   public RuntimeInvisTypeAnnos(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIdx, len, cpool);
      this.readTypeAnnotations(dis, cpool);
   }

   public RuntimeInvisTypeAnnos(int nameIdx, int len, ConstantPool cpool) {
      super((byte)21, false, nameIdx, len, cpool);
   }

   public void accept(ClassVisitor v) {
      v.visitRuntimeInvisibleTypeAnnotations(this);
   }
}
