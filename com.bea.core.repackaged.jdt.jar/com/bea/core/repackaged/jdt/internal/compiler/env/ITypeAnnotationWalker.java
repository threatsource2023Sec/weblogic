package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface ITypeAnnotationWalker {
   IBinaryAnnotation[] NO_ANNOTATIONS = new IBinaryAnnotation[0];
   ITypeAnnotationWalker EMPTY_ANNOTATION_WALKER = new ITypeAnnotationWalker() {
      public ITypeAnnotationWalker toField() {
         return this;
      }

      public ITypeAnnotationWalker toThrows(int rank) {
         return this;
      }

      public ITypeAnnotationWalker toTypeArgument(int rank) {
         return this;
      }

      public ITypeAnnotationWalker toMethodParameter(short index) {
         return this;
      }

      public ITypeAnnotationWalker toSupertype(short index, char[] superTypeSignature) {
         return this;
      }

      public ITypeAnnotationWalker toTypeParameterBounds(boolean isClassTypeParameter, int parameterRank) {
         return this;
      }

      public ITypeAnnotationWalker toTypeBound(short boundIndex) {
         return this;
      }

      public ITypeAnnotationWalker toTypeParameter(boolean isClassTypeParameter, int rank) {
         return this;
      }

      public ITypeAnnotationWalker toMethodReturn() {
         return this;
      }

      public ITypeAnnotationWalker toReceiver() {
         return this;
      }

      public ITypeAnnotationWalker toWildcardBound() {
         return this;
      }

      public ITypeAnnotationWalker toNextArrayDimension() {
         return this;
      }

      public ITypeAnnotationWalker toNextNestedType() {
         return this;
      }

      public IBinaryAnnotation[] getAnnotationsAtCursor(int currentTypeId, boolean mayApplyArrayContentsDefaultNullness) {
         return NO_ANNOTATIONS;
      }
   };

   ITypeAnnotationWalker toField();

   ITypeAnnotationWalker toMethodReturn();

   ITypeAnnotationWalker toReceiver();

   ITypeAnnotationWalker toTypeParameter(boolean var1, int var2);

   ITypeAnnotationWalker toTypeParameterBounds(boolean var1, int var2);

   ITypeAnnotationWalker toTypeBound(short var1);

   ITypeAnnotationWalker toSupertype(short var1, char[] var2);

   ITypeAnnotationWalker toMethodParameter(short var1);

   ITypeAnnotationWalker toThrows(int var1);

   ITypeAnnotationWalker toTypeArgument(int var1);

   ITypeAnnotationWalker toWildcardBound();

   ITypeAnnotationWalker toNextArrayDimension();

   ITypeAnnotationWalker toNextNestedType();

   IBinaryAnnotation[] getAnnotationsAtCursor(int var1, boolean var2);
}
