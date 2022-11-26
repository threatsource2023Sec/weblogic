package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.util.Iterator;
import java.util.List;

class NoOpGenerator implements CallbackGenerator {
   public static final NoOpGenerator INSTANCE = new NoOpGenerator();

   public void generate(ClassEmitter ce, CallbackGenerator.Context context, List methods) {
      Iterator it = methods.iterator();

      while(true) {
         MethodInfo method;
         do {
            if (!it.hasNext()) {
               return;
            }

            method = (MethodInfo)it.next();
         } while(!TypeUtils.isBridge(method.getModifiers()) && (!TypeUtils.isProtected(context.getOriginalModifiers(method)) || !TypeUtils.isPublic(method.getModifiers())));

         CodeEmitter e = EmitUtils.begin_method(ce, method);
         e.load_this();
         context.emitLoadArgsAndInvoke(e, method);
         e.return_value();
         e.end_method();
      }
   }

   public void generateStatic(CodeEmitter e, CallbackGenerator.Context context, List methods) {
   }
}
