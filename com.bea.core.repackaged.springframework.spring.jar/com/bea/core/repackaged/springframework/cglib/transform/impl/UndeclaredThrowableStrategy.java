package com.bea.core.repackaged.springframework.cglib.transform.impl;

import com.bea.core.repackaged.springframework.cglib.core.ClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.DefaultGeneratorStrategy;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import com.bea.core.repackaged.springframework.cglib.transform.ClassTransformer;
import com.bea.core.repackaged.springframework.cglib.transform.MethodFilter;
import com.bea.core.repackaged.springframework.cglib.transform.MethodFilterTransformer;
import com.bea.core.repackaged.springframework.cglib.transform.TransformingClassGenerator;

public class UndeclaredThrowableStrategy extends DefaultGeneratorStrategy {
   private Class wrapper;
   private static final MethodFilter TRANSFORM_FILTER = new MethodFilter() {
      public boolean accept(int access, String name, String desc, String signature, String[] exceptions) {
         return !TypeUtils.isPrivate(access) && name.indexOf(36) < 0;
      }
   };

   public UndeclaredThrowableStrategy(Class wrapper) {
      this.wrapper = wrapper;
   }

   protected ClassGenerator transform(ClassGenerator cg) throws Exception {
      ClassTransformer tr = new UndeclaredThrowableTransformer(this.wrapper);
      ClassTransformer tr = new MethodFilterTransformer(TRANSFORM_FILTER, tr);
      return new TransformingClassGenerator(cg, tr);
   }
}
