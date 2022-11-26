package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.function.Consumer;

public class ArrayTypeReference extends SingleTypeReference {
   public int dimensions;
   private Annotation[][] annotationsOnDimensions;
   public int originalSourceEnd;
   public int extendedDimensions;
   public TypeBinding leafComponentTypeWithoutDefaultNullness;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition;

   public ArrayTypeReference(char[] source, int dimensions, long pos) {
      super(source, pos);
      this.originalSourceEnd = this.sourceEnd;
      this.dimensions = dimensions;
      this.annotationsOnDimensions = null;
   }

   public ArrayTypeReference(char[] source, int dimensions, Annotation[][] annotationsOnDimensions, long pos) {
      this(source, dimensions, pos);
      if (annotationsOnDimensions != null) {
         this.bits |= 1048576;
      }

      this.annotationsOnDimensions = annotationsOnDimensions;
   }

   public int dimensions() {
      return this.dimensions;
   }

   public int extraDimensions() {
      return this.extendedDimensions;
   }

   public Annotation[][] getAnnotationsOnDimensions(boolean useSourceOrder) {
      if (!useSourceOrder && this.annotationsOnDimensions != null && this.annotationsOnDimensions.length != 0 && this.extendedDimensions != 0 && this.extendedDimensions != this.dimensions) {
         Annotation[][] externalAnnotations = new Annotation[this.dimensions][];
         int baseDimensions = this.dimensions - this.extendedDimensions;
         System.arraycopy(this.annotationsOnDimensions, baseDimensions, externalAnnotations, 0, this.extendedDimensions);
         System.arraycopy(this.annotationsOnDimensions, 0, externalAnnotations, this.extendedDimensions, baseDimensions);
         return externalAnnotations;
      } else {
         return this.annotationsOnDimensions;
      }
   }

   public void setAnnotationsOnDimensions(Annotation[][] annotationsOnDimensions) {
      this.annotationsOnDimensions = annotationsOnDimensions;
   }

   public char[][] getParameterizedTypeName() {
      int dim = this.dimensions;
      char[] dimChars = new char[dim * 2];

      for(int i = 0; i < dim; ++i) {
         int index = i * 2;
         dimChars[index] = '[';
         dimChars[index + 1] = ']';
      }

      return new char[][]{CharOperation.concat(this.token, dimChars)};
   }

   protected TypeBinding getTypeBinding(Scope scope) {
      if (this.resolvedType != null) {
         return this.resolvedType;
      } else {
         if (this.dimensions > 255) {
            scope.problemReporter().tooManyDimensions(this);
         }

         TypeBinding leafComponentType = scope.getType(this.token);
         return scope.createArrayType(leafComponentType, this.dimensions);
      }
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      super.printExpression(indent, output);
      int i;
      if ((this.bits & 16384) != 0) {
         for(i = 0; i < this.dimensions - 1; ++i) {
            if (this.annotationsOnDimensions != null && this.annotationsOnDimensions[i] != null) {
               output.append(' ');
               printAnnotations(this.annotationsOnDimensions[i], output);
               output.append(' ');
            }

            output.append("[]");
         }

         if (this.annotationsOnDimensions != null && this.annotationsOnDimensions[this.dimensions - 1] != null) {
            output.append(' ');
            printAnnotations(this.annotationsOnDimensions[this.dimensions - 1], output);
            output.append(' ');
         }

         output.append("...");
      } else {
         for(i = 0; i < this.dimensions; ++i) {
            if (this.annotationsOnDimensions != null && this.annotationsOnDimensions[i] != null) {
               output.append(" ");
               printAnnotations(this.annotationsOnDimensions[i], output);
               output.append(" ");
            }

            output.append("[]");
         }
      }

      return output;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int max;
         if (this.annotations != null) {
            Annotation[] typeAnnotations = this.annotations[0];
            max = 0;

            for(int length = typeAnnotations == null ? 0 : typeAnnotations.length; max < length; ++max) {
               typeAnnotations[max].traverse(visitor, scope);
            }
         }

         if (this.annotationsOnDimensions != null) {
            int i = 0;

            for(max = this.annotationsOnDimensions.length; i < max; ++i) {
               Annotation[] annotations2 = this.annotationsOnDimensions[i];
               if (annotations2 != null) {
                  int j = 0;

                  for(int max2 = annotations2.length; j < max2; ++j) {
                     Annotation annotation = annotations2[j];
                     annotation.traverse(visitor, scope);
                  }
               }
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         int max;
         if (this.annotations != null) {
            Annotation[] typeAnnotations = this.annotations[0];
            max = 0;

            for(int length = typeAnnotations == null ? 0 : typeAnnotations.length; max < length; ++max) {
               typeAnnotations[max].traverse(visitor, scope);
            }
         }

         if (this.annotationsOnDimensions != null) {
            int i = 0;

            for(max = this.annotationsOnDimensions.length; i < max; ++i) {
               Annotation[] annotations2 = this.annotationsOnDimensions[i];
               if (annotations2 != null) {
                  int j = 0;

                  for(int max2 = annotations2.length; j < max2; ++j) {
                     Annotation annotation = annotations2[j];
                     annotation.traverse(visitor, scope);
                  }
               }
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   protected TypeBinding internalResolveType(Scope scope, int location) {
      TypeBinding internalResolveType = super.internalResolveType(scope, location);
      internalResolveType = maybeMarkArrayContentsNonNull(scope, internalResolveType, this.sourceStart, this.dimensions, (leafType) -> {
         this.leafComponentTypeWithoutDefaultNullness = leafType;
      });
      return internalResolveType;
   }

   static TypeBinding maybeMarkArrayContentsNonNull(Scope scope, TypeBinding typeBinding, int sourceStart, int dimensions, Consumer leafConsumer) {
      LookupEnvironment environment = scope.environment();
      if (environment.usesNullTypeAnnotations() && scope.hasDefaultNullnessFor(512, sourceStart)) {
         AnnotationBinding nonNullAnnotation = environment.getNonNullAnnotation();
         typeBinding = addNonNullToDimensions(scope, (TypeBinding)typeBinding, nonNullAnnotation, dimensions);
         TypeBinding leafComponentType = ((TypeBinding)typeBinding).leafComponentType();
         if ((leafComponentType.tagBits & 108086391056891904L) == 0L && leafComponentType.acceptsNonNullDefault()) {
            if (leafConsumer != null) {
               leafConsumer.accept(leafComponentType);
            }

            TypeBinding nonNullLeafComponentType = scope.environment().createAnnotatedType(leafComponentType, new AnnotationBinding[]{nonNullAnnotation});
            typeBinding = scope.createArrayType(nonNullLeafComponentType, ((TypeBinding)typeBinding).dimensions(), ((TypeBinding)typeBinding).getTypeAnnotations());
         }
      }

      return (TypeBinding)typeBinding;
   }

   static TypeBinding addNonNullToDimensions(Scope scope, TypeBinding typeBinding, AnnotationBinding nonNullAnnotation, int dimensions2) {
      AnnotationBinding[][] newAnnots = new AnnotationBinding[dimensions2][];
      AnnotationBinding[] oldAnnots = typeBinding.getTypeAnnotations();
      int j;
      if (oldAnnots == null) {
         for(j = 1; j < dimensions2; ++j) {
            newAnnots[j] = new AnnotationBinding[]{nonNullAnnotation};
         }
      } else {
         j = 0;

         for(int i = 0; i < dimensions2; ++i) {
            if (j < oldAnnots.length && oldAnnots[j] != null) {
               int k = j;

               boolean seen;
               for(seen = false; oldAnnots[k] != null; ++k) {
                  seen |= oldAnnots[k].getAnnotationType().hasNullBit(96);
               }

               AnnotationBinding[] annotationsForDimension;
               if (!seen && i != 0) {
                  annotationsForDimension = new AnnotationBinding[k - j + 1];
                  annotationsForDimension[0] = nonNullAnnotation;
                  System.arraycopy(oldAnnots, j, annotationsForDimension, 1, k - j);
                  newAnnots[i] = annotationsForDimension;
               } else if (k > j) {
                  annotationsForDimension = new AnnotationBinding[k - j];
                  System.arraycopy(oldAnnots, j, annotationsForDimension, 0, k - j);
                  newAnnots[i] = annotationsForDimension;
               }

               j = k + 1;
            } else {
               if (i != 0) {
                  newAnnots[i] = new AnnotationBinding[]{nonNullAnnotation};
               }

               ++j;
            }
         }
      }

      return scope.environment().createAnnotatedType(typeBinding, newAnnots);
   }

   public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition position) {
      switch (position) {
         case MAIN_TYPE:
            if (this.annotationsOnDimensions != null && this.annotationsOnDimensions.length > 0) {
               Annotation[] innerAnnotations = this.annotationsOnDimensions[0];
               return containsNullAnnotation(innerAnnotations);
            }

            return super.hasNullTypeAnnotation(position);
         case LEAF_TYPE:
            return super.hasNullTypeAnnotation(position);
         case ANY:
            if (super.hasNullTypeAnnotation(position)) {
               return true;
            } else if (this.resolvedType != null && !this.resolvedType.hasNullTypeAnnotations()) {
               return false;
            } else if (this.annotationsOnDimensions != null) {
               for(int i = 0; i < this.annotationsOnDimensions.length; ++i) {
                  Annotation[] innerAnnotations = this.annotationsOnDimensions[i];
                  if (containsNullAnnotation(innerAnnotations)) {
                     return true;
                  }
               }
            }
         default:
            return false;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition() {
      int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[TypeReference.AnnotationPosition.values().length];

         try {
            var0[TypeReference.AnnotationPosition.ANY.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TypeReference.AnnotationPosition.LEAF_TYPE.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[TypeReference.AnnotationPosition.MAIN_TYPE.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition = var0;
         return var0;
      }
   }
}
