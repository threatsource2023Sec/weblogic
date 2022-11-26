package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class JavadocAllocationExpression extends AllocationExpression {
   public int tagSourceStart;
   public int tagSourceEnd;
   public int tagValue;
   public int memberStart;
   public char[][] qualification;

   public JavadocAllocationExpression(int start, int end) {
      this.sourceStart = start;
      this.sourceEnd = end;
      this.bits |= 32768;
   }

   public JavadocAllocationExpression(long pos) {
      this((int)(pos >>> 32), (int)pos);
   }

   TypeBinding internalResolveType(Scope scope) {
      this.constant = Constant.NotAConstant;
      if (this.type == null) {
         this.resolvedType = scope.enclosingSourceType();
      } else if (scope.kind == 3) {
         this.resolvedType = this.type.resolveType((ClassScope)scope);
      } else {
         this.resolvedType = this.type.resolveType((BlockScope)scope, true);
      }

      this.argumentTypes = Binding.NO_PARAMETERS;
      boolean hasTypeVarArgs = false;
      if (this.arguments != null) {
         this.argumentsHaveErrors = false;
         int length = this.arguments.length;
         this.argumentTypes = new TypeBinding[length];

         for(int i = 0; i < length; ++i) {
            Expression argument = this.arguments[i];
            if (scope.kind == 3) {
               this.argumentTypes[i] = argument.resolveType((ClassScope)scope);
            } else {
               this.argumentTypes[i] = argument.resolveType((BlockScope)scope);
            }

            if (this.argumentTypes[i] == null) {
               this.argumentsHaveErrors = true;
            } else if (!hasTypeVarArgs) {
               hasTypeVarArgs = this.argumentTypes[i].isTypeVariable();
            }
         }

         if (this.argumentsHaveErrors) {
            return null;
         }
      }

      if (this.resolvedType == null) {
         return null;
      } else {
         this.resolvedType = scope.environment().convertToRawType(this.type.resolvedType, true);
         SourceTypeBinding enclosingType = scope.enclosingSourceType();
         if (enclosingType != null && enclosingType.isCompatibleWith(this.resolvedType)) {
            this.bits |= 16384;
         }

         ReferenceBinding allocationType = (ReferenceBinding)this.resolvedType;
         this.binding = scope.getConstructor(allocationType, this.argumentTypes, this);
         if (!this.binding.isValidBinding()) {
            ReferenceBinding enclosingTypeBinding = allocationType;

            MethodBinding contructorBinding;
            for(contructorBinding = this.binding; !contructorBinding.isValidBinding() && (enclosingTypeBinding.isMemberType() || enclosingTypeBinding.isLocalType()); contructorBinding = scope.getConstructor(enclosingTypeBinding, this.argumentTypes, this)) {
               enclosingTypeBinding = enclosingTypeBinding.enclosingType();
            }

            if (contructorBinding.isValidBinding()) {
               this.binding = contructorBinding;
            }
         }

         if (!this.binding.isValidBinding()) {
            MethodBinding methodBinding = scope.getMethod(this.resolvedType, this.resolvedType.sourceName(), this.argumentTypes, this);
            if (methodBinding.isValidBinding()) {
               this.binding = methodBinding;
            } else {
               if (this.binding.declaringClass == null) {
                  this.binding.declaringClass = allocationType;
               }

               scope.problemReporter().javadocInvalidConstructor(this, this.binding, scope.getDeclarationModifiers());
            }

            return this.resolvedType;
         } else {
            int length;
            if (this.binding.isVarargs()) {
               length = this.argumentTypes.length;
               if (this.binding.parameters.length != length || !this.argumentTypes[length - 1].isArrayType()) {
                  MethodBinding problem = new ProblemMethodBinding(this.binding, this.binding.selector, this.argumentTypes, 1);
                  scope.problemReporter().javadocInvalidConstructor(this, problem, scope.getDeclarationModifiers());
               }
            } else if (hasTypeVarArgs) {
               MethodBinding problem = new ProblemMethodBinding(this.binding, this.binding.selector, this.argumentTypes, 1);
               scope.problemReporter().javadocInvalidConstructor(this, problem, scope.getDeclarationModifiers());
            } else {
               int idx;
               if (this.binding instanceof ParameterizedMethodBinding) {
                  ParameterizedMethodBinding paramMethodBinding = (ParameterizedMethodBinding)this.binding;
                  if (paramMethodBinding.hasSubstitutedParameters()) {
                     int length = this.argumentTypes.length;

                     for(idx = 0; idx < length; ++idx) {
                        if (TypeBinding.notEquals(paramMethodBinding.parameters[idx], this.argumentTypes[idx]) && TypeBinding.notEquals(paramMethodBinding.parameters[idx].erasure(), this.argumentTypes[idx].erasure())) {
                           MethodBinding problem = new ProblemMethodBinding(this.binding, this.binding.selector, this.argumentTypes, 1);
                           scope.problemReporter().javadocInvalidConstructor(this, problem, scope.getDeclarationModifiers());
                           break;
                        }
                     }
                  }
               } else if (this.resolvedType.isMemberType()) {
                  length = this.qualification.length;
                  if (length > 1) {
                     ReferenceBinding enclosingTypeBinding = allocationType;
                     if (this.type instanceof JavadocQualifiedTypeReference && ((JavadocQualifiedTypeReference)this.type).tokens.length != length) {
                        scope.problemReporter().javadocInvalidMemberTypeQualification(this.memberStart + 1, this.sourceEnd, scope.getDeclarationModifiers());
                     } else {
                        idx = length;

                        while(idx > 0) {
                           --idx;
                           if (!CharOperation.equals(this.qualification[idx], enclosingTypeBinding.sourceName) || (enclosingTypeBinding = enclosingTypeBinding.enclosingType()) == null) {
                              break;
                           }
                        }

                        if (idx > 0 || enclosingTypeBinding != null) {
                           scope.problemReporter().javadocInvalidMemberTypeQualification(this.memberStart + 1, this.sourceEnd, scope.getDeclarationModifiers());
                        }
                     }
                  }
               }
            }

            if (this.isMethodUseDeprecated(this.binding, scope, true, this)) {
               scope.problemReporter().javadocDeprecatedMethod(this.binding, this, scope.getDeclarationModifiers());
            }

            return allocationType;
         }
      }
   }

   public boolean isSuperAccess() {
      return (this.bits & 16384) != 0;
   }

   public TypeBinding resolveType(BlockScope scope) {
      return this.internalResolveType(scope);
   }

   public TypeBinding resolveType(ClassScope scope) {
      return this.internalResolveType(scope);
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int argumentsLength;
         if (this.typeArguments != null) {
            i = 0;

            for(argumentsLength = this.typeArguments.length; i < argumentsLength; ++i) {
               this.typeArguments[i].traverse(visitor, scope);
            }
         }

         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }

         if (this.arguments != null) {
            i = 0;

            for(argumentsLength = this.arguments.length; i < argumentsLength; ++i) {
               this.arguments[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int argumentsLength;
         if (this.typeArguments != null) {
            i = 0;

            for(argumentsLength = this.typeArguments.length; i < argumentsLength; ++i) {
               this.typeArguments[i].traverse(visitor, scope);
            }
         }

         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }

         if (this.arguments != null) {
            i = 0;

            for(argumentsLength = this.arguments.length; i < argumentsLength; ++i) {
               this.arguments[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }
}
