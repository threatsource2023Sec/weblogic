package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import java.lang.reflect.Method;

class MixinBeanEmitter extends MixinEmitter {
   public MixinBeanEmitter(ClassVisitor v, String className, Class[] classes) {
      super(v, className, classes, (int[])null);
   }

   protected Class[] getInterfaces(Class[] classes) {
      return null;
   }

   protected Method[] getMethods(Class type) {
      return ReflectUtils.getPropertyMethods(ReflectUtils.getBeanProperties(type), true, true);
   }
}
