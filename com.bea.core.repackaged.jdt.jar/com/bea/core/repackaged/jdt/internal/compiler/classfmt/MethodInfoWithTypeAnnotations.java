package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;

class MethodInfoWithTypeAnnotations extends MethodInfoWithParameterAnnotations {
   private TypeAnnotationInfo[] typeAnnotations;

   MethodInfoWithTypeAnnotations(MethodInfo methodInfo, AnnotationInfo[] annotations, AnnotationInfo[][] parameterAnnotations, TypeAnnotationInfo[] typeAnnotations) {
      super(methodInfo, annotations, parameterAnnotations);
      this.typeAnnotations = typeAnnotations;
   }

   public IBinaryTypeAnnotation[] getTypeAnnotations() {
      return this.typeAnnotations;
   }

   protected void initialize() {
      int i = 0;

      for(int l = this.typeAnnotations == null ? 0 : this.typeAnnotations.length; i < l; ++i) {
         this.typeAnnotations[i].initialize();
      }

      super.initialize();
   }

   protected void reset() {
      int i = 0;

      for(int l = this.typeAnnotations == null ? 0 : this.typeAnnotations.length; i < l; ++i) {
         this.typeAnnotations[i].reset();
      }

      super.reset();
   }
}
