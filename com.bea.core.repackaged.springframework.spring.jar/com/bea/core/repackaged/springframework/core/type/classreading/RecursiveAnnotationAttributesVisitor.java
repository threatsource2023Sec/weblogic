package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;

class RecursiveAnnotationAttributesVisitor extends AbstractRecursiveAnnotationVisitor {
   protected final String annotationType;

   public RecursiveAnnotationAttributesVisitor(String annotationType, AnnotationAttributes attributes, @Nullable ClassLoader classLoader) {
      super(classLoader, attributes);
      this.annotationType = annotationType;
   }

   public void visitEnd() {
      AnnotationUtils.registerDefaultValues(this.attributes);
   }
}
