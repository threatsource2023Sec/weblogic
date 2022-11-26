package com.bea.core.repackaged.springframework.cglib.beans;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.AbstractClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.MethodInfo;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public class ImmutableBean {
   private static final Type ILLEGAL_STATE_EXCEPTION = TypeUtils.parseType("IllegalStateException");
   private static final Signature CSTRUCT_OBJECT = TypeUtils.parseConstructor("Object");
   private static final Class[] OBJECT_CLASSES = new Class[]{Object.class};
   private static final String FIELD_NAME = "CGLIB$RWBean";

   private ImmutableBean() {
   }

   public static Object create(Object bean) {
      Generator gen = new Generator();
      gen.setBean(bean);
      return gen.create();
   }

   public static class Generator extends AbstractClassGenerator {
      private static final AbstractClassGenerator.Source SOURCE = new AbstractClassGenerator.Source(ImmutableBean.class.getName());
      private Object bean;
      private Class target;

      public Generator() {
         super(SOURCE);
      }

      public void setBean(Object bean) {
         this.bean = bean;
         this.target = bean.getClass();
      }

      protected ClassLoader getDefaultClassLoader() {
         return this.target.getClassLoader();
      }

      protected ProtectionDomain getProtectionDomain() {
         return ReflectUtils.getProtectionDomain(this.target);
      }

      public Object create() {
         String name = this.target.getName();
         this.setNamePrefix(name);
         return super.create(name);
      }

      public void generateClass(ClassVisitor v) {
         Type targetType = Type.getType(this.target);
         ClassEmitter ce = new ClassEmitter(v);
         ce.begin_class(46, 1, this.getClassName(), targetType, (Type[])null, "<generated>");
         ce.declare_field(18, "CGLIB$RWBean", targetType, (Object)null);
         CodeEmitter e = ce.begin_method(1, ImmutableBean.CSTRUCT_OBJECT, (Type[])null);
         e.load_this();
         e.super_invoke_constructor();
         e.load_this();
         e.load_arg(0);
         e.checkcast(targetType);
         e.putfield("CGLIB$RWBean");
         e.return_value();
         e.end_method();
         PropertyDescriptor[] descriptors = ReflectUtils.getBeanProperties(this.target);
         Method[] getters = ReflectUtils.getPropertyMethods(descriptors, true, false);
         Method[] setters = ReflectUtils.getPropertyMethods(descriptors, false, true);

         int i;
         MethodInfo setter;
         for(i = 0; i < getters.length; ++i) {
            setter = ReflectUtils.getMethodInfo(getters[i]);
            e = EmitUtils.begin_method(ce, setter, 1);
            e.load_this();
            e.getfield("CGLIB$RWBean");
            e.invoke(setter);
            e.return_value();
            e.end_method();
         }

         for(i = 0; i < setters.length; ++i) {
            setter = ReflectUtils.getMethodInfo(setters[i]);
            e = EmitUtils.begin_method(ce, setter, 1);
            e.throw_exception(ImmutableBean.ILLEGAL_STATE_EXCEPTION, "Bean is immutable");
            e.end_method();
         }

         ce.end_class();
      }

      protected Object firstInstance(Class type) {
         return ReflectUtils.newInstance(type, ImmutableBean.OBJECT_CLASSES, new Object[]{this.bean});
      }

      protected Object nextInstance(Object instance) {
         return this.firstInstance(instance.getClass());
      }
   }
}
