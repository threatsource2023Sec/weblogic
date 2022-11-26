package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;

class AnnotationMethodInfoWithTypeAnnotations extends AnnotationMethodInfoWithAnnotations {
   private TypeAnnotationInfo[] typeAnnotations;

   AnnotationMethodInfoWithTypeAnnotations(MethodInfo methodInfo, Object defaultValue, AnnotationInfo[] annotations, TypeAnnotationInfo[] typeAnnotations) {
      super(methodInfo, defaultValue, annotations);
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
