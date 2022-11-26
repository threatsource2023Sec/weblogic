package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class VoidTypeBinding extends BaseTypeBinding {
   VoidTypeBinding() {
      super(6, TypeConstants.VOID, new char[]{'V'});
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
