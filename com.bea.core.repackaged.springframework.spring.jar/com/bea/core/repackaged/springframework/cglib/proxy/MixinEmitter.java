package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.MethodWrapper;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

class MixinEmitter extends ClassEmitter {
   private static final String FIELD_NAME = "CGLIB$DELEGATES";
   private static final Signature CSTRUCT_OBJECT_ARRAY = TypeUtils.parseConstructor("Object[]");
   private static final Type MIXIN = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.proxy.Mixin");
   private static final Signature NEW_INSTANCE;

   public MixinEmitter(ClassVisitor v, String className, Class[] classes, int[] route) {
      super(v);
      this.begin_class(46, 1, className, MIXIN, TypeUtils.getTypes(this.getInterfaces(classes)), "<generated>");
      EmitUtils.null_constructor(this);
      EmitUtils.factory_method(this, NEW_INSTANCE);
      this.declare_field(2, "CGLIB$DELEGATES", Constants.TYPE_OBJECT_ARRAY, (Object)null);
      CodeEmitter e = this.begin_method(1, CSTRUCT_OBJECT_ARRAY, (Type[])null);
      e.load_this();
      e.super_invoke_constructor();
      e.load_this();
      e.load_arg(0);
      e.putfield("CGLIB$DELEGATES");
      e.return_value();
      e.end_method();
      Set unique = new HashSet();

      for(int i = 0; i < classes.length; ++i) {
         Method[] methods = this.getMethods(classes[i]);

         for(int j = 0; j < methods.length; ++j) {
            if (unique.add(MethodWrapper.create(methods[j]))) {
               MethodInfo method = ReflectUtils.getMethodInfo(methods[j]);
               int modifiers = 1;
               if ((method.getModifiers() & 128) == 128) {
                  modifiers |= 128;
               }

               e = EmitUtils.begin_method(this, method, modifiers);
               e.load_this();
               e.getfield("CGLIB$DELEGATES");
               e.aaload(route != null ? route[i] : i);
               e.checkcast(method.getClassInfo().getType());
               e.load_args();
               e.invoke(method);
               e.return_value();
               e.end_method();
            }
         }
      }

      this.end_class();
   }

   protected Class[] getInterfaces(Class[] classes) {
      return classes;
   }

   protected Method[] getMethods(Class type) {
      return type.getMethods();
   }

   static {
      NEW_INSTANCE = new Signature("newInstance", MIXIN, new Type[]{Constants.TYPE_OBJECT_ARRAY});
   }
}
