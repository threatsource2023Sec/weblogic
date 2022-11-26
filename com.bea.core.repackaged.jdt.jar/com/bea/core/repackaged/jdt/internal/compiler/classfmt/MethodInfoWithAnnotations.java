package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;

public class MethodInfoWithAnnotations extends MethodInfo {
   protected AnnotationInfo[] annotations;

   MethodInfoWithAnnotations(MethodInfo methodInfo, AnnotationInfo[] annotations) {
      super(methodInfo.reference, methodInfo.constantPoolOffsets, methodInfo.structOffset, methodInfo.version);
      this.annotations = annotations;
      this.accessFlags = methodInfo.accessFlags;
      this.attributeBytes = methodInfo.attributeBytes;
      this.descriptor = methodInfo.descriptor;
      this.exceptionNames = methodInfo.exceptionNames;
      this.name = methodInfo.name;
      this.signature = methodInfo.signature;
      this.signatureUtf8Offset = methodInfo.signatureUtf8Offset;
      this.tagBits = methodInfo.tagBits;
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
