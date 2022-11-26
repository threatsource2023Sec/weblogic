package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.CollectionUtils;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import com.bea.core.repackaged.springframework.cglib.core.RejectModifierPredicate;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MixinEverythingEmitter extends MixinEmitter {
   public MixinEverythingEmitter(ClassVisitor v, String className, Class[] classes) {
      super(v, className, classes, (int[])null);
   }

   protected Class[] getInterfaces(Class[] classes) {
      List list = new ArrayList();

      for(int i = 0; i < classes.length; ++i) {
         ReflectUtils.addAllInterfaces(classes[i], list);
      }

      return (Class[])((Class[])list.toArray(new Class[list.size()]));
   }

   protected Method[] getMethods(Class type) {
      List methods = new ArrayList(Arrays.asList(type.getMethods()));
      CollectionUtils.filter(methods, new RejectModifierPredicate(24));
      return (Method[])((Method[])methods.toArray(new Method[methods.size()]));
   }
}
