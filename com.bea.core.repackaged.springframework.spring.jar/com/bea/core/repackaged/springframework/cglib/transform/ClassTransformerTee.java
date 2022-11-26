package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.Constants;

public class ClassTransformerTee extends ClassTransformer {
   private ClassVisitor branch;

   public ClassTransformerTee(ClassVisitor branch) {
      super(Constants.ASM_API);
      this.branch = branch;
   }

   public void setTarget(ClassVisitor target) {
      this.cv = new ClassVisitorTee(this.branch, target);
   }
}
