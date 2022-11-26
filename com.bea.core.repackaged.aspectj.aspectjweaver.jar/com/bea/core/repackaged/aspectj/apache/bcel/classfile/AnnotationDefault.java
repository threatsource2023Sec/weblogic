package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ElementValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AnnotationDefault extends Attribute {
   private ElementValue value;

   public AnnotationDefault(int nameIndex, int len, DataInputStream dis, ConstantPool cpool) throws IOException {
      this(nameIndex, len, ElementValue.readElementValue(dis, cpool), cpool);
   }

   private AnnotationDefault(int nameIndex, int len, ElementValue value, ConstantPool cpool) {
      super((byte)18, nameIndex, len, cpool);
      this.value = value;
   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }

   public final ElementValue getElementValue() {
      return this.value;
   }

   public final void dump(DataOutputStream dos) throws IOException {
      super.dump(dos);
      this.value.dump(dos);
   }

   public void accept(ClassVisitor v) {
      v.visitAnnotationDefault(this);
   }
}
