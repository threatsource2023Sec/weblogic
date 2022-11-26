package com.bea.core.repackaged.springframework.cglib.beans;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.AbstractClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.Converter;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.KeyFactory;
import com.bea.core.repackaged.springframework.cglib.core.Local;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

public abstract class BeanCopier {
   private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey)KeyFactory.create(BeanCopierKey.class);
   private static final Type CONVERTER = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.core.Converter");
   private static final Type BEAN_COPIER = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.beans.BeanCopier");
   private static final Signature COPY;
   private static final Signature CONVERT;

   public static BeanCopier create(Class source, Class target, boolean useConverter) {
      Generator gen = new Generator();
      gen.setSource(source);
      gen.setTarget(target);
      gen.setUseConverter(useConverter);
      return gen.create();
   }

   public abstract void copy(Object var1, Object var2, Converter var3);

   static {
      COPY = new Signature("copy", Type.VOID_TYPE, new Type[]{Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER});
      CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");
   }

   public static class Generator extends AbstractClassGenerator {
      private static final AbstractClassGenerator.Source SOURCE = new AbstractClassGenerator.Source(BeanCopier.class.getName());
      private Class source;
      private Class target;
      private boolean useConverter;

      public Generator() {
         super(SOURCE);
      }

      public void setSource(Class source) {
         if (!Modifier.isPublic(source.getModifiers())) {
            this.setNamePrefix(source.getName());
         }

         this.source = source;
      }

      public void setTarget(Class target) {
         if (!Modifier.isPublic(target.getModifiers())) {
            this.setNamePrefix(target.getName());
         }

         this.target = target;
      }

      public void setUseConverter(boolean useConverter) {
         this.useConverter = useConverter;
      }

      protected ClassLoader getDefaultClassLoader() {
         return this.source.getClassLoader();
      }

      protected ProtectionDomain getProtectionDomain() {
         return ReflectUtils.getProtectionDomain(this.source);
      }

      public BeanCopier create() {
         Object key = BeanCopier.KEY_FACTORY.newInstance(this.source.getName(), this.target.getName(), this.useConverter);
         return (BeanCopier)super.create(key);
      }

      public void generateClass(ClassVisitor v) {
         Type sourceType = Type.getType(this.source);
         Type targetType = Type.getType(this.target);
         ClassEmitter ce = new ClassEmitter(v);
         ce.begin_class(46, 1, this.getClassName(), BeanCopier.BEAN_COPIER, (Type[])null, "<generated>");
         EmitUtils.null_constructor(ce);
         CodeEmitter e = ce.begin_method(1, BeanCopier.COPY, (Type[])null);
         PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(this.source);
         PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(this.target);
         Map names = new HashMap();

         for(int i = 0; i < getters.length; ++i) {
            names.put(getters[i].getName(), getters[i]);
         }

         Local targetLocal = e.make_local();
         Local sourceLocal = e.make_local();
         if (this.useConverter) {
            e.load_arg(1);
            e.checkcast(targetType);
            e.store_local(targetLocal);
            e.load_arg(0);
            e.checkcast(sourceType);
            e.store_local(sourceLocal);
         } else {
            e.load_arg(1);
            e.checkcast(targetType);
            e.load_arg(0);
            e.checkcast(sourceType);
         }

         for(int i = 0; i < setters.length; ++i) {
            PropertyDescriptor setter = setters[i];
            PropertyDescriptor getter = (PropertyDescriptor)names.get(setter.getName());
            if (getter != null) {
               MethodInfo read = ReflectUtils.getMethodInfo(getter.getReadMethod());
               MethodInfo write = ReflectUtils.getMethodInfo(setter.getWriteMethod());
               if (this.useConverter) {
                  Type setterType = write.getSignature().getArgumentTypes()[0];
                  e.load_local(targetLocal);
                  e.load_arg(2);
                  e.load_local(sourceLocal);
                  e.invoke(read);
                  e.box(read.getSignature().getReturnType());
                  EmitUtils.load_class(e, setterType);
                  e.push(write.getSignature().getName());
                  e.invoke_interface(BeanCopier.CONVERTER, BeanCopier.CONVERT);
                  e.unbox_or_zero(setterType);
                  e.invoke(write);
               } else if (compatible(getter, setter)) {
                  e.dup2();
                  e.invoke(read);
                  e.invoke(write);
               }
            }
         }

         e.return_value();
         e.end_method();
         ce.end_class();
      }

      private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
         return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
      }

      protected Object firstInstance(Class type) {
         return ReflectUtils.newInstance(type);
      }

      protected Object nextInstance(Object instance) {
         return instance;
      }
   }

   interface BeanCopierKey {
      Object newInstance(String var1, String var2, boolean var3);
   }
}
