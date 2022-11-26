package com.bea.core.repackaged.springframework.cglib.transform.impl;

import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.transform.ClassEmitterTransformer;
import java.lang.reflect.Method;

public class AddInitTransformer extends ClassEmitterTransformer {
   private MethodInfo info;

   public AddInitTransformer(Method method) {
      this.info = ReflectUtils.getMethodInfo(method);
      Type[] types = this.info.getSignature().getArgumentTypes();
      if (types.length != 1 || !types[0].equals(Constants.TYPE_OBJECT) || !this.info.getSignature().getReturnType().equals(Type.VOID_TYPE)) {
         throw new IllegalArgumentException(method + " illegal signature");
      }
   }

   public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
      CodeEmitter emitter = super.begin_method(access, sig, exceptions);
      return sig.getName().equals("<init>") ? new CodeEmitter(emitter) {
         public void visitInsn(int opcode) {
            if (opcode == 177) {
               this.load_this();
               this.invoke(AddInitTransformer.this.info);
            }

            super.visitInsn(opcode);
         }
      } : emitter;
   }
}
