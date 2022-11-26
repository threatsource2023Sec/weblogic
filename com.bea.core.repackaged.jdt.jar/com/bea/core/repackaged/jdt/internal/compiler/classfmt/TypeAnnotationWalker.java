package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.ITypeAnnotationWalker;

public class TypeAnnotationWalker implements ITypeAnnotationWalker {
   protected final IBinaryTypeAnnotation[] typeAnnotations;
   protected final long matches;
   protected final int pathPtr;

   public TypeAnnotationWalker(IBinaryTypeAnnotation[] typeAnnotations) {
      this(typeAnnotations, -1L >>> 64 - typeAnnotations.length);
   }

   TypeAnnotationWalker(IBinaryTypeAnnotation[] typeAnnotations, long matchBits) {
      this(typeAnnotations, matchBits, 0);
   }

   protected TypeAnnotationWalker(IBinaryTypeAnnotation[] typeAnnotations, long matchBits, int pathPtr) {
      this.typeAnnotations = typeAnnotations;
      this.matches = matchBits;
      this.pathPtr = pathPtr;
   }

   protected ITypeAnnotationWalker restrict(long newMatches, int newPathPtr) {
      if (this.matches == newMatches && this.pathPtr == newPathPtr) {
         return this;
      } else {
         return (ITypeAnnotationWalker)(newMatches != 0L && this.typeAnnotations != null && this.typeAnnotations.length != 0 ? new TypeAnnotationWalker(this.typeAnnotations, newMatches, newPathPtr) : EMPTY_ANNOTATION_WALKER);
      }
   }

   public ITypeAnnotationWalker toField() {
      return this.toTarget(19);
   }

   public ITypeAnnotationWalker toMethodReturn() {
      return this.toTarget(20);
   }

   public ITypeAnnotationWalker toReceiver() {
      return this.toTarget(21);
   }

   protected ITypeAnnotationWalker toTarget(int targetType) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            if (this.typeAnnotations[i].getTargetType() != targetType) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, 0);
      }
   }

   public ITypeAnnotationWalker toTypeParameter(boolean isClassTypeParameter, int rank) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int targetType = isClassTypeParameter ? 0 : 1;
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            if (candidate.getTargetType() != targetType || candidate.getTypeParameterIndex() != rank) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, 0);
      }
   }

   public ITypeAnnotationWalker toTypeParameterBounds(boolean isClassTypeParameter, int parameterRank) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         int targetType = isClassTypeParameter ? 17 : 18;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            if (candidate.getTargetType() != targetType || (short)candidate.getTypeParameterIndex() != parameterRank) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, 0);
      }
   }

   public ITypeAnnotationWalker toTypeBound(short boundIndex) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            if ((short)candidate.getBoundIndex() != boundIndex) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, 0);
      }
   }

   public ITypeAnnotationWalker toSupertype(short index, char[] superTypeSignature) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            if (candidate.getTargetType() != 16 || (short)candidate.getSupertypeIndex() != index) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, 0);
      }
   }

   public ITypeAnnotationWalker toMethodParameter(short index) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            if (candidate.getTargetType() != 22 || (short)candidate.getMethodFormalParameterIndex() != index) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, 0);
      }
   }

   public ITypeAnnotationWalker toThrows(int index) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            if (candidate.getTargetType() != 23 || candidate.getThrowsTypeIndex() != index) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, 0);
      }
   }

   public ITypeAnnotationWalker toTypeArgument(int rank) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            int[] path = candidate.getTypePath();
            if (this.pathPtr >= path.length || path[this.pathPtr] != 3 || path[this.pathPtr + 1] != rank) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, this.pathPtr + 2);
      }
   }

   public ITypeAnnotationWalker toWildcardBound() {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return EMPTY_ANNOTATION_WALKER;
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            int[] path = candidate.getTypePath();
            if (this.pathPtr >= path.length || path[this.pathPtr] != 2) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, this.pathPtr + 2);
      }
   }

   public ITypeAnnotationWalker toNextArrayDimension() {
      return this.toNextDetail(0);
   }

   public ITypeAnnotationWalker toNextNestedType() {
      return this.toNextDetail(1);
   }

   protected ITypeAnnotationWalker toNextDetail(int detailKind) {
      long newMatches = this.matches;
      if (newMatches == 0L) {
         return this.restrict(newMatches, this.pathPtr + 2);
      } else {
         int length = this.typeAnnotations.length;
         long mask = 1L;

         for(int i = 0; i < length; mask <<= 1) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            int[] path = candidate.getTypePath();
            if (this.pathPtr >= path.length || path[this.pathPtr] != detailKind) {
               newMatches &= ~mask;
            }

            ++i;
         }

         return this.restrict(newMatches, this.pathPtr + 2);
      }
   }

   public IBinaryAnnotation[] getAnnotationsAtCursor(int currentTypeId, boolean mayApplyArrayContentsDefaultNullness) {
      int length = this.typeAnnotations.length;
      IBinaryAnnotation[] filtered = new IBinaryAnnotation[length];
      long ptr = 1L;
      int count = 0;

      for(int i = 0; i < length; ptr <<= 1) {
         if ((this.matches & ptr) != 0L) {
            IBinaryTypeAnnotation candidate = this.typeAnnotations[i];
            if (candidate.getTypePath().length <= this.pathPtr) {
               filtered[count++] = candidate.getAnnotation();
            }
         }

         ++i;
      }

      if (count == 0) {
         return NO_ANNOTATIONS;
      } else {
         if (count < length) {
            System.arraycopy(filtered, 0, filtered = new IBinaryAnnotation[count], 0, count);
         }

         return filtered;
      }
   }
}
