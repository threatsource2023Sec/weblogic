package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryElementValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.ITypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;

public class NonNullDefaultAwareTypeAnnotationWalker extends TypeAnnotationWalker {
   private final int defaultNullness;
   private final boolean atDefaultLocation;
   private final boolean atTypeBound;
   private final boolean currentArrayContentIsNonNull;
   private final boolean isEmpty;
   private final IBinaryAnnotation nonNullAnnotation;
   private final LookupEnvironment environment;
   private boolean nextIsDefaultLocation;
   private boolean nextIsTypeBound;
   private boolean nextArrayContentIsNonNull;

   public NonNullDefaultAwareTypeAnnotationWalker(IBinaryTypeAnnotation[] typeAnnotations, int defaultNullness, LookupEnvironment environment) {
      super(typeAnnotations);
      this.nonNullAnnotation = getNonNullAnnotation(environment);
      this.defaultNullness = defaultNullness;
      this.environment = environment;
      this.atDefaultLocation = false;
      this.atTypeBound = false;
      this.isEmpty = false;
      this.currentArrayContentIsNonNull = false;
   }

   public NonNullDefaultAwareTypeAnnotationWalker(int defaultNullness, LookupEnvironment environment) {
      this(defaultNullness, getNonNullAnnotation(environment), false, false, environment, false);
   }

   NonNullDefaultAwareTypeAnnotationWalker(IBinaryTypeAnnotation[] typeAnnotations, long newMatches, int newPathPtr, int defaultNullness, IBinaryAnnotation nonNullAnnotation, boolean atDefaultLocation, boolean atTypeBound, LookupEnvironment environment, boolean currentArrayContentIsNonNull) {
      super(typeAnnotations, newMatches, newPathPtr);
      this.defaultNullness = defaultNullness;
      this.nonNullAnnotation = nonNullAnnotation;
      this.atDefaultLocation = atDefaultLocation;
      this.atTypeBound = atTypeBound;
      this.environment = environment;
      this.currentArrayContentIsNonNull = this.nextArrayContentIsNonNull = currentArrayContentIsNonNull;
      this.isEmpty = false;
   }

   NonNullDefaultAwareTypeAnnotationWalker(int defaultNullness, IBinaryAnnotation nonNullAnnotation, boolean atDefaultLocation, boolean atTypeBound, LookupEnvironment environment, boolean currentArrayContentIsNonNull) {
      super((IBinaryTypeAnnotation[])null, 0L, 0);
      this.nonNullAnnotation = nonNullAnnotation;
      this.defaultNullness = defaultNullness;
      this.atDefaultLocation = atDefaultLocation;
      this.atTypeBound = atTypeBound;
      this.isEmpty = true;
      this.environment = environment;
      this.currentArrayContentIsNonNull = this.nextArrayContentIsNonNull = currentArrayContentIsNonNull;
   }

   private static IBinaryAnnotation getNonNullAnnotation(LookupEnvironment environment) {
      final char[] nonNullAnnotationName = CharOperation.concat('L', CharOperation.concatWith(environment.getNonNullAnnotationName(), '/'), ';');
      return new IBinaryAnnotation() {
         public char[] getTypeName() {
            return nonNullAnnotationName;
         }

         public IBinaryElementValuePair[] getElementValuePairs() {
            return null;
         }
      };
   }

   protected TypeAnnotationWalker restrict(long newMatches, int newPathPtr) {
      NonNullDefaultAwareTypeAnnotationWalker var5;
      try {
         if (this.matches != newMatches || this.pathPtr != newPathPtr || this.atDefaultLocation != this.nextIsDefaultLocation || this.atTypeBound != this.nextIsTypeBound || this.currentArrayContentIsNonNull != this.nextArrayContentIsNonNull) {
            if (newMatches != 0L && this.typeAnnotations != null && this.typeAnnotations.length != 0) {
               var5 = new NonNullDefaultAwareTypeAnnotationWalker(this.typeAnnotations, newMatches, newPathPtr, this.defaultNullness, this.nonNullAnnotation, this.nextIsDefaultLocation, this.nextIsTypeBound, this.environment, this.nextArrayContentIsNonNull);
               return var5;
            }

            var5 = new NonNullDefaultAwareTypeAnnotationWalker(this.defaultNullness, this.nonNullAnnotation, this.nextIsDefaultLocation, this.nextIsTypeBound, this.environment, this.nextArrayContentIsNonNull);
            return var5;
         }

         var5 = this;
      } finally {
         this.nextIsDefaultLocation = false;
         this.nextIsTypeBound = false;
         this.nextArrayContentIsNonNull = this.currentArrayContentIsNonNull;
      }

      return var5;
   }

   public ITypeAnnotationWalker toSupertype(short index, char[] superTypeSignature) {
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toSupertype(index, superTypeSignature));
   }

   public ITypeAnnotationWalker toMethodParameter(short index) {
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toMethodParameter(index));
   }

   public ITypeAnnotationWalker toField() {
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toField());
   }

   public ITypeAnnotationWalker toMethodReturn() {
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toMethodReturn());
   }

   public ITypeAnnotationWalker toTypeBound(short boundIndex) {
      this.nextIsDefaultLocation = (this.defaultNullness & 256) != 0;
      this.nextIsTypeBound = true;
      this.nextArrayContentIsNonNull = false;
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toTypeBound(boundIndex));
   }

   public ITypeAnnotationWalker toWildcardBound() {
      this.nextIsDefaultLocation = (this.defaultNullness & 256) != 0;
      this.nextIsTypeBound = true;
      this.nextArrayContentIsNonNull = false;
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toWildcardBound());
   }

   public ITypeAnnotationWalker toTypeParameterBounds(boolean isClassTypeParameter, int parameterRank) {
      this.nextIsDefaultLocation = (this.defaultNullness & 256) != 0;
      this.nextIsTypeBound = true;
      this.nextArrayContentIsNonNull = false;
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toTypeParameterBounds(isClassTypeParameter, parameterRank));
   }

   public ITypeAnnotationWalker toTypeArgument(int rank) {
      this.nextIsDefaultLocation = (this.defaultNullness & 64) != 0;
      this.nextIsTypeBound = false;
      this.nextArrayContentIsNonNull = false;
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toTypeArgument(rank));
   }

   public ITypeAnnotationWalker toTypeParameter(boolean isClassTypeParameter, int rank) {
      this.nextIsDefaultLocation = (this.defaultNullness & 128) != 0;
      this.nextIsTypeBound = false;
      this.nextArrayContentIsNonNull = false;
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toTypeParameter(isClassTypeParameter, rank));
   }

   protected ITypeAnnotationWalker toNextDetail(int detailKind) {
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toNextDetail(detailKind));
   }

   public IBinaryAnnotation[] getAnnotationsAtCursor(int currentTypeId, boolean mayApplyArrayContentsDefaultNullness) {
      IBinaryAnnotation[] normalAnnotations = this.isEmpty ? NO_ANNOTATIONS : super.getAnnotationsAtCursor(currentTypeId, mayApplyArrayContentsDefaultNullness);
      if ((this.atDefaultLocation || mayApplyArrayContentsDefaultNullness && this.currentArrayContentIsNonNull) && currentTypeId != -1 && (!this.atTypeBound || currentTypeId != 1)) {
         if (normalAnnotations != null && normalAnnotations.length != 0) {
            if (this.environment.containsNullTypeAnnotation(normalAnnotations)) {
               return normalAnnotations;
            } else {
               int len = normalAnnotations.length;
               IBinaryAnnotation[] newAnnots = new IBinaryAnnotation[len + 1];
               System.arraycopy(normalAnnotations, 0, newAnnots, 0, len);
               newAnnots[len] = this.nonNullAnnotation;
               return newAnnots;
            }
         } else {
            return new IBinaryAnnotation[]{this.nonNullAnnotation};
         }
      } else {
         return normalAnnotations;
      }
   }

   public ITypeAnnotationWalker toNextArrayDimension() {
      boolean hasNNBDForArrayContents = (this.defaultNullness & 512) != 0;
      if (hasNNBDForArrayContents) {
         this.nextArrayContentIsNonNull = true;
      }

      this.nextIsDefaultLocation = false;
      this.nextIsTypeBound = false;
      return (ITypeAnnotationWalker)(this.isEmpty ? this.restrict(this.matches, this.pathPtr) : super.toNextArrayDimension());
   }

   public static ITypeAnnotationWalker updateWalkerForParamNonNullDefault(ITypeAnnotationWalker walker, int defaultNullness, LookupEnvironment environment) {
      if (environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && defaultNullness != 0) {
         if (defaultNullness == 2) {
            if (walker instanceof NonNullDefaultAwareTypeAnnotationWalker) {
               NonNullDefaultAwareTypeAnnotationWalker nonNullDefaultAwareTypeAnnotationWalker = (NonNullDefaultAwareTypeAnnotationWalker)walker;
               return new TypeAnnotationWalker(nonNullDefaultAwareTypeAnnotationWalker.typeAnnotations, nonNullDefaultAwareTypeAnnotationWalker.matches, nonNullDefaultAwareTypeAnnotationWalker.pathPtr);
            } else {
               return walker;
            }
         } else if (walker instanceof TypeAnnotationWalker) {
            TypeAnnotationWalker typeAnnotationWalker = (TypeAnnotationWalker)walker;
            IBinaryAnnotation nonNullAnnotation2;
            if (walker instanceof NonNullDefaultAwareTypeAnnotationWalker) {
               NonNullDefaultAwareTypeAnnotationWalker nonNullDefaultAwareTypeAnnotationWalker = (NonNullDefaultAwareTypeAnnotationWalker)walker;
               if (nonNullDefaultAwareTypeAnnotationWalker.isEmpty) {
                  return new NonNullDefaultAwareTypeAnnotationWalker(defaultNullness, environment);
               }

               nonNullAnnotation2 = nonNullDefaultAwareTypeAnnotationWalker.nonNullAnnotation;
            } else {
               nonNullAnnotation2 = getNonNullAnnotation(environment);
            }

            return new NonNullDefaultAwareTypeAnnotationWalker(typeAnnotationWalker.typeAnnotations, typeAnnotationWalker.matches, typeAnnotationWalker.pathPtr, defaultNullness, nonNullAnnotation2, false, false, environment, false);
         } else {
            return new NonNullDefaultAwareTypeAnnotationWalker(defaultNullness, environment);
         }
      } else {
         return walker;
      }
   }
}
