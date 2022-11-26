package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.function.Consumer;

public class ParameterizedQualifiedTypeReference extends ArrayQualifiedTypeReference {
   public TypeReference[][] typeArguments;
   ReferenceBinding[] typesPerToken;

   public ParameterizedQualifiedTypeReference(char[][] tokens, TypeReference[][] typeArguments, int dim, long[] positions) {
      super(tokens, dim, positions);
      this.typeArguments = typeArguments;
      int i = 0;

      for(int max = typeArguments.length; i < max; ++i) {
         TypeReference[] typeArgumentsOnTypeComponent = typeArguments[i];
         if (typeArgumentsOnTypeComponent != null) {
            int j = 0;

            for(int max2 = typeArgumentsOnTypeComponent.length; j < max2; ++j) {
               if ((typeArgumentsOnTypeComponent[j].bits & 1048576) != 0) {
                  this.bits |= 1048576;
                  return;
               }
            }
         }
      }

   }

   public ParameterizedQualifiedTypeReference(char[][] tokens, TypeReference[][] typeArguments, int dim, Annotation[][] annotationsOnDimensions, long[] positions) {
      this(tokens, typeArguments, dim, positions);
      this.setAnnotationsOnDimensions(annotationsOnDimensions);
      if (annotationsOnDimensions != null) {
         this.bits |= 1048576;
      }

   }

   public void checkBounds(Scope scope) {
      if (this.resolvedType != null && this.resolvedType.isValidBinding()) {
         this.checkBounds((ReferenceBinding)this.resolvedType.leafComponentType(), scope, this.typeArguments.length - 1);
      }
   }

   public void checkBounds(ReferenceBinding type, Scope scope, int index) {
      if (index > 0) {
         ReferenceBinding enclosingType = this.typesPerToken[index - 1];
         if (enclosingType != null) {
            this.checkBounds(enclosingType, scope, index - 1);
         }
      }

      if (type.isParameterizedTypeWithActualArguments()) {
         ParameterizedTypeBinding parameterizedType = (ParameterizedTypeBinding)type;
         ReferenceBinding currentType = parameterizedType.genericType();
         TypeVariableBinding[] typeVariables = currentType.typeVariables();
         if (typeVariables != null) {
            parameterizedType.boundCheck(scope, this.typeArguments[index]);
         }
      }

   }

   public TypeReference augmentTypeWithAdditionalDimensions(int additionalDimensions, Annotation[][] additionalAnnotations, boolean isVarargs) {
      int totalDimensions = this.dimensions() + additionalDimensions;
      Annotation[][] allAnnotations = this.getMergedAnnotationsOnDimensions(additionalDimensions, additionalAnnotations);
      ParameterizedQualifiedTypeReference pqtr = new ParameterizedQualifiedTypeReference(this.tokens, this.typeArguments, totalDimensions, allAnnotations, this.sourcePositions);
      pqtr.annotations = this.annotations;
      pqtr.bits |= this.bits & 1048576;
      if (!isVarargs) {
         pqtr.extendedDimensions = additionalDimensions;
      }

      return pqtr;
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
                  TypeReference[] arguments = this.typeArguments[i];
                  if (arguments != null) {
                     for(int j = 0; j < arguments.length; ++j) {
                        if (arguments[j].hasNullTypeAnnotation(position)) {
                           return true;
                        }
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   public char[][] getParameterizedTypeName() {
      int length = this.tokens.length;
      char[][] qParamName = new char[length][];

      int i;
      int j;
      for(i = 0; i < length; ++i) {
         TypeReference[] arguments = this.typeArguments[i];
         if (arguments == null) {
            qParamName[i] = this.tokens[i];
         } else {
            StringBuffer buffer = new StringBuffer(5);
            buffer.append(this.tokens[i]);
            buffer.append('<');
            j = 0;

            for(int argLength = arguments.length; j < argLength; ++j) {
               if (j > 0) {
                  buffer.append(',');
               }

               buffer.append(CharOperation.concatWith(arguments[j].getParameterizedTypeName(), '.'));
            }

            buffer.append('>');
            j = buffer.length();
            qParamName[i] = new char[j];
            buffer.getChars(0, j, qParamName[i], 0);
         }
      }

      i = this.dimensions;
      if (i > 0) {
         char[] dimChars = new char[i * 2];

         for(int i = 0; i < i; ++i) {
            j = i * 2;
            dimChars[j] = '[';
            dimChars[j + 1] = ']';
         }

         qParamName[length - 1] = CharOperation.concat(qParamName[length - 1], dimChars);
      }

      return qParamName;
   }

   public TypeReference[][] getTypeArguments() {
      return this.typeArguments;
   }

   protected TypeBinding getTypeBinding(Scope scope) {
      return null;
   }

   private TypeBinding internalResolveType(Scope scope, boolean checkBounds, int location) {
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
         type = this.internalResolveLeafType(scope, checkBounds);
         this.createArrayType(scope);
         this.resolveAnnotations(scope, location);
         if (this.dimensions > 0) {
            this.resolvedType = ArrayTypeReference.maybeMarkArrayContentsNonNull(scope, this.resolvedType, this.sourceStart, this.dimensions, (Consumer)null);
         }

         if (this.typeArguments != null) {
            this.checkIllegalNullAnnotations(scope, this.typeArguments[this.typeArguments.length - 1]);
         }

         return type == null ? type : this.resolvedType;
      }
   }

   private TypeBinding internalResolveLeafType(Scope scope, boolean checkBounds) {
      boolean isClassScope = scope.kind == 3;
      Binding binding = scope.getPackage(this.tokens);
      int max;
      int i;
      if (binding != null && !binding.isValidBinding()) {
         this.resolvedType = (ReferenceBinding)binding;
         this.reportInvalidType(scope);
         int i = 0;

         for(int max = this.tokens.length; i < max; ++i) {
            TypeReference[] args = this.typeArguments[i];
            if (args != null) {
               max = args.length;

               for(i = 0; i < max; ++i) {
                  TypeReference typeArgument = args[i];
                  if (isClassScope) {
                     typeArgument.resolveType((ClassScope)scope);
                  } else {
                     typeArgument.resolveType((BlockScope)scope, checkBounds);
                  }
               }
            }
         }

         return null;
      } else {
         PackageBinding packageBinding = binding == null ? null : (PackageBinding)binding;
         this.rejectAnnotationsOnPackageQualifiers(scope, packageBinding);
         boolean typeIsConsistent = true;
         ReferenceBinding qualifyingType = null;
         max = this.tokens.length;
         this.typesPerToken = new ReferenceBinding[max];

         for(i = packageBinding == null ? 0 : packageBinding.compoundName.length; i < max; ++i) {
            this.findNextTypeBinding(i, scope, packageBinding);
            int argLength;
            TypeReference[] args;
            if (!this.resolvedType.isValidBinding()) {
               this.reportInvalidType(scope);

               for(int j = i; j < max; ++j) {
                  args = this.typeArguments[j];
                  if (args != null) {
                     int argLength = args.length;

                     for(argLength = 0; argLength < argLength; ++argLength) {
                        TypeReference typeArgument = args[argLength];
                        if (isClassScope) {
                           typeArgument.resolveType((ClassScope)scope);
                        } else {
                           typeArgument.resolveType((BlockScope)scope);
                        }
                     }
                  }
               }

               return null;
            }

            ReferenceBinding currentType = (ReferenceBinding)this.resolvedType;
            if (qualifyingType == null) {
               qualifyingType = currentType.enclosingType();
               if (qualifyingType != null && currentType.hasEnclosingInstanceContext()) {
                  qualifyingType = scope.environment().convertToParameterizedType((ReferenceBinding)qualifyingType);
               }
            } else {
               if (this.annotations != null) {
                  rejectAnnotationsOnStaticMemberQualififer(scope, currentType, this.annotations[i - 1]);
               }

               if (typeIsConsistent && currentType.isStatic() && (((ReferenceBinding)qualifyingType).isParameterizedTypeWithActualArguments() || ((ReferenceBinding)qualifyingType).isGenericType())) {
                  scope.problemReporter().staticMemberOfParameterizedType(this, currentType, (ReferenceBinding)qualifyingType, i);
                  typeIsConsistent = false;
                  qualifyingType = ((ReferenceBinding)qualifyingType).actualType();
               }

               ReferenceBinding enclosingType = currentType.enclosingType();
               if (enclosingType != null && TypeBinding.notEquals(enclosingType.erasure(), ((ReferenceBinding)qualifyingType).erasure())) {
                  qualifyingType = enclosingType;
               }
            }

            args = this.typeArguments[i];
            if (args != null) {
               TypeReference keep = null;
               if (isClassScope) {
                  keep = ((ClassScope)scope).superTypeReference;
                  ((ClassScope)scope).superTypeReference = null;
               }

               argLength = args.length;
               boolean isDiamond = argLength == 0 && i == max - 1 && (this.bits & 524288) != 0;
               TypeBinding[] argTypes = new TypeBinding[argLength];
               boolean argHasError = false;
               ReferenceBinding currentOriginal = (ReferenceBinding)currentType.original();

               for(int j = 0; j < argLength; ++j) {
                  TypeReference arg = args[j];
                  TypeBinding argType = isClassScope ? arg.resolveTypeArgument((ClassScope)scope, currentOriginal, j) : arg.resolveTypeArgument((BlockScope)scope, currentOriginal, j);
                  if (argType == null) {
                     argHasError = true;
                  } else {
                     argTypes[j] = argType;
                  }
               }

               if (argHasError) {
                  return null;
               }

               if (isClassScope) {
                  ((ClassScope)scope).superTypeReference = keep;
                  if (((ClassScope)scope).detectHierarchyCycle(currentOriginal, this)) {
                     return null;
                  }
               }

               TypeVariableBinding[] typeVariables = currentOriginal.typeVariables();
               if (typeVariables == Binding.NO_TYPE_VARIABLES) {
                  if (scope.compilerOptions().originalSourceLevel >= 3211264L) {
                     scope.problemReporter().nonGenericTypeCannotBeParameterized(i, this, currentType, argTypes);
                     return null;
                  }

                  this.resolvedType = (TypeBinding)(qualifyingType != null && ((ReferenceBinding)qualifyingType).isParameterizedType() ? scope.environment().createParameterizedType(currentOriginal, (TypeBinding[])null, (ReferenceBinding)qualifyingType) : currentType);
                  return this.resolvedType;
               }

               if (argLength != typeVariables.length && !isDiamond) {
                  scope.problemReporter().incorrectArityForParameterizedType(this, currentType, argTypes, i);
                  return null;
               }

               if (typeIsConsistent) {
                  if (!currentType.hasEnclosingInstanceContext()) {
                     if (qualifyingType != null && ((ReferenceBinding)qualifyingType).isRawType()) {
                        this.typesPerToken[i - 1] = (ReferenceBinding)(qualifyingType = ((ReferenceBinding)qualifyingType).actualType());
                     }
                  } else {
                     ReferenceBinding actualEnclosing = currentType.enclosingType();
                     if (actualEnclosing != null && actualEnclosing.isRawType()) {
                        scope.problemReporter().rawMemberTypeCannotBeParameterized(this, scope.environment().createRawType(currentOriginal, actualEnclosing), argTypes);
                        typeIsConsistent = false;
                     }
                  }
               }

               ParameterizedTypeBinding parameterizedType = scope.environment().createParameterizedType(currentOriginal, argTypes, (ReferenceBinding)qualifyingType);
               if (!isDiamond) {
                  if (checkBounds) {
                     parameterizedType.boundCheck(scope, args);
                  } else {
                     scope.deferBoundCheck(this);
                  }
               } else {
                  parameterizedType.arguments = ParameterizedSingleTypeReference.DIAMOND_TYPE_ARGUMENTS;
               }

               qualifyingType = parameterizedType;
            } else {
               ReferenceBinding currentOriginal = (ReferenceBinding)currentType.original();
               if (isClassScope && ((ClassScope)scope).detectHierarchyCycle(currentOriginal, this)) {
                  return null;
               }

               if (currentOriginal.isGenericType()) {
                  if (typeIsConsistent && qualifyingType != null && ((ReferenceBinding)qualifyingType).isParameterizedType() && currentOriginal.hasEnclosingInstanceContext()) {
                     scope.problemReporter().parameterizedMemberTypeMissingArguments(this, scope.environment().createParameterizedType(currentOriginal, (TypeBinding[])null, (ReferenceBinding)qualifyingType), i);
                     typeIsConsistent = false;
                  }

                  qualifyingType = scope.environment().createRawType(currentOriginal, (ReferenceBinding)qualifyingType);
               } else {
                  qualifyingType = scope.environment().maybeCreateParameterizedType(currentOriginal, (ReferenceBinding)qualifyingType);
               }
            }

            if (this.isTypeUseDeprecated((TypeBinding)qualifyingType, scope)) {
               this.reportDeprecatedType((TypeBinding)qualifyingType, scope, i);
            }

            this.resolvedType = (TypeBinding)qualifyingType;
            this.typesPerToken[i] = (ReferenceBinding)qualifyingType;
            this.recordResolution(scope.environment(), this.resolvedType);
         }

         return this.resolvedType;
      }
   }

   private void createArrayType(Scope scope) {
      if (this.dimensions > 0) {
         if (this.dimensions > 255) {
            scope.problemReporter().tooManyDimensions(this);
         }

         this.resolvedType = scope.createArrayType(this.resolvedType, this.dimensions);
      }

   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      int length = this.tokens.length;

      int i;
      int j;
      for(int i = 0; i < length - 1; ++i) {
         if (this.annotations != null && this.annotations[i] != null) {
            printAnnotations(this.annotations[i], output);
            output.append(' ');
         }

         output.append(this.tokens[i]);
         TypeReference[] typeArgument = this.typeArguments[i];
         if (typeArgument != null) {
            output.append('<');
            i = typeArgument.length;
            if (i > 0) {
               j = i - 1;

               for(int j = 0; j < j; ++j) {
                  typeArgument[j].print(0, output);
                  output.append(", ");
               }

               typeArgument[j].print(0, output);
            }

            output.append('>');
         }

         output.append('.');
      }

      if (this.annotations != null && this.annotations[length - 1] != null) {
         output.append(" ");
         printAnnotations(this.annotations[length - 1], output);
         output.append(' ');
      }

      output.append(this.tokens[length - 1]);
      TypeReference[] typeArgument = this.typeArguments[length - 1];
      if (typeArgument != null) {
         output.append('<');
         int typeArgumentLength = typeArgument.length;
         if (typeArgumentLength > 0) {
            i = typeArgumentLength - 1;

            for(j = 0; j < i; ++j) {
               typeArgument[j].print(0, output);
               output.append(", ");
            }

            typeArgument[i].print(0, output);
         }

         output.append('>');
      }

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
      return this.internalResolveType(scope, checkBounds, location);
   }

   public TypeBinding resolveType(ClassScope scope, int location) {
      return this.internalResolveType(scope, false, location);
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int annotationsLength;
         int j;
         if (this.annotations != null) {
            int annotationsLevels = this.annotations.length;

            for(i = 0; i < annotationsLevels; ++i) {
               annotationsLength = this.annotations[i] == null ? 0 : this.annotations[i].length;

               for(j = 0; j < annotationsLength; ++j) {
                  this.annotations[i][j].traverse(visitor, scope);
               }
            }
         }

         Annotation[][] annotationsOnDimensions = this.getAnnotationsOnDimensions(true);
         int max2;
         if (annotationsOnDimensions != null) {
            i = 0;

            for(annotationsLength = annotationsOnDimensions.length; i < annotationsLength; ++i) {
               Annotation[] annotations2 = annotationsOnDimensions[i];
               max2 = 0;

               for(int max2 = annotations2 == null ? 0 : annotations2.length; max2 < max2; ++max2) {
                  Annotation annotation = annotations2[max2];
                  annotation.traverse(visitor, scope);
               }
            }
         }

         i = 0;

         for(annotationsLength = this.typeArguments.length; i < annotationsLength; ++i) {
            if (this.typeArguments[i] != null) {
               j = 0;

               for(max2 = this.typeArguments[i].length; j < max2; ++j) {
                  this.typeArguments[i][j].traverse(visitor, scope);
               }
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int annotationsLength;
         int j;
         if (this.annotations != null) {
            int annotationsLevels = this.annotations.length;

            for(i = 0; i < annotationsLevels; ++i) {
               annotationsLength = this.annotations[i] == null ? 0 : this.annotations[i].length;

               for(j = 0; j < annotationsLength; ++j) {
                  this.annotations[i][j].traverse(visitor, scope);
               }
            }
         }

         Annotation[][] annotationsOnDimensions = this.getAnnotationsOnDimensions(true);
         int max2;
         if (annotationsOnDimensions != null) {
            i = 0;

            for(annotationsLength = annotationsOnDimensions.length; i < annotationsLength; ++i) {
               Annotation[] annotations2 = annotationsOnDimensions[i];
               max2 = 0;

               for(int max2 = annotations2 == null ? 0 : annotations2.length; max2 < max2; ++max2) {
                  Annotation annotation = annotations2[max2];
                  annotation.traverse(visitor, scope);
               }
            }
         }

         i = 0;

         for(annotationsLength = this.typeArguments.length; i < annotationsLength; ++i) {
            if (this.typeArguments[i] != null) {
               j = 0;

               for(max2 = this.typeArguments[i].length; j < max2; ++j) {
                  this.typeArguments[i][j].traverse(visitor, scope);
               }
            }
         }
      }

      visitor.endVisit(this, scope);
   }
}
