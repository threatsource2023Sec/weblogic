package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CompilationUnitScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ImportBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;

public class Javadoc extends ASTNode {
   public JavadocSingleNameReference[] paramReferences;
   public JavadocSingleTypeReference[] paramTypeParameters;
   public TypeReference[] exceptionReferences;
   public JavadocReturnStatement returnStatement;
   public Expression[] seeReferences;
   public long[] inheritedPositions = null;
   public JavadocSingleNameReference[] invalidParameters;
   public long valuePositions = -1L;

   public Javadoc(int sourceStart, int sourceEnd) {
      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
      this.bits |= 65536;
   }

   boolean canBeSeen(int visibility, int modifiers) {
      if (modifiers < 0) {
         return true;
      } else {
         switch (modifiers & 7) {
            case 0:
               if (visibility != 0 && visibility != 2) {
                  return false;
               }

               return true;
            case 1:
               return true;
            case 2:
               if (visibility == 2) {
                  return true;
               }

               return false;
            case 3:
            default:
               return true;
            case 4:
               return visibility != 1;
         }
      }
   }

   public ASTNode getNodeStartingAt(int start) {
      int length = false;
      int i;
      JavadocSingleNameReference param;
      int length;
      if (this.paramReferences != null) {
         length = this.paramReferences.length;

         for(i = 0; i < length; ++i) {
            param = this.paramReferences[i];
            if (param.sourceStart == start) {
               return param;
            }
         }
      }

      if (this.invalidParameters != null) {
         length = this.invalidParameters.length;

         for(i = 0; i < length; ++i) {
            param = this.invalidParameters[i];
            if (param.sourceStart == start) {
               return param;
            }
         }
      }

      if (this.paramTypeParameters != null) {
         length = this.paramTypeParameters.length;

         for(i = 0; i < length; ++i) {
            JavadocSingleTypeReference param = this.paramTypeParameters[i];
            if (param.sourceStart == start) {
               return param;
            }
         }
      }

      if (this.exceptionReferences != null) {
         length = this.exceptionReferences.length;

         for(i = 0; i < length; ++i) {
            TypeReference typeRef = this.exceptionReferences[i];
            if (typeRef.sourceStart == start) {
               return typeRef;
            }
         }
      }

      if (this.seeReferences != null) {
         length = this.seeReferences.length;

         for(i = 0; i < length; ++i) {
            Expression expression = this.seeReferences[i];
            if (expression.sourceStart == start) {
               return expression;
            }

            int j;
            int l;
            if (expression instanceof JavadocAllocationExpression) {
               JavadocAllocationExpression allocationExpr = (JavadocAllocationExpression)this.seeReferences[i];
               if (allocationExpr.binding != null && allocationExpr.binding.isValidBinding() && allocationExpr.arguments != null) {
                  j = 0;

                  for(l = allocationExpr.arguments.length; j < l; ++j) {
                     if (allocationExpr.arguments[j].sourceStart == start) {
                        return allocationExpr.arguments[j];
                     }
                  }
               }
            } else if (expression instanceof JavadocMessageSend) {
               JavadocMessageSend messageSend = (JavadocMessageSend)this.seeReferences[i];
               if (messageSend.binding != null && messageSend.binding.isValidBinding() && messageSend.arguments != null) {
                  j = 0;

                  for(l = messageSend.arguments.length; j < l; ++j) {
                     if (messageSend.arguments[j].sourceStart == start) {
                        return messageSend.arguments[j];
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output).append("/**\n");
      int i;
      int length;
      if (this.paramReferences != null) {
         i = 0;

         for(length = this.paramReferences.length; i < length; ++i) {
            printIndent(indent + 1, output).append(" * @param ");
            this.paramReferences[i].print(indent, output).append('\n');
         }
      }

      if (this.paramTypeParameters != null) {
         i = 0;

         for(length = this.paramTypeParameters.length; i < length; ++i) {
            printIndent(indent + 1, output).append(" * @param <");
            this.paramTypeParameters[i].print(indent, output).append(">\n");
         }
      }

      if (this.returnStatement != null) {
         printIndent(indent + 1, output).append(" * @");
         this.returnStatement.print(indent, output).append('\n');
      }

      if (this.exceptionReferences != null) {
         i = 0;

         for(length = this.exceptionReferences.length; i < length; ++i) {
            printIndent(indent + 1, output).append(" * @throws ");
            this.exceptionReferences[i].print(indent, output).append('\n');
         }
      }

      if (this.seeReferences != null) {
         i = 0;

         for(length = this.seeReferences.length; i < length; ++i) {
            printIndent(indent + 1, output).append(" * @see ");
            this.seeReferences[i].print(indent, output).append('\n');
         }
      }

      printIndent(indent, output).append(" */\n");
      return output;
   }

   public void resolve(ClassScope scope) {
      if ((this.bits & 65536) != 0) {
         this.bits &= -65537;
         int paramTagsSize;
         int throwsTagsLength;
         int seeTagsLength;
         int i;
         if (this.inheritedPositions != null) {
            paramTagsSize = this.inheritedPositions.length;

            for(throwsTagsLength = 0; throwsTagsLength < paramTagsSize; ++throwsTagsLength) {
               seeTagsLength = (int)(this.inheritedPositions[throwsTagsLength] >>> 32);
               i = (int)this.inheritedPositions[throwsTagsLength];
               scope.problemReporter().javadocUnexpectedTag(seeTagsLength, i);
            }
         }

         paramTagsSize = this.paramReferences == null ? 0 : this.paramReferences.length;

         for(throwsTagsLength = 0; throwsTagsLength < paramTagsSize; ++throwsTagsLength) {
            JavadocSingleNameReference param = this.paramReferences[throwsTagsLength];
            scope.problemReporter().javadocUnexpectedTag(param.tagSourceStart, param.tagSourceEnd);
         }

         this.resolveTypeParameterTags(scope, true);
         if (this.returnStatement != null) {
            scope.problemReporter().javadocUnexpectedTag(this.returnStatement.sourceStart, this.returnStatement.sourceEnd);
         }

         throwsTagsLength = this.exceptionReferences == null ? 0 : this.exceptionReferences.length;

         for(seeTagsLength = 0; seeTagsLength < throwsTagsLength; ++seeTagsLength) {
            TypeReference typeRef = this.exceptionReferences[seeTagsLength];
            int start;
            int end;
            if (typeRef instanceof JavadocSingleTypeReference) {
               JavadocSingleTypeReference singleRef = (JavadocSingleTypeReference)typeRef;
               start = singleRef.tagSourceStart;
               end = singleRef.tagSourceEnd;
            } else if (typeRef instanceof JavadocQualifiedTypeReference) {
               JavadocQualifiedTypeReference qualifiedRef = (JavadocQualifiedTypeReference)typeRef;
               start = qualifiedRef.tagSourceStart;
               end = qualifiedRef.tagSourceEnd;
            } else {
               start = typeRef.sourceStart;
               end = typeRef.sourceEnd;
            }

            scope.problemReporter().javadocUnexpectedTag(start, end);
         }

         seeTagsLength = this.seeReferences == null ? 0 : this.seeReferences.length;

         for(i = 0; i < seeTagsLength; ++i) {
            this.resolveReference(this.seeReferences[i], scope);
         }

         boolean source15 = scope.compilerOptions().sourceLevel >= 3211264L;
         if (!source15 && this.valuePositions != -1L) {
            scope.problemReporter().javadocUnexpectedTag((int)(this.valuePositions >>> 32), (int)this.valuePositions);
         }

      }
   }

   public void resolve(CompilationUnitScope unitScope) {
      if ((this.bits & 65536) != 0) {
         ;
      }
   }

   public void resolve(MethodScope methScope) {
      if ((this.bits & 65536) != 0) {
         this.bits &= -65537;
         AbstractMethodDeclaration methDecl = methScope.referenceMethod();
         boolean overriding = methDecl != null && methDecl.binding != null ? !methDecl.binding.isStatic() && (methDecl.binding.modifiers & 805306368) != 0 : false;
         int seeTagsLength = this.seeReferences == null ? 0 : this.seeReferences.length;
         boolean superRef = false;

         int i;
         for(i = 0; i < seeTagsLength; ++i) {
            this.resolveReference(this.seeReferences[i], methScope);
            if (methDecl != null && !superRef) {
               ReferenceBinding methodReceiverType;
               if (!methDecl.isConstructor()) {
                  if (overriding && this.seeReferences[i] instanceof JavadocMessageSend) {
                     JavadocMessageSend messageSend = (JavadocMessageSend)this.seeReferences[i];
                     if (messageSend.binding != null && messageSend.binding.isValidBinding() && messageSend.actualReceiverType instanceof ReferenceBinding) {
                        methodReceiverType = (ReferenceBinding)messageSend.actualReceiverType;
                        TypeBinding superType = methDecl.binding.declaringClass.findSuperTypeOriginatingFrom(methodReceiverType);
                        if (superType != null && TypeBinding.notEquals(superType.original(), methDecl.binding.declaringClass) && CharOperation.equals(messageSend.selector, methDecl.selector) && methScope.environment().methodVerifier().doesMethodOverride(methDecl.binding, messageSend.binding.original())) {
                           superRef = true;
                        }
                     }
                  }
               } else if (this.seeReferences[i] instanceof JavadocAllocationExpression) {
                  JavadocAllocationExpression allocationExpr = (JavadocAllocationExpression)this.seeReferences[i];
                  if (allocationExpr.binding != null && allocationExpr.binding.isValidBinding()) {
                     methodReceiverType = (ReferenceBinding)allocationExpr.resolvedType.original();
                     ReferenceBinding superType = (ReferenceBinding)methDecl.binding.declaringClass.findSuperTypeOriginatingFrom(methodReceiverType);
                     if (superType != null && TypeBinding.notEquals(superType.original(), methDecl.binding.declaringClass)) {
                        MethodBinding superConstructor = methScope.getConstructor(superType, methDecl.binding.parameters, allocationExpr);
                        if (superConstructor.isValidBinding() && superConstructor.original() == allocationExpr.binding.original()) {
                           MethodBinding current = methDecl.binding;
                           if (methScope.compilerOptions().sourceLevel >= 3407872L && current.typeVariables != Binding.NO_TYPE_VARIABLES) {
                              current = current.asRawMethod(methScope.environment());
                           }

                           if (superConstructor.areParametersEqual(current)) {
                              superRef = true;
                           }
                        }
                     }
                  }
               }
            }
         }

         int length;
         if (!superRef && methDecl != null && methDecl.annotations != null) {
            i = methDecl.annotations.length;

            for(length = 0; length < i && !superRef; ++length) {
               superRef = (methDecl.binding.tagBits & 562949953421312L) != 0L;
            }
         }

         boolean reportMissing = methDecl == null || (!overriding || this.inheritedPositions == null) && !superRef && (methDecl.binding.declaringClass == null || !methDecl.binding.declaringClass.isLocalType());
         int length;
         int i;
         if (!overriding && this.inheritedPositions != null) {
            length = this.inheritedPositions.length;

            for(int i = 0; i < length; ++i) {
               length = (int)(this.inheritedPositions[i] >>> 32);
               i = (int)this.inheritedPositions[i];
               methScope.problemReporter().javadocUnexpectedTag(length, i);
            }
         }

         CompilerOptions compilerOptions = methScope.compilerOptions();
         this.resolveParamTags(methScope, reportMissing, compilerOptions.reportUnusedParameterIncludeDocCommentReference);
         this.resolveTypeParameterTags(methScope, reportMissing && compilerOptions.reportMissingJavadocTagsMethodTypeParameters);
         if (this.returnStatement == null) {
            if (reportMissing && methDecl != null && methDecl.isMethod()) {
               MethodDeclaration meth = (MethodDeclaration)methDecl;
               if (meth.binding.returnType != TypeBinding.VOID) {
                  methScope.problemReporter().javadocMissingReturnTag(meth.returnType.sourceStart, meth.returnType.sourceEnd, methDecl.binding.modifiers);
               }
            }
         } else {
            this.returnStatement.resolve(methScope);
         }

         this.resolveThrowsTags(methScope, reportMissing);
         boolean source15 = compilerOptions.sourceLevel >= 3211264L;
         if (!source15 && methDecl != null && this.valuePositions != -1L) {
            methScope.problemReporter().javadocUnexpectedTag((int)(this.valuePositions >>> 32), (int)this.valuePositions);
         }

         length = this.invalidParameters == null ? 0 : this.invalidParameters.length;

         for(i = 0; i < length; ++i) {
            this.invalidParameters[i].resolve(methScope, false, false);
         }

      }
   }

   private void resolveReference(Expression reference, Scope scope) {
      int problemCount = scope.referenceContext().compilationResult().problemCount;
      switch (scope.kind) {
         case 2:
            reference.resolveType((BlockScope)((MethodScope)scope));
            break;
         case 3:
            reference.resolveType((ClassScope)scope);
      }

      boolean hasProblems = scope.referenceContext().compilationResult().problemCount > problemCount;
      boolean source15 = scope.compilerOptions().sourceLevel >= 3211264L;
      int scopeModifiers = -1;
      ReferenceBinding resolvedType;
      if (reference instanceof JavadocFieldReference) {
         JavadocFieldReference fieldRef = (JavadocFieldReference)reference;
         if (fieldRef.methodBinding != null) {
            if (fieldRef.tagValue == 10) {
               if (scopeModifiers == -1) {
                  scopeModifiers = scope.getDeclarationModifiers();
               }

               scope.problemReporter().javadocInvalidValueReference(fieldRef.sourceStart, fieldRef.sourceEnd, scopeModifiers);
            } else if (fieldRef.actualReceiverType != null) {
               if (scope.enclosingSourceType().isCompatibleWith(fieldRef.actualReceiverType)) {
                  fieldRef.bits |= 16384;
               }

               resolvedType = (ReferenceBinding)fieldRef.actualReceiverType;
               if (CharOperation.equals(resolvedType.sourceName(), fieldRef.token)) {
                  fieldRef.methodBinding = scope.getConstructor(resolvedType, Binding.NO_TYPES, fieldRef);
               } else {
                  fieldRef.methodBinding = scope.findMethod(resolvedType, fieldRef.token, Binding.NO_TYPES, fieldRef, false);
               }
            }
         } else if (source15 && fieldRef.binding != null && fieldRef.binding.isValidBinding() && fieldRef.tagValue == 10 && !fieldRef.binding.isStatic()) {
            if (scopeModifiers == -1) {
               scopeModifiers = scope.getDeclarationModifiers();
            }

            scope.problemReporter().javadocInvalidValueReference(fieldRef.sourceStart, fieldRef.sourceEnd, scopeModifiers);
         }

         if (!hasProblems && fieldRef.binding != null && fieldRef.binding.isValidBinding() && fieldRef.actualReceiverType instanceof ReferenceBinding) {
            resolvedType = (ReferenceBinding)fieldRef.actualReceiverType;
            this.verifyTypeReference(fieldRef, fieldRef.receiver, scope, source15, resolvedType, fieldRef.binding.modifiers);
         }

      } else {
         if (!hasProblems && (reference instanceof JavadocSingleTypeReference || reference instanceof JavadocQualifiedTypeReference) && reference.resolvedType instanceof ReferenceBinding) {
            ReferenceBinding resolvedType = (ReferenceBinding)reference.resolvedType;
            this.verifyTypeReference(reference, reference, scope, source15, resolvedType, resolvedType.modifiers);
         }

         if (reference instanceof JavadocMessageSend) {
            JavadocMessageSend msgSend = (JavadocMessageSend)reference;
            if (source15 && msgSend.tagValue == 10) {
               if (scopeModifiers == -1) {
                  scopeModifiers = scope.getDeclarationModifiers();
               }

               scope.problemReporter().javadocInvalidValueReference(msgSend.sourceStart, msgSend.sourceEnd, scopeModifiers);
            }

            if (!hasProblems && msgSend.binding != null && msgSend.binding.isValidBinding() && msgSend.actualReceiverType instanceof ReferenceBinding) {
               resolvedType = (ReferenceBinding)msgSend.actualReceiverType;
               this.verifyTypeReference(msgSend, msgSend.receiver, scope, source15, resolvedType, msgSend.binding.modifiers);
            }
         } else if (reference instanceof JavadocAllocationExpression) {
            JavadocAllocationExpression alloc = (JavadocAllocationExpression)reference;
            if (source15 && alloc.tagValue == 10) {
               if (scopeModifiers == -1) {
                  scopeModifiers = scope.getDeclarationModifiers();
               }

               scope.problemReporter().javadocInvalidValueReference(alloc.sourceStart, alloc.sourceEnd, scopeModifiers);
            }

            if (!hasProblems && alloc.binding != null && alloc.binding.isValidBinding() && alloc.resolvedType instanceof ReferenceBinding) {
               resolvedType = (ReferenceBinding)alloc.resolvedType;
               this.verifyTypeReference(alloc, alloc.type, scope, source15, resolvedType, alloc.binding.modifiers);
            }
         } else if (reference instanceof JavadocSingleTypeReference && reference.resolvedType != null && reference.resolvedType.isTypeVariable()) {
            scope.problemReporter().javadocInvalidReference(reference.sourceStart, reference.sourceEnd);
         }

      }
   }

   private void resolveParamTags(MethodScope scope, boolean reportMissing, boolean considerParamRefAsUsage) {
      AbstractMethodDeclaration methodDecl = scope.referenceMethod();
      int paramTagsSize = this.paramReferences == null ? 0 : this.paramReferences.length;
      int argumentsSize;
      if (methodDecl == null) {
         for(argumentsSize = 0; argumentsSize < paramTagsSize; ++argumentsSize) {
            JavadocSingleNameReference param = this.paramReferences[argumentsSize];
            scope.problemReporter().javadocUnexpectedTag(param.tagSourceStart, param.tagSourceEnd);
         }

      } else {
         argumentsSize = methodDecl.arguments == null ? 0 : methodDecl.arguments.length;
         if (paramTagsSize == 0) {
            if (reportMissing) {
               for(int i = 0; i < argumentsSize; ++i) {
                  Argument arg = methodDecl.arguments[i];
                  scope.problemReporter().javadocMissingParamTag(arg.name, arg.sourceStart, arg.sourceEnd, methodDecl.binding.modifiers);
               }
            }
         } else {
            LocalVariableBinding[] bindings = new LocalVariableBinding[paramTagsSize];
            int maxBindings = 0;

            int i;
            boolean found;
            int j;
            for(i = 0; i < paramTagsSize; ++i) {
               JavadocSingleNameReference param = this.paramReferences[i];
               param.resolve(scope, true, considerParamRefAsUsage);
               if (param.binding != null && param.binding.isValidBinding()) {
                  found = false;

                  for(j = 0; j < maxBindings && !found; ++j) {
                     if (bindings[j] == param.binding) {
                        scope.problemReporter().javadocDuplicatedParamTag(param.token, param.sourceStart, param.sourceEnd, methodDecl.binding.modifiers);
                        found = true;
                     }
                  }

                  if (!found) {
                     bindings[maxBindings++] = (LocalVariableBinding)param.binding;
                  }
               }
            }

            if (reportMissing) {
               for(i = 0; i < argumentsSize; ++i) {
                  Argument arg = methodDecl.arguments[i];
                  found = false;

                  for(j = 0; j < maxBindings && !found; ++j) {
                     LocalVariableBinding binding = bindings[j];
                     if (arg.binding == binding) {
                        found = true;
                     }
                  }

                  if (!found) {
                     scope.problemReporter().javadocMissingParamTag(arg.name, arg.sourceStart, arg.sourceEnd, methodDecl.binding.modifiers);
                  }
               }
            }
         }

      }
   }

   private void resolveTypeParameterTags(Scope scope, boolean reportMissing) {
      int paramTypeParamLength = this.paramTypeParameters == null ? 0 : this.paramTypeParameters.length;
      TypeParameter[] parameters = null;
      TypeVariableBinding[] typeVariables = null;
      int modifiers = -1;
      int i;
      switch (scope.kind) {
         case 2:
            AbstractMethodDeclaration methodDeclaration = ((MethodScope)scope).referenceMethod();
            if (methodDeclaration == null) {
               for(i = 0; i < paramTypeParamLength; ++i) {
                  JavadocSingleTypeReference param = this.paramTypeParameters[i];
                  scope.problemReporter().javadocUnexpectedTag(param.tagSourceStart, param.tagSourceEnd);
               }

               return;
            }

            parameters = methodDeclaration.typeParameters();
            typeVariables = methodDeclaration.binding.typeVariables;
            modifiers = methodDeclaration.binding.modifiers;
            break;
         case 3:
            TypeDeclaration typeDeclaration = ((ClassScope)scope).referenceContext;
            parameters = typeDeclaration.typeParameters;
            typeVariables = typeDeclaration.binding.typeVariables;
            modifiers = typeDeclaration.binding.modifiers;
      }

      int typeParametersLength;
      if (typeVariables != null && typeVariables.length != 0) {
         if (parameters != null) {
            reportMissing = reportMissing && scope.compilerOptions().sourceLevel >= 3211264L;
            typeParametersLength = parameters.length;
            int i;
            if (paramTypeParamLength == 0) {
               if (reportMissing) {
                  i = 0;

                  for(i = typeParametersLength; i < i; ++i) {
                     scope.problemReporter().javadocMissingParamTag(parameters[i].name, parameters[i].sourceStart, parameters[i].sourceEnd, modifiers);
                  }
               }
            } else if (typeVariables.length == typeParametersLength) {
               TypeVariableBinding[] bindings = new TypeVariableBinding[paramTypeParamLength];

               JavadocSingleTypeReference param;
               for(i = 0; i < paramTypeParamLength; ++i) {
                  param = this.paramTypeParameters[i];
                  TypeBinding paramBindind = param.internalResolveType(scope, 0);
                  if (paramBindind != null && paramBindind.isValidBinding()) {
                     if (!paramBindind.isTypeVariable()) {
                        scope.problemReporter().javadocUndeclaredParamTagName(param.token, param.sourceStart, param.sourceEnd, modifiers);
                     } else {
                        if (scope.compilerOptions().reportUnusedParameterIncludeDocCommentReference) {
                           TypeVariableBinding typeVariableBinding = (TypeVariableBinding)paramBindind;
                           typeVariableBinding.modifiers |= 134217728;
                        }

                        boolean duplicate = false;

                        for(int j = 0; j < i && !duplicate; ++j) {
                           if (TypeBinding.equalsEquals(bindings[j], param.resolvedType)) {
                              scope.problemReporter().javadocDuplicatedParamTag(param.token, param.sourceStart, param.sourceEnd, modifiers);
                              duplicate = true;
                           }
                        }

                        if (!duplicate) {
                           bindings[i] = (TypeVariableBinding)param.resolvedType;
                        }
                     }
                  }
               }

               for(i = 0; i < typeParametersLength; ++i) {
                  TypeParameter parameter = parameters[i];
                  boolean found = false;

                  for(int j = 0; j < paramTypeParamLength && !found; ++j) {
                     if (TypeBinding.equalsEquals(parameter.binding, bindings[j])) {
                        found = true;
                        bindings[j] = null;
                     }
                  }

                  if (!found && reportMissing) {
                     scope.problemReporter().javadocMissingParamTag(parameter.name, parameter.sourceStart, parameter.sourceEnd, modifiers);
                  }
               }

               for(i = 0; i < paramTypeParamLength; ++i) {
                  if (bindings[i] != null) {
                     param = this.paramTypeParameters[i];
                     scope.problemReporter().javadocUndeclaredParamTagName(param.token, param.sourceStart, param.sourceEnd, modifiers);
                  }
               }
            }
         }

      } else {
         for(typeParametersLength = 0; typeParametersLength < paramTypeParamLength; ++typeParametersLength) {
            JavadocSingleTypeReference param = this.paramTypeParameters[typeParametersLength];
            scope.problemReporter().javadocUnexpectedTag(param.tagSourceStart, param.tagSourceEnd);
         }

      }
   }

   private void resolveThrowsTags(MethodScope methScope, boolean reportMissing) {
      AbstractMethodDeclaration md = methScope.referenceMethod();
      int throwsTagsLength = this.exceptionReferences == null ? 0 : this.exceptionReferences.length;
      int boundExceptionLength;
      int maxRef;
      if (md == null) {
         for(boundExceptionLength = 0; boundExceptionLength < throwsTagsLength; ++boundExceptionLength) {
            TypeReference typeRef = this.exceptionReferences[boundExceptionLength];
            maxRef = typeRef.sourceStart;
            int end = typeRef.sourceEnd;
            if (typeRef instanceof JavadocQualifiedTypeReference) {
               maxRef = ((JavadocQualifiedTypeReference)typeRef).tagSourceStart;
               end = ((JavadocQualifiedTypeReference)typeRef).tagSourceEnd;
            } else if (typeRef instanceof JavadocSingleTypeReference) {
               maxRef = ((JavadocSingleTypeReference)typeRef).tagSourceStart;
               end = ((JavadocSingleTypeReference)typeRef).tagSourceEnd;
            }

            methScope.problemReporter().javadocUnexpectedTag(maxRef, end);
         }

      } else {
         boundExceptionLength = md.binding == null ? 0 : md.binding.thrownExceptions.length;
         int thrownExceptionLength = md.thrownExceptions == null ? 0 : md.thrownExceptions.length;
         int i;
         if (throwsTagsLength == 0) {
            if (reportMissing) {
               for(maxRef = 0; maxRef < boundExceptionLength; ++maxRef) {
                  ReferenceBinding exceptionBinding = md.binding.thrownExceptions[maxRef];
                  if (exceptionBinding != null && exceptionBinding.isValidBinding()) {
                     for(i = maxRef; i < thrownExceptionLength && TypeBinding.notEquals(exceptionBinding, md.thrownExceptions[i].resolvedType); ++i) {
                     }

                     if (i < thrownExceptionLength) {
                        methScope.problemReporter().javadocMissingThrowsTag(md.thrownExceptions[i], md.binding.modifiers);
                     }
                  }
               }
            }
         } else {
            maxRef = 0;
            TypeReference[] typeReferences = new TypeReference[throwsTagsLength];

            TypeReference typeRef;
            for(i = 0; i < throwsTagsLength; ++i) {
               typeRef = this.exceptionReferences[i];
               typeRef.resolve(methScope);
               TypeBinding typeBinding = typeRef.resolvedType;
               if (typeBinding != null && typeBinding.isValidBinding() && typeBinding.isClass()) {
                  typeReferences[maxRef++] = typeRef;
               }
            }

            int k;
            TypeBinding exceptionBinding;
            boolean compatible;
            for(i = 0; i < boundExceptionLength; ++i) {
               ReferenceBinding exceptionBinding = md.binding.thrownExceptions[i];
               if (exceptionBinding != null) {
                  exceptionBinding = (ReferenceBinding)exceptionBinding.erasure();
               }

               compatible = false;

               for(k = 0; k < maxRef && !compatible; ++k) {
                  if (typeReferences[k] != null) {
                     exceptionBinding = typeReferences[k].resolvedType;
                     if (TypeBinding.equalsEquals(exceptionBinding, exceptionBinding)) {
                        compatible = true;
                        typeReferences[k] = null;
                     }
                  }
               }

               if (!compatible && reportMissing && exceptionBinding != null && exceptionBinding.isValidBinding()) {
                  for(k = i; k < thrownExceptionLength && TypeBinding.notEquals(exceptionBinding, md.thrownExceptions[k].resolvedType); ++k) {
                  }

                  if (k < thrownExceptionLength) {
                     methScope.problemReporter().javadocMissingThrowsTag(md.thrownExceptions[k], md.binding.modifiers);
                  }
               }
            }

            for(i = 0; i < maxRef; ++i) {
               typeRef = typeReferences[i];
               if (typeRef != null) {
                  compatible = false;

                  for(k = 0; k < thrownExceptionLength && !compatible; ++k) {
                     exceptionBinding = md.thrownExceptions[k].resolvedType;
                     if (exceptionBinding != null) {
                        compatible = typeRef.resolvedType.isCompatibleWith(exceptionBinding);
                     }
                  }

                  if (!compatible && !typeRef.resolvedType.isUncheckedException(false)) {
                     methScope.problemReporter().javadocInvalidThrowsClassName(typeRef, md.binding.modifiers);
                  }
               }
            }
         }

      }
   }

   private void verifyTypeReference(Expression reference, Expression typeReference, Scope scope, boolean source15, ReferenceBinding resolvedType, int modifiers) {
      if (resolvedType.isValidBinding()) {
         int scopeModifiers = -1;
         if (!this.canBeSeen(scope.problemReporter().options.reportInvalidJavadocTagsVisibility, modifiers)) {
            scope.problemReporter().javadocHiddenReference(typeReference.sourceStart, reference.sourceEnd, scope, modifiers);
            return;
         }

         if (reference != typeReference && !this.canBeSeen(scope.problemReporter().options.reportInvalidJavadocTagsVisibility, resolvedType.modifiers)) {
            scope.problemReporter().javadocHiddenReference(typeReference.sourceStart, typeReference.sourceEnd, scope, resolvedType.modifiers);
            return;
         }

         if (resolvedType.isMemberType()) {
            ReferenceBinding topLevelType = resolvedType;
            int packageLength = resolvedType.fPackage.compoundName.length;
            int depth = resolvedType.depth();
            int idx = depth + packageLength;
            char[][] computedCompoundName = new char[idx + 1][];

            for(computedCompoundName[idx] = resolvedType.sourceName; topLevelType.enclosingType() != null; computedCompoundName[idx] = topLevelType.sourceName) {
               topLevelType = topLevelType.enclosingType();
               --idx;
            }

            int i = packageLength;

            while(true) {
               --i;
               if (i < 0) {
                  ClassScope topLevelScope = scope.classScope();
                  int i;
                  if (topLevelScope.parent.kind != 4 || !CharOperation.equals(topLevelType.sourceName, topLevelScope.referenceContext.name)) {
                     topLevelScope = topLevelScope.outerMostClassScope();
                     if (typeReference instanceof JavadocSingleTypeReference && (!source15 && depth == 1 || TypeBinding.notEquals(topLevelType, topLevelScope.referenceContext.binding))) {
                        boolean hasValidImport = false;
                        if (!source15) {
                           if (scopeModifiers == -1) {
                              scopeModifiers = scope.getDeclarationModifiers();
                           }

                           scope.problemReporter().javadocInvalidMemberTypeQualification(typeReference.sourceStart, typeReference.sourceEnd, scopeModifiers);
                           return;
                        }

                        CompilationUnitScope unitScope = topLevelScope.compilationUnitScope();
                        ImportBinding[] imports = unitScope.imports;
                        i = imports == null ? 0 : imports.length;

                        label123:
                        for(int i = 0; i < i; ++i) {
                           char[][] compoundName = imports[i].compoundName;
                           int compoundNameLength = compoundName.length;
                           if (imports[i].onDemand && compoundNameLength == computedCompoundName.length - 1 || compoundNameLength == computedCompoundName.length) {
                              int j = compoundNameLength;

                              do {
                                 --j;
                                 if (j < 0 || !CharOperation.equals(imports[i].compoundName[j], computedCompoundName[j])) {
                                    continue label123;
                                 }
                              } while(j != 0);

                              hasValidImport = true;
                              ImportReference importReference = imports[i].reference;
                              if (importReference != null) {
                                 importReference.bits |= 2;
                              }
                              break;
                           }
                        }

                        if (!hasValidImport) {
                           if (scopeModifiers == -1) {
                              scopeModifiers = scope.getDeclarationModifiers();
                           }

                           scope.problemReporter().javadocInvalidMemberTypeQualification(typeReference.sourceStart, typeReference.sourceEnd, scopeModifiers);
                        }
                     }
                  }

                  if (typeReference instanceof JavadocQualifiedTypeReference && !scope.isDefinedInSameUnit(resolvedType)) {
                     char[][] typeRefName = ((JavadocQualifiedTypeReference)typeReference).getTypeName();
                     int skipLength = 0;
                     if (topLevelScope.getCurrentPackage() == resolvedType.getPackage() && typeRefName.length < computedCompoundName.length) {
                        skipLength = resolvedType.fPackage.compoundName.length;
                     }

                     boolean valid = true;
                     if (typeRefName.length == computedCompoundName.length - skipLength) {
                        for(i = 0; i < typeRefName.length; ++i) {
                           if (!CharOperation.equals(typeRefName[i], computedCompoundName[i + skipLength])) {
                              valid = false;
                              break;
                           }
                        }
                     } else {
                        valid = false;
                     }

                     if (!valid) {
                        if (scopeModifiers == -1) {
                           scopeModifiers = scope.getDeclarationModifiers();
                        }

                        scope.problemReporter().javadocInvalidMemberTypeQualification(typeReference.sourceStart, typeReference.sourceEnd, scopeModifiers);
                        return;
                     }
                  }
                  break;
               }

               --idx;
               computedCompoundName[idx] = topLevelType.fPackage.compoundName[i];
            }
         }

         if (scope.referenceCompilationUnit().isPackageInfo() && typeReference instanceof JavadocSingleTypeReference && resolvedType.fPackage.compoundName.length > 0) {
            scope.problemReporter().javadocInvalidReference(typeReference.sourceStart, typeReference.sourceEnd);
            return;
         }
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int length;
         if (this.paramReferences != null) {
            i = 0;

            for(length = this.paramReferences.length; i < length; ++i) {
               this.paramReferences[i].traverse(visitor, scope);
            }
         }

         if (this.paramTypeParameters != null) {
            i = 0;

            for(length = this.paramTypeParameters.length; i < length; ++i) {
               this.paramTypeParameters[i].traverse(visitor, scope);
            }
         }

         if (this.returnStatement != null) {
            this.returnStatement.traverse(visitor, scope);
         }

         if (this.exceptionReferences != null) {
            i = 0;

            for(length = this.exceptionReferences.length; i < length; ++i) {
               this.exceptionReferences[i].traverse(visitor, scope);
            }
         }

         if (this.seeReferences != null) {
            i = 0;

            for(length = this.seeReferences.length; i < length; ++i) {
               this.seeReferences[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         int i;
         int length;
         if (this.paramReferences != null) {
            i = 0;

            for(length = this.paramReferences.length; i < length; ++i) {
               this.paramReferences[i].traverse(visitor, scope);
            }
         }

         if (this.paramTypeParameters != null) {
            i = 0;

            for(length = this.paramTypeParameters.length; i < length; ++i) {
               this.paramTypeParameters[i].traverse(visitor, scope);
            }
         }

         if (this.returnStatement != null) {
            this.returnStatement.traverse(visitor, scope);
         }

         if (this.exceptionReferences != null) {
            i = 0;

            for(length = this.exceptionReferences.length; i < length; ++i) {
               this.exceptionReferences[i].traverse(visitor, scope);
            }
         }

         if (this.seeReferences != null) {
            i = 0;

            for(length = this.seeReferences.length; i < length; ++i) {
               this.seeReferences[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }
}
