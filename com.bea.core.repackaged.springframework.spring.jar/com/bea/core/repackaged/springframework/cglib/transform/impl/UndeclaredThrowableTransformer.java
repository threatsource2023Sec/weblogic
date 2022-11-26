package com.bea.core.repackaged.springframework.cglib.transform.impl;

import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.Block;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import com.bea.core.repackaged.springframework.cglib.transform.ClassEmitterTransformer;
import java.lang.reflect.Constructor;

public class UndeclaredThrowableTransformer extends ClassEmitterTransformer {
   private Type wrapper;

   public UndeclaredThrowableTransformer(Class wrapper) {
      this.wrapper = Type.getType(wrapper);
      boolean found = false;
      Constructor[] cstructs = wrapper.getConstructors();

      for(int i = 0; i < cstructs.length; ++i) {
         Class[] types = cstructs[i].getParameterTypes();
         if (types.length == 1 && types[0].equals(Throwable.class)) {
            found = true;
            break;
         }
      }

      if (!found) {
         throw new IllegalArgumentException(wrapper + " does not have a single-arg constructor that takes a Throwable");
      }
   }

   public CodeEmitter begin_method(int access, Signature sig, final Type[] exceptions) {
      CodeEmitter e = super.begin_method(access, sig, exceptions);
      return !TypeUtils.isAbstract(access) && !sig.equals(Constants.SIG_STATIC) ? new CodeEmitter(e) {
         private Block handler = this.begin_block();

         public void visitMaxs(int maxStack, int maxLocals) {
            this.handler.end();
            EmitUtils.wrap_undeclared_throwable(this, this.handler, exceptions, UndeclaredThrowableTransformer.this.wrapper);
            super.visitMaxs(maxStack, maxLocals);
         }
      } : e;
   }
}
