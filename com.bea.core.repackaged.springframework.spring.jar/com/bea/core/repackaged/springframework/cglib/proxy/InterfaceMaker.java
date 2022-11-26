package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.AbstractClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InterfaceMaker extends AbstractClassGenerator {
   private static final AbstractClassGenerator.Source SOURCE = new AbstractClassGenerator.Source(InterfaceMaker.class.getName());
   private Map signatures = new HashMap();

   public InterfaceMaker() {
      super(SOURCE);
   }

   public void add(Signature sig, Type[] exceptions) {
      this.signatures.put(sig, exceptions);
   }

   public void add(Method method) {
      this.add(ReflectUtils.getSignature(method), ReflectUtils.getExceptionTypes(method));
   }

   public void add(Class clazz) {
      Method[] methods = clazz.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         if (!m.getDeclaringClass().getName().equals("java.lang.Object")) {
            this.add(m);
         }
      }

   }

   public Class create() {
      this.setUseCache(false);
      return (Class)super.create(this);
   }

   protected ClassLoader getDefaultClassLoader() {
      return null;
   }

   protected Object firstInstance(Class type) {
      return type;
   }

   protected Object nextInstance(Object instance) {
      throw new IllegalStateException("InterfaceMaker does not cache");
   }

   public void generateClass(ClassVisitor v) throws Exception {
      ClassEmitter ce = new ClassEmitter(v);
      ce.begin_class(46, 513, this.getClassName(), (Type)null, (Type[])null, "<generated>");
      Iterator it = this.signatures.keySet().iterator();

      while(it.hasNext()) {
         Signature sig = (Signature)it.next();
         Type[] exceptions = (Type[])((Type[])this.signatures.get(sig));
         ce.begin_method(1025, sig, exceptions).end_method();
      }

      ce.end_class();
   }
}
