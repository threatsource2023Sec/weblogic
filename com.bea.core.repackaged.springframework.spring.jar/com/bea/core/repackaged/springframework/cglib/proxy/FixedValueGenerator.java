package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.util.Iterator;
import java.util.List;

class FixedValueGenerator implements CallbackGenerator {
   public static final FixedValueGenerator INSTANCE = new FixedValueGenerator();
   private static final Type FIXED_VALUE = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.proxy.FixedValue");
   private static final Signature LOAD_OBJECT = TypeUtils.parseSignature("Object loadObject()");

   public void generate(ClassEmitter ce, CallbackGenerator.Context context, List methods) {
      Iterator it = methods.iterator();

      while(it.hasNext()) {
         MethodInfo method = (MethodInfo)it.next();
         CodeEmitter e = context.beginMethod(ce, method);
         context.emitCallback(e, context.getIndex(method));
         e.invoke_interface(FIXED_VALUE, LOAD_OBJECT);
         e.unbox_or_zero(e.getReturnType());
         e.return_value();
         e.end_method();
      }

   }

   public void generateStatic(CodeEmitter e, CallbackGenerator.Context context, List methods) {
   }
}
