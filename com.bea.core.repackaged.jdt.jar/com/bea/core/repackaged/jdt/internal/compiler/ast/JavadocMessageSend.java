package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class JavadocMessageSend extends MessageSend {
   public int tagSourceStart;
   public int tagSourceEnd;
   public int tagValue;

   public JavadocMessageSend(char[] name, long pos) {
      this.selector = name;
      this.nameSourcePosition = pos;
      this.sourceStart = (int)(this.nameSourcePosition >>> 32);
      this.sourceEnd = (int)this.nameSourcePosition;
      this.bits |= 32768;
   }

   public JavadocMessageSend(char[] name, long pos, JavadocArgumentExpression[] arguments) {
      this(name, pos);
      this.arguments = arguments;
   }

   private TypeBinding internalResolveType(Scope scope) {
      this.constant = Constant.NotAConstant;
      if (this.receiver == null) {
         this.actualReceiverType = scope.enclosingReceiverType();
      } else if (scope.kind == 3) {
         this.actualReceiverType = this.receiver.resolveType((ClassScope)scope);
      } else {
         this.actualReceiverType = this.receiver.resolveType((BlockScope)scope);
      }

      boolean hasArgsTypeVar = false;
      int length;
      if (this.arguments != null) {
         this.argumentsHaveErrors = false;
         int length = this.arguments.length;
         this.argumentTypes = new TypeBinding[length];

         for(length = 0; length < length; ++length) {
            Expression argument = this.arguments[length];
            if (scope.kind == 3) {
               this.argumentTypes[length] = argument.resolveType((ClassScope)scope);
            } else {
               this.argumentTypes[length] = argument.resolveType((BlockScope)scope);
            }

            if (this.argumentTypes[length] == null) {
               this.argumentsHaveErrors = true;
            } else if (!hasArgsTypeVar) {
               hasArgsTypeVar = this.argumentTypes[length].isTypeVariable();
            }
         }

         if (this.argumentsHaveErrors) {
            return null;
         }
      }

      if (this.actualReceiverType == null) {
         return null;
      } else {
         this.actualReceiverType = scope.environment().convertToRawType(this.receiver.resolvedType, true);
         ReferenceBinding enclosingType = scope.enclosingReceiverType();
         if (enclosingType != null && enclosingType.isCompatibleWith(this.actualReceiverType)) {
            this.bits |= 16384;
         }

         if (this.actualReceiverType.isBaseType()) {
            scope.problemReporter().javadocErrorNoMethodFor(this, this.actualReceiverType, this.argumentTypes, scope.getDeclarationModifiers());
            return null;
         } else {
            this.binding = scope.getMethod(this.actualReceiverType, this.selector, this.argumentTypes, this);
            if (!this.binding.isValidBinding()) {
               TypeBinding enclosingTypeBinding = this.actualReceiverType;

               MethodBinding methodBinding;
               for(methodBinding = this.binding; !methodBinding.isValidBinding() && (((TypeBinding)enclosingTypeBinding).isMemberType() || ((TypeBinding)enclosingTypeBinding).isLocalType()); methodBinding = scope.getMethod((TypeBinding)enclosingTypeBinding, this.selector, this.argumentTypes, this)) {
                  enclosingTypeBinding = ((TypeBinding)enclosingTypeBinding).enclosingType();
               }

               if (methodBinding.isValidBinding()) {
                  this.binding = methodBinding;
               } else {
                  enclosingTypeBinding = this.actualReceiverType;
                  MethodBinding contructorBinding = this.binding;
                  if (!contructorBinding.isValidBinding() && CharOperation.equals(this.selector, ((TypeBinding)enclosingTypeBinding).shortReadableName())) {
                     contructorBinding = scope.getConstructor((ReferenceBinding)enclosingTypeBinding, this.argumentTypes, this);
                  }

                  while(!contructorBinding.isValidBinding() && (((TypeBinding)enclosingTypeBinding).isMemberType() || ((TypeBinding)enclosingTypeBinding).isLocalType())) {
                     enclosingTypeBinding = ((TypeBinding)enclosingTypeBinding).enclosingType();
                     if (CharOperation.equals(this.selector, ((TypeBinding)enclosingTypeBinding).shortReadableName())) {
                        contructorBinding = scope.getConstructor((ReferenceBinding)enclosingTypeBinding, this.argumentTypes, this);
                     }
                  }

                  if (contructorBinding.isValidBinding()) {
                     this.binding = contructorBinding;
                  }
               }
            }

            MethodBinding closestMatch;
            if (!this.binding.isValidBinding()) {
               switch (this.binding.problemId()) {
                  case 3:
                  case 5:
                  case 6:
                  case 7:
                     closestMatch = ((ProblemMethodBinding)this.binding).closestMatch;
                     if (closestMatch != null) {
                        this.binding = closestMatch;
                     }
                  case 4:
               }
            }

            if (!this.binding.isValidBinding()) {
               if (this.receiver.resolvedType instanceof ProblemReferenceBinding) {
                  return null;
               } else {
                  if (this.binding.declaringClass == null) {
                     if (!(this.actualReceiverType instanceof ReferenceBinding)) {
                        scope.problemReporter().javadocErrorNoMethodFor(this, this.actualReceiverType, this.argumentTypes, scope.getDeclarationModifiers());
                        return null;
                     }

                     this.binding.declaringClass = (ReferenceBinding)this.actualReceiverType;
                  }

                  scope.problemReporter().javadocInvalidMethod(this, this.binding, scope.getDeclarationModifiers());
                  if (this.binding instanceof ProblemMethodBinding) {
                     closestMatch = ((ProblemMethodBinding)this.binding).closestMatch;
                     if (closestMatch != null) {
                        this.binding = closestMatch;
                     }
                  }

                  return this.resolvedType = this.binding == null ? null : this.binding.returnType;
               }
            } else {
               if (hasArgsTypeVar) {
                  MethodBinding problem = new ProblemMethodBinding(this.binding, this.selector, this.argumentTypes, 1);
                  scope.problemReporter().javadocInvalidMethod(this, problem, scope.getDeclarationModifiers());
               } else if (this.binding.isVarargs()) {
                  length = this.argumentTypes.length;
                  if (this.binding.parameters.length != length || !this.argumentTypes[length - 1].isArrayType()) {
                     MethodBinding problem = new ProblemMethodBinding(this.binding, this.selector, this.argumentTypes, 1);
                     scope.problemReporter().javadocInvalidMethod(this, problem, scope.getDeclarationModifiers());
                  }
               } else {
                  length = this.argumentTypes.length;

                  for(int i = 0; i < length; ++i) {
                     if (TypeBinding.notEquals(this.binding.parameters[i].erasure(), this.argumentTypes[i].erasure())) {
                        MethodBinding problem = new ProblemMethodBinding(this.binding, this.selector, this.argumentTypes, 1);
                        scope.problemReporter().javadocInvalidMethod(this, problem, scope.getDeclarationModifiers());
                        break;
                     }
                  }
               }

               if (this.isMethodUseDeprecated(this.binding, scope, true, this)) {
                  scope.problemReporter().javadocDeprecatedMethod(this.binding, this, scope.getDeclarationModifiers());
               }

               return this.resolvedType = this.binding.returnType;
            }
         }
      }
   }

   public boolean isSuperAccess() {
      return (this.bits & 16384) != 0;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      if (this.receiver != null) {
         this.receiver.printExpression(0, output);
      }

      output.append('#').append(this.selector).append('(');
      if (this.arguments != null) {
         for(int i = 0; i < this.arguments.length; ++i) {
            if (i > 0) {
               output.append(", ");
            }

            this.arguments[i].printExpression(0, output);
         }
      }

      return output.append(')');
   }

   public TypeBinding resolveType(BlockScope scope) {
      return this.internalResolveType(scope);
   }

   public TypeBinding resolveType(ClassScope scope) {
      return this.internalResolveType(scope);
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         if (this.receiver != null) {
            this.receiver.traverse(visitor, blockScope);
         }

         if (this.arguments != null) {
            int argumentsLength = this.arguments.length;

            for(int i = 0; i < argumentsLength; ++i) {
               this.arguments[i].traverse(visitor, blockScope);
            }
         }
      }

      visitor.endVisit(this, blockScope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.receiver != null) {
            this.receiver.traverse(visitor, scope);
         }

         if (this.arguments != null) {
            int argumentsLength = this.arguments.length;

            for(int i = 0; i < argumentsLength; ++i) {
               this.arguments[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }
}
