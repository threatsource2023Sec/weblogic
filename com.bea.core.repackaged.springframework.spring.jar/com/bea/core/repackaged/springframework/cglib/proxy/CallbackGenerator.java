package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import java.util.List;

interface CallbackGenerator {
   void generate(ClassEmitter var1, Context var2, List var3) throws Exception;

   void generateStatic(CodeEmitter var1, Context var2, List var3) throws Exception;

   public interface Context {
      ClassLoader getClassLoader();

      CodeEmitter beginMethod(ClassEmitter var1, MethodInfo var2);

      int getOriginalModifiers(MethodInfo var1);

      int getIndex(MethodInfo var1);

      void emitCallback(CodeEmitter var1, int var2);

      Signature getImplSignature(MethodInfo var1);

      void emitLoadArgsAndInvoke(CodeEmitter var1, MethodInfo var2);
   }
}
