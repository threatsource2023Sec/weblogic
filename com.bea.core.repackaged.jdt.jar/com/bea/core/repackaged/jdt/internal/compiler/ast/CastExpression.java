package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IrritantSet;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceContext18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InvocationSite;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PolymorphicMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class CastExpression extends Expression {
   public Expression expression;
   public TypeReference type;
   public TypeBinding expectedType;

   public CastExpression(Expression expression, TypeReference type) {
      this.expression = expression;
      this.type = type;
      type.bits |= 1073741824;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      FlowInfo result = this.expression.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits();
      this.expression.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
      flowContext.recordAbruptExit();
      return result;
   }

   public static void checkNeedForAssignedCast(BlockScope scope, TypeBinding expectedType, CastExpression rhs) {
      CompilerOptions compilerOptions = scope.compilerOptions();
      if (compilerOptions.getSeverity(67108864) != 256) {
         TypeBinding castedExpressionType = rhs.expression.resolvedType;
         if (castedExpressionType != null && !rhs.resolvedType.isBaseType()) {
            if (castedExpressionType.isCompatibleWith(expectedType, scope)) {
               if (scope.environment().usesNullTypeAnnotations() && NullAnnotationMatching.analyse(expectedType, castedExpressionType, -1).isAnyMismatch()) {
                  return;
               }

               scope.problemReporter().unnecessaryCast(rhs);
            }

         }
      }
   }

   public static void checkNeedForCastCast(BlockScope scope, CastExpression enclosingCast) {
      if (scope.compilerOptions().getSeverity(67108864) != 256) {
         CastExpression nestedCast = (CastExpression)enclosingCast.expression;
         if ((nestedCast.bits & 16384) != 0) {
            CastExpression alternateCast = new CastExpression((Expression)null, enclosingCast.type);
            alternateCast.resolvedType = enclosingCast.resolvedType;
            if (alternateCast.checkCastTypesCompatibility(scope, enclosingCast.resolvedType, nestedCast.expression.resolvedType, (Expression)null)) {
               scope.problemReporter().unnecessaryCast(nestedCast);
            }
         }
      }
   }

   public static void checkNeedForEnclosingInstanceCast(BlockScope scope, Expression enclosingInstance, TypeBinding enclosingInstanceType, TypeBinding memberType) {
      if (scope.compilerOptions().getSeverity(67108864) != 256) {
         TypeBinding castedExpressionType = ((CastExpression)enclosingInstance).expression.resolvedType;
         if (castedExpressionType != null) {
            if (TypeBinding.equalsEquals(castedExpressionType, enclosingInstanceType)) {
               scope.problemReporter().unnecessaryCast((CastExpression)enclosingInstance);
            } else {
               if (castedExpressionType == TypeBinding.NULL) {
                  return;
               }

               if (castedExpressionType.isBaseType() || castedExpressionType.isArrayType()) {
                  return;
               }

               if (TypeBinding.equalsEquals(memberType, scope.getMemberType(memberType.sourceName(), (ReferenceBinding)castedExpressionType))) {
                  scope.problemReporter().unnecessaryCast((CastExpression)enclosingInstance);
               }
            }

         }
      }
   }

   public static void checkNeedForArgumentCast(BlockScope scope, int operator, int operatorSignature, Expression expression, int expressionTypeId) {
      if (scope.compilerOptions().getSeverity(67108864) != 256) {
         if ((expression.bits & 16384) != 0 || !expression.resolvedType.isBaseType()) {
            TypeBinding alternateLeftType = ((CastExpression)expression).expression.resolvedType;
            if (alternateLeftType != null) {
               if (alternateLeftType.id == expressionTypeId) {
                  scope.problemReporter().unnecessaryCast((CastExpression)expression);
               }
            }
         }
      }
   }

   public static void checkNeedForArgumentCasts(BlockScope scope, Expression receiver, TypeBinding receiverType, MethodBinding binding, Expression[] arguments, TypeBinding[] argumentTypes, InvocationSite invocationSite) {
      if (scope.compilerOptions().getSeverity(67108864) != 256) {
         int length = argumentTypes.length;
         TypeBinding[] rawArgumentTypes = argumentTypes;

         for(int i = 0; i < length; ++i) {
            Expression argument = arguments[i];
            if (argument instanceof CastExpression && ((argument.bits & 16384) != 0 || !argument.resolvedType.isBaseType())) {
               TypeBinding castedExpressionType = ((CastExpression)argument).expression.resolvedType;
               if (castedExpressionType == null) {
                  return;
               }

               if (TypeBinding.equalsEquals(castedExpressionType, argumentTypes[i])) {
                  scope.problemReporter().unnecessaryCast((CastExpression)argument);
               } else if (castedExpressionType != TypeBinding.NULL && (argument.implicitConversion & 512) == 0) {
                  if (rawArgumentTypes == argumentTypes) {
                     System.arraycopy(rawArgumentTypes, 0, rawArgumentTypes = new TypeBinding[length], 0, length);
                  }

                  rawArgumentTypes[i] = castedExpressionType;
               }
            }
         }

         if (rawArgumentTypes != argumentTypes) {
            checkAlternateBinding(scope, receiver, receiverType, binding, arguments, argumentTypes, rawArgumentTypes, invocationSite);
         }

      }
   }

   public static void checkNeedForArgumentCasts(BlockScope scope, int operator, int operatorSignature, Expression left, int leftTypeId, boolean leftIsCast, Expression right, int rightTypeId, boolean rightIsCast) {
      if (scope.compilerOptions().getSeverity(67108864) != 256) {
         int alternateLeftTypeId = leftTypeId;
         if (leftIsCast) {
            if ((left.bits & 16384) == 0 && left.resolvedType.isBaseType()) {
               leftIsCast = false;
            } else {
               TypeBinding alternateLeftType = ((CastExpression)left).expression.resolvedType;
               if (alternateLeftType == null) {
                  return;
               }

               if ((alternateLeftTypeId = alternateLeftType.id) != leftTypeId && scope.environment().computeBoxingType(alternateLeftType).id != leftTypeId) {
                  if (alternateLeftTypeId == 12) {
                     alternateLeftTypeId = leftTypeId;
                     leftIsCast = false;
                  }
               } else {
                  scope.problemReporter().unnecessaryCast((CastExpression)left);
                  leftIsCast = false;
               }
            }
         }

         int alternateRightTypeId = rightTypeId;
         if (rightIsCast) {
            if ((right.bits & 16384) == 0 && right.resolvedType.isBaseType()) {
               rightIsCast = false;
            } else {
               TypeBinding alternateRightType = ((CastExpression)right).expression.resolvedType;
               if (alternateRightType == null) {
                  return;
               }

               if ((alternateRightTypeId = alternateRightType.id) != rightTypeId && scope.environment().computeBoxingType(alternateRightType).id != rightTypeId) {
                  if (alternateRightTypeId == 12) {
                     alternateRightTypeId = rightTypeId;
                     rightIsCast = false;
                  }
               } else {
                  scope.problemReporter().unnecessaryCast((CastExpression)right);
                  rightIsCast = false;
               }
            }
         }

         if (leftIsCast || rightIsCast) {
            if (alternateLeftTypeId > 15 || alternateRightTypeId > 15) {
               if (alternateLeftTypeId == 11) {
                  alternateRightTypeId = 1;
               } else {
                  if (alternateRightTypeId != 11) {
                     return;
                  }

                  alternateLeftTypeId = 1;
               }
            }

            int alternateOperatorSignature = OperatorExpression.OperatorSignatures[operator][(alternateLeftTypeId << 4) + alternateRightTypeId];
            if ((operatorSignature & 986895) == (alternateOperatorSignature & 986895)) {
               if (leftIsCast) {
                  scope.problemReporter().unnecessaryCast((CastExpression)left);
               }

               if (rightIsCast) {
                  scope.problemReporter().unnecessaryCast((CastExpression)right);
               }
            }
         }

      }
   }

   public boolean checkNPE(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, int ttlForFieldCheck) {
      if ((this.resolvedType.tagBits & 72057594037927936L) != 0L) {
         return true;
      } else {
         this.checkNPEbyUnboxing(scope, flowContext, flowInfo);
         return this.expression.checkNPE(scope, flowContext, flowInfo, ttlForFieldCheck);
      }
   }

   private static void checkAlternateBinding(BlockScope scope, Expression receiver, TypeBinding receiverType, MethodBinding binding, Expression[] arguments, TypeBinding[] originalArgumentTypes, TypeBinding[] alternateArgumentTypes, final InvocationSite invocationSite) {
      InvocationSite fakeInvocationSite = new InvocationSite() {
         public TypeBinding[] genericTypeArguments() {
            return null;
         }

         public boolean isSuperAccess() {
            return invocationSite.isSuperAccess();
         }

         public boolean isTypeAccess() {
            return invocationSite.isTypeAccess();
         }

         public void setActualReceiverType(ReferenceBinding actualReceiverType) {
         }

         public void setDepth(int depth) {
         }

         public void setFieldIndex(int depth) {
         }

         public int sourceStart() {
            return 0;
         }

         public int sourceEnd() {
            return 0;
         }

         public TypeBinding invocationTargetType() {
            return invocationSite.invocationTargetType();
         }

         public boolean receiverIsImplicitThis() {
            return invocationSite.receiverIsImplicitThis();
         }

         public InferenceContext18 freshInferenceContext(Scope someScope) {
            return invocationSite.freshInferenceContext(someScope);
         }

         public ExpressionContext getExpressionContext() {
            return invocationSite.getExpressionContext();
         }

         public boolean isQualifiedSuper() {
            return invocationSite.isQualifiedSuper();
         }

         public boolean checkingPotentialCompatibility() {
            return false;
         }

         public void acceptPotentiallyCompatibleMethods(MethodBinding[] methods) {
         }
      };
      MethodBinding bindingIfNoCast;
      if (binding.isConstructor()) {
         bindingIfNoCast = scope.getConstructor((ReferenceBinding)receiverType, alternateArgumentTypes, fakeInvocationSite);
      } else {
         bindingIfNoCast = receiver.isImplicitThis() ? scope.getImplicitMethod(binding.selector, alternateArgumentTypes, fakeInvocationSite) : scope.getMethod(receiverType, binding.selector, alternateArgumentTypes, fakeInvocationSite);
      }

      if (bindingIfNoCast == binding) {
         int argumentLength = originalArgumentTypes.length;
         int i;
         if (binding.isVarargs()) {
            i = binding.parameters.length;
            if (i == argumentLength) {
               int varargsIndex = i - 1;
               ArrayBinding varargsType = (ArrayBinding)binding.parameters[varargsIndex];
               TypeBinding lastArgType = alternateArgumentTypes[varargsIndex];
               if (varargsType.dimensions != lastArgType.dimensions()) {
                  return;
               }

               if (lastArgType.isCompatibleWith(varargsType.elementsType()) && lastArgType.isCompatibleWith(varargsType)) {
                  return;
               }
            }
         }

         for(i = 0; i < argumentLength; ++i) {
            if (TypeBinding.notEquals(originalArgumentTypes[i], alternateArgumentTypes[i]) && !preventsUnlikelyTypeWarning(originalArgumentTypes[i], alternateArgumentTypes[i], receiverType, binding, scope)) {
               scope.problemReporter().unnecessaryCast((CastExpression)arguments[i]);
            }
         }
      }

   }

   private static boolean preventsUnlikelyTypeWarning(TypeBinding castedType, TypeBinding uncastedType, TypeBinding receiverType, MethodBinding binding, BlockScope scope) {
      if (!scope.compilerOptions().isAnyEnabled(IrritantSet.UNLIKELY_ARGUMENT_TYPE)) {
         return false;
      } else if (!binding.isStatic() && binding.parameters.length == 1) {
         UnlikelyArgumentCheck argumentChecks = UnlikelyArgumentCheck.determineCheckForNonStaticSingleArgumentMethod(uncastedType, scope, binding.selector, receiverType, binding.parameters);
         if (argumentChecks != null && argumentChecks.isDangerous(scope)) {
            argumentChecks = UnlikelyArgumentCheck.determineCheckForNonStaticSingleArgumentMethod(castedType, scope, binding.selector, receiverType, binding.parameters);
            if (argumentChecks == null || !argumentChecks.isDangerous(scope)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean checkUnsafeCast(Scope scope, TypeBinding castType, TypeBinding expressionType, TypeBinding match, boolean isNarrowing) {
      if (TypeBinding.equalsEquals(match, castType)) {
         if (!isNarrowing && TypeBinding.equalsEquals(match, this.resolvedType.leafComponentType()) && (!expressionType.isParameterizedType() || !expressionType.isProvablyDistinct(castType))) {
            this.tagAsUnnecessaryCast(scope, castType);
         }

         return true;
      } else {
         if (match != null) {
            label124: {
               if (isNarrowing) {
                  if (!match.isProvablyDistinct(expressionType)) {
                     break label124;
                  }
               } else if (!castType.isProvablyDistinct(match)) {
                  break label124;
               }

               return false;
            }
         }

         switch (castType.kind()) {
            case 68:
               TypeBinding leafType = castType.leafComponentType();
               if (!isNarrowing || leafType.isReifiable() && !leafType.isTypeVariable()) {
                  break;
               }

               this.bits |= 128;
               return true;
            case 260:
               if (!castType.isReifiable()) {
                  if (match == null) {
                     this.bits |= 128;
                     return true;
                  }

                  switch (match.kind()) {
                     case 260:
                        if (isNarrowing) {
                           if (!expressionType.isRawType() && expressionType.isEquivalentTo(match)) {
                              ParameterizedTypeBinding paramCastType = (ParameterizedTypeBinding)castType;
                              ParameterizedTypeBinding paramMatch = (ParameterizedTypeBinding)match;
                              TypeBinding[] castArguments = paramCastType.arguments;
                              int length = castArguments == null ? 0 : castArguments.length;
                              if (paramMatch.arguments != null && length <= paramMatch.arguments.length) {
                                 if ((paramCastType.tagBits & 1610612736L) != 0L) {
                                    int i = 0;

                                    while(i < length) {
                                       switch (castArguments[i].kind()) {
                                          case 516:
                                          case 4100:
                                             TypeBinding[] alternateArguments;
                                             System.arraycopy(paramCastType.arguments, 0, alternateArguments = new TypeBinding[length], 0, length);
                                             alternateArguments[i] = scope.getJavaLangObject();
                                             LookupEnvironment environment = scope.environment();
                                             ParameterizedTypeBinding alternateCastType = environment.createParameterizedType((ReferenceBinding)castType.erasure(), alternateArguments, castType.enclosingType());
                                             if (TypeBinding.equalsEquals(alternateCastType.findSuperTypeOriginatingFrom(expressionType), match)) {
                                                this.bits |= 128;
                                                return true;
                                             }
                                          default:
                                             ++i;
                                       }
                                    }
                                 }
                              } else {
                                 this.bits |= 128;
                              }

                              return true;
                           }

                           this.bits |= 128;
                           return true;
                        }

                        if (!match.isEquivalentTo(castType)) {
                           this.bits |= 128;
                           return true;
                        }
                        break;
                     case 1028:
                        this.bits |= 128;
                        return true;
                     default:
                        if (isNarrowing) {
                           this.bits |= 128;
                           return true;
                        }
                  }
               }
               break;
            case 4100:
               this.bits |= 128;
               return true;
         }

         if (!isNarrowing && TypeBinding.equalsEquals(match, this.resolvedType.leafComponentType())) {
            this.tagAsUnnecessaryCast(scope, castType);
         }

         return true;
      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      boolean annotatedCast = (this.type.bits & 1048576) != 0;
      boolean needRuntimeCheckcast = (this.bits & 64) != 0;
      if (this.constant != Constant.NotAConstant) {
         if (valueRequired || needRuntimeCheckcast || annotatedCast) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
            if (needRuntimeCheckcast || annotatedCast) {
               codeStream.checkcast(this.type, this.resolvedType, pc);
            }

            if (!valueRequired) {
               codeStream.pop();
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else {
         this.expression.generateCode(currentScope, codeStream, annotatedCast || valueRequired || needRuntimeCheckcast);
         if (annotatedCast || needRuntimeCheckcast && TypeBinding.notEquals(this.expression.postConversionType(currentScope), this.resolvedType.erasure())) {
            codeStream.checkcast(this.type, this.resolvedType, pc);
         }

         if (valueRequired) {
            codeStream.generateImplicitConversion(this.implicitConversion);
         } else if (annotatedCast || needRuntimeCheckcast) {
            switch (this.resolvedType.id) {
               case 7:
               case 8:
                  codeStream.pop2();
                  break;
               default:
                  codeStream.pop();
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public Expression innermostCastedExpression() {
      Expression current;
      for(current = this.expression; current instanceof CastExpression; current = ((CastExpression)current).expression) {
      }

      return current;
   }

   public LocalVariableBinding localVariableBinding() {
      return this.expression.localVariableBinding();
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      return (this.implicitConversion & 512) != 0 ? 4 : this.expression.nullStatus(flowInfo, flowContext);
   }

   public Constant optimizedBooleanConstant() {
      switch (this.resolvedType.id) {
         case 5:
         case 33:
            return this.expression.optimizedBooleanConstant();
         default:
            return Constant.NotAConstant;
      }
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      int parenthesesCount = (this.bits & 534773760) >> 21;
      String suffix = "";

      for(int i = 0; i < parenthesesCount; ++i) {
         output.append('(');
         suffix = suffix + ')';
      }

      output.append('(');
      this.type.print(0, output).append(") ");
      return this.expression.printExpression(0, output).append(suffix);
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      this.implicitConversion = 0;
      boolean exprContainCast = false;
      TypeBinding castType = this.resolvedType = this.type.resolveType(scope);
      if (scope.compilerOptions().sourceLevel >= 3407872L) {
         this.expression.setExpressionContext(ExpressionContext.CASTING_CONTEXT);
         if (this.expression instanceof FunctionalExpression) {
            this.expression.setExpectedType(this.resolvedType);
            this.bits |= 32;
         }
      }

      if (this.expression instanceof CastExpression) {
         Expression var10000 = this.expression;
         var10000.bits |= 32;
         exprContainCast = true;
      }

      TypeBinding expressionType = this.expression.resolveType(scope);
      if (this.expression instanceof MessageSend) {
         MessageSend messageSend = (MessageSend)this.expression;
         MethodBinding methodBinding = messageSend.binding;
         if (methodBinding != null && methodBinding.isPolymorphic()) {
            messageSend.binding = scope.environment().updatePolymorphicMethodReturnType((PolymorphicMethodBinding)methodBinding, castType);
            if (TypeBinding.notEquals(expressionType, castType)) {
               expressionType = castType;
               this.bits |= 32;
            }
         }
      }

      if (castType != null) {
         if (expressionType != null) {
            boolean nullAnnotationMismatch = scope.compilerOptions().isAnnotationBasedNullAnalysisEnabled && NullAnnotationMatching.analyse(castType, expressionType, -1).isAnyMismatch();
            boolean isLegal = this.checkCastTypesCompatibility(scope, castType, expressionType, this.expression);
            if (isLegal) {
               this.expression.computeConversion(scope, castType, expressionType);
               if ((this.bits & 128) != 0) {
                  if (scope.compilerOptions().reportUnavoidableGenericTypeProblems || !expressionType.isRawType() || !this.expression.forcedToBeRaw(scope.referenceContext())) {
                     scope.problemReporter().unsafeCast(this, scope);
                  }
               } else if (nullAnnotationMismatch) {
                  scope.problemReporter().unsafeNullnessCast(this, scope);
               } else {
                  if (castType.isRawType() && scope.compilerOptions().getSeverity(536936448) != 256) {
                     scope.problemReporter().rawTypeReference(this.type, castType);
                  }

                  if ((this.bits & 16416) == 16384 && !this.isIndirectlyUsed()) {
                     scope.problemReporter().unnecessaryCast(this);
                  }
               }
            } else {
               if ((castType.tagBits & 128L) == 0L) {
                  scope.problemReporter().typeCastError(this, castType, expressionType);
               }

               this.bits |= 32;
            }
         }

         this.resolvedType = castType.capture(scope, this.type.sourceStart, this.type.sourceEnd);
         if (exprContainCast) {
            checkNeedForCastCast(scope, this);
         }
      }

      return this.resolvedType;
   }

   public void setExpectedType(TypeBinding expectedType) {
      this.expectedType = expectedType;
   }

   private boolean isIndirectlyUsed() {
      if (this.expression instanceof MessageSend) {
         MethodBinding method = ((MessageSend)this.expression).binding;
         if (method instanceof ParameterizedGenericMethodBinding && ((ParameterizedGenericMethodBinding)method).inferredReturnType) {
            if (this.expectedType == null) {
               return true;
            }

            if (TypeBinding.notEquals(this.resolvedType, this.expectedType)) {
               return true;
            }
         }
      }

      return this.expectedType != null && this.resolvedType.isBaseType() && !this.resolvedType.isCompatibleWith(this.expectedType);
   }

   public void tagAsNeedCheckCast() {
      this.bits |= 64;
   }

   public void tagAsUnnecessaryCast(Scope scope, TypeBinding castType) {
      this.bits |= 16384;
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.type.traverse(visitor, blockScope);
         this.expression.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }
}
