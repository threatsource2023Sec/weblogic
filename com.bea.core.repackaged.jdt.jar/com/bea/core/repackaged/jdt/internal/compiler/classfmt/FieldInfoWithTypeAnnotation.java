package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;

public final class FieldInfoWithTypeAnnotation extends FieldInfoWithAnnotation {
   private TypeAnnotationInfo[] typeAnnotations;

   FieldInfoWithTypeAnnotation(FieldInfo info, AnnotationInfo[] annos, TypeAnnotationInfo[] typeAnnos) {
      super(info, annos);
      this.typeAnnotations = typeAnnos;
   }

   public IBinaryTypeAnnotation[] getTypeAnnotations() {
      return this.typeAnnotations;
   }

   protected void initialize() {
      int i = 0;

      for(int max = this.typeAnnotations.length; i < max; ++i) {
         this.typeAnnotations[i].initialize();
      }

      super.initialize();
   }

   protected void reset() {
      if (this.typeAnnotations != null) {
         int i = 0;

         for(int max = this.typeAnnotations.length; i < max; ++i) {
            this.typeAnnotations[i].reset();
         }
      }

      super.reset();
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(this.getClass().getName());
      if (this.typeAnnotations != null) {
         buffer.append('\n');
         buffer.append("type annotations:");

         for(int i = 0; i < this.typeAnnotations.length; ++i) {
            buffer.append(this.typeAnnotations[i]);
            buffer.append('\n');
         }
      }

      this.toStringContent(buffer);
      return buffer.toString();
   }
}
