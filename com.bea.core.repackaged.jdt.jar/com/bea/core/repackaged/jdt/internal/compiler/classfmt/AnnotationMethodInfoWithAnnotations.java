package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;

public class AnnotationMethodInfoWithAnnotations extends AnnotationMethodInfo {
   private AnnotationInfo[] annotations;

   AnnotationMethodInfoWithAnnotations(MethodInfo methodInfo, Object defaultValue, AnnotationInfo[] annotations) {
      super(methodInfo, defaultValue);
      this.annotations = annotations;
   }

   public IBinaryAnnotation[] getAnnotations() {
      return this.annotations;
   }

   protected void initialize() {
      int i = 0;

      for(int l = this.annotations == null ? 0 : this.annotations.length; i < l; ++i) {
         if (this.annotations[i] != null) {
            this.annotations[i].initialize();
         }
      }

      super.initialize();
   }

   protected void reset() {
      int i = 0;

      for(int l = this.annotations == null ? 0 : this.annotations.length; i < l; ++i) {
         if (this.annotations[i] != null) {
            this.annotations[i].reset();
         }
      }

      super.reset();
   }
}
