package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;

public class ParameterizedSingleTypeReference extends ArrayTypeReference {
   public static final TypeBinding[] DIAMOND_TYPE_ARGUMENTS = new TypeBinding[0];
   public TypeReference[] typeArguments;

   public ParameterizedSingleTypeReference(char[] name, TypeReference[] typeArguments, int dim, long pos) {
      super(name, dim, pos);
      this.originalSourceEnd = this.sourceEnd;
      this.typeArguments = typeArguments;
      int i = 0;

      for(int max = typeArguments.length; i < max; ++i) {
         if ((typeArguments[i].bits & 1048576) != 0) {
            this.bits |= 1048576;
            break;
         }
      }

   }

   public ParameterizedSingleTypeReference(char[] name, TypeReference[] typeArguments, int dim, Annotation[][] annotationsOnDimensions, long pos) {
      this(name, typeArguments, dim, pos);
      this.setAnnotationsOnDimensions(annotationsOnDimensions);
      if (annotationsOnDimensions != null) {
         this.bits |= 1048576;
      }

   }

   public void checkBounds(Scope scope) {
      if (this.resolvedType != null) {
         if (this.resolvedType.leafComponentType() instanceof ParameterizedTypeBinding) {
            ParameterizedTypeBinding parameterizedType = (ParameterizedTypeBinding)this.resolvedType.leafComponentType();
            TypeBinding[] argTypes = parameterizedType.arguments;
            if (argTypes != null) {
               parameterizedType.boundCheck(scope, this.typeArguments);
            }
         }

      }
   }

   public TypeReference augmentTypeWithAdditionalDimensions(int additionalDimensions, Annotation[][] additionalAnnotations, boolean isVarargs) {
      int totalDimensions = this.dimensions() + additionalDimensions;
      Annotation[][] allAnnotations = this.getMergedAnnotationsOnDimensions(additionalDimensions, additionalAnnotations);
      ParameterizedSingleTypeReference parameterizedSingleTypeReference = new ParameterizedSingleTypeReference(this.token, this.typeArguments, totalDimensions, allAnnotations, ((long)this.sourceStart << 32) + (long)this.sourceEnd);
      parameterizedSingleTypeReference.annotations = this.annotations;
      parameterizedSingleTypeReference.bits |= this.bits & 1048576;
      if (!isVarargs) {
         parameterizedSingleTypeReference.extendedDimensions = additionalDimensions;
      }

      return parameterizedSingleTypeReference;
   }

   public char[][] getParameterizedTypeName() {
      StringBuffer buffer = new StringBuffer(5);
      buffer.append(this.token).append('<');
      int i = 0;

      for(int length = this.typeArguments.length; i < length; ++i) {
         if (i > 0) {
            buffer.append(',');
         }

         buffer.append(CharOperation.concatWith(this.typeArguments[i].getParameterizedTypeName(), '.'));
      }

      buffer.append('>');
      i = buffer.length();
      char[] name = new char[i];
      buffer.getChars(0, i, name, 0);
      int dim = this.dimensions;
      if (dim > 0) {
         char[] dimChars = new char[dim * 2];

         for(int i = 0; i < dim; ++i) {
            int index = i * 2;
            dimChars[index] = '[';
            dimChars[index + 1] = ']';
         }

         name = CharOperation.concat(name, dimChars);
      }

      return new char[][]{name};
   }

   public TypeReference[][] getTypeArguments() {
      return new TypeReference[][]{this.typeArguments};
   }

   protected TypeBinding getTypeBinding(Scope scope) {
      return null;
   }

   public boolean isParameterizedTypeReference() {
      return true;
   }

   public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition position) {
      if (super.hasNullTypeAnnotation(position)) {
         return true;
      } else {
         if (position == TypeReference.AnnotationPosition.ANY) {
            if (this.resolvedType != null && !this.resolvedType.hasNullTypeAnnotations()) {
               return false;
            }

            if (this.typeArguments != null) {
               for(int i = 0; i < this.typeArguments.length; ++i) {
                  if (this.typeArguments[i].hasNullTypeAnnotation(position)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private TypeBinding internalResolveType(Scope scope, ReferenceBinding enclosingType, boolean checkBounds, int location) {
      this.constant = Constant.NotAConstant;
      TypeBinding type;
      if ((this.bits & 262144) != 0 && this.resolvedType != null) {
         if (this.resolvedType.isValidBinding()) {
            return this.resolvedType;
         } else {
            switch (this.resolvedType.problemId()) {
               case 1:
               case 2:
               case 5:
                  type = this.resolvedType.closestMatch();
                  return type;
               case 3:
               case 4:
               default:
                  return null;
            }
         }
      } else {
         this.bits |= 262144;
         type = this.internalResolveLeafType(scope, enclosingType, checkBounds);
         if (type == null) {
            this.resolvedType = this.createArrayType(scope, this.resolvedType);
            this.resolveAnnotations(scope, 0);
            return null;
         } else {
            type = this.createArrayType(scope, type);
            if (!this.resolvedType.isValidBinding() && this.resolvedType.dimensions() == type.dimensions()) {
               this.resolveAnnotations(scope, 0);
               return type;
            } else {
               this.resolvedType = type;
               this.resolveAnnotations(scope, location);
               if (this.dimensions > 0) {
                  this.resolvedType = ArrayTypeReference.maybeMarkArrayContentsNonNull(scope, this.resolvedType, this.sourceStart, this.dimensions, (leafType) -> {
                     this.leafComponentTypeWithoutDefaultNullness = leafType;
                  });
               }

               return this.resolvedType;
            }
         }
      }
   }

   private TypeBinding internalResolveLeafType(Scope scope, ReferenceBinding enclosingType, boolean checkBounds) {
      ReferenceBinding currentType;
      int argLength;
      if (enclosingType != null) {
         this.resolvedType = currentType = scope.getMemberType(this.token, enclosingType);
         if (!this.resolvedType.isValidBinding()) {
            scope.problemReporter().invalidEnclosingType(this, currentType, enclosingType);
            return null;
         }

         if (this.isTypeUseDeprecated(currentType, scope)) {
            scope.problemReporter().deprecatedType(currentType, this);
         }

         ReferenceBinding currentEnclosing = currentType.enclosingType();
         if (currentEnclosing != null && TypeBinding.notEquals(currentEnclosing.erasure(), enclosingType.erasure())) {
            enclosingType = currentEnclosing;
         }
      } else {
         label173: {
            label147: {
               this.resolvedType = scope.getType(this.token);
               if (this.resolvedType.isValidBinding()) {
                  currentType = (ReferenceBinding)this.resolvedType;
               } else {
                  this.reportInvalidType(scope);
                  switch (this.resolvedType.problemId()) {
                     case 1:
                     case 2:
                     case 5:
                        TypeBinding type = this.resolvedType.closestMatch();
                        if (type instanceof ReferenceBinding) {
                           currentType = (ReferenceBinding)type;
                           break;
                        }
                     case 3:
                     case 4:
                     default:
                        break label147;
                  }
               }

               enclosingType = currentType.enclosingType();
               if (enclosingType != null && currentType.hasEnclosingInstanceContext()) {
                  enclosingType = scope.environment().convertToParameterizedType(enclosingType);
               }
               break label173;
            }

            boolean isClassScope = scope.kind == 3;
            int argLength = this.typeArguments.length;

            for(argLength = 0; argLength < argLength; ++argLength) {
               TypeReference typeArgument = this.typeArguments[argLength];
               if (isClassScope) {
                  typeArgument.resolveType((ClassScope)scope);
               } else {
                  typeArgument.resolveType((BlockScope)scope, checkBounds);
               }
            }

            return null;
         }
      }

      boolean isClassScope = scope.kind == 3;
      TypeReference keep = null;
      if (isClassScope) {
         keep = ((ClassScope)scope).superTypeReference;
         ((ClassScope)scope).superTypeReference = null;
      }

      boolean isDiamond = (this.bits & 524288) != 0;
      argLength = this.typeArguments.length;
      TypeBinding[] argTypes = new TypeBinding[argLength];
      boolean argHasError = false;
      ReferenceBinding currentOriginal = (ReferenceBinding)currentType.original();

      for(int i = 0; i < argLength; ++i) {
         TypeReference typeArgument = this.typeArguments[i];
         TypeBinding argType = isClassScope ? typeArgument.resolveTypeArgument((ClassScope)scope, currentOriginal, i) : typeArgument.resolveTypeArgument((BlockScope)scope, currentOriginal, i);
         this.bits |= typeArgument.bits & 1048576;
         if (argType == null) {
            argHasError = true;
         } else {
            argTypes[i] = argType;
         }
      }

      if (argHasError) {
         return null;
      } else {
         if (isClassScope) {
            ((ClassScope)scope).superTypeReference = keep;
            if (((ClassScope)scope).detectHierarchyCycle(currentOriginal, this)) {
               return null;
            }
         }

         TypeVariableBinding[] typeVariables = currentOriginal.typeVariables();
         if (typeVariables == Binding.NO_TYPE_VARIABLES) {
            boolean isCompliant15 = scope.compilerOptions().originalSourceLevel >= 3211264L;
            if ((currentOriginal.tagBits & 128L) == 0L && isCompliant15) {
               this.resolvedType = currentType;
               scope.problemReporter().nonGenericTypeCannotBeParameterized(0, this, currentType, argTypes);
               return null;
            }

            if (!isCompliant15) {
               if (!this.resolvedType.isValidBinding()) {
                  return currentType;
               }

               return this.resolvedType = currentType;
            }
         } else if (argLength != typeVariables.length) {
            if (!isDiamond) {
               scope.problemReporter().incorrectArityForParameterizedType(this, currentType, argTypes);
               return null;
            }
         } else if (!currentType.isStatic()) {
            ReferenceBinding actualEnclosing = currentType.enclosingType();
            if (actualEnclosing != null && actualEnclosing.isRawType()) {
               scope.problemReporter().rawMemberTypeCannotBeParameterized(this, scope.environment().createRawType(currentOriginal, actualEnclosing), argTypes);
               return null;
            }
         }

         ParameterizedTypeBinding parameterizedType = scope.environment().createParameterizedType(currentOriginal, argTypes, enclosingType);
         if (!isDiamond) {
            if (checkBounds) {
               parameterizedType.boundCheck(scope, this.typeArguments);
            } else {
               scope.deferBoundCheck(this);
            }
         } else {
            parameterizedType.arguments = DIAMOND_TYPE_ARGUMENTS;
         }

         if (this.isTypeUseDeprecated(parameterizedType, scope)) {
            this.reportDeprecatedType(parameterizedType, scope);
         }

         this.checkIllegalNullAnnotations(scope, this.typeArguments);
         if (!this.resolvedType.isValidBinding()) {
            return parameterizedType;
         } else {
            return this.resolvedType = parameterizedType;
         }
      }
   }

   private TypeBinding createArrayType(Scope scope, TypeBinding type) {
      if (this.dimensions > 0) {
         if (this.dimensions > 255) {
            scope.problemReporter().tooManyDimensions(this);
         }

         return scope.createArrayType(type, this.dimensions);
      } else {
         return type;
      }
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      if (this.annotations != null && this.annotations[0] != null) {
         printAnnotations(this.annotations[0], output);
         output.append(' ');
      }

      output.append(this.token);
      output.append("<");
      int length = this.typeArguments.length;
      int i;
      if (length > 0) {
         int max = length - 1;

         for(i = 0; i < max; ++i) {
            this.typeArguments[i].print(0, output);
            output.append(", ");
         }

         this.typeArguments[max].print(0, output);
      }

      output.append(">");
      Annotation[][] annotationsOnDimensions = this.getAnnotationsOnDimensions();
      if ((this.bits & 16384) != 0) {
         for(i = 0; i < this.dimensions - 1; ++i) {
            if (annotationsOnDimensions != null && annotationsOnDimensions[i] != null) {
               output.append(" ");
               printAnnotations(annotationsOnDimensions[i], output);
               output.append(" ");
            }

            output.append("[]");
         }

         if (annotationsOnDimensions != null && annotationsOnDimensions[this.dimensions - 1] != null) {
            output.append(" ");
            printAnnotations(annotationsOnDimensions[this.dimensions - 1], output);
            output.append(" ");
         }

         output.append("...");
      } else {
         for(i = 0; i < this.dimensions; ++i) {
            if (annotationsOnDimensions != null && annotationsOnDimensions[i] != null) {
               output.append(" ");
               printAnnotations(annotationsOnDimensions[i], output);
               output.append(" ");
            }

            output.append("[]");
         }
      }

      return output;
   }

   public TypeBinding resolveType(BlockScope scope, boolean checkBounds, int location) {
      return this.internalResolveType(scope, (ReferenceBinding)null, checkBounds, location);
   }

   public TypeBinding resolveType(ClassScope scope, int location) {
      return this.internalResolveType(scope, (ReferenceBinding)null, false, location);
   }

   public TypeBinding resolveTypeEnclosing(BlockScope scope, ReferenceBinding enclosingType) {
      return this.internalResolveType(scope, enclosingType, true, 0);
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int max;
         if (this.annotations != null) {
            Annotation[] typeAnnotations = this.annotations[0];
            i = 0;

            for(max = typeAnnotations == null ? 0 : typeAnnotations.length; i < max; ++i) {
               typeAnnotations[i].traverse(visitor, scope);
            }
         }

         Annotation[][] annotationsOnDimensions = this.getAnnotationsOnDimensions(true);
         if (annotationsOnDimensions != null) {
            i = 0;

            for(max = annotationsOnDimensions.length; i < max; ++i) {
               Annotation[] annotations2 = annotationsOnDimensions[i];
               if (annotations2 != null) {
                  int j = 0;

                  for(int max2 = annotations2.length; j < max2; ++j) {
                     Annotation annotation = annotations2[j];
                     annotation.traverse(visitor, scope);
                  }
               }
            }
         }

         i = 0;

         for(max = this.typeArguments.length; i < max; ++i) {
            this.typeArguments[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int max;
         if (this.annotations != null) {
            Annotation[] typeAnnotations = this.annotations[0];
            i = 0;

            for(max = typeAnnotations == null ? 0 : typeAnnotations.length; i < max; ++i) {
               typeAnnotations[i].traverse(visitor, scope);
            }
         }

         Annotation[][] annotationsOnDimensions = this.getAnnotationsOnDimensions(true);
         if (annotationsOnDimensions != null) {
            i = 0;

            for(max = annotationsOnDimensions.length; i < max; ++i) {
               Annotation[] annotations2 = annotationsOnDimensions[i];
               int j = 0;

               for(int max2 = annotations2.length; j < max2; ++j) {
                  Annotation annotation = annotations2[j];
                  annotation.traverse(visitor, scope);
               }
            }
         }

         i = 0;

         for(max = this.typeArguments.length; i < max; ++i) {
            this.typeArguments[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }
}
