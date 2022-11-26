package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.Attribute;
import com.bea.core.repackaged.springframework.asm.ClassReader;
import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.ClassGenerator;

public class ClassReaderGenerator implements ClassGenerator {
   private final ClassReader r;
   private final Attribute[] attrs;
   private final int flags;

   public ClassReaderGenerator(ClassReader r, int flags) {
      this(r, (Attribute[])null, flags);
   }

   public ClassReaderGenerator(ClassReader r, Attribute[] attrs, int flags) {
      this.r = r;
      this.attrs = attrs != null ? attrs : new Attribute[0];
      this.flags = flags;
   }

   public void generateClass(ClassVisitor v) {
      this.r.accept(v, this.attrs, this.flags);
   }
}
