package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class NullTypeBinding extends BaseTypeBinding {
   NullTypeBinding() {
      super(12, TypeConstants.NULL, new char[]{'N'});
   }

   public TypeBinding clone(TypeBinding enclosingType) {
      return this;
   }

   public void setTypeAnnotations(AnnotationBinding[] annotations, boolean evalNullAnnotations) {
   }

   public TypeBinding unannotated() {
      return this;
   }
}
