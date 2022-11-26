package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.Constants;

public abstract class ClassTransformer extends ClassVisitor {
   public ClassTransformer() {
      super(Constants.ASM_API);
   }

   public ClassTransformer(int opcode) {
      super(opcode);
   }

   public abstract void setTarget(ClassVisitor var1);
}
