package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceContext18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.RawTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;

public class ExplicitConstructorCall extends Statement implements Invocation {
   public Expression[] arguments;
   public Expression qualification;
   public MethodBinding binding;
   MethodBinding syntheticAccessor;
   public int accessMode;
   public TypeReference[] typeArguments;
   public TypeBinding[] genericTypeArguments;
   public static final int ImplicitSuper = 1;
   public static final int Super = 2;
   public static final int This = 3;
   public VariableBinding[][] implicitArguments;
   public int typeArgumentsSourceStart;

   public ExplicitConstructorCall(int accessMode) {
      this.accessMode = accessMode;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      Object var8;
      try {
         ((MethodScope)currentScope).isConstructorCall = true;
         if (this.qualification != null) {
            flowInfo = this.qualification.analyseCode(currentScope, flowContext, (FlowInfo)flowInfo).unconditionalInits();
         }

         if (this.arguments != null) {
            boolean analyseResources = currentScope.compilerOptions().analyseResourceLeaks;
            int i = 0;

            for(int max = this.arguments.length; i < max; ++i) {
               flowInfo = this.arguments[i].analyseCode(currentScope, flowContext, (FlowInfo)flowInfo).unconditionalInits();
               if (analyseResources) {
                  flowInfo = FakedTrackingVariable.markPassedToOutside(currentScope, this.arguments[i], (FlowInfo)flowInfo, flowContext, false);
               }

               this.arguments[i].checkNPEbyUnboxing(currentScope, flowContext, (FlowInfo)flowInfo);
            }

            this.analyseArguments(currentScope, flowContext, (FlowInfo)flowInfo, this.binding, this.arguments);
         }

         ReferenceBinding[] thrownExceptions;
         if ((thrownExceptions = this.binding.thrownExceptions) != Binding.NO_EXCEPTIONS) {
            if ((this.bits & 65536) != 0 && this.genericTypeArguments == null) {
               thrownExceptions = currentScope.environment().convertToRawTypes(this.binding.thrownExceptions, true, true);
            }

            flowContext.checkExceptionHandlers((TypeBinding[])thrownExceptions, (ASTNode)(this.accessMode == 1 ? (ASTNode)currentScope.methodScope().referenceContext : this), (FlowInfo)flowInfo, currentScope);
         }

         this.manageEnclosingInstanceAccessIfNecessary(currentScope, (FlowInfo)flowInfo);
         this.manageSyntheticAccessIfNecessary(currentScope, (FlowInfo)flowInfo);
         var8 = flowInfo;
      } finally {
         ((MethodScope)currentScope).isConstructorCall = false;
      }

      return (FlowInfo)var8;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         try {
            ((MethodScope)currentScope).isConstructorCall = true;
            int pc = codeStream.position;
            codeStream.aload_0();
            MethodBinding codegenBinding = this.binding.original();
            ReferenceBinding targetType = codegenBinding.declaringClass;
            if (targetType.erasure().id == 41 || targetType.isEnum()) {
               codeStream.aload_1();
               codeStream.iload_2();
            }

            if (targetType.isNestedType()) {
               codeStream.generateSyntheticEnclosingInstanceValues(currentScope, targetType, (this.bits & 8192) != 0 ? null : this.qualification, this);
            }

            this.generateArguments(this.binding, this.arguments, currentScope, codeStream);
            if (targetType.isNestedType()) {
               codeStream.generateSyntheticOuterArgumentValues(currentScope, targetType, this);
            }

            if (this.syntheticAccessor == null) {
               codeStream.invoke((byte)-73, codegenBinding, (TypeBinding)null, this.typeArguments);
            } else {
               int i = 0;

               for(int max = this.syntheticAccessor.parameters.length - codegenBinding.parameters.length; i < max; ++i) {
                  codeStream.aconst_null();
               }

               codeStream.invoke((byte)-73, this.syntheticAccessor, (TypeBinding)null, this.typeArguments);
            }

            codeStream.recordPositionsFrom(pc, this.sourceStart);
         } finally {
            ((MethodScope)currentScope).isConstructorCall = false;
         }

      }
   }

   public TypeBinding[] genericTypeArguments() {
      return this.genericTypeArguments;
   }

   public boolean isImplicitSuper() {
      return this.accessMode == 1;
   }

   public boolean isSuperAccess() {
      return this.accessMode != 3;
   }

   public boolean isTypeAccess() {
      return true;
   }

   void manageEnclosingInstanceAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo) {
      ReferenceBinding superTypeErasure = (ReferenceBinding)this.binding.declaringClass.erasure();
      if ((flowInfo.tagBits & 1) == 0 && superTypeErasure.isNestedType() && currentScope.enclosingSourceType().isLocalType()) {
         if (superTypeErasure.isLocalType()) {
            ((LocalTypeBinding)superTypeErasure).addInnerEmulationDependent(currentScope, this.qualification != null);
         } else {
            currentScope.propagateInnerEmulation(superTypeErasure, this.qualification != null);
         }
      }

   }

   public void manageSyntheticAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         MethodBinding codegenBinding = this.binding.original();
         if (this.binding.isPrivate() && !currentScope.enclosingSourceType().isNestmateOf(this.binding.declaringClass) && this.accessMode != 3) {
            ReferenceBinding declaringClass = codegenBinding.declaringClass;
            if ((declaringClass.tagBits & 16L) != 0L && currentScope.compilerOptions().complianceLevel >= 3145728L) {
               codegenBinding.tagBits |= 512L;
            } else {
               this.syntheticAccessor = ((SourceTypeBinding)declaringClass).addSyntheticMethod(codegenBinding, this.isSuperAccess());
               currentScope.problemReporter().needToEmulateMethodAccess(codegenBinding, this);
            }
         }
      }

   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      printIndent(indent, output);
      if (this.qualification != null) {
         this.qualification.printExpression(0, output).append('.');
      }

      int i;
      if (this.typeArguments != null) {
         output.append('<');
         i = this.typeArguments.length - 1;

         for(int j = 0; j < i; ++j) {
            this.typeArguments[j].print(0, output);
            output.append(", ");
         }

         this.typeArguments[i].print(0, output);
         output.append('>');
      }

      if (this.accessMode == 3) {
         output.append("this(");
      } else {
         output.append("super(");
      }

      if (this.arguments != null) {
         for(i = 0; i < this.arguments.length; ++i) {
            if (i > 0) {
               output.append(", ");
            }

            this.arguments[i].printExpression(0, output);
         }
      }

      return output.append(");");
   }

   public void resolve(BlockScope scope) {
      MethodScope methodScope = scope.methodScope();

      try {
         AbstractMethodDeclaration methodDeclaration = methodScope.referenceMethod();
         if (methodDeclaration == null || !methodDeclaration.isConstructor() || ((ConstructorDeclaration)methodDeclaration).constructorCall != this) {
            scope.problemReporter().invalidExplicitConstructorCall(this);
            if (this.qualification != null) {
               this.qualification.resolveType(scope);
            }

            int i;
            int max;
            if (this.typeArguments != null) {
               i = 0;

               for(max = this.typeArguments.length; i < max; ++i) {
                  this.typeArguments[i].resolveType(scope, true);
               }
            }

            if (this.arguments == null) {
               return;
            }

            i = 0;

            for(max = this.arguments.length; i < max; ++i) {
               this.arguments[i].resolveType(scope);
            }

            return;
         }

         methodScope.isConstructorCall = true;
         ReferenceBinding receiverType = scope.enclosingReceiverType();
         boolean rcvHasError = false;
         if (this.accessMode != 3) {
            receiverType = receiverType.superclass();
            TypeReference superclassRef = scope.referenceType().superclass;
            if (superclassRef != null && superclassRef.resolvedType != null && !superclassRef.resolvedType.isValidBinding()) {
               rcvHasError = true;
            }
         }

         if (receiverType != null) {
            if (this.accessMode == 2 && receiverType.erasure().id == 41) {
               scope.problemReporter().cannotInvokeSuperConstructorInEnum(this, methodScope.referenceMethod().binding);
            }

            if (this.qualification != null) {
               if (this.accessMode != 2) {
                  scope.problemReporter().unnecessaryEnclosingInstanceSpecification(this.qualification, receiverType);
               }

               if (!rcvHasError) {
                  ReferenceBinding enclosingType = receiverType.enclosingType();
                  if (enclosingType == null) {
                     scope.problemReporter().unnecessaryEnclosingInstanceSpecification(this.qualification, receiverType);
                     this.bits |= 8192;
                  } else {
                     TypeBinding qTb = this.qualification.resolveTypeExpecting(scope, enclosingType);
                     this.qualification.computeConversion(scope, qTb, qTb);
                  }
               }
            }
         }

         long sourceLevel = scope.compilerOptions().sourceLevel;
         int length;
         if (this.typeArguments != null) {
            boolean argHasError = sourceLevel < 3211264L;
            int length = this.typeArguments.length;
            this.genericTypeArguments = new TypeBinding[length];

            int i;
            for(i = 0; i < length; ++i) {
               TypeReference typeReference = this.typeArguments[i];
               if ((this.genericTypeArguments[i] = typeReference.resolveType(scope, true)) == null) {
                  argHasError = true;
               }

               if (argHasError && typeReference instanceof Wildcard) {
                  scope.problemReporter().illegalUsageOfWildcard(typeReference);
               }
            }

            if (argHasError) {
               if (this.arguments == null) {
                  return;
               }

               i = 0;

               for(length = this.arguments.length; i < length; ++i) {
                  this.arguments[i].resolveType(scope);
               }

               return;
            }
         }

         TypeBinding[] argumentTypes = Binding.NO_PARAMETERS;
         boolean argsContainCast = false;
         if (this.arguments == null) {
            if (receiverType.erasure().id == 41) {
               argumentTypes = new TypeBinding[]{scope.getJavaLangString(), TypeBinding.INT};
            }
         } else {
            boolean argHasError = false;
            length = this.arguments.length;
            argumentTypes = new TypeBinding[length];

            for(int i = 0; i < length; ++i) {
               Expression argument = this.arguments[i];
               if (argument instanceof CastExpression) {
                  argument.bits |= 32;
                  argsContainCast = true;
               }

               argument.setExpressionContext(ExpressionContext.INVOCATION_CONTEXT);
               if ((argumentTypes[i] = argument.resolveType(scope)) == null) {
                  argHasError = true;
               }
            }

            if (argHasError) {
               if (receiverType == null) {
                  return;
               }

               TypeBinding[] pseudoArgs = new TypeBinding[length];
               int i = length;

               while(true) {
                  --i;
                  if (i < 0) {
                     this.binding = scope.findMethod(receiverType, TypeConstants.INIT, pseudoArgs, this, false);
                     if (this.binding != null && !this.binding.isValidBinding()) {
                        MethodBinding closestMatch = ((ProblemMethodBinding)this.binding).closestMatch;
                        if (closestMatch != null) {
                           if (((MethodBinding)closestMatch).original().typeVariables != Binding.NO_TYPE_VARIABLES) {
                              closestMatch = scope.environment().createParameterizedGenericMethod(((MethodBinding)closestMatch).original(), (RawTypeBinding)null);
                           }

                           this.binding = (MethodBinding)closestMatch;
                           MethodBinding closestMatchOriginal = ((MethodBinding)closestMatch).original();
                           if (closestMatchOriginal.isOrEnclosedByPrivateType() && !scope.isDefinedInMethod(closestMatchOriginal)) {
                              closestMatchOriginal.modifiers |= 134217728;
                           }

                           return;
                        }
                     }

                     return;
                  }

                  pseudoArgs[i] = (TypeBinding)(argumentTypes[i] == null ? TypeBinding.NULL : argumentTypes[i]);
               }
            }
         }

         if (receiverType != null) {
            this.binding = this.findConstructorBinding(scope, this, receiverType, argumentTypes);
            if (this.binding.isValidBinding()) {
               if ((this.binding.tagBits & 128L) != 0L && !methodScope.enclosingSourceType().isAnonymousType()) {
                  scope.problemReporter().missingTypeInConstructor(this, this.binding);
               }

               if (this.isMethodUseDeprecated(this.binding, scope, this.accessMode != 1, this)) {
                  scope.problemReporter().deprecatedMethod(this.binding, this);
               }

               if (checkInvocationArguments(scope, (Expression)null, receiverType, this.binding, this.arguments, argumentTypes, argsContainCast, this)) {
                  this.bits |= 65536;
               }

               if (this.binding.isOrEnclosedByPrivateType()) {
                  MethodBinding var10000 = this.binding.original();
                  var10000.modifiers |= 134217728;
               }

               if (this.typeArguments != null && this.binding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
                  scope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.genericTypeArguments, this.typeArguments);
                  return;
               }
            } else {
               if (this.binding.declaringClass == null) {
                  this.binding.declaringClass = receiverType;
               }

               if (rcvHasError) {
                  return;
               }

               scope.problemReporter().invalidConstructor(this, this.binding);
            }

            return;
         }
      } finally {
         methodScope.isConstructorCall = false;
      }

   }

   public void setActualReceiverType(ReferenceBinding receiverType) {
   }

   public void setDepth(int depth) {
   }

   public void setFieldIndex(int depth) {
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.qualification != null) {
            this.qualification.traverse(visitor, scope);
         }

         int i;
         int argumentLength;
         if (this.typeArguments != null) {
            i = 0;

            for(argumentLength = this.typeArguments.length; i < argumentLength; ++i) {
               this.typeArguments[i].traverse(visitor, scope);
            }
         }

         if (this.arguments != null) {
            i = 0;

            for(argumentLength = this.arguments.length; i < argumentLength; ++i) {
               this.arguments[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public MethodBinding binding() {
      return this.binding;
   }

   public void registerInferenceContext(ParameterizedGenericMethodBinding method, InferenceContext18 infCtx18) {
   }

   public void registerResult(TypeBinding targetType, MethodBinding method) {
   }

   public InferenceContext18 getInferenceContext(ParameterizedMethodBinding method) {
      return null;
   }

   public void cleanUpInferenceContexts() {
   }

   public Expression[] arguments() {
      return this.arguments;
   }

   public InferenceContext18 freshInferenceContext(Scope scope) {
      return new InferenceContext18(scope, this.arguments, this, (InferenceContext18)null);
   }
}
