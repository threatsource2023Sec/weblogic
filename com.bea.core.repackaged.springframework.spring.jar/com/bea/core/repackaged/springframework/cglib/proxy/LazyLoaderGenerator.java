package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class LazyLoaderGenerator implements CallbackGenerator {
   public static final LazyLoaderGenerator INSTANCE = new LazyLoaderGenerator();
   private static final Signature LOAD_OBJECT = TypeUtils.parseSignature("Object loadObject()");
   private static final Type LAZY_LOADER = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.proxy.LazyLoader");

   public void generate(ClassEmitter ce, CallbackGenerator.Context context, List methods) {
      Set indexes = new HashSet();
      Iterator it = methods.iterator();

      CodeEmitter e;
      while(it.hasNext()) {
         MethodInfo method = (MethodInfo)it.next();
         if (!TypeUtils.isProtected(method.getModifiers())) {
            int index = context.getIndex(method);
            indexes.add(new Integer(index));
            e = context.beginMethod(ce, method);
            e.load_this();
            e.dup();
            e.invoke_virtual_this(this.loadMethod(index));
            e.checkcast(method.getClassInfo().getType());
            e.load_args();
            e.invoke(method);
            e.return_value();
            e.end_method();
         }
      }

      it = indexes.iterator();

      while(it.hasNext()) {
         int index = (Integer)it.next();
         String delegate = "CGLIB$LAZY_LOADER_" + index;
         ce.declare_field(2, delegate, Constants.TYPE_OBJECT, (Object)null);
         e = ce.begin_method(50, this.loadMethod(index), (Type[])null);
         e.load_this();
         e.getfield(delegate);
         e.dup();
         Label end = e.make_label();
         e.ifnonnull(end);
         e.pop();
         e.load_this();
         context.emitCallback(e, index);
         e.invoke_interface(LAZY_LOADER, LOAD_OBJECT);
         e.dup_x1();
         e.putfield(delegate);
         e.mark(end);
         e.return_value();
         e.end_method();
      }

   }

   private Signature loadMethod(int index) {
      return new Signature("CGLIB$LOAD_PRIVATE_" + index, Constants.TYPE_OBJECT, Constants.TYPES_EMPTY);
   }

   public void generateStatic(CodeEmitter e, CallbackGenerator.Context context, List methods) {
   }
}
