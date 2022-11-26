package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SplitPackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;

public class QualifiedTypeReference extends TypeReference {
   public char[][] tokens;
   public long[] sourcePositions;

   public QualifiedTypeReference(char[][] sources, long[] poss) {
      this.tokens = sources;
      this.sourcePositions = poss;
      this.sourceStart = (int)(this.sourcePositions[0] >>> 32);
      this.sourceEnd = (int)(this.sourcePositions[this.sourcePositions.length - 1] & 4294967295L);
   }

   public TypeReference augmentTypeWithAdditionalDimensions(int additionalDimensions, Annotation[][] additionalAnnotations, boolean isVarargs) {
      int totalDimensions = this.dimensions() + additionalDimensions;
      Annotation[][] allAnnotations = this.getMergedAnnotationsOnDimensions(additionalDimensions, additionalAnnotations);
      ArrayQualifiedTypeReference arrayQualifiedTypeReference = new ArrayQualifiedTypeReference(this.tokens, totalDimensions, allAnnotations, this.sourcePositions);
      arrayQualifiedTypeReference.annotations = this.annotations;
      arrayQualifiedTypeReference.bits |= this.bits & 1048576;
      if (!isVarargs) {
         arrayQualifiedTypeReference.extendedDimensions = additionalDimensions;
      }

      return arrayQualifiedTypeReference;
   }

   protected TypeBinding findNextTypeBinding(int tokenIndex, Scope scope, PackageBinding packageBinding) {
      LookupEnvironment env = scope.environment();

      TypeBinding var7;
      try {
         env.missingClassFileLocation = this;
         if (this.resolvedType == null) {
            this.resolvedType = scope.getType(this.tokens[tokenIndex], packageBinding);
         } else {
            this.resolvedType = scope.getMemberType(this.tokens[tokenIndex], (ReferenceBinding)this.resolvedType);
            if (!this.resolvedType.isValidBinding()) {
               this.resolvedType = new ProblemReferenceBinding(CharOperation.subarray((char[][])this.tokens, 0, tokenIndex + 1), (ReferenceBinding)this.resolvedType.closestMatch(), this.resolvedType.problemId());
            }
         }

         var7 = this.resolvedType;
      } catch (AbortCompilation var10) {
         var10.updateContext((ASTNode)this, scope.referenceCompilationUnit().compilationResult);
         throw var10;
      } finally {
         env.missingClassFileLocation = null;
      }

      return var7;
   }

   public char[] getLastToken() {
      return this.tokens[this.tokens.length - 1];
   }

   protected void rejectAnnotationsOnPackageQualifiers(Scope scope, PackageBinding packageBinding) {
      if (packageBinding != null && this.annotations != null) {
         int i = packageBinding.compoundName.length;

         for(int j = 0; j < i; ++j) {
            Annotation[] qualifierAnnot = this.annotations[j];
            if (qualifierAnnot != null && qualifierAnnot.length > 0) {
               if (j == 0) {
                  for(int k = 0; k < qualifierAnnot.length; ++k) {
                     scope.problemReporter().typeAnnotationAtQualifiedName(qualifierAnnot[k]);
                  }
               } else {
                  scope.problemReporter().misplacedTypeAnnotations(qualifierAnnot[0], qualifierAnnot[qualifierAnnot.length - 1]);
                  this.annotations[j] = null;
               }
            }
         }

      }
   }

   protected static void rejectAnnotationsOnStaticMemberQualififer(Scope scope, ReferenceBinding currentType, Annotation[] qualifierAnnot) {
      if (currentType.isMemberType() && currentType.isStatic() && qualifierAnnot != null && qualifierAnnot.length > 0) {
         scope.problemReporter().illegalTypeAnnotationsInStaticMemberAccess(qualifierAnnot[0], qualifierAnnot[qualifierAnnot.length - 1]);
      }

   }

   protected TypeBinding getTypeBinding(Scope scope) {
      if (this.resolvedType != null) {
         return this.resolvedType;
      } else {
         Binding binding = scope.getPackage(this.tokens);
         if (this.resolvedType != null) {
            return this.resolvedType;
         } else if (binding != null && !binding.isValidBinding()) {
            if (binding instanceof ProblemReferenceBinding && binding.problemId() == 1) {
               ProblemReferenceBinding problemBinding = (ProblemReferenceBinding)binding;
               Binding pkg = scope.getTypeOrPackage(this.tokens);
               return new ProblemReferenceBinding(problemBinding.compoundName, pkg instanceof PackageBinding ? null : scope.environment().createMissingType((PackageBinding)null, this.tokens), 1);
            } else {
               return (ReferenceBinding)binding;
            }
         } else {
            PackageBinding packageBinding = binding == null ? null : (PackageBinding)binding;
            int typeStart = packageBinding == null ? 0 : packageBinding.compoundName.length;
            if (packageBinding != null) {
               PackageBinding uniquePackage = packageBinding.getVisibleFor(scope.module(), false);
               if (uniquePackage instanceof SplitPackageBinding) {
                  CompilerOptions compilerOptions = scope.compilerOptions();
                  boolean inJdtDebugCompileMode = compilerOptions.enableJdtDebugCompileMode;
                  if (!inJdtDebugCompileMode) {
                     SplitPackageBinding splitPackage = (SplitPackageBinding)uniquePackage;
                     scope.problemReporter().conflictingPackagesFromModules(splitPackage, scope.module(), this.sourceStart, (int)this.sourcePositions[typeStart - 1]);
                     this.resolvedType = new ProblemReferenceBinding(this.tokens, (ReferenceBinding)null, 3);
                     return null;
                  }
               }
            }

            this.rejectAnnotationsOnPackageQualifiers(scope, packageBinding);
            boolean isClassScope = scope.kind == 3;
            ReferenceBinding qualifiedType = null;
            int i = typeStart;
            int max = this.tokens.length;

            for(int last = max - 1; i < max; ++i) {
               this.findNextTypeBinding(i, scope, packageBinding);
               if (!this.resolvedType.isValidBinding()) {
                  return this.resolvedType;
               }

               if (i == 0 && this.resolvedType.isTypeVariable() && ((TypeVariableBinding)this.resolvedType).firstBound == null) {
                  scope.problemReporter().illegalAccessFromTypeVariable((TypeVariableBinding)this.resolvedType, this);
                  return null;
               }

               if (i <= last && this.isTypeUseDeprecated(this.resolvedType, scope)) {
                  this.reportDeprecatedType(this.resolvedType, scope, i);
               }

               if (isClassScope && ((ClassScope)scope).detectHierarchyCycle(this.resolvedType, this)) {
                  return null;
               }

               ReferenceBinding currentType = (ReferenceBinding)this.resolvedType;
               if (qualifiedType != null) {
                  if (this.annotations != null) {
                     rejectAnnotationsOnStaticMemberQualififer(scope, currentType, this.annotations[i - 1]);
                  }

                  ReferenceBinding enclosingType = currentType.enclosingType();
                  if (enclosingType != null && TypeBinding.notEquals(enclosingType.erasure(), ((ReferenceBinding)qualifiedType).erasure())) {
                     qualifiedType = enclosingType;
                  }

                  if (currentType.isGenericType()) {
                     qualifiedType = scope.environment().createRawType(currentType, (ReferenceBinding)qualifiedType);
                  } else if (!currentType.hasEnclosingInstanceContext()) {
                     qualifiedType = currentType;
                  } else {
                     boolean rawQualified = ((ReferenceBinding)qualifiedType).isRawType();
                     if (rawQualified) {
                        qualifiedType = scope.environment().createRawType((ReferenceBinding)currentType.erasure(), (ReferenceBinding)qualifiedType);
                     } else if (((ReferenceBinding)qualifiedType).isParameterizedType() && TypeBinding.equalsEquals(((ReferenceBinding)qualifiedType).erasure(), currentType.enclosingType().erasure())) {
                        qualifiedType = scope.environment().createParameterizedType((ReferenceBinding)currentType.erasure(), (TypeBinding[])null, (ReferenceBinding)qualifiedType);
                     } else {
                        qualifiedType = currentType;
                     }
                  }
               } else {
                  qualifiedType = currentType.isGenericType() ? (ReferenceBinding)scope.environment().convertToRawType(currentType, false) : currentType;
               }

               this.recordResolution(scope.environment(), (TypeBinding)qualifiedType);
            }

            this.resolvedType = (TypeBinding)qualifiedType;
            return this.resolvedType;
         }
      }
   }

   void recordResolution(LookupEnvironment env, TypeBinding typeFound) {
      if (typeFound != null && typeFound.isValidBinding()) {
         synchronized(env.root) {
            for(int i = 0; i < env.root.resolutionListeners.length; ++i) {
               env.root.resolutionListeners[i].recordResolution(this, typeFound);
            }
         }
      }

   }

   public char[][] getTypeName() {
      return this.tokens;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      for(int i = 0; i < this.tokens.length; ++i) {
         if (i > 0) {
            output.append('.');
         }

         if (this.annotations != null && this.annotations[i] != null) {
            printAnnotations(this.annotations[i], output);
            output.append(' ');
         }

         output.append(this.tokens[i]);
      }

      return output;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope) && this.annotations != null) {
         int annotationsLevels = this.annotations.length;

         for(int i = 0; i < annotationsLevels; ++i) {
            int annotationsLength = this.annotations[i] == null ? 0 : this.annotations[i].length;

            for(int j = 0; j < annotationsLength; ++j) {
               this.annotations[i][j].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope) && this.annotations != null) {
         int annotationsLevels = this.annotations.length;

         for(int i = 0; i < annotationsLevels; ++i) {
            int annotationsLength = this.annotations[i] == null ? 0 : this.annotations[i].length;

            for(int j = 0; j < annotationsLength; ++j) {
               this.annotations[i][j].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public int getAnnotatableLevels() {
      return this.tokens.length;
   }
}
