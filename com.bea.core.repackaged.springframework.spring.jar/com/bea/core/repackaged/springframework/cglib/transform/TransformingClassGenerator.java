package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.ClassGenerator;

public class TransformingClassGenerator implements ClassGenerator {
   private ClassGenerator gen;
   private ClassTransformer t;

   public TransformingClassGenerator(ClassGenerator gen, ClassTransformer t) {
      this.gen = gen;
      this.t = t;
   }

   public void generateClass(ClassVisitor v) throws Exception {
      this.t.setTarget(v);
      this.gen.generateClass(this.t);
   }
}
