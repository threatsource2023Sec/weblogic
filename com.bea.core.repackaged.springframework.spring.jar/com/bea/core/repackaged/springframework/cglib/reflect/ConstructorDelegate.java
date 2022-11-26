package com.bea.core.repackaged.springframework.cglib.reflect;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.AbstractClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.KeyFactory;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public abstract class ConstructorDelegate {
   private static final ConstructorKey KEY_FACTORY;

   protected ConstructorDelegate() {
   }

   public static ConstructorDelegate create(Class targetClass, Class iface) {
      Generator gen = new Generator();
      gen.setTargetClass(targetClass);
      gen.setInterface(iface);
      return gen.create();
   }

   static {
      KEY_FACTORY = (ConstructorKey)KeyFactory.create(ConstructorKey.class, KeyFactory.CLASS_BY_NAME);
   }

   public static class Generator extends AbstractClassGenerator {
      private static final AbstractClassGenerator.Source SOURCE = new AbstractClassGenerator.Source(ConstructorDelegate.class.getName());
      private static final Type CONSTRUCTOR_DELEGATE = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.reflect.ConstructorDelegate");
      private Class iface;
      private Class targetClass;

      public Generator() {
         super(SOURCE);
      }

      public void setInterface(Class iface) {
         this.iface = iface;
      }

      public void setTargetClass(Class targetClass) {
         this.targetClass = targetClass;
      }

      public ConstructorDelegate create() {
         this.setNamePrefix(this.targetClass.getName());
         Object key = ConstructorDelegate.KEY_FACTORY.newInstance(this.iface.getName(), this.targetClass.getName());
         return (ConstructorDelegate)super.create(key);
      }

      protected ClassLoader getDefaultClassLoader() {
         return this.targetClass.getClassLoader();
      }

      protected ProtectionDomain getProtectionDomain() {
         return ReflectUtils.getProtectionDomain(this.targetClass);
      }

      public void generateClass(ClassVisitor v) {
         this.setNamePrefix(this.targetClass.getName());
         Method newInstance = ReflectUtils.findNewInstance(this.iface);
         if (!newInstance.getReturnType().isAssignableFrom(this.targetClass)) {
            throw new IllegalArgumentException("incompatible return type");
         } else {
            Constructor constructor;
            try {
               constructor = this.targetClass.getDeclaredConstructor(newInstance.getParameterTypes());
            } catch (NoSuchMethodException var7) {
               throw new IllegalArgumentException("interface does not match any known constructor");
            }

            ClassEmitter ce = new ClassEmitter(v);
            ce.begin_class(46, 1, this.getClassName(), CONSTRUCTOR_DELEGATE, new Type[]{Type.getType(this.iface)}, "<generated>");
            Type declaring = Type.getType(constructor.getDeclaringClass());
            EmitUtils.null_constructor(ce);
            CodeEmitter e = ce.begin_method(1, ReflectUtils.getSignature(newInstance), ReflectUtils.getExceptionTypes(newInstance));
            e.new_instance(declaring);
            e.dup();
            e.load_args();
            e.invoke_constructor(declaring, ReflectUtils.getSignature(constructor));
            e.return_value();
            e.end_method();
            ce.end_class();
         }
      }

      protected Object firstInstance(Class type) {
         return ReflectUtils.newInstance(type);
      }

      protected Object nextInstance(Object instance) {
         return instance;
      }
   }

   interface ConstructorKey {
      Object newInstance(String var1, String var2);
   }
}
