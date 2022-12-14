package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.ClassReader;
import com.bea.core.repackaged.springframework.cglib.core.ClassGenerator;

public class TransformingClassLoader extends AbstractClassLoader {
   private ClassTransformerFactory t;

   public TransformingClassLoader(ClassLoader parent, ClassFilter filter, ClassTransformerFactory t) {
      super(parent, parent, filter);
      this.t = t;
   }

   protected ClassGenerator getGenerator(ClassReader r) {
      ClassTransformer t2 = this.t.newInstance();
      return new TransformingClassGenerator(super.getGenerator(r), t2);
   }
}
