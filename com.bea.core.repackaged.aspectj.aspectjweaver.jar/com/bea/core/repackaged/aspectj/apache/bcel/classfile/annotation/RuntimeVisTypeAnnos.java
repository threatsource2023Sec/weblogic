package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassVisitor;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeVisTypeAnnos extends RuntimeTypeAnnos {
   public RuntimeVisTypeAnnos(int nameIdx, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIdx, len, cpool);
      this.readTypeAnnotations(dis, cpool);
   }

   public RuntimeVisTypeAnnos(int nameIdx, int len, ConstantPool cpool) {
      super((byte)20, true, nameIdx, len, cpool);
   }

   public void accept(ClassVisitor v) {
      v.visitRuntimeVisibleTypeAnnotations(this);
   }
}
