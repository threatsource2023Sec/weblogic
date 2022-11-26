package com.bea.core.repackaged.aspectj.weaver;

import java.io.IOException;

public class AnnotationOnTypeMunger extends ResolvedTypeMunger {
   AnnotationAJ newAnnotation;
   private volatile int hashCode = 0;

   public AnnotationOnTypeMunger(AnnotationAJ anno) {
      super(AnnotationOnType, (ResolvedMember)null);
      this.newAnnotation = anno;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new RuntimeException("unimplemented");
   }

   public AnnotationAJ getNewAnnotation() {
      return this.newAnnotation;
   }

   public boolean equals(Object other) {
      if (!(other instanceof AnnotationOnTypeMunger)) {
         return false;
      } else {
         AnnotationOnTypeMunger o = (AnnotationOnTypeMunger)other;
         return this.newAnnotation.getTypeSignature().equals(o.newAnnotation.getTypeSignature());
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + this.newAnnotation.getTypeSignature().hashCode();
         this.hashCode = result;
      }

      return this.hashCode;
   }
}
