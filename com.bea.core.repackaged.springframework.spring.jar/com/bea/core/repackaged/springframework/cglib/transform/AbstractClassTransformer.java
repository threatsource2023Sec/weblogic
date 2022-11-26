package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.Constants;

public abstract class AbstractClassTransformer extends ClassTransformer {
   protected AbstractClassTransformer() {
      super(Constants.ASM_API);
   }

   public void setTarget(ClassVisitor target) {
      this.cv = target;
   }
}
